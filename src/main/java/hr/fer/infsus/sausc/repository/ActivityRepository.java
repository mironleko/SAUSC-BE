package hr.fer.infsus.sausc.repository;

import hr.fer.infsus.sausc.model.db.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query(value = """ 
            SELECT a
            FROM Activity a
            WHERE (:name IS NULL OR LOWER(CAST(a.name AS string)) LIKE CONCAT('%', LOWER(CAST(:name AS string)), '%'))""")
    List<Activity> search(@Param("name") String name);
}
