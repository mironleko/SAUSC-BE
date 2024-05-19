package hr.fer.infsus.sausc.model.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "administrator")
@Getter
@Setter
public class Administrator extends User {

    @Column(name = "employment_date")
    private Date employmentDate;

}
