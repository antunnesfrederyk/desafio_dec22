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
@Table(name = "VOTE")
public class VoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "UUID")
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "UUID_SESSION")
    private String uuidSession;

    @Column(name = "VOTE")
    private String vote;

    @Column(name = "DOCUMENT")
    private String document;

    @Builder.Default
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();


}
