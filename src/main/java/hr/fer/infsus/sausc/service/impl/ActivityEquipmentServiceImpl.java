package hr.fer.infsus.sausc.service.impl;

import hr.fer.infsus.sausc.mapper.ActivityEquipmentMapper;
import hr.fer.infsus.sausc.mapper.EquipmentMapper;
import hr.fer.infsus.sausc.model.db.Activity;
import hr.fer.infsus.sausc.model.db.ActivityEquipment;
import hr.fer.infsus.sausc.model.db.Equipment;
import hr.fer.infsus.sausc.repository.ActivityEquipmentRepository;
import hr.fer.infsus.sausc.repository.ActivityRepository;
import hr.fer.infsus.sausc.repository.EquipmentRepository;
import hr.fer.infsus.sausc.rest.model.EquipmentDto;
import hr.fer.infsus.sausc.rest.model.EquipmentForm;
import hr.fer.infsus.sausc.rest.model.ListEquipmentDto;
import hr.fer.infsus.sausc.service.ActivityEquipmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityEquipmentServiceImpl implements ActivityEquipmentService {

    private final EquipmentMapper equipmentMapper;
    private final EquipmentRepository equipmentRepository;
    private final ActivityEquipmentRepository activityEquipmentRepository;
    private final ActivityEquipmentMapper activityEquipmentMapper;
    private final ActivityRepository activityRepository;

    @Transactional
    @Override
    public EquipmentDto addEquipment(Long activityId, EquipmentForm equipmentForm) {

        activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity with ID: " + activityId + " not found"));

        Equipment equipment = equipmentRepository.save(equipmentMapper.toEntity(equipmentForm));
        ActivityEquipment activityEquipment = activityEquipmentRepository.save(activityEquipmentMapper.toEntity(activityId, equipment.getIdEquipment(), equipmentForm.getQuantity()));

        return equipmentMapper.toDto(equipment, activityEquipment.getQuantity());
    }

    @Override
    public List<EquipmentDto> getActivityEquipmentList(Long activityId) {

        List<ActivityEquipmentRepository.EquipmentQuantity> equipmentList = activityEquipmentRepository.search(activityId);
        return equipmentList.stream().map(equipmentMapper::toDto).toList();
    }

    @Override
    public EquipmentDto getActivityEquipment(Long activityId, Long equipmentId) {

        ActivityEquipmentRepository.EquipmentQuantity equipmentQuantity = activityEquipmentRepository
                .findActivityEquipment(activityId, equipmentId)
                .orElseThrow(() -> new EntityNotFoundException("ActivityEquipment Activity with ID: " + activityId + "and Equipment ID: " + equipmentId + " not found"));
        return equipmentMapper.toDto(equipmentQuantity);
    }

    @Transactional
    @Override
    public EquipmentDto updateActivityEquipment(Long activityId, Long equipmentId, EquipmentForm equipmentForm) {

        activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity with ID: " + activityId + " not found"));

        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Equipment with ID: " + equipmentId + " not found"));

        ActivityEquipment activityEquipment = activityEquipmentRepository.findByActivityIdAndEquipmentId(activityId, equipmentId)
                .orElseThrow(() -> new EntityNotFoundException("ActivityEquipment with Activity ID: " + activityId + " and Equipment ID: " + equipmentId + " not found"));

        equipmentMapper.toEquipment(equipment, equipmentForm);
        equipmentRepository.save(equipment);

        activityEquipmentMapper.toActivityEquipment(activityEquipment,activityId, equipmentId, equipmentForm.getQuantity());
        activityEquipmentRepository.save(activityEquipment);


        return getActivityEquipment(activityId,equipmentId);
    }

    @Transactional
    @Override
    public void deleteActivityEquipment(Long activityId, Long equipmentId) {
        activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity with ID: " + activityId + " not found"));

        equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Equipment with ID: " + equipmentId + " not found"));

        ActivityEquipment activityEquipment = activityEquipmentRepository.findByActivityIdAndEquipmentId(activityId, equipmentId)
                .orElseThrow(() -> new EntityNotFoundException("ActivityEquipment with Activity ID: " + activityId + " and Equipment ID: " + equipmentId + " not found"));

        activityEquipmentRepository.delete(activityEquipment);

        boolean isEquipmentUsedInOtherActivities = activityEquipmentRepository.existsByEquipmentId(equipmentId);
        if (!isEquipmentUsedInOtherActivities) {
            equipmentRepository.deleteById(equipmentId);
        }
    }
}
