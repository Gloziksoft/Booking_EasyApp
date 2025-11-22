🔵 POSTUP profesionálneho deploy-u
1. Zmeníš kód lokálne (na svojom PC)

→ commit + push do GitHubu.

2. Na serveri (VM) urobíš pull
cd ~/Booking_EasyApp
git pull

3. Znovu vytvoríš jar

(toto sa dá aj automatizovať)

mvn clean package -DskipTests

4. Rebuildneš Docker image aplikácie
docker compose build app

5. Reštartneš kontajner
docker compose up -d app


🔥 Hotovo
Aplikácia beží s novým kódom.
