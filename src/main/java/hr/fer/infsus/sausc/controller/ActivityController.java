package hr.fer.infsus.sausc.controller;

import hr.fer.infsus.sausc.rest.api.ActivityApi;
import hr.fer.infsus.sausc.rest.model.ActivityDto;
import hr.fer.infsus.sausc.rest.model.ActivityForm;
import hr.fer.infsus.sausc.rest.model.ActivitySearchRequestDto;
import hr.fer.infsus.sausc.rest.model.ListActivitySearchResponseDto;
import hr.fer.infsus.sausc.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ActivityController implements ActivityApi {
    private final ActivityService activityService;

    @Override
    public ResponseEntity<ListActivitySearchResponseDto> apiV1ActivitiesPost(ActivitySearchRequestDto activitySearchRequestDto) {

        List<ActivityDto> activities = activityService.searchActivities(activitySearchRequestDto);
        ListActivitySearchResponseDto responseDto = new ListActivitySearchResponseDto().activities(activities);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<String> apiV1ActivityActivityIdDelete(Long activityId) {
        activityService.deleteActivity(activityId);
        return ResponseEntity.ok("Activity with id: " + activityId + " deleted successfully.");
    }

    @Override
    public ResponseEntity<ActivityDto> apiV1ActivityActivityIdGet(Long activityId) {
        return ResponseEntity.ok(activityService.getActivity(activityId));
    }

    @Override
    public ResponseEntity<ActivityDto> apiV1ActivityActivityIdPut(Long activityId, ActivityForm activityForm) {
        return ResponseEntity.ok(activityService.updateActivity(activityId,activityForm));
    }

    @Override
    public ResponseEntity<ActivityDto> apiV1ActivityPost(ActivityForm activityForm) {
        return ResponseEntity.ok(activityService.addActivity(activityForm));
    }
}
