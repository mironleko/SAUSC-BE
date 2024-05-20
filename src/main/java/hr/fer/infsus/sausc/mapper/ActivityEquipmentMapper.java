package hr.fer.infsus.sausc.mapper;

import hr.fer.infsus.sausc.model.db.Activity;
import hr.fer.infsus.sausc.model.db.ActivityEquipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ActivityMapper.class, EquipmentMapper.class})
public abstract class ActivityEquipmentMapper {

    @PersistenceContext
    EntityManager entityManager;

    public ActivityEquipment toEntity(final Long activityEquipmentId) {
        if (activityEquipmentId == null) return null;
        return entityManager.getReference(ActivityEquipment.class, activityEquipmentId);
    }
    @Mapping(source = "activityId",target = "activity")
    @Mapping(source = "equipmentId",target = "equipment")
    public abstract ActivityEquipment toEntity(Long activityId,Long equipmentId,int quantity);

    @Mapping(source = "activityId",target = "activity")
    @Mapping(source = "equipmentId",target = "equipment")
    public abstract void toActivityEquipment(@MappingTarget ActivityEquipment activityEquipment, Long activityId, Long equipmentId, Integer quantity);

}
