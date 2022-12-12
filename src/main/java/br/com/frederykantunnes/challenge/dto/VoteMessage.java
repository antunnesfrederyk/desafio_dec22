package br.com.frederykantunnes.challenge.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VoteMessage {
    private String uuidSession;
    private String uuidStave;
    private LocalDateTime startedSession;
    private LocalDateTime finishedSession;
    private int totalPositiveVotes;
    private int totalNegativeVotes;
}
