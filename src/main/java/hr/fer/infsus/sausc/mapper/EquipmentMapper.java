package hr.fer.infsus.sausc.mapper;

import hr.fer.infsus.sausc.model.db.Activity;
import hr.fer.infsus.sausc.model.db.Equipment;
import hr.fer.infsus.sausc.repository.ActivityEquipmentRepository;
import hr.fer.infsus.sausc.rest.model.EquipmentDto;
import hr.fer.infsus.sausc.rest.model.EquipmentForm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class EquipmentMapper {
    @PersistenceContext
    EntityManager entityManager;

    public Equipment toEntity(final Long equipmentId) {
        if (equipmentId == null) return null;
        return entityManager.getReference(Equipment.class, equipmentId);
    }

    public abstract Equipment toEntity(final EquipmentForm equipmentForm);

    public abstract EquipmentDto toDto(final Equipment equipment,final Integer quantity);

    public abstract EquipmentDto toDto(final ActivityEquipmentRepository.EquipmentQuantity equipmentQuantity);

    public abstract void toEquipment(@MappingTarget Equipment equipment, EquipmentForm equipmentForm);
}
