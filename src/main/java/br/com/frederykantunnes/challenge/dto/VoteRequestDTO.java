package br.com.frederykantunnes.challenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequestDTO {
    private String document;
    private String vote;
}
