package br.com.frederykantunnes.challenge.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "UUID_STAVE")
    private String uuidStave;

    @Builder.Default
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "DURATION_IN_MINUTES")
    private Integer durationInMinutes = 1;

}
