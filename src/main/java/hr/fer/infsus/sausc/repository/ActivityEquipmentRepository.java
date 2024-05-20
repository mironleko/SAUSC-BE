package hr.fer.infsus.sausc.repository;

import hr.fer.infsus.sausc.model.db.ActivityEquipment;
import hr.fer.infsus.sausc.model.db.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Query(value = """ 
            SELECT e.idEquipment AS idEquipment,
                   e.name AS name,
                   e.description AS description,
                   ae.quantity AS quantity
            FROM ActivityEquipment ae
            JOIN ae.activity a
            JOIN ae.equipment e
            WHERE a.idActivity = :activityId AND e.idEquipment = :equipmentId""")
    Optional<EquipmentQuantity> findActivityEquipment(@Param("activityId") Long activityId, @Param("equipmentId")Long equipmentId);

    @Query("""
            SELECT ae
            FROM ActivityEquipment ae
            JOIN FETCH ae.activity a
            JOIN FETCH ae.equipment e
            WHERE a.idActivity = :activityId AND e.idEquipment = :equipmentId""")
    Optional<ActivityEquipment> findByActivityIdAndEquipmentId(@Param("activityId") Long activityId, @Param("equipmentId") Long equipmentId);

    @Query("""
            SELECT CASE WHEN COUNT(ae) > 0 THEN true ELSE false END
            FROM ActivityEquipment ae
            WHERE ae.equipment.idEquipment = :equipmentId""")
    boolean existsByEquipmentId(@Param("equipmentId") Long equipmentId);

    interface EquipmentQuantity {
        Long getIdEquipment();
        String getName();
        String getDescription();
        Long getQuantity();
    }
}
