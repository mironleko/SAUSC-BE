package hr.fer.infsus.sausc.mapper;

import hr.fer.infsus.sausc.model.db.SportsCenterMember;
import hr.fer.infsus.sausc.model.db.Status;
import hr.fer.infsus.sausc.rest.model.StatusDto;
import hr.fer.infsus.sausc.rest.model.StatusForm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class StatusMapper {
    @PersistenceContext
    EntityManager entityManager;

    public Status toEntity(final Long statusId) {
        if (statusId == null) return null;
        return entityManager.getReference(Status.class, statusId);
    }

    public abstract Status toEntity(final StatusForm statusForm);

    public abstract Status toStatus(@MappingTarget Status status, final StatusForm statusForm);

    public abstract StatusDto toDto(final Status status);

}
