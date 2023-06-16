-- Tworzenie widoku V_MOVIES

CREATE VIEW V_MOVIES AS
SELECT s.id AS id, m.title AS movie_title, s.start_time AS screening_time
FROM MOVIES m
JOIN SCREENINGS s ON m.id = s.movie_id
WHERE m.deleted = FALSE
ORDER BY m.title, s.start_time;