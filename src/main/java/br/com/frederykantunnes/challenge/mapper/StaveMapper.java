package br.com.frederykantunnes.challenge.mapper;

import br.com.frederykantunnes.challenge.dto.StaveRequestDTO;
import br.com.frederykantunnes.challenge.dto.StaveResponseDTO;
import br.com.frederykantunnes.challenge.model.StaveModel;

import java.util.UUID;


public class StaveMapper {
    public static StaveResponseDTO staveMapperToResponse(StaveModel staveModel) {
        return StaveResponseDTO.builder()
                .uuid(staveModel.getUuid())
                .title(staveModel.getTitle())
                .createdAt(staveModel.getCreatedAt())
                .build();
    }

    public static StaveModel staveMapperToNewModel(StaveRequestDTO stave) {
        return StaveModel.builder()
                .uuid(UUID.randomUUID().toString())
                .title(stave.getTitle())
                .build();
    }
}
