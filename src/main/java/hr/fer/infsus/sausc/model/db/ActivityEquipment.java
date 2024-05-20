package hr.fer.infsus.sausc.model.db;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "activity_equipment")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ActivityEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_activity_equipment")
    private Long idActivityEquipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipment")
    private Equipment equipment;

    @Column(name = "quantity")
    private Integer quantity;


}
