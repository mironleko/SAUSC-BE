package hr.fer.infsus.sausc;

import hr.fer.infsus.sausc.mapper.ActivityMapper;
import hr.fer.infsus.sausc.model.db.Activity;
import hr.fer.infsus.sausc.repository.ActivityRepository;
import hr.fer.infsus.sausc.rest.model.*;
import hr.fer.infsus.sausc.service.ActivityService;
import hr.fer.infsus.sausc.service.impl.ActivityServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ActivityMapper activityMapper;

    @InjectMocks
    private ActivityServiceImpl activityService;

    private ActivityForm activityForm;
    private Activity activity;
    private ActivityDto activityDto;

    @BeforeEach
    public void init() {
        activityForm = new ActivityForm()
                .name("Test Activity")
                .description("This is a test activity")
                .pricePerHour(50.0)
                .idUser(null);

        activity = new Activity();
        activity.setIdActivity(1L);
        activity.setName("Test Activity");
        activity.setDescription("This is a test activity");
        activity.setPricePerHour(50.0);

        activityDto = new ActivityDto();
        activityDto.setIdActivity(1L);
        activityDto.setName("Test Activity");
        activityDto.setDescription("This is a test activity");
        activityDto.setPricePerHour(50.0);
    }

    @Test
    public void testAddActivity() {
        given(activityMapper.toEntity(activityForm)).willReturn(activity);
        given(activityRepository.save(activity)).willReturn(activity);
        given(activityMapper.toDto(activity)).willReturn(activityDto);

        ActivityDto createdActivity = activityService.addActivity(activityForm);

        verify(activityMapper).toEntity(activityForm);
        verify(activityRepository).save(activity);
        verify(activityMapper).toDto(activity);

        assertEquals(activityDto, createdActivity);
    }

    @Test
    public void testSearchActivities() {
        ActivitySearchRequestDto searchRequestDto = new ActivitySearchRequestDto();
        searchRequestDto.setName("Test Activity");

        given(activityRepository.search("Test Activity")).willReturn(List.of(activity));
        given(activityMapper.toDto(activity)).willReturn(activityDto);

        List<ActivityDto> activities = activityService.searchActivities(searchRequestDto);

        verify(activityRepository).search("Test Activity");

        assertEquals(1, activities.size());
        assertEquals(activityDto, activities.get(0));
    }

    @Test
    public void testGetActivity() {
        given(activityRepository.findById(1L)).willReturn(Optional.of(activity));
        given(activityMapper.toDto(activity)).willReturn(activityDto);

        ActivityDto foundActivity = activityService.getActivity(1L);

        verify(activityRepository).findById(1L);
        verify(activityMapper).toDto(activity);

        assertEquals(activityDto, foundActivity);
    }

    @Test
    public void testGetActivityNotFound() {
        given(activityRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> activityService.getActivity(1L));
    }

    @Test
    public void testUpdateActivity() {
        given(activityRepository.findById(1L)).willReturn(Optional.of(activity));
        given(activityRepository.save(activity)).willReturn(activity);
        given(activityMapper.toDto(activity)).willReturn(activityDto);

        ActivityDto updatedActivity = activityService.updateActivity(1L, activityForm);

        verify(activityRepository,times(2)).findById(1L);
        verify(activityMapper).toActivity(activity, activityForm);
        verify(activityRepository).save(activity);
        verify(activityMapper).toDto(activity);

        assertEquals(activityDto, updatedActivity);
    }

    @Test
    public void testUpdateActivityNotFound() {
        given(activityRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> activityService.updateActivity(1L, activityForm));
    }

    @Test
    public void testDeleteActivity() {
        given(activityRepository.findById(1L)).willReturn(Optional.of(activity));

        activityService.deleteActivity(1L);

        verify(activityRepository).findById(1L);
        verify(activityRepository).deleteById(1L);
    }

    @Test
    public void testDeleteActivityNotFound() {
        given(activityRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> activityService.deleteActivity(1L));
    }
}