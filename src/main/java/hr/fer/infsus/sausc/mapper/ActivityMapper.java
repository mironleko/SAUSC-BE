package hr.fer.infsus.sausc.mapper;

import hr.fer.infsus.sausc.model.db.Activity;
import hr.fer.infsus.sausc.rest.model.ActivityDto;
import hr.fer.infsus.sausc.rest.model.ActivityForm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {AdministratorMapper.class})
public abstract class ActivityMapper {

    @PersistenceContext
    EntityManager entityManager;

    public Activity toEntity(final Long activityId) {
        if (activityId == null) return null;
        return entityManager.getReference(Activity.class, activityId);
    }

    @Mapping(source = "idUser",target = "administrator")
    public abstract Activity toEntity(final ActivityForm activityForm);

    @Mapping(source = "administrator",target = "userInfo",qualifiedByName = "userInfoMapper")
    public abstract ActivityDto toDto(final Activity activity);

}
