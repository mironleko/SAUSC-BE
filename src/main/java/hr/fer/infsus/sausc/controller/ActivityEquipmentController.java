package hr.fer.infsus.sausc.controller;

import hr.fer.infsus.sausc.rest.api.ActivityEquipmentApi;
import hr.fer.infsus.sausc.rest.model.EquipmentDto;
import hr.fer.infsus.sausc.rest.model.EquipmentForm;
import hr.fer.infsus.sausc.rest.model.ListEquipmentDto;
import hr.fer.infsus.sausc.service.ActivityEquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ActivityEquipmentController implements ActivityEquipmentApi {

    private final ActivityEquipmentService activityEquipmentService;

    @Override
    public ResponseEntity<ListEquipmentDto> apiV1ActivityActivityIdEquipmentListGet(Long activityId) {
        ListEquipmentDto listEquipmentDto = new ListEquipmentDto();
        listEquipmentDto.equipmentList(activityEquipmentService.getActivityEquipment(activityId));
        return ResponseEntity.ok(listEquipmentDto);
    }

    @Override
    public ResponseEntity<EquipmentDto> apiV1ActivityActivityIdEquipmentPost(Long activityId, EquipmentForm equipmentForm) {
        return ResponseEntity.ok(activityEquipmentService.addEquipment(activityId,equipmentForm));
    }
}
