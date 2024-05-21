package hr.fer.infsus.sausc.service.impl;

import hr.fer.infsus.sausc.mapper.ActivityMapper;
import hr.fer.infsus.sausc.model.db.Activity;
import hr.fer.infsus.sausc.repository.ActivityRepository;
import hr.fer.infsus.sausc.rest.model.ActivityDto;
import hr.fer.infsus.sausc.rest.model.ActivityForm;
import hr.fer.infsus.sausc.rest.model.ActivitySearchRequestDto;
import hr.fer.infsus.sausc.rest.model.ListActivitySearchResponseDto;
import hr.fer.infsus.sausc.service.ActivityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    @Transactional
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

    @Override
    public ActivityDto getActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity with ID: " + activityId + " not found"));

        return activityMapper.toDto(activity);
    }

    @Transactional
    @Override
    public ActivityDto updateActivity(Long activityId, ActivityForm activityForm) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity with ID: " + activityId + " not found"));

        activityMapper.toActivity(activity, activityForm);
        activityRepository.save(activity);

        return getActivity(activityId);
    }

    @Transactional
    @Override
    public void deleteActivity(Long activityId) {
        activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity with ID: " + activityId + " not found"));
        activityRepository.deleteById(activityId);
    }
}
