INSERT INTO MOVIES (id, title, deleted)
VALUES (1, 'Movie 1', FALSE),
       (2, 'Movie 2', FALSE),
       (3, 'Movie 3', FALSE);

INSERT INTO SCREENING_ROOMS (id, room_name, deleted)
VALUES (1, 'Room 1', FALSE),
       (2, 'Room 2', FALSE),
       (3, 'Room 3', FALSE);

 INSERT INTO SCREENINGS (id, movie_id, screening_room_id, start_time, deleted) VALUES
(1, 1, 1, '2024-06-10 10:00:00', FALSE),
(2, 2, 1, '2024-06-10 13:30:00', FALSE),
(3, 3, 2, '2024-06-10 16:45:00', FALSE);

INSERT INTO VOUCHERS (code, discount, deleted)
VALUES ('777', 50, FALSE);

INSERT INTO SEATS (screening_id, row_number, number, reserved, reservation_id, deleted)
VALUES
(1, 1, 1, 0, NULL, 0),
(1, 1, 2, 0, NULL, 0),
(1, 1, 3, 0, NULL, 0),
(1, 1, 4, 0, NULL, 0),
(1, 1, 5, 0, NULL, 0),
(1, 2, 1, 0, NULL, 0),
(1, 2, 2, 0, NULL, 0),
(1, 2, 3, 0, NULL, 0),
(1, 2, 4, 0, NULL, 0),
(1, 2, 5, 0, NULL, 0),
(1, 3, 1, 0, NULL, 0),
(1, 3, 2, 0, NULL, 0),
(1, 3, 3, 0, NULL, 0),
(1, 3, 4, 0, NULL, 0),
(1, 3, 5, 0, NULL, 0),
(1, 4, 1, 0, NULL, 0),
(1, 4, 2, 0, NULL, 0),
(1, 4, 3, 0, NULL, 0),
(1, 4, 4, 0, NULL, 0),
(1, 4, 5, 0, NULL, 0);
