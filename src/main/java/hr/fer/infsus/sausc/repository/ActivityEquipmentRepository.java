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
            SELECT e.idEquipment AS idEquipment,
                   e.name AS name,
                   e.description AS description,
                   ae.quantity AS quantity
            FROM ActivityEquipment ae
            JOIN ae.activity a
            JOIN ae.equipment e
            WHERE a.idActivity = :activityId""")
    List<EquipmentQuantity> search(@Param("activityId") Long activityId);

    interface EquipmentQuantity {
        Long getIdEquipment();
        String getName();
        String getDescription();
        Long getQuantity();
    }
}
