package hr.fer.infsus.sausc.model.db;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "equipment")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipment")
    private Long idEquipment;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "equipment")
    private Set<ActivityEquipment> activityEquipments;
}
