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
    public ResponseEntity<String> apiV1ActivityActivityIdEquipmentEquipmentIdDelete(Long activityId, Long equipmentId) {
        activityEquipmentService.deleteActivityEquipment(activityId,equipmentId);
        return ResponseEntity.ok("Equipment with id: " + equipmentId + " deleted successfully.");
    }

    @Override
    public ResponseEntity<EquipmentDto> apiV1ActivityActivityIdEquipmentEquipmentIdGet(Long activityId, Long equipmentId) {
        return ResponseEntity.ok(activityEquipmentService.getActivityEquipment(activityId, equipmentId));
    }

    @Override
    public ResponseEntity<EquipmentDto> apiV1ActivityActivityIdEquipmentEquipmentIdPut(Long activityId, Long equipmentId, EquipmentForm equipmentForm) {
        return ResponseEntity.ok(activityEquipmentService.updateActivityEquipment(activityId, equipmentId, equipmentForm));
    }

    @Override
    public ResponseEntity<ListEquipmentDto> apiV1ActivityActivityIdEquipmentListGet(Long activityId) {
        ListEquipmentDto listEquipmentDto = new ListEquipmentDto();
        listEquipmentDto.equipmentList(activityEquipmentService.getActivityEquipmentList(activityId));
        return ResponseEntity.ok(listEquipmentDto);
    }

    @Override
    public ResponseEntity<EquipmentDto> apiV1ActivityActivityIdEquipmentPost(Long activityId, EquipmentForm equipmentForm) {
        return ResponseEntity.ok(activityEquipmentService.addEquipment(activityId, equipmentForm));
    }
}
