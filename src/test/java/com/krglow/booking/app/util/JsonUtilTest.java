package com.krglow.booking.app.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krglow.booking.app.service.reservation.dto.Ticket;


class JsonUtilTest {

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deserialize_ValidJson_ReturnsDeserializedObject() throws JsonMappingException, JsonProcessingException {
        String json = "{\"seat\":\"A1\",\"ticketType\":\"Standard\"}";
        Ticket expectedTicket = new Ticket();
        expectedTicket.setSeat("A1");
        expectedTicket.setTicketType("Standard");

        when(objectMapper.readValue(json, Ticket.class)).thenReturn(expectedTicket);

        Ticket result = JsonUtil.deserialize(json, Ticket.class, objectMapper);

        assertEquals(expectedTicket.getSeat(), result.getSeat());
        assertEquals(expectedTicket.getTicketType(), result.getTicketType());
        verify(objectMapper, times(1)).readValue(json, Ticket.class);
    }

    @Test
    void deserialize_NullJson_ReturnsNull() {
        String json = null;

        Ticket result = JsonUtil.deserialize(json, Ticket.class, objectMapper);

        assertNull(result);
        verifyNoInteractions(objectMapper);
    }

    @Test
    void deserialize_EmptyJson_ReturnsNull() {
        String json = "";

        Ticket result = JsonUtil.deserialize(json, Ticket.class, objectMapper);

        assertNull(result);
        verifyNoInteractions(objectMapper);
    }

    @Test
    void serialize_Object_ReturnsSerializedJson() throws JsonProcessingException {
        Ticket ticket = new Ticket();
        ticket.setSeat("A1");
        ticket.setTicketType("Standard");
        String expectedJson = "{\"seat\":\"A1\",\"ticketType\":\"Standard\"}";

        when(objectMapper.writeValueAsString(ticket)).thenReturn(expectedJson);

        String result = JsonUtil.serialize(ticket, objectMapper);

        assertEquals(expectedJson, result);
        verify(objectMapper, times(1)).writeValueAsString(ticket);
    }

}
