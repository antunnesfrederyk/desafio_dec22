package br.com.frederykantunnes.challenge.service;

import br.com.frederykantunnes.challenge.dto.VoteMessage;
import br.com.frederykantunnes.challenge.model.SessionModel;
import br.com.frederykantunnes.challenge.model.VoteCountModel;
import br.com.frederykantunnes.challenge.repository.SessionRepository;
import br.com.frederykantunnes.challenge.repository.VoteRepository;
import br.com.frederykantunnes.challenge.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class VoteVerificationService {
    public static final String NEGATIVE_OPTION = "NÃO";
    public static final String POSITIVE_OPTION = "SIM";
    private final SessionRepository sessionRepository;
    private final VoteRepository voteRepository;
    private final Sinks.Many<Message<VoteMessage>> process = Sinks.many().unicast().onBackpressureBuffer();

    @Bean
    public Supplier<Flux<Message<VoteMessage>>> registerVote() {
        return process::asFlux;
    }

    @Scheduled(cron = "${schedule-verification-votes}")
    public void verificationSessions(){
        log.info("Started votes count");
        List<SessionModel> allByVotesCountedIsFalse = sessionRepository.findAllByVotesCountedIsFalse();
        log.info("Total sessions pending votes count: {}", (long) allByVotesCountedIsFalse.size());
        allByVotesCountedIsFalse.forEach(session->{
            if(finishedSessionTime(session)){
                log.info("Started votes count for session {}", JsonUtils.toJson(session));

                List<VoteCountModel> votesSession = voteRepository.countVotesBySession(session.getUuid());
                var totalPositiveVotes = countVotesByOption(votesSession, POSITIVE_OPTION);
                var totalNegativeVotes = countVotesByOption(votesSession, NEGATIVE_OPTION);

                VoteMessage message = VoteMessage.builder()
                        .uuidStave(session.getUuidStave())
                        .uuidSession(session.getUuid())
                        .totalNegativeVotes(totalNegativeVotes)
                        .totalPositiveVotes(totalPositiveVotes)
                        .startedSession(session.getCreatedAt())
                        .finishedSession(session.getCreatedAt().plusMinutes(session.getDurationInMinutes()))
                        .build();
                process.tryEmitNext(MessageBuilder.withPayload(message).setHeader(KafkaHeaders.MESSAGE_KEY, message.getUuidSession().getBytes(StandardCharsets.UTF_8)).build());
                session.setVotesCounted(true);
                this.sessionRepository.save(session);
            }
        });
        log.info("Finished votes count");
    }

    private static long countVotesByOption(List<VoteCountModel> votesSession, String option) {
        var positiveVotes = votesSession.stream().filter(vote -> vote.getVote().equalsIgnoreCase(option)).findAny();
        return positiveVotes.isPresent()?positiveVotes.get().getTotal():0;
    }

    private boolean finishedSessionTime(SessionModel session){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime finalTime = session.getCreatedAt().plusMinutes(session.getDurationInMinutes());
        return now.isAfter(finalTime);
    }
}
