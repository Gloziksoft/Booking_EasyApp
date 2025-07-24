
# Booking EasyApp

**ENGLISH VERSION**

Booking EasyApp is a web application for managing reservations, built using **Java Spring Boot**. It allows user registration, reservation creation/editing, and management. It includes REST API, frontend templates using Thymeleaf, and is ready for deployment via Docker.

## 🧰 Technologies

- Java 17
- Spring Boot
- Spring MVC + Security
- Thymeleaf
- JPA / Hibernate
- MySQL
- Docker + Docker Compose
- Maven

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/gloziksoft/booking/          # Backend logic
│   │   ├── controllers                        # Web and REST controllers
│   │   ├── data                               # Entities, enums, repositories
│   │   ├── models                             # DTOs, exceptions, services
│   │   └── configuration                      # Security config
│   └── resources/
│       ├── templates/                         # Thymeleaf HTML templates
│       ├── static/                            # CSS, JS, images
│       └── application.properties             # App settings
└── test/                                      # Unit tests
```

## 🚀 Run the Project

### Locally

1. **Requirements:**
    - Java 17+
    - Maven
    - MySQL (or use Docker Compose)

2. **Configure DB:**

Edit `src/main/resources/application.properties`:

```properties
spring.application.name=BookingApp
spring.datasource.url=jdbc:mariadb://localhost:3307/booking_app?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

3. **Run:**

```bash
./mvnw spring-boot:run
```

App runs on `http://localhost:8080`

### Docker

1. Launch via Docker Compose:

```bash
docker-compose up --build
```

2. Services:
    - Backend: `http://localhost:8080`
    - Database: `localhost:3307`

## 🧪 Testing

```bash
./mvnw test
```

## 📌 Features

- User registration/login
- Create, edit, delete reservations
- REST API (`/api/reservations`)
- HTML pages (home, about, offers)
- Custom error pages (404, 500, etc.)
- Simple admin panel

## 🔐 Security

- Spring Security with authentication
- Access control on routes

## 🗃️ Database Backup

SQL backup is available as:
```
backup.sql
```

## 📋 Future Plans

- Multilanguage support
- Email notifications
- Role-based access
- Responsive UI with React/Vue

## 🧑‍💻 Author

Peter Glozik — [https://www.linkedin.com/in/peter-gl%C3%B3zik-a7292b272)

## 📝 License

MIT License


---

**SLOVENSKÁ VERZIA**

Booking EasyApp je webová aplikácia pre správu rezervácií, vytvorená v prostredí **Java Spring Boot**. Umožňuje registráciu používateľov, vytváranie, úpravu a správu rezervácií. Obsahuje REST API, šablóny s Thymeleaf a podporuje nasadenie pomocou Dockeru.

## 🧰 Technológie

- Java 17
- Spring Boot
- Spring MVC + Security
- Thymeleaf
- JPA / Hibernate
- MySQL
- Docker + Docker Compose
- Maven

## 📁 Štruktúra projektu

```
src/
├── main/
│   ├── java/com/gloziksoft/booking/          # Backend logika
│   │   ├── controllers                        # Webové a REST kontroléry
│   │   ├── data                               # Entitné triedy, enumy, repozitáre
│   │   ├── models                             # DTO, výnimky a služby
│   │   └── configuration                      # Konfigurácia bezpečnosti
│   └── resources/
│       ├── templates/                         # HTML šablóny (Thymeleaf)
│       ├── static/                            # CSS, JS, obrázky
│       └── application.properties             # Nastavenia aplikácie
└── test/                                      # Unit testy
```

## 🚀 Spustenie projektu

### Lokálne

1. **Vyžaduje:**
    - Java 17+
    - Maven
    - MySQL (alebo Docker Compose)

2. **Konfigurácia databázy:**

Úprava `application.properties`:

```properties
spring.application.name=BookingApp
spring.datasource.url=jdbc:mariadb://localhost:3307/booking_app?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

3. **Spustenie:**

```bash
./mvnw spring-boot:run
```

Aplikácia bude bežať na `http://localhost:8080`

### Docker

1. Spusti pomocou Docker Compose:

```bash
docker-compose up --build
```

2. Služby:
    - Backend: `http://localhost:8080`
    - Databáza: `localhost:3307`

## 🧪 Testovanie

```bash
./mvnw test
```

## 📌 Funkcionality

- Registrácia a prihlásenie
- Tvorba, úprava a mazanie rezervácií
- REST API (`/api/reservations`)
- HTML stránky (domov, ponuka, o nás)
- Vlastné chybové stránky (404, 500...)
- Jednoduchý administračný panel

## 🔐 Bezpečnosť

- Spring Security
- Ochrana citlivých ciest

## 🗃️ Záloha databázy

Záloha sa nachádza v súbore:
```
backup.sql
```

## 📋 Budúci vývoj

- Podpora viacerých jazykov
- Email notifikácie
- Detailnejší systém rolí
- Responzívne UI (napr. React/Vue)

## 🧑‍💻 Autor

Peter Glozik — [https://www.linkedin.com/in/peter-gl%C3%B3zik-a7292b272)

## 📝 Licencia

MIT licencia

