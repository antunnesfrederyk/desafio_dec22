package br.com.frederykantunnes.challenge.controller;


import br.com.frederykantunnes.challenge.dto.StartSessionRequestDTO;
import br.com.frederykantunnes.challenge.dto.StartSessionResponseDTO;
import br.com.frederykantunnes.challenge.dto.StaveRequestDTO;
import br.com.frederykantunnes.challenge.dto.StaveResponseDTO;
import br.com.frederykantunnes.challenge.service.SessionService;
import br.com.frederykantunnes.challenge.service.StaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staves")
public class StaveController {

    private final StaveService staveService;
    private final SessionService sessionService;

    @Autowired
    public StaveController(StaveService staveService, SessionService sessionService) {
        this.staveService = staveService;
        this.sessionService = sessionService;
    }

    @PostMapping
    public StaveResponseDTO createNewStave(@RequestBody StaveRequestDTO stave) {
        return staveService.create(stave);
    }

    @GetMapping
    public ResponseEntity<List<StaveResponseDTO>> listAllStaves() {
        return ResponseEntity.ok(staveService.findAllStaves());
    }

    @PostMapping("/start")
    public StartSessionResponseDTO startSession(@RequestBody StartSessionRequestDTO session) {
        return sessionService.startSession(session);
    }

}
