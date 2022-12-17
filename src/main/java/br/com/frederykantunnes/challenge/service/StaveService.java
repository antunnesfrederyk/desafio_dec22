package br.com.frederykantunnes.challenge.service;

import br.com.frederykantunnes.challenge.dto.StaveRequestDTO;
import br.com.frederykantunnes.challenge.dto.StaveResponseDTO;
import br.com.frederykantunnes.challenge.mapper.StaveMapper;
import br.com.frederykantunnes.challenge.model.StaveModel;
import br.com.frederykantunnes.challenge.repository.StaveRepository;
import br.com.frederykantunnes.challenge.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaveService {

    private final StaveRepository staveRepository;

    public List<StaveResponseDTO> findAllStaves(){
        List<StaveModel> all = this.staveRepository.findAll();
        return all.stream().map(StaveMapper::staveMapperToResponse).collect(Collectors.toList());
    }

    public StaveResponseDTO create(StaveRequestDTO stave){
        log.info("Create Stave: {}", JsonUtils.toJson(stave));
        StaveModel save = this.staveRepository.save(StaveMapper.staveMapperToNewModel(stave));
        return StaveMapper.staveMapperToResponse(save);
    }

}
