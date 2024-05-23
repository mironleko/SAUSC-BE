package hr.fer.infsus.sausc;


import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.infsus.sausc.controller.ReservationController;
import hr.fer.infsus.sausc.rest.model.*;
import hr.fer.infsus.sausc.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateReservation() throws Exception {
        ReservationForm reservationForm = new ReservationForm();
        reservationForm.setStartTime(LocalDateTime.of(2024, 6, 1, 10, 0));
        reservationForm.setEndTime(LocalDateTime.of(2024, 6, 1, 12, 0));
        reservationForm.setNumberOfParticipants(5);
        reservationForm.setSportsCenterMemberId(1L);
        reservationForm.setIdActivity(1L);
        reservationForm.setIdStatus(1L);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setIdReservation(1L);
        reservationDto.setStartTime(LocalDateTime.of(2024, 6, 1, 10, 0));
        reservationDto.setEndTime(LocalDateTime.of(2024, 6, 1, 12, 0));
        reservationDto.setNumberOfParticipants(5);
        reservationDto.setReservationPrice(100.0);

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setIdUser(1L);
        userInfoDto.setFirstName("John");
        userInfoDto.setLastName("Doe");
        userInfoDto.setEmail("john.doe@example.com");
        reservationDto.setSportsCenterMember(userInfoDto);

        ActivityDto activityDto = new ActivityDto();
        activityDto.setIdActivity(1L);
        activityDto.setName("Football");
        activityDto.setDescription("Football");
        activityDto.setPricePerHour(50.0);
        reservationDto.setActivity(activityDto);

        StatusDto statusDto = new StatusDto();
        statusDto.setIdStatus(1L);
        statusDto.setName("Confirmed");
        statusDto.setAbbreviation("CONF");
        reservationDto.setStatus(statusDto);

        given(reservationService.createReservation(ArgumentMatchers.any())).willReturn(reservationDto);

        ResultActions response = mockMvc.perform(post("/api/v1/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservationForm)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation", is(1)))
                .andExpect(jsonPath("$.startTime", is("2024-06-01T10:00:00")))
                .andExpect(jsonPath("$.endTime", is("2024-06-01T12:00:00")))
                .andExpect(jsonPath("$.numberOfParticipants", is(5)))
                .andExpect(jsonPath("$.reservationPrice", is(100.0)))
                .andExpect(jsonPath("$.sportsCenterMember.idUser", is(1)))
                .andExpect(jsonPath("$.sportsCenterMember.firstName", is("John")))
                .andExpect(jsonPath("$.sportsCenterMember.lastName", is("Doe")))
                .andExpect(jsonPath("$.sportsCenterMember.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.activity.idActivity", is(1)))
                .andExpect(jsonPath("$.activity.name", is("Football")))
                .andExpect(jsonPath("$.activity.description", is("Football")))
                .andExpect(jsonPath("$.activity.pricePerHour", is(50.0)))
                .andExpect(jsonPath("$.status.idStatus", is(1)))
                .andExpect(jsonPath("$.status.name", is("Confirmed")))
                .andExpect(jsonPath("$.status.abbreviation", is("CONF")));
    }
}
