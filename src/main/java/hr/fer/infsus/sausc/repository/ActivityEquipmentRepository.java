package hr.fer.infsus.sausc.repository;

import hr.fer.infsus.sausc.model.db.ActivityEquipment;
import hr.fer.infsus.sausc.model.db.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityEquipmentRepository extends JpaRepository<ActivityEquipment, Long> {
    @Query(value = """ 
            SELECT e
            FROM ActivityEquipment ae
            JOIN ae.activity a
            JOIN ae.equipment e
            WHERE a.idActivity = :activityId""")
    List<Equipment> search(@Param("activityId") Long activityId);
}
