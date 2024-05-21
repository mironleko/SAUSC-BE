package hr.fer.infsus.sausc.repository;

import hr.fer.infsus.sausc.model.db.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
