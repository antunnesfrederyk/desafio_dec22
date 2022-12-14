package br.com.frederykantunnes.challenge.repository;

import br.com.frederykantunnes.challenge.model.VoteCountModel;
import br.com.frederykantunnes.challenge.model.VoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<VoteModel, Long> {
    Optional<VoteModel> findByUuidSessionAndDocument(String uuidSession, String document);

    @Query("select NEW br.com.frederykantunnes.challenge.model.VoteCountModel(vt.vote, count(vt.id)) from VoteModel vt where vt.uuidSession = ?1 group by vt.vote")
    List<VoteCountModel> countVotesBySession(String uuidSession);
}
