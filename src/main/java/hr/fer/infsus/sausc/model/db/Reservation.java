package hr.fer.infsus.sausc.model.db;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "reservation")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_reservation")
    private Long idReservation;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "number_of_participants")
    private Integer numberOfParticipants;

    @Column(name = "reservation_price")
    private Double reservationPrice;

    @ManyToOne
    @JoinColumn(name = "sports_center_member_id")
    private SportsCenterMember sportsCenterMember;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
}
