# Changelog
## 07.12.23
 - podstawowy model danych
 - import plików działający w backendzie
 
## 14.12.23
 - działająca baza danych z przykładowymi danymi
 - endpointy w backendzie
 - pobieranie listy dostępnych quizów
 - wyświetlanie rekordów i wyników wybranego quizu
 

## SQLite
Baza danych znajduje się w backend/mydb.sqlite

### Problemy

1. Z jakiegoś powodu, jeśli spróbujemy zrobić import quizu, a serwer nie odpowiada (np. localhost jak backend jest wyłączony),
a następnie zamkniemy aplikację ikonką 'X', to ok. minuty program działa w tle po czym kończy się normalnie.

## Model danych i aplikacji (do zmiany)
Model danych
![Model](docs/ModelDanych.jpg)

Schemat aplikacji
![Schemat aplikacji](docs/AppSchematics.jpg)

Przejrzysta historia commitów
![Slalom](docs/slalom.png)

