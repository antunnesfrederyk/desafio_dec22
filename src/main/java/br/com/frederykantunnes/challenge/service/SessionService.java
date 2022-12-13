package br.com.frederykantunnes.challenge.service;

import br.com.frederykantunnes.challenge.dto.StartSessionRequestDTO;
import br.com.frederykantunnes.challenge.dto.StartSessionResponseDTO;
import br.com.frederykantunnes.challenge.exceptions.BadRequestException;
import br.com.frederykantunnes.challenge.exceptions.response.ErrorCodeEnum;
import br.com.frederykantunnes.challenge.mapper.SessionMapper;
import br.com.frederykantunnes.challenge.model.SessionModel;
import br.com.frederykantunnes.challenge.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public StartSessionResponseDTO startSession(StartSessionRequestDTO stave){
        boolean existSession = sessionRepository.findByUuidStave(stave.getUuidStave()).isPresent();
        if(existSession)
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Session has already started", ErrorCodeEnum.E0002);
        SessionModel save = this.sessionRepository.save(SessionMapper.startSessionToSessionModel(stave));
        return SessionMapper.sessionModelToResponse(save);
    }
}
