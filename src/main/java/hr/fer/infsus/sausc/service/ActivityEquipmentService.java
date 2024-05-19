package hr.fer.infsus.sausc.service;

import hr.fer.infsus.sausc.rest.model.EquipmentDto;
import hr.fer.infsus.sausc.rest.model.EquipmentForm;
import hr.fer.infsus.sausc.rest.model.ListEquipmentDto;

import java.util.List;

public interface ActivityEquipmentService {

    EquipmentDto addEquipment(Long activityId,EquipmentForm equipmentForm);

    List<EquipmentDto> getActivityEquipment(Long activityId);
}
