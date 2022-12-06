package br.com.frederykantunnes.challenge.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class StaveResponseDTO{
    private String uuid;
    private String title;
    private LocalDateTime createdAt;
}
