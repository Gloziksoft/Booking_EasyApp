# 🕒 Dynamické voľné termíny	Admin nastaví otváracie hodiny a systém ukáže len voľné sloty
# 👥 Role: zákazník vs. admin	Admin spravuje služby, zákazník len rezervuje
# 📧 Email/SMS notifikácie	Po rezervácii alebo zrušení
# 📊 Dashboard pre admina	Prehľad rezervácií, obsadenosť, filtrovanie
# 📱 Responzívne rozhranie (Thymeleaf + Bootstrap)	Pre mobil a desktop
# 🌍 Viacjazyčná podpora (i18n)	Napr. slovenčina, angličtina
# 📆 Kalendarizácia (zobrazenie v kalendári)	Týždenný a denný prehľad
# 📦 Docker + PostgreSQL/MariaDB	Appka beží izolovane a produkčne-ready
# 🔒 Spring Security + šifrované heslá	Bezpečnosť ako štandard
# 🧪 Testy (unit/integration)	Ukážeš, že ti záleží na kvalite kódu
SQL Prikaz admin role

UPDATE user_roles
SET role = 'ADMIN'
WHERE user_id = 1;

