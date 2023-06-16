CREATE TABLE MOVIES (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255),
  deleted BOOLEAN
);

CREATE TABLE SCREENING_ROOMS (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  room_name VARCHAR(255),
  number_of_rows BIGINT,
  seats_per_row BIGINT,
  deleted BOOLEAN
);

CREATE TABLE SCREENINGS (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  movie_id BIGINT,
  screening_room_id BIGINT,
  start_time TIMESTAMP,
  deleted BOOLEAN,
  FOREIGN KEY (movie_id) REFERENCES MOVIES(id),
  FOREIGN KEY (screening_room_id) REFERENCES SCREENING_ROOMS(id)
);

CREATE TABLE RESERVATIONS (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  surname VARCHAR(255),
  total_amount DECIMAL(19, 2),
  expiration_time TIMESTAMP,
  confirmed BOOLEAN,
  screening_id BIGINT,
  confirmation_token VARCHAR(255),
  email VARCHAR(255),
  deleted BOOLEAN,
  FOREIGN KEY (screening_id) REFERENCES SCREENINGS(id)
);

CREATE TABLE SEATS (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  screening_id BIGINT,
  row_number INT,
  number INT,
  reserved BOOLEAN,
  reservation_id BIGINT,
  deleted BOOLEAN,
  FOREIGN KEY (screening_id) REFERENCES SCREENINGS(id),
  FOREIGN KEY (reservation_id) REFERENCES RESERVATIONS(id)
);

CREATE TABLE VOUCHERS (
  code VARCHAR(255) PRIMARY KEY,
  discount DECIMAL(19, 2),
  deleted BOOLEAN
);

CREATE INDEX idx_screenings_movie_id ON SCREENINGS(movie_id);
CREATE INDEX idx_screenings_screening_room_id ON SCREENINGS(screening_room_id);
CREATE INDEX idx_reservations_screening_id ON RESERVATIONS(screening_id);
CREATE INDEX idx_seats_screening_id ON SEATS(screening_id);
CREATE INDEX idx_seats_reservation_id ON SEATS(reservation_id);
CREATE INDEX idx_screenings_start_time ON SCREENINGS(start_time);
CREATE INDEX idx_reservations_expiration_time ON RESERVATIONS(expiration_time);
CREATE INDEX idx_seats_row_number ON SEATS(row_number);
CREATE INDEX idx_seats_number ON SEATS(number);
