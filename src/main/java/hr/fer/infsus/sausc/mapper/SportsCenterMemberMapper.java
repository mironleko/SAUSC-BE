package hr.fer.infsus.sausc.mapper;

import hr.fer.infsus.sausc.model.db.Administrator;
import hr.fer.infsus.sausc.model.db.Reservation;
import hr.fer.infsus.sausc.model.db.SportsCenterMember;
import hr.fer.infsus.sausc.rest.model.UserInfoDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class SportsCenterMemberMapper {

    @PersistenceContext
    EntityManager entityManager;

    public SportsCenterMember toEntity(final Long sportsCenterMemberId) {
        if (sportsCenterMemberId == null) return null;
        return entityManager.getReference(SportsCenterMember.class, sportsCenterMemberId);
    }

    @Named("userInfoSportsCenterMemberMapper")
    public abstract UserInfoDto toUserInfoDto(SportsCenterMember sportsCenterMember);
}
