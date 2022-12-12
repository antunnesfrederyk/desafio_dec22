package br.com.frederykantunnes.challenge.repository;

import br.com.frederykantunnes.challenge.model.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<SessionModel, Long> {
    Optional<SessionModel> findByUuid(String uuid);
    Optional<SessionModel> findByUuidStave(String uuidStave);
    List<SessionModel> findAllByVotesCountedIsFalse();
}
