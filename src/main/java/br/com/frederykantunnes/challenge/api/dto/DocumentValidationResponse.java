package br.com.frederykantunnes.challenge.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentValidationResponse {
    private String status;
}
