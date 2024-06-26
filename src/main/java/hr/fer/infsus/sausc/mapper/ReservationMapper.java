package hr.fer.infsus.sausc.mapper;

import hr.fer.infsus.sausc.model.db.Equipment;
import hr.fer.infsus.sausc.model.db.Reservation;
import hr.fer.infsus.sausc.rest.model.ChangeReservationStatusRequestDto;
import hr.fer.infsus.sausc.rest.model.EquipmentForm;
import hr.fer.infsus.sausc.rest.model.ReservationDto;
import hr.fer.infsus.sausc.rest.model.ReservationForm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",uses = {ActivityMapper.class,SportsCenterMemberMapper.class,StatusMapper.class})
public abstract class ReservationMapper {
    @PersistenceContext
    EntityManager entityManager;

    public Reservation toEntity(final Long reservationId) {
        if (reservationId == null) return null;
        return entityManager.getReference(Reservation.class, reservationId);
    }

    @Mapping(source = "reservationForm.sportsCenterMemberId",target = "sportsCenterMember")
    @Mapping(source = "reservationForm.idActivity",target = "activity")
    @Mapping(source = "reservationForm.idStatus",target = "status")
    public abstract Reservation toEntity(final ReservationForm reservationForm,double reservationPrice);

    @Mapping(source = "sportsCenterMember",target = "sportsCenterMember",qualifiedByName = "userInfoSportsCenterMemberMapper")
    public abstract ReservationDto toDto(Reservation reservation);

    @Mapping(source = "idStatus",target = "status")
    public abstract void changeReservationStatus(@MappingTarget Reservation reservation, ChangeReservationStatusRequestDto request);
}
