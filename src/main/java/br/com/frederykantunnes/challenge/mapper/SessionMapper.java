package br.com.frederykantunnes.challenge.mapper;

import br.com.frederykantunnes.challenge.dto.StartSessionRequestDTO;
import br.com.frederykantunnes.challenge.dto.StartSessionResponseDTO;
import br.com.frederykantunnes.challenge.dto.StaveRequestDTO;
import br.com.frederykantunnes.challenge.dto.StaveResponseDTO;
import br.com.frederykantunnes.challenge.model.SessionModel;
import br.com.frederykantunnes.challenge.model.StaveModel;

import java.util.Objects;
import java.util.UUID;


public class SessionMapper {
    public static SessionModel startSessionToSessionModel(StartSessionRequestDTO sessionRequestDTO) {
        return SessionModel.builder()
                .uuid(UUID.randomUUID().toString())
                .uuidStave(sessionRequestDTO.getUuidStave())
                .build();
    }
    public static StartSessionResponseDTO sessionModelToResponse(SessionModel model) {
        return StartSessionResponseDTO.builder()
                .uuidStave(model.getUuidStave())
                .minutesDuration(model.getDurationInMinutes())
                .build();
    }

}
