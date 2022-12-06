package br.com.frederykantunnes.challenge.repository;

import br.com.frederykantunnes.challenge.model.StaveModel;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StaveRepository extends JpaRepository<StaveModel, Long> {

}
