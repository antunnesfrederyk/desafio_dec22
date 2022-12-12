package br.com.frederykantunnes.challenge.service;

import br.com.frederykantunnes.challenge.dto.VoteRequestDTO;
import br.com.frederykantunnes.challenge.dto.VoteResponseDTO;
import br.com.frederykantunnes.challenge.mapper.VoteMapper;
import br.com.frederykantunnes.challenge.model.SessionModel;
import br.com.frederykantunnes.challenge.model.VoteModel;
import br.com.frederykantunnes.challenge.repository.SessionRepository;
import br.com.frederykantunnes.challenge.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final SessionRepository sessionRepository;
    private final VoteRepository voteRepository;
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
            throw new RuntimeException("404");
        }
        return session.get();
    }

    private void validateSessionTime(SessionModel session){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime finalTime = session.getCreatedAt().plusMinutes(session.getDurationInMinutes());
        if(now.isAfter(finalTime)){
            throw new RuntimeException("Votação Encerrada!");
        }
    }

    private void validateDocument(VoteRequestDTO vote){
        if(Objects.isNull(vote.getDocument())){
            throw new RuntimeException("Invalid Document");
        }
    }

    private void voteByDocumentValidate(String uuidSession, VoteRequestDTO vote){
        boolean hasVoteByDocument = voteRepository.findByUuidSessionAndDocument(uuidSession, vote.getDocument()).isPresent();
        if (hasVoteByDocument)
            throw new RuntimeException("Documento já votou");
    }

    private void voteOptionValidate(VoteRequestDTO vote){
        if(!votingOptions.contains(vote.getVote().toUpperCase()))
            throw new RuntimeException("Opção de Voto Indisponível");
    }

    private VoteResponseDTO voteRegister(String uuidSession, VoteRequestDTO vote){
        VoteModel voteModel = VoteMapper.createVoteModel(uuidSession, vote);
        VoteModel savedVote = this.voteRepository.save(voteModel);
        return VoteMapper.generateResponseDto(savedVote, "Voto realizado com Sucesso!");
    }

}
