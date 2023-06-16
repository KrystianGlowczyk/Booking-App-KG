package com.krglow.booking.app.controller.movie;

import static com.krglow.booking.app.dataset.movie.SqlMovieInit.DAY_VALUE;
import static com.krglow.booking.app.dataset.movie.SqlMovieInit.ID_VALUE;
import static com.krglow.booking.app.dataset.movie.SqlMovieInit.ITEM;
import static com.krglow.booking.app.dataset.movie.SqlMovieInit.MOVIE;
import static com.krglow.booking.app.dataset.movie.SqlMovieInit.MOVIE_TITLE_VALUE;
import static com.krglow.booking.app.dataset.movie.SqlMovieInit.SAMPLE_DAY;
import static com.krglow.booking.app.dataset.movie.SqlMovieInit.SAMPLE_ID;
import static com.krglow.booking.app.dataset.movie.SqlMovieInit.SAMPLE_MOVIE_TITLE;
import static com.krglow.booking.app.dataset.movie.SqlMovieInit.SAMPLE_SCREENING_TIME;
import static com.krglow.booking.app.dataset.movie.SqlMovieInit.SAMPLE_TIME;
import static com.krglow.booking.app.dataset.movie.SqlMovieInit.SCREENING_TIME_VALUE;
import static com.krglow.booking.app.dataset.movie.SqlMovieInit.TIME_VALUE;
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
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getMoviesByTimeRangeTest() throws Exception {
        MvcResult result = mockMvc.perform(get(MOVIE).param(SAMPLE_DAY, DAY_VALUE).param(SAMPLE_TIME, TIME_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ITEM + SAMPLE_ID).value(ID_VALUE))
                .andExpect(jsonPath(ITEM + SAMPLE_MOVIE_TITLE).value(MOVIE_TITLE_VALUE))
                .andExpect(jsonPath(ITEM + SAMPLE_SCREENING_TIME).value(SCREENING_TIME_VALUE))
                .andReturn();
        assertNotNull(result);
    }

}
