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
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {


    private final SessionRepository sessionRepository;
    private final VoteRepository voteRepository;
    private final DocumentValidatorAPI validatorAPI;
    public static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
    public static final String NUMERIC_REGEX = "[\\D]";

    @Value("${integrations.validate-document.enabled}")
    private boolean validateDocumentActive;

    public VoteResponseDTO vote(String uuidSession, VoteRequestDTO vote){
        log.info("Register Vote Document: {}", vote.getDocument());
        Optional<SessionModel> optionalSession = sessionRepository.findByUuid(uuidSession);
        SessionModel session = getSession(optionalSession);
        validateSessionTime(session);
        voteByDocumentValidate(uuidSession, vote);
        validateDocument(vote);
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
        if(validateDocumentActive){
            DocumentValidationResponse documentValidationResponse = validatorAPI.validateDocument(vote.getDocument().replaceAll(NUMERIC_REGEX, Strings.EMPTY));
            if(!ABLE_TO_VOTE.equalsIgnoreCase(documentValidationResponse.getStatus()))
                throw new BadRequestException(HttpStatus.UNPROCESSABLE_ENTITY, "Document not able to vote!", ErrorCodeEnum.E0002);
        }
    }

    private void voteByDocumentValidate(String uuidSession, VoteRequestDTO vote){
        boolean hasVoteByDocument = voteRepository.findByUuidSessionAndDocument(uuidSession, vote.getDocument()).isPresent();
        if (hasVoteByDocument)
            throw new DuplicatedException(HttpStatus.CONFLICT, "Document has vote for current session", ErrorCodeEnum.E0003);
    }

    private VoteResponseDTO voteRegister(String uuidSession, VoteRequestDTO vote){
        VoteModel voteModel = VoteMapper.createVoteModel(uuidSession, vote);
        VoteModel savedVote = this.voteRepository.save(voteModel);
        return VoteMapper.generateResponseDto(savedVote, "Voto realizado com Sucesso!");
    }

}
