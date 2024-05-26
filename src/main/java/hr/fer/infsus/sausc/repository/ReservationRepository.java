package hr.fer.infsus.sausc.repository;

import hr.fer.infsus.sausc.model.db.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findBySportsCenterMember_IdUser(Long userId);

    @Query(""" 
            SELECT r
            FROM Reservation r
            JOIN FETCH r.status s
            WHERE r.startTime >= :startTime
            AND r.endTime <= :endTime AND s.idStatus != 3
            """)
    List<Reservation> findReservationsInInterval(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query(""" 
                SELECT r
                FROM Reservation r
                JOIN r.activity a 
                JOIN r.status s
                WHERE a.idActivity = :idActivity 
                AND r.startTime >= :startOfDay 
                AND r.endTime < :endOfDay 
                AND s.idStatus != 3""")
    List<Reservation> findByActivity_IdActivityAndDate(@Param("idActivity") Long idActivity, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    @Query("""
               SELECT r 
               FROM Reservation r
               JOIN r.activity a
               JOIN r.status s 
               WHERE a.idActivity = :idActivity 
               AND r.startTime < :endTime 
               AND r.endTime > :startTime 
               AND s.idStatus != 3 """)
    List<Reservation> findByActivity_IdActivityAndDateRange(@Param("idActivity") Long idActivity, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}
