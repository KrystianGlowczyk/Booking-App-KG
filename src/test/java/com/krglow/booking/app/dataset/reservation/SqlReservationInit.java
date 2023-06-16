package com.krglow.booking.app.dataset.reservation;

import static com.krglow.booking.app.dataset.SqlCommonInit.FORWARD_SLASH;


public interface SqlReservationInit {

    String RESERVATION = FORWARD_SLASH + "reservations";

    String SAMPLE_RESERVATION_ID = "$.reservationId";
    String SAMPLE_TOTAL_AMOUNT = "$.totalAmount";
    String SAMPLE_EXPIRATION_TIME = "$.expirationTime";
    String SAMPLE_CONFIRMATION_LINK = "$.confirmationLink";

}
