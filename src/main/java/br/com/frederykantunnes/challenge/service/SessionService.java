package br.com.frederykantunnes.challenge.service;

import br.com.frederykantunnes.challenge.dto.StartSessionRequestDTO;
import br.com.frederykantunnes.challenge.dto.StartSessionResponseDTO;
import br.com.frederykantunnes.challenge.mapper.SessionMapper;
import br.com.frederykantunnes.challenge.model.SessionModel;
import br.com.frederykantunnes.challenge.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    public StartSessionResponseDTO startSession(StartSessionRequestDTO stave){
        boolean existSession = sessionRepository.findByUuidStave(stave.getUuidStave()).isPresent();
        if(existSession)
            throw new RuntimeException("Session has already started");
        SessionModel save = this.sessionRepository.save(SessionMapper.startSessionToSessionModel(stave));
        return SessionMapper.sessionModelToResponse(save);
    }
}
