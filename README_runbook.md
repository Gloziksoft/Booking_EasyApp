🛠️ 1. App Deployment

Táto sekcia popisuje kompletný deployment flow aplikácie Booking_EasyApp – od lokálneho vývoja až po produkčné nasadenie na VM pomocou Dockeru a CI/CD.

📦 Architektúra nasadenia (prehľad)

Aplikácia: Spring Boot (Java 17)

Build: Maven

Container registry: GitHub Container Registry (GHCR)

Runtime: Docker + Docker Compose

CI/CD: GitHub Actions

Produkcia: Oracle Free VM (Ubuntu)

Monitoring: Prometheus + Grafana + cAdvisor (oddelený compose stack)

📁 Štruktúra deployment súborov v repozitári
Booking_EasyApp/
├── docker-compose.app.yml          # Produkčný stack aplikácie
├── docker-compose.monitoring.yml   # Monitoring stack (Prometheus, Grafana, cAdvisor)
├── Dockerfile                      # Build Spring Boot aplikácie
├── .env.example                    # Vzor environment premenných
├── monitoring/
│   └── prometheus.yml              # Prometheus scrape konfigurácia
└── .github/
    └── workflows/
        └── build-push.yml          # CI/CD pipeline


⚠️ .env súbory nikdy nie sú v repozitári
Na VM aj lokálne existujú len ako lokálne konfigurácie podľa .env.example.

🧑‍💻 Lokálny vývoj (Local)

Lokálne sa používa hybridný režim:

PostgreSQL beží v Dockeri

Spring Boot app sa spúšťa z IntelliJ IDEA

Profil: local

Spustenie databázy lokálne:
docker compose -f docker-compose.app.yml up -d db

Spustenie aplikácie:

IntelliJ → Run configuration

Environment:

SPRING_PROFILES_ACTIVE=local

🚀 Produkčné nasadenie (Production)

V produkcii beží celá aplikácia v Dockeri.

Profil: prod

Image: ghcr.io/gloziksoft/booking-app:prod

Produkčný compose stack:
docker compose -p booking -f docker-compose.app.yml up -d


Bežia služby:

app

db

pgadmin

🔄 CI/CD – automatický deploy

Deployment je plne automatický cez GitHub Actions.

Trigger:
push → branch: main

Kroky pipeline:

Build JAR (mvn clean package -DskipTests)

Build Docker image

Push image do GHCR

SSH deploy na VM

Používané tagy image:
ghcr.io/gloziksoft/booking-app:<commit-sha>
ghcr.io/gloziksoft/booking-app:prod

📡 Nasadenie na VM (automaticky aj manuálne)
Automaticky (CI):

GitHub Actions vykoná na VM:

docker compose -p booking down --remove-orphans
docker compose -p booking pull
docker compose -p booking up -d

Manuálne (ak treba):
cd /opt/booking-easyapp
docker compose -p booking -f docker-compose.app.yml pull
docker compose -p booking -f docker-compose.app.yml up -d

🔁 Aktualizácia konfigurácie (SPRÁVNY FLOW)

Zmeny sa nikdy nerobia priamo na VM

✅ Správny postup:

Úprava súborov v repozitári (napr. application-prod.properties)

git commit

git push

CI/CD vytvorí nový Docker image

VM automaticky nasadí novú verziu

🧹 Čistenie a riešenie konfliktov

Ak vzniknú konflikty portov alebo staré kontajnery:

docker compose -p booking down --remove-orphans
docker system prune -f

🧠 Dôležité poznámky

docker-compose.app.yml neobsahuje monitoring

monitoring beží v samostatnom compose projekte

VM je runtime-only, nie zdroj pravdy

repo je jediný zdroj pravdy

🎯 Prečo je tento setup hodnotný

reálny production deployment

oddelený monitoring stack

CI/CD bez manuálnych zásahov

vhodné ako produkčné demo pre job hunting
