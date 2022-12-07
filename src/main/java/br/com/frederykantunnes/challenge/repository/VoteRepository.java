package br.com.frederykantunnes.challenge.repository;

import br.com.frederykantunnes.challenge.model.VoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<VoteModel, Long> {
    Optional<VoteModel> findByUuidSessionAndDocument(String uuidSession, String document);
}
