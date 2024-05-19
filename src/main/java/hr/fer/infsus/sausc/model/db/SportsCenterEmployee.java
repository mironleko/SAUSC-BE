package hr.fer.infsus.sausc.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.*;

@Entity
@Table(name = "sports_center_employee")
@Getter
@Setter
public class SportsCenterEmployee extends User{

    @Column(name = "employment_date")
    private LocalDate employmentDate;

    @Column(name = "position")
    private String position;

    @Column(name = "department")
    private String department;
}
