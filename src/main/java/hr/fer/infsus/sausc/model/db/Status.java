package hr.fer.infsus.sausc.model.db;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "status")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_status")
    private Long idStatus;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;
}