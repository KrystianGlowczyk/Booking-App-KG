#!/bin/bash
echo
curl -X GET 'http://localhost:8080/movies?day=2024-06-10&time=09:30:00&page=0&size=25' -H 'accept: */*'
echo
echo
echo "-------------------"
echo
echo
curl -X GET 'http://localhost:8080/screenings/1' -H 'accept: application/json'
echo
echo
echo "-------------------"
echo
echo
curl -X POST 'http://localhost:8080/reservations' -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "screeningId": 1,
  "seats": [
    {
      "seat": "1-1",
      "ticketType": "ADULT"
    }
  ],
  "name": "Krystian",
  "surname": "Kowalski",
  "email": "krystian@gmail.com",
  "voucher": ""
}'
echo
echo
echo "-------------------"
echo
echo