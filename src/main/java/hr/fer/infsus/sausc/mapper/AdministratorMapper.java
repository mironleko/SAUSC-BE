package hr.fer.infsus.sausc.mapper;

import hr.fer.infsus.sausc.model.db.Activity;
import hr.fer.infsus.sausc.model.db.Administrator;
import hr.fer.infsus.sausc.rest.model.UserInfoDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class AdministratorMapper {

    @PersistenceContext
    EntityManager entityManager;

    public Administrator toEntity(final Long administratorId) {
        if (administratorId == null) return null;
        return entityManager.getReference(Administrator.class, administratorId);
    }

    @Named("userInfoAdministratorMapper")
    public abstract UserInfoDto toUserInfoDto(Administrator administrator);
}
