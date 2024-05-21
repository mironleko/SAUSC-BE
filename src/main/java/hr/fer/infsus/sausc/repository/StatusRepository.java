package hr.fer.infsus.sausc.repository;

import hr.fer.infsus.sausc.model.db.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
