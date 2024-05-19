package hr.fer.infsus.sausc.model.db;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@Entity
@Table(name = "activity")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_activity")
    private Long idActivity;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price_per_hour")
    private Double pricePerHour;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Administrator administrator;

    @OneToMany(mappedBy = "activity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<ActivityEquipment> activityEquipments;

}
