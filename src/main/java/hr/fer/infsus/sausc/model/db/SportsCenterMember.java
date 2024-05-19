package hr.fer.infsus.sausc.model.db;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "sports_center_member")
@Getter
@Setter
public class SportsCenterMember extends User{

    @Column(name = "membership_status")
    private String membershipStatus;

    @Column(name = "membership_start_date")
    private LocalDate membershipStartDate;

    @Column(name = "membership_end_date")
    private LocalDate membershipEndDate;

    @Column(name = "number_of_absences")
    private Integer numberOfAbsences;
}
