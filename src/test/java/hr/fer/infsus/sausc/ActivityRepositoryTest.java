package hr.fer.infsus.sausc;

import hr.fer.infsus.sausc.model.db.Activity;
import hr.fer.infsus.sausc.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    private Activity activity;

    @BeforeEach
    public void setUp() {
        activity = new Activity();
        activity.setName("Football");
        activity.setDescription("Football class");
        activity.setPricePerHour(20.0);
        activity.setAdministrator(null);
    }

    @Test
    public void testSaveActivity() {
        Activity savedActivity = activityRepository.save(activity);

        assertThat(savedActivity).isNotNull();
        assertThat(savedActivity.getIdActivity()).isNotNull();
    }

    @Test
    public void testFindById() {
        Activity savedActivity = activityRepository.save(activity);

        Optional<Activity> foundActivity = activityRepository.findById(savedActivity.getIdActivity());

        assertThat(foundActivity).isPresent();
        assertThat(foundActivity.get().getIdActivity()).isEqualTo(savedActivity.getIdActivity());
    }

    @Test
    public void testFindAll() {
        activityRepository.save(activity);

        List<Activity> activities = activityRepository.findAll();

        assertThat(activities).isNotEmpty();
        assertThat(activities.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void testDeleteActivity() {
        Activity savedActivity = activityRepository.save(activity);

        activityRepository.deleteById(savedActivity.getIdActivity());

        Optional<Activity> deletedActivity = activityRepository.findById(savedActivity.getIdActivity());
        assertThat(deletedActivity).isNotPresent();
    }
}

