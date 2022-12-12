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
@Table(name = "STAVE")
public class StaveModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "UUID")
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "TITLE")
    private String title;

    @Builder.Default
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

}
