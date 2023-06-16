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

CREATE VIEW V_MOVIES AS
SELECT s.id AS id, m.title AS movie_title, s.start_time AS screening_time
FROM MOVIES m
JOIN SCREENINGS s ON m.id = s.movie_id
WHERE m.deleted = FALSE
ORDER BY m.title, s.start_time;