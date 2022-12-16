package br.com.frederykantunnes.challenge.dto;

import br.com.frederykantunnes.challenge.enums.VoteOptionsEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequestDTO {
    private String document;
    private VoteOptionsEnum vote;
}
