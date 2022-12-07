package br.com.frederykantunnes.challenge.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StartSessionResponseDTO {
    private String uuidSession;
    private String uuidStave;
    private int minutesDuration;
}
