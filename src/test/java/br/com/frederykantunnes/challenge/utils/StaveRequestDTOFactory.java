package br.com.frederykantunnes.challenge.utils;

import br.com.frederykantunnes.challenge.dto.StaveRequestDTO;

public class StaveRequestDTOFactory {
    public static StaveRequestDTO buildStaveRequestDTO(String title) {
        StaveRequestDTO staveRequestDTO = new StaveRequestDTO();
        staveRequestDTO.setTitle(title);
        return staveRequestDTO;
    }
}
