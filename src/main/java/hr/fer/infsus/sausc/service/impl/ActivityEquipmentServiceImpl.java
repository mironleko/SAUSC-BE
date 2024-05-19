package hr.fer.infsus.sausc.service.impl;

import hr.fer.infsus.sausc.mapper.ActivityEquipmentMapper;
import hr.fer.infsus.sausc.mapper.EquipmentMapper;
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
        activityEquipmentRepository.save(activityEquipmentMapper.toEntity(activityId,equipment.getIdEquipment(),equipmentForm.getQuantity()));

        return equipmentMapper.toDto(equipment);
    }

    @Override
    public List<EquipmentDto> getActivityEquipment(Long activityId) {

        List<Equipment> equipmentList = activityEquipmentRepository.search(activityId);
        return equipmentList.stream().map(equipmentMapper::toDto).toList();
    }
}
