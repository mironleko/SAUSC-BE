package hr.fer.infsus.sausc;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.infsus.sausc.controller.ActivityController;
import hr.fer.infsus.sausc.rest.model.*;
import hr.fer.infsus.sausc.service.ActivityService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ActivityController.class)
public class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testApiV1ActivityPost() throws Exception {
        ActivityForm activityForm = new ActivityForm()
                .name("Test Activity")
                .description("This is a test activity")
                .pricePerHour(50.0)
                .idUser(null);

        ActivityDto activityDto = new ActivityDto()
                .idActivity(1L)
                .name("Test Activity")
                .description("This is a test activity")
                .pricePerHour(50.0);

        given(activityService.addActivity(ArgumentMatchers.any(ActivityForm.class))).willReturn(activityDto);

        ResultActions response = mockMvc.perform(post("/api/v1/activity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(activityForm)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.idActivity", is(1)))
                .andExpect(jsonPath("$.name", is("Test Activity")))
                .andExpect(jsonPath("$.description", is("This is a test activity")))
                .andExpect(jsonPath("$.pricePerHour", is(50.0)));
    }

    @Test
    public void testApiV1ActivitiesPost() throws Exception {
        ActivitySearchRequestDto searchRequestDto = new ActivitySearchRequestDto().name("Test Activity");

        ActivityDto activityDto = new ActivityDto()
                .idActivity(1L)
                .name("Test Activity")
                .description("This is a test activity")
                .pricePerHour(50.0);

        ListActivitySearchResponseDto responseDto = new ListActivitySearchResponseDto()
                .activities(List.of(activityDto));

        given(activityService.searchActivities(ArgumentMatchers.any(ActivitySearchRequestDto.class))).willReturn(List.of(activityDto));

        ResultActions response = mockMvc.perform(post("/api/v1/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.activities[0].idActivity", is(1)))
                .andExpect(jsonPath("$.activities[0].name", is("Test Activity")))
                .andExpect(jsonPath("$.activities[0].description", is("This is a test activity")))
                .andExpect(jsonPath("$.activities[0].pricePerHour", is(50.0)));
    }

    @Test
    public void testApiV1ActivityActivityIdGet() throws Exception {
        ActivityDto activityDto = new ActivityDto()
                .idActivity(1L)
                .name("Test Activity")
                .description("This is a test activity")
                .pricePerHour(50.0);

        given(activityService.getActivity(1L)).willReturn(activityDto);

        ResultActions response = mockMvc.perform(get("/api/v1/activity/{activityId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.idActivity", is(1)))
                .andExpect(jsonPath("$.name", is("Test Activity")))
                .andExpect(jsonPath("$.description", is("This is a test activity")))
                .andExpect(jsonPath("$.pricePerHour", is(50.0)));
    }

    @Test
    public void testApiV1ActivityActivityIdPut() throws Exception {
        ActivityForm activityForm = new ActivityForm()
                .name("Updated Activity")
                .description("This is an updated test activity")
                .pricePerHour(60.0)
                .idUser(null);

        ActivityDto activityDto = new ActivityDto()
                .idActivity(1L)
                .name("Updated Activity")
                .description("This is an updated test activity")
                .pricePerHour(60.0);

        given(activityService.updateActivity(ArgumentMatchers.anyLong(), ArgumentMatchers.any(ActivityForm.class))).willReturn(activityDto);

        ResultActions response = mockMvc.perform(put("/api/v1/activity/{activityId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(activityForm)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.idActivity", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Activity")))
                .andExpect(jsonPath("$.description", is("This is an updated test activity")))
                .andExpect(jsonPath("$.pricePerHour", is(60.0)));
    }

    @Test
    public void testApiV1ActivityActivityIdDelete() throws Exception {
        doNothing().when(activityService).deleteActivity(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/activity/{activityId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().string("Activity with id: 1 deleted successfully."));
    }
}
