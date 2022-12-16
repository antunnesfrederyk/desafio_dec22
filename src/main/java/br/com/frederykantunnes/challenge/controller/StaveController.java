package br.com.frederykantunnes.challenge.controller;


import br.com.frederykantunnes.challenge.dto.StartSessionRequestDTO;
import br.com.frederykantunnes.challenge.dto.StartSessionResponseDTO;
import br.com.frederykantunnes.challenge.dto.StaveRequestDTO;
import br.com.frederykantunnes.challenge.dto.StaveResponseDTO;
import br.com.frederykantunnes.challenge.service.SessionService;
import br.com.frederykantunnes.challenge.service.StaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staves")
@Api(value = "Api Pautas e Sessões", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, tags = {"Pautas e Sessões"})
public class StaveController {

    private final StaveService staveService;
    private final SessionService sessionService;

    @Autowired
    public StaveController(StaveService staveService, SessionService sessionService) {
        this.staveService = staveService;
        this.sessionService = sessionService;
    }

    @PostMapping
    @ApiOperation(value = "Cria uma pauta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StaveResponseDTO> createNewStave(@RequestBody StaveRequestDTO stave) {
        return ResponseEntity.ok(staveService.create(stave));
    }

    @GetMapping
    @ApiOperation(value = "Lista todas as pautas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaveResponseDTO>> listAllStaves() {
        return ResponseEntity.ok(staveService.findAllStaves());
    }

    @PostMapping("/start")
    @ApiOperation(value = "Inicia sessão de votação em pauta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StartSessionResponseDTO> startSession(@RequestBody StartSessionRequestDTO session) {
        return ResponseEntity.ok(sessionService.startSession(session));
    }

}
