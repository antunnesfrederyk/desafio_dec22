package br.com.frederykantunnes.challenge.controller;

import br.com.frederykantunnes.challenge.dto.*;
import br.com.frederykantunnes.challenge.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteService service;

    @Autowired
    public VoteController(VoteService service) {
        this.service = service;
    }

    @PostMapping("/{uuidSession}")
    public ResponseEntity<VoteResponseDTO> vote(@PathVariable("uuidSession") String uuidSession, @RequestBody VoteRequestDTO vote) {
        return ResponseEntity.ok(service.vote(uuidSession,vote));
    }


}
