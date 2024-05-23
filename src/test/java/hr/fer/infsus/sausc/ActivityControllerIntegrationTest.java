package hr.fer.infsus.sausc;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.infsus.sausc.repository.ActivityRepository;
import hr.fer.infsus.sausc.rest.model.ActivityDto;
import hr.fer.infsus.sausc.rest.model.ActivityForm;
import hr.fer.infsus.sausc.rest.model.ActivitySearchRequestDto;
import hr.fer.infsus.sausc.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ActivityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRepository activityRepository;

    private ActivityForm activityForm;

    @BeforeEach
    public void setUp() {
        activityRepository.deleteAll();

        activityForm = new ActivityForm()
                .name("Test Activity")
                .description("This is a test activity")
                .pricePerHour(50.0)
                .idUser(null);
    }

    @Test
    public void testApiV1ActivityPost() throws Exception {
        String activityFormJson = objectMapper.writeValueAsString(activityForm);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/activity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(activityFormJson))
                .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.name").value("Test Activity"))
                .andExpect(jsonPath("$.description").value("This is a test activity"))
                .andExpect(jsonPath("$.pricePerHour").value(50.0));
    }

    @Test
    public void testApiV1ActivitiesPost() throws Exception {
        activityService.addActivity(activityForm);

        ActivitySearchRequestDto searchRequestDto = new ActivitySearchRequestDto().name("Test Activity");

        String searchRequestDtoJson = objectMapper.writeValueAsString(searchRequestDto);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(searchRequestDtoJson))
                .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.activities[0].name").value("Test Activity"))
                .andExpect(jsonPath("$.activities[0].description").value("This is a test activity"))
                .andExpect(jsonPath("$.activities[0].pricePerHour").value(50.0));
    }

    @Test
    public void testApiV1ActivityActivityIdGet() throws Exception {
        ActivityDto activityDto = activityService.addActivity(activityForm);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/activity/{activityId}", activityDto.getIdActivity())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.name").value("Test Activity"))
                .andExpect(jsonPath("$.description").value("This is a test activity"))
                .andExpect(jsonPath("$.pricePerHour").value(50.0));
    }

    @Test
    public void testApiV1ActivityActivityIdPut() throws Exception {
        ActivityDto activityDto = activityService.addActivity(activityForm);

        ActivityForm updatedActivityForm = new ActivityForm()
                .name("Updated Activity")
                .description("This is an updated test activity")
                .pricePerHour(60.0)
                .idUser(null);

        String updatedActivityFormJson = objectMapper.writeValueAsString(updatedActivityForm);

        ResultActions resultActions = mockMvc.perform(put("/api/v1/activity/{activityId}", activityDto.getIdActivity())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedActivityFormJson))
                .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.name").value("Updated Activity"))
                .andExpect(jsonPath("$.description").value("This is an updated test activity"))
                .andExpect(jsonPath("$.pricePerHour").value(60.0));
    }

    @Test
    public void testApiV1ActivityActivityIdDelete() throws Exception {
        ActivityDto activityDto = activityService.addActivity(activityForm);

        mockMvc.perform(delete("/api/v1/activity/{activityId}", activityDto.getIdActivity())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Activity with id: " + activityDto.getIdActivity() + " deleted successfully."));
    }
}
