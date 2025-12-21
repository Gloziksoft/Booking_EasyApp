🔵 POSTUP profesionálneho deploy-u
1. Zmeníš kód lokálne (na svojom PC)

→ commit + push do GitHubu.

2. Na serveri (VM) urobíš pull
cd ~/InsuranceApp
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

6. pull projektu a jar suboru po zmene kodu a vycistenie aj bordelu zaroven po image starom

cd ~/Booking_EasyApp && git fetch --all && git reset --hard origin/main && mvn clean package -DskipTests && docker compose --profile prod up -d --build --force-recreate && docker image prune -f

