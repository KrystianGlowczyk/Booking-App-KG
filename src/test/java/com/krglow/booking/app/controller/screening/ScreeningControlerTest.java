package com.krglow.booking.app.controller.screening;

import static com.krglow.booking.app.dataset.screening.SqlScreeningInit.ID_1;
import static com.krglow.booking.app.dataset.screening.SqlScreeningInit.ID_VALUE;
import static com.krglow.booking.app.dataset.screening.SqlScreeningInit.MOVIE_TITLE_VALUE;
import static com.krglow.booking.app.dataset.screening.SqlScreeningInit.ROOM_VALUE;
import static com.krglow.booking.app.dataset.screening.SqlScreeningInit.SAMPLE_ID;
import static com.krglow.booking.app.dataset.screening.SqlScreeningInit.SAMPLE_MOVIE_TITLE;
import static com.krglow.booking.app.dataset.screening.SqlScreeningInit.SAMPLE_ROOM;
import static com.krglow.booking.app.dataset.screening.SqlScreeningInit.SCREENING;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
class ScreeningControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getScreeningTest() throws Exception {
        MvcResult result = mockMvc.perform(get(SCREENING + ID_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath(SAMPLE_ID).value(ID_VALUE))
                .andExpect(jsonPath(SAMPLE_MOVIE_TITLE).value(MOVIE_TITLE_VALUE))
                .andExpect(jsonPath(SAMPLE_ROOM).value(ROOM_VALUE))
                .andReturn();
        assertNotNull(result);
    }

}
