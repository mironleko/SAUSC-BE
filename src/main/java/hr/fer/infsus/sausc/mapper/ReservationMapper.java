package hr.fer.infsus.sausc.mapper;

import hr.fer.infsus.sausc.model.db.Equipment;
import hr.fer.infsus.sausc.model.db.Reservation;
import hr.fer.infsus.sausc.rest.model.EquipmentForm;
import hr.fer.infsus.sausc.rest.model.ReservationDto;
import hr.fer.infsus.sausc.rest.model.ReservationForm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {ActivityMapper.class,SportsCenterMemberMapper.class,StatusMapper.class})
public abstract class ReservationMapper {
    @PersistenceContext
    EntityManager entityManager;

    public Reservation toEntity(final Long reservationId) {
        if (reservationId == null) return null;
        return entityManager.getReference(Reservation.class, reservationId);
    }

    @Mapping(source = "sportsCenterMemberId",target = "sportsCenterMember")
    @Mapping(source = "activityId",target = "activity")
    @Mapping(source = "statusId",target = "status")
    public abstract Reservation toEntity(final ReservationForm reservationForm);

    @Mapping(source = "sportsCenterMember",target = "sportsCenterMember",qualifiedByName = "userInfoSportsCenterMemberMapper")
    public abstract ReservationDto toDto(Reservation reservation);
}
