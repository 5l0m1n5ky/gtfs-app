
# GTFS-app


Aplikacja przeznaczona do śledzenia publikacji danych real-time związanych z komunikacją miejską. Pozyskany strumień danych w ramach standardu GTFS, zwierający informacje takich jak pozycje pojazdów, informacje o pojazdach oraz alerty na temat tras przejazdu wyświetlany jest na mapie. Interface strowania wyposażony będzie w wyświetlanie szczegółowych informacji, filtrowania oraz danych użytkownika.



## Schemat działania

API oparte na mikroserwisach odpytuje cyklicznie zasoby plików formacie GTFS ze źródła:

https://github.com/mbta/gtfs-documentation/blob/master/reference/gtfs-realtime.md#json-feeds



Dane umieszczane są w strumieniu Kafki. Strumieniowe dane podlegają autoryzacji i uwierzytelnieniu zanim zostaną opublikowane w bramce WebSocket (frontend pozyskuje dane w ten sposób). Jednocześnie frontend umożliwia filtrowanie pozyskanych danych, np linie tramwajowe lub autobusowe. Dane o charakterze stałym, tj. użytkownicy, linie komunikacyjne itp. umieszczane są na stałe w bazie danych

## Założenia projektowe

- Backend: Spring Boot, Kafka, Asynchroniczność, Mikroserwisy, JUnit & Mockito

- Frontend: Angular 19, Singals, RxJS, NgRx, Weboscket, Leaflet (mapa), Angular Materials, Cyrpess

- format GTFS: https://gtfs.org/documentation/overview/








