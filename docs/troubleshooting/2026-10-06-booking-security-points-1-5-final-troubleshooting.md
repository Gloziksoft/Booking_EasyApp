# 2026-10-06 – Booking VM – Security Points 1–5 – Final Troubleshooting & Recap

## Stav

Všetkých 5 bezpečnostných bodov bolo dokončených, opravených a overených.

---

## Point 1 – DNS

- `bookingapp.gloziksoft.sk` smeruje na verejnú IP Booking VM.
- DNS A záznam bol overený.
- AAAA záznam nie je použitý.

**Stav: ✅ HOTOVÉ**

---

## Point 2 – Verejne dostupné porty

Externé overenie potvrdilo iba:

```text
22/tcp   SSH
80/tcp   HTTP
443/tcp  HTTPS
```

Databázy, monitoring a administračné služby nie sú verejne dostupné.

**Stav: ✅ HOTOVÉ**

---

## Point 3 – Monitoring a administračné rozhrania

Produkčné služby boli obmedzené na localhost/private access.

Booking VM:

```text
127.0.0.1:3000  → Grafana
127.0.0.1:9090  → Prometheus
127.0.0.1:8081  → cAdvisor
127.0.0.1:8082  → pgAdmin
```

Insurance VM:

```text
127.0.0.1:8081  → phpMyAdmin
10.0.0.38:8082  → cAdvisor
```

Prístup k monitoringu a administrácii je riešený cez SSH tunely.

**Stav: ✅ HOTOVÉ**

---

## Point 4 – Databázy a interné služby

Booking:

```text
booking-db → PostgreSQL
```

Insurance:

```text
insuranceapp-db-1 → MySQL
```

Databázy nemajú verejné host porty a sú dostupné iba interne.

**Stav: ✅ HOTOVÉ**

---

# Point 5 – Input validation / security

Overenie zahŕňalo používateľské vstupy a password-reset flow.

### RegistrationDTO

Použité validácie:

```text
@NotBlank
@Size
@Pattern
@Email
```

Overené:

- ✅ prázdne meno/priezvisko → odmietnuté
- ✅ nepovolené znaky v mene/priezvisku → odmietnuté
- ✅ prázdny email → odmietnutý
- ✅ neplatný email → odmietnutý
- ✅ heslo kratšie ako 8 znakov → odmietnuté
- ✅ presne 8 znakov → prijaté
- ✅ potvrdenie hesla → overené

### Login

Login spracováva Spring Security.

Samostatný `LoginDTO` nie je potrebný.

Overené:

- ✅ úspešné prihlásenie
- ✅ nesprávne prihlasovacie údaje → odmietnuté

### ForgotPasswordDTO

```text
@NotBlank
@Email
@Size(max = 254)
```

Overené:

- ✅ prázdny email → odmietnutý
- ✅ neplatný email → odmietnutý
- ✅ backendová `@Email` validácia funguje aj bez HTML5 validácie

HTML `type="email"` zostáva v produkcii ako prvá validačná vrstva.

### ResetPasswordDTO

```text
@NotBlank
@Size(min = 8, max = 72)
```

Reset token je UUID s platnosťou 15 minút.

Overené:

- ✅ 7 znakov → odmietnuté
- ✅ 8 znakov → prijaté
- ✅ rozdielne heslá → odmietnuté
- ✅ platný reset token → reset úspešný
- ✅ po úspešnom resete sa token okamžite zneplatní
- ✅ `reset_token = NULL`
- ✅ `reset_token_expiration = NULL`
- ✅ rovnaký token nie je možné použiť druhýkrát
- ✅ expirovaný token je odmietnutý

### Password security

Heslá sa neukladajú v plaintext forme.

Používa sa:

```text
BCryptPasswordEncoder
```

---

# HTML + Backend validácia

Validácia je riešená na dvoch úrovniach:

```text
HTML5
  ↓
okamžitá kontrola v prehliadači
  ↓
HTTP request
  ↓
Spring / Jakarta Bean Validation
  ↓
@NotBlank / @Email / @Size / @Pattern
```

Backendová validácia je rozhodujúca, pretože HTML validáciu možno obísť priamym HTTP requestom.

To bolo prakticky overené pri `ForgotPasswordDTO`: `peto7724@` bol odmietnutý backendom po dočasnom odstránení HTML `type="email"`.

---

# SMTP / Password Reset troubleshooting

Počas testovania Forgot Password bol nájdený problém s duplicitnými environment premennými.

Pôvodne existovali:

```text
MAIL_USERNAME
MAIL_PASSWORD

SPRING_MAIL_USERNAME
SPRING_MAIL_PASSWORD
```

Spring konfigurácia používa:

```text
spring.mail.username=${SPRING_MAIL_USERNAME}
spring.mail.password=${SPRING_MAIL_PASSWORD}
```

Preto boli redundantné `MAIL_*` premenné odstránené.

Po oprave:

- ✅ reset email bol doručený
- ✅ email obsahoval reset link
- ✅ token bol uložený v DB
- ✅ expiration bol nastavený
- ✅ reset hesla fungoval

---

# Finálna rekapitulácia

| Bod | Stav |
|---|---|
| 1. DNS | ✅ HOTOVÉ |
| 2. Verejné porty | ✅ HOTOVÉ |
| 3. Monitoring/admin rozhrania | ✅ HOTOVÉ |
| 4. Databázy/interné služby | ✅ HOTOVÉ |
| 5. Input validation/security | ✅ HOTOVÉ |

## Záver

Všetkých 5 bodov bolo opravených a prakticky overených.

Booking VM má po tejto kontrole:

- iba potrebné verejné porty,
- databázy mimo verejného internetu,
- monitoring a administračné nástroje mimo verejného internetu,
- SSH tunel pre interný monitoring,
- backendovú validáciu používateľských vstupov,
- BCrypt ochranu hesiel,
- časovo obmedzený jednorazový password-reset token,
- okamžité zneplatnenie tokenu po úspešnom resete.

**STATUS: SECURITY POINTS 1–5 – COMPLETED & VERIFIED**
