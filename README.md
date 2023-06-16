# Booking App

Aplikacja do rezerwowania biletów w kinie.

## Budowanie i uruchamianie aplikacji

### MacOS

Aby zbudować i uruchomić aplikację na MacOS, wykonaj następujące kroki:

1. Otwórz Terminal.
2. Przejdź do katalogu, gdzie znajduje się twój projekt. Możesz to zrobić za pomocą komendy `cd`, a następnie podając ścieżkę do katalogu z projektem. Na przykład, jeśli twój projekt znajduje się w katalogu `Projekty` na pulpicie, komenda mogłaby wyglądać tak: `cd ~/Desktop/Projekty/booking-app`.
3. Przejdź do katalogu `scripts/MacOs` za pomocą komendy `cd scripts/MacOs`.
4. Uruchom skrypt `buildAndRun.sh` za pomocą komendy `./buildAndRun.sh`.
5. Uruchom skrypt `testEndpoints.sh` za pomocą komendy `./testEndpoints.sh`.


### Windows

Aby zbudować i uruchomić aplikację na Windows, wykonaj następujące kroki:

1. Otwórz Wiersz poleceń lub PowerShell.
2. Przejdź do katalogu, gdzie znajduje się twój projekt. Możesz to zrobić za pomocą komendy `cd`, a następnie podając ścieżkę do katalogu z projektem. Na przykład, jeśli twój projekt znajduje się w katalogu `Projekty` na dysku `C`, komenda mogłaby wyglądać tak: `cd C:\Projekty\booking-app`.
3. Przejdź do katalogu `scripts\Windows` za pomocą komendy `cd scripts\Windows`.
4. Uruchom skrypt `buildAndRun.bat` za pomocą komendy `buildAndRun.bat`.
5. Uruchom skrypt `testEndpoints.bat` za pomocą komendy `testEndpoints.bat`.

## Założenia

Założenia dodatkowe:
1. Potwierdzanie rezerwacji:
    - użytkownik po złożeniu rezerwacji otrzymuje link do potwierdzenia rejestracji. Możliwe jest też potwierdzenie rezerwacji poprzez podanie id wykonując ządanie: 
        curl -X 'POST' \
        'http://localhost:8080/reservations/1/confirmation' \
        -H 'accept: */*' \
        -d ''
    - zaimplementowana jest możliwość wysyłki maila z linkiem na podany przez użytkownika wcześniej email (obecnie jest to zakomentowane w kodzie)
    - zaimplementowany jest scheduler, który sprawdza czy czas wygasniecia niepotwierdzonej rezerwacji dobiega końca i zakańcza rezerwację jeśli nie została potwierdzona

2. Różne ceny biletów:
    - ceny biletów są wiekszę o 4 PLN w weekendy (od piątku od godziny 14:00 do niedzieli do 23)
    - użytkownik może podać kod vouchera, na chwile obecna w bazie jest jeden voucher: kod "777" wartość: 50% zniżki

3. Stworzona została dokumentacja Swagger pod adresem: localhost:8080/swagger-ui/index.html

4. Kod został pokryty testami funkcjonalnymi i jednostkowymi

5. Przeprowadzona została analiza kodu SonarQube i zrzut znajduje się w katalogu SonarQube
![Raport SonarQube](/SonarQube/raportSonarQube.png "Raport SonarQube")