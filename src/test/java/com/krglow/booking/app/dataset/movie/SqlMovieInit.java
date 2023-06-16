package com.krglow.booking.app.dataset.movie;

import static com.krglow.booking.app.dataset.SqlCommonInit.FORWARD_SLASH;


public interface SqlMovieInit {

    String SAMPLE_DAY = "day";
    String SAMPLE_ID = "id";
    String SAMPLE_MOVIE_TITLE = "movieTitle";
    String SAMPLE_SCREENING_TIME = "screeningTime";
    String SAMPLE_TIME = "time";

    String ITEM = "$[0].";

    String DAY_VALUE = "2024-06-10";
    String ID_VALUE = "3";
    String MOVIE = FORWARD_SLASH + "movies";
    String MOVIE_TITLE_VALUE = "Movie 3";
    String SCREENING_TIME_VALUE = "2024-06-10T16:45:00";
    String TIME_VALUE = "16:00:00";

}
