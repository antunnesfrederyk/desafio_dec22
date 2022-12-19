package br.com.frederykantunnes.challenge.utils;

import br.com.frederykantunnes.challenge.dto.StaveResponseDTO;
import br.com.frederykantunnes.challenge.model.StaveModel;

public class StaveResponseDTOFactory {
    public static StaveResponseDTO buildStaveResponseDTO(StaveModel stave) {
        return StaveResponseDTO.builder()
                .uuid(stave.getUuid())
                .title(stave.getTitle())
                .createdAt(stave.getCreatedAt())
                .build();
    }
}
