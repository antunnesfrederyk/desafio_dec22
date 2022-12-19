package br.com.frederykantunnes.challenge.utils;

import br.com.frederykantunnes.challenge.dto.StartSessionRequestDTO;

public class StartSessionRequestDTOFactory {
    public static StartSessionRequestDTO buildStartSessionRequestDTOWithDuration(String uuidStave) {
        StartSessionRequestDTO data = new StartSessionRequestDTO();
        data.setUuidStave(uuidStave);
        data.setMinutesDuration(1);
        return data;
    }

    public static StartSessionRequestDTO buildStartSessionRequestDTOWithoutDuration(String uuidStave) {
        StartSessionRequestDTO data = new StartSessionRequestDTO();
        data.setUuidStave(uuidStave);
        return data;
    }
}
