package br.com.frederykantunnes.challenge.mapper;

import br.com.frederykantunnes.challenge.dto.StaveRequestDTO;
import br.com.frederykantunnes.challenge.dto.StaveResponseDTO;
import br.com.frederykantunnes.challenge.model.StaveModel;
import org.springframework.stereotype.Service;

@Service
public class StaveMapper {
    public StaveResponseDTO staveMapperToResponse(StaveModel staveModel) {
        return StaveResponseDTO.builder()
                .uuid(staveModel.getUuid())
                .title(staveModel.getTitle())
                .createdAt(staveModel.getCreatedAt())
                .build();
    }

    public StaveModel staveMapperToNewModel(StaveRequestDTO stave) {
        return StaveModel.builder()
                .title(stave.getTitle())
                .build();
    }
}
