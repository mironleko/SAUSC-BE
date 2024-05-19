package hr.fer.infsus.sausc.service.impl;

import hr.fer.infsus.sausc.mapper.ActivityMapper;
import hr.fer.infsus.sausc.model.db.Activity;
import hr.fer.infsus.sausc.repository.ActivityRepository;
import hr.fer.infsus.sausc.rest.model.ActivityDto;
import hr.fer.infsus.sausc.rest.model.ActivityForm;
import hr.fer.infsus.sausc.rest.model.ActivitySearchRequestDto;
import hr.fer.infsus.sausc.rest.model.ListActivitySearchResponseDto;
import hr.fer.infsus.sausc.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    @Override
    public ActivityDto addActivity(ActivityForm activityForm) {
        return activityMapper.toDto(activityRepository.save(activityMapper.toEntity(activityForm)));
    }

    @Override
    public List<ActivityDto> searchActivities(ActivitySearchRequestDto activitySearchRequestDto) {
        List<Activity> activities = activityRepository
                .search(activitySearchRequestDto.getName());
        return activities.stream().map(activityMapper::toDto).toList();
    }
}
