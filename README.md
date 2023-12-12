Model danych
![Model](docs/ModelDanych.jpg)

Schemat aplikacji
![Schemat aplikacji](docs/AppSchematics.jpg)



## Postgresql
### Zarządzanie DBMS na linuksie

Dostęp do konsoli postgresa:
1. $ `sudo -i -u postgres`
2. postgres$ `pgsql`

Wyjście z konsoli postgresa do terminala:
1. postgres=# `\q`
2. postgres$ `exit`

Odpalanie usługi postgresa:
1. $ `sudo systemctl start postgresql`


Sprawdzanie stanu usługi postgresa:
1. $ `sudo systemctl status postgresql`

Zatrzymanie usługi postgresa:
1. $ `sudo systemctl stop postgresql`

Lista wszystkich uruchomionych usług:
1. $ `sudo systemctl --type=service --state=running`

### Założenia

1. W bazie postgresowej istnieje użytkownik o nazwie "sianko", który loguje się do systemu hasłem "sianko"
2. Baza danych nazywa się quiz_manager i użytkownik sianko ma do niej pełny dostęp



### Problemy

1. Z jakiegoś powodu, jeśli spróbujemy zrobić import quizu, a serwer nie odpowiada (np. localhost jak backend jest wyłączony),
a następnie zamkniemy aplikację ikonką 'X', to ok. minuty program działa w tle po czym kończy się normalnie.