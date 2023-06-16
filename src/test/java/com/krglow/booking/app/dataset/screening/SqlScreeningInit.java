package com.krglow.booking.app.dataset.screening;

import static com.krglow.booking.app.dataset.SqlCommonInit.FORWARD_SLASH;


public interface SqlScreeningInit {

    String ID_1 = FORWARD_SLASH + "1";
    String ID_VALUE = "1";

    String SAMPLE_ID = "id";
    String SAMPLE_MOVIE_TITLE = "movieTitle";
    String SAMPLE_ROOM = "room";

    String MOVIE_TITLE_VALUE = "Movie 1";
    String ROOM_VALUE = "Room 1";
    String SCREENING = FORWARD_SLASH + "screenings";
}
