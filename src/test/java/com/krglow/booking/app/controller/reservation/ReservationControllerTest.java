package com.krglow.booking.app.controller.reservation;

import static com.krglow.booking.app.dataset.SqlCommonInit.CHARSET_UTF_8;
import static com.krglow.booking.app.dataset.SqlCommonInit.SEMICOLON;
import static com.krglow.booking.app.dataset.reservation.SqlReservationInit.RESERVATION;
import static com.krglow.booking.app.dataset.reservation.SqlReservationInit.SAMPLE_CONFIRMATION_LINK;
import static com.krglow.booking.app.dataset.reservation.SqlReservationInit.SAMPLE_EXPIRATION_TIME;
import static com.krglow.booking.app.dataset.reservation.SqlReservationInit.SAMPLE_RESERVATION_ID;
import static com.krglow.booking.app.dataset.reservation.SqlReservationInit.SAMPLE_TOTAL_AMOUNT;
import static com.krglow.booking.app.util.JsonUtil.serialize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krglow.booking.app.entity.reservation.ReservationEntity;
import com.krglow.booking.app.repository.reservation.ReservationRepository;
import com.krglow.booking.app.service.reservation.dto.ReservationRequest;
import com.krglow.booking.app.service.reservation.dto.Ticket;


@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private ReservationRepository reservationRepository;

    @Test
    void createReservationTest() throws Exception {

        MvcResult result = mockMvc.perform(post(RESERVATION).content(serialize(sampleCreateReservation(), objectMapper)).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + SEMICOLON + CHARSET_UTF_8))
                .andExpect(jsonPath(SAMPLE_RESERVATION_ID, notNullValue()))
                .andExpect(jsonPath(SAMPLE_TOTAL_AMOUNT, notNullValue()))
                .andExpect(jsonPath(SAMPLE_EXPIRATION_TIME, notNullValue()))
                .andExpect(jsonPath(SAMPLE_CONFIRMATION_LINK, notNullValue()))
                .andReturn();
        assertNotNull(result);
    }

    @Test
    void createReservation_InvalidVoucherCode_ReturnsNotFound() throws Exception {
        ReservationRequest reservationRequest = sampleCreateReservation();
        reservationRequest.setVoucher("INVALID");

        MvcResult result = mockMvc.perform(post(RESERVATION).content(serialize(reservationRequest, objectMapper)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
        assertNotNull(result);
    }

    @Test
    void createReservation_InvalidName_ReturnsConflict() throws Exception {
        ReservationRequest reservationRequest = sampleCreateReservation();
        reservationRequest.setName("a");

        MvcResult result = mockMvc.perform(post(RESERVATION).content(serialize(reservationRequest, objectMapper)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict()).andReturn();
        assertNotNull(result);
    }

    @Test
    void confirmReservationTest() throws Exception {
        doReturn(getReservationEntity()).when(reservationRepository).findById(anyLong());

        MvcResult result = mockMvc.perform(post(RESERVATION + "/777" + "/confirmation").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Potwierdzenie rejestracji zakończone powodzeniem!"))
                .andReturn();

        assertNotNull(result);
    }

    @Test
    void confirmReservationFunctionalTest() throws Exception {
        String token = "token";
        doReturn(getReservationEntity()).when(reservationRepository).findByConfirmationToken(anyString());
        MvcResult result = mockMvc.perform(get("/reservations/confirm").param("token", token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Rezerwacja potwierdzona!"))
                .andReturn();
        assertNotNull(result);
    }

    private Optional<ReservationEntity> getReservationEntity() {
        ReservationEntity result = new ReservationEntity();
        result.setConfirmationToken("token");
        result.setConfirmed(false);
        result.setDeleted(false);
        result.setEmail("krystian.gloowczyk@gmail.com");
        result.setExpirationTime(LocalDateTime.MAX);
        result.setId(777L);
        result.setName("Krystian");
        result.setScreening(null);
        result.setSeats(null);
        result.setSurname("Kowalski");
        result.setTotalAmount(BigDecimal.TEN);
        return Optional.of(result);
    }

    private ReservationRequest sampleCreateReservation() {
        ReservationRequest result = new ReservationRequest();

        Ticket ticket = new Ticket();
        ticket.setSeat("1-1");
        ticket.setTicketType("STUDENT");

        result.setEmail("krystian.gloowczyk@gmail.com");
        result.setName("Krystian");
        result.setSurname("Kowalski");
        result.setScreeningId(1L);
        result.setSeats(Arrays.asList(ticket));
        return result;
    }

}
