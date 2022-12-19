package br.com.frederykantunnes.challenge.service;

import br.com.frederykantunnes.challenge.dto.StaveResponseDTO;
import br.com.frederykantunnes.challenge.mapper.StaveMapper;
import br.com.frederykantunnes.challenge.repository.StaveRepository;
import br.com.frederykantunnes.challenge.utils.StaveModelFactory;
import br.com.frederykantunnes.challenge.utils.StaveRequestDTOFactory;
import br.com.frederykantunnes.challenge.utils.StaveResponseDTOFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @InjectMocks
    private StaveService tested;

    @Mock
    private StaveRepository staveRepository;

    @Mock
    private StaveMapper mapper;

    @Test
    @DisplayName("Should generate vote stave with success")
    void shouldGenerateVoteStaveWithSuccess(){
        var request = StaveRequestDTOFactory.buildStaveRequestDTO("Stave Test Title");
        var model = StaveModelFactory.buildStaveModel(request.getTitle());
        var modelSaved = StaveModelFactory.buildStaveModelSaved(model.getUuid(), request.getTitle());
        var response = StaveResponseDTOFactory.buildStaveResponseDTO(modelSaved);

        when(mapper.staveMapperToNewModel(request)).thenReturn(model);
        when(staveRepository.save(model)).thenReturn(modelSaved);
        when(mapper.staveMapperToResponse(modelSaved)).thenReturn(response);

        StaveResponseDTO staveResponseDTO = tested.create(request);

        verify(mapper).staveMapperToNewModel(request);
        verify(staveRepository).save(model);
        verify(mapper).staveMapperToResponse(modelSaved);
        assertEquals(staveResponseDTO, response);
    }

    @Test
    @DisplayName("Should look for voting guidelines")
    void shouldLookForVotingGuidelines(){
        var listModels = List.of(StaveModelFactory.buildStaveModelSaved(UUID.randomUUID().toString(), "Title Test"));
        var localMapper = new StaveMapper();

        when(staveRepository.findAll()).thenReturn(listModels);
        when(mapper.staveMapperToResponse(listModels.get(0))).thenReturn(localMapper.staveMapperToResponse(listModels.get(0)));

        List<StaveResponseDTO> allStaves = tested.findAllStaves();

        verify(mapper).staveMapperToResponse(listModels.get(0));
        verify(staveRepository).findAll();
        assertEquals(listModels.size(), allStaves.size());
        assertEquals(allStaves.get(0).getTitle(), listModels.get(0).getTitle());
        assertEquals(allStaves.get(0).getUuid(), listModels.get(0).getUuid());

    }
}
