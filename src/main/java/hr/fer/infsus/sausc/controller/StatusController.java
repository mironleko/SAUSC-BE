package hr.fer.infsus.sausc.controller;

import hr.fer.infsus.sausc.rest.api.StatusApi;
import hr.fer.infsus.sausc.rest.model.ListStatusDto;
import hr.fer.infsus.sausc.rest.model.StatusDto;
import hr.fer.infsus.sausc.rest.model.StatusForm;
import hr.fer.infsus.sausc.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StatusController implements StatusApi {
    private final StatusService statusService;
    @Override
    public ResponseEntity<StatusDto> apiV1StatusPost(StatusForm statusForm) {
        return ResponseEntity.ok(statusService.createStatus(statusForm));
    }

    @Override
    public ResponseEntity<String> apiV1StatusStatusIdDelete(Long statusId) {
        statusService.deleteStatus(statusId);
        return ResponseEntity.ok("Status with id: " + statusId + " deleted successfully.");
    }

    @Override
    public ResponseEntity<StatusDto> apiV1StatusStatusIdGet(Long statusId) {
        return ResponseEntity.ok(statusService.getStatus(statusId));
    }

    @Override
    public ResponseEntity<StatusDto> apiV1StatusStatusIdPut(Long statusId, StatusForm statusForm) {
        return ResponseEntity.ok(statusService.updateStatus(statusId,statusForm));
    }

    @Override
    public ResponseEntity<ListStatusDto> apiV1StatusesGet() {
        List<StatusDto> statuses = statusService.getStatuses();
        return ResponseEntity.ok(new ListStatusDto().statuses(statuses));
    }
}
