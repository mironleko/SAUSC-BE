package hr.fer.infsus.sausc.repository;

import hr.fer.infsus.sausc.model.db.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}
