package hr.fer.infsus.sausc.service;

import hr.fer.infsus.sausc.rest.model.EquipmentDto;
import hr.fer.infsus.sausc.rest.model.EquipmentForm;

import java.util.List;

public interface ActivityEquipmentService {

    EquipmentDto addEquipment(Long activityId,EquipmentForm equipmentForm);

    List<EquipmentDto> getActivityEquipmentList(Long activityId);

    EquipmentDto getActivityEquipment(Long activityId, Long equipmentId);

    EquipmentDto updateActivityEquipment(Long activityId, Long equipmentId, EquipmentForm equipmentForm);

    void deleteActivityEquipment(Long activityId, Long equipmentId);
}
