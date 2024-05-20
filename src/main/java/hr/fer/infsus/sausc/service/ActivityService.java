package hr.fer.infsus.sausc.service;

import hr.fer.infsus.sausc.rest.model.ActivityDto;
import hr.fer.infsus.sausc.rest.model.ActivityForm;
import hr.fer.infsus.sausc.rest.model.ActivitySearchRequestDto;
import hr.fer.infsus.sausc.rest.model.ListActivitySearchResponseDto;

import java.util.List;

public interface ActivityService {

    ActivityDto addActivity(ActivityForm activityForm);

    List<ActivityDto> searchActivities(ActivitySearchRequestDto activitySearchRequestDto);

    ActivityDto getActivity(Long activityId);

    ActivityDto updateActivity(Long activityId, ActivityForm activityForm);

    void deleteActivity(Long activityId);
}
