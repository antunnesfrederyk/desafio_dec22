package br.com.frederykantunnes.challenge.service;

import br.com.frederykantunnes.challenge.api.DocumentValidatorAPI;
import br.com.frederykantunnes.challenge.api.dto.DocumentValidationResponse;
import br.com.frederykantunnes.challenge.dto.VoteRequestDTO;
import br.com.frederykantunnes.challenge.dto.VoteResponseDTO;
import br.com.frederykantunnes.challenge.exceptions.BadRequestException;
import br.com.frederykantunnes.challenge.exceptions.DuplicatedException;
import br.com.frederykantunnes.challenge.exceptions.NotFountDataException;
import br.com.frederykantunnes.challenge.exceptions.response.ErrorCodeEnum;
import br.com.frederykantunnes.challenge.mapper.VoteMapper;
import br.com.frederykantunnes.challenge.model.SessionModel;
import br.com.frederykantunnes.challenge.model.VoteModel;
import br.com.frederykantunnes.challenge.repository.SessionRepository;
import br.com.frederykantunnes.challenge.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {


    private final SessionRepository sessionRepository;
    private final VoteRepository voteRepository;
    private final DocumentValidatorAPI validatorAPI;

    public static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
    public static final String NUMERIC_REGEX = "[\\D]";
    private final List<String> votingOptions = List.of("SIM", "NÃO");

    public VoteResponseDTO vote(String uuidSession, VoteRequestDTO vote){
        Optional<SessionModel> optionalSession = sessionRepository.findByUuid(uuidSession);
        SessionModel session = getSession(optionalSession);
        validateSessionTime(session);
        validateDocument(vote);
        voteByDocumentValidate(uuidSession, vote);
        voteOptionValidate(vote);
        return voteRegister(uuidSession, vote);
    }

    private SessionModel getSession(Optional<SessionModel> session){
        if(session.isEmpty()){
            throw new NotFountDataException("Session not found!");
        }
        return session.get();
    }

    private void validateSessionTime(SessionModel session){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime finalTime = session.getCreatedAt().plusMinutes(session.getDurationInMinutes());
        if(now.isAfter(finalTime)){
            throw new BadRequestException(HttpStatus.UNPROCESSABLE_ENTITY, "Session is finished", ErrorCodeEnum.E0002);
        }
    }

    private void validateDocument(VoteRequestDTO vote){
        DocumentValidationResponse documentValidationResponse = validatorAPI.validateDocument(vote.getDocument().replaceAll(NUMERIC_REGEX, Strings.EMPTY));
        if(!ABLE_TO_VOTE.equalsIgnoreCase(documentValidationResponse.getStatus()))
            throw new BadRequestException(HttpStatus.UNPROCESSABLE_ENTITY, "Document not able to vote!", ErrorCodeEnum.E0002);
    }

    private void voteByDocumentValidate(String uuidSession, VoteRequestDTO vote){
        boolean hasVoteByDocument = voteRepository.findByUuidSessionAndDocument(uuidSession, vote.getDocument()).isPresent();
        if (hasVoteByDocument)
            throw new DuplicatedException(HttpStatus.CONFLICT, "Document has vote for current session", ErrorCodeEnum.E0003);
    }

    private void voteOptionValidate(VoteRequestDTO vote){
        if(!votingOptions.contains(vote.getVote().toUpperCase()))
            throw new BadRequestException(HttpStatus.UNPROCESSABLE_ENTITY, "Voting option unavailable!", ErrorCodeEnum.E0002);
    }

    private VoteResponseDTO voteRegister(String uuidSession, VoteRequestDTO vote){
        VoteModel voteModel = VoteMapper.createVoteModel(uuidSession, vote);
        VoteModel savedVote = this.voteRepository.save(voteModel);
        return VoteMapper.generateResponseDto(savedVote, "Voto realizado com Sucesso!");
    }

}
