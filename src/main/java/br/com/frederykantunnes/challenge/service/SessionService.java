package br.com.frederykantunnes.challenge.service;

import br.com.frederykantunnes.challenge.dto.StartSessionRequestDTO;
import br.com.frederykantunnes.challenge.dto.StartSessionResponseDTO;
import br.com.frederykantunnes.challenge.exceptions.BadRequestException;
import br.com.frederykantunnes.challenge.exceptions.response.ErrorCodeEnum;
import br.com.frederykantunnes.challenge.mapper.SessionMapper;
import br.com.frederykantunnes.challenge.model.SessionModel;
import br.com.frederykantunnes.challenge.repository.SessionRepository;
import br.com.frederykantunnes.challenge.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper mapper;

    public StartSessionResponseDTO startSession(StartSessionRequestDTO stave){
        log.info("Starting Session: {}", JsonUtils.toJson(stave));
        boolean existSession = sessionRepository.findByUuidStave(stave.getUuidStave()).isPresent();
        if(existSession)
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Session has already started", ErrorCodeEnum.E0002);
        SessionModel save = this.sessionRepository.save(mapper.startSessionToSessionModel(stave));
        return mapper.sessionModelToResponse(save);
    }
}
