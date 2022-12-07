package br.com.frederykantunnes.challenge.mapper;

import br.com.frederykantunnes.challenge.dto.StartSessionRequestDTO;
import br.com.frederykantunnes.challenge.dto.StartSessionResponseDTO;
import br.com.frederykantunnes.challenge.model.SessionModel;
import java.util.Objects;

public class SessionMapper {
    public static SessionModel startSessionToSessionModel(StartSessionRequestDTO sessionRequestDTO) {
        return SessionModel.builder()
                .uuidStave(sessionRequestDTO.getUuidStave())
                .durationInMinutes(Objects.nonNull(sessionRequestDTO.getMinutesDuration())?sessionRequestDTO.getMinutesDuration():1)
                .build();
    }
    public static StartSessionResponseDTO sessionModelToResponse(SessionModel model) {
        return StartSessionResponseDTO.builder()
                .uuidSession(model.getUuid())
                .uuidStave(model.getUuidStave())
                .minutesDuration(model.getDurationInMinutes())
                .build();
    }

}
