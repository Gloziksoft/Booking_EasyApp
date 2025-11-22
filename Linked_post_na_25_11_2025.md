## SK Verzia

🔵 Budujem vlastnú BookingApp – dnes zdieľam jej architektúru

Dnes posúvam vývoj mojej aplikácie o krok ďalej a prinášam pohľad do vnútra jej architektúry.
Ako full-stack developer sa snažím vytvárať aplikácie, ktoré sú nielen funkčné, ale aj čisté, udržateľné a pripravené na rozširovanie.

Toto je aktuálna podoba architektonickej vrstvy mojej BookingApp 👇

📌 Spring Boot (REST + Service + Repository)
📌 JPA/Hibernate + MySQL
📌 Thymeleaf UI (server-side rendering)
📌 Modulárna štruktúra s mapovaním DTO → Entity (MapStruct)
📌 Jednoznačné oddelenie controller–service–repository
📌 Plná podpora stránkovania, validácií a email notifikácií

💡 Prečo to zdieľam?

Lebo vývoj nie je iba o kóde, ale aj o architektúre, ktorá umožní aplikácii ďalej rásť.
Posledné dni som pracoval na refaktoringu stránkovania rezervácií – výsledkom je oveľa čistejší a stabilnejší kód.

Použil som:

čistejšie repository metódy

správne separovanie „List vs Page“

optimalizované EntityGraph načítavanie

Tým získala aplikácia:
⚡ rýchlosť
⚡ stabilitu
⚡ konzistentné výsledky pre admina aj používateľa
⚡ jednoduchšie budúce rozšírenia

🎯 Čo bude nasledovať?

V najbližších týždňoch plánujem doplniť:

📝 export rezervácií do PDF a Excelu
📅 kalendárový prehľad rezervácií
📊 admin dashboard
📲 REST API pre budúcu mobilnú aplikáciu
🔔 pripomienkový systém (cron job)

🔽 Diagram architektúry

(prilož obrázok – toto si už máš pripravené)

🙌 Rád si prečítam spätnú väzbu

Ak máš nápady na zlepšenie, sem s nimi.
Každý posun vpred ma teší a rád budem zdieľať ďalšie kroky v budovaní tejto aplikácie.

#development #springboot #java #softwarearchitecture #codingjourney #programming #webdevelopment #cleanarchitecture #designpatterns #springframework #hibernate #jpa


## EN Verazia

🔵 Building my own BookingApp – sharing its architecture today

Today I’m taking my project a step further and sharing a look inside the architecture of my BookingApp.
As a full-stack developer, I aim to build applications that are not only functional, but clean, scalable, and easy to maintain.

Here’s the current architectural overview 👇

📌 Spring Boot (Controllers + Services + Repositories)
📌 JPA/Hibernate + MySQL
📌 Thymeleaf UI (server-side rendering)
📌 Modular DTO ↔ Entity mapping via MapStruct
📌 Clear separation of concerns
📌 Full pagination support, validations, and email notifications

💡 Why am I sharing this?

Because development isn’t only about writing code — it’s about building solid architecture that allows the application to grow.

Over the past days, I refactored the reservation pagination system.
The result? A cleaner, more stable, and predictable solution for both users and administrators.

I focused on:

cleaner repository methods

strict separation of “List vs Page” queries

optimized EntityGraph loading

This improved:
⚡ Performance
⚡ Stability
⚡ Consistency across all views
⚡ Future extensibility

🎯 What’s coming next?

In the next weeks, I plan to add:

📝 Export reservations to PDF and Excel
📅 Calendar view for reservations
📊 Admin dashboard
📲 REST API for a future mobile app
🔔 Reminder & notification system (cron jobs)

🔽 Architecture diagram

(Attach the generated image here.)

🙌 Feedback welcome

If you have suggestions or ideas for improving the architecture, I’d love to hear them.
I’ll continue sharing my progress as the application evolves.

#development #springboot #java #softwarearchitecture #cleanarchitecture #coding #programming #webdevelopment #hibernate #jpa #springframework
