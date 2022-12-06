package br.com.frederykantunnes.challenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartSessionRequestDTO {
    private String uuidStave;

    private Integer minutesDuration;
}
