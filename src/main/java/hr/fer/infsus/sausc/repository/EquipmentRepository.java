package hr.fer.infsus.sausc.repository;

import hr.fer.infsus.sausc.model.db.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
