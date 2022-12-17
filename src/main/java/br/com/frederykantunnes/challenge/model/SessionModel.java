package br.com.frederykantunnes.challenge.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SESSION")
public class SessionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "UUID")
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "UUID_STAVE")
    private String uuidStave;

    @Builder.Default
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "DURATION_IN_MINUTES")
    private Integer durationInMinutes = 1;

    @Column(name = "VOTES_COUNTED")
    private boolean votesCounted;

    @Column(name = "TOTAL_POSITIVE_VOTES")
    private long totalPositiveVotes;

    @Column(name = "TOTAL_NEGATIVE_VOTES")
    private long totalNegativeVotes;

}
