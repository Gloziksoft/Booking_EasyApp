--
-- PostgreSQL database dump
--


\restrict hFUronZDihggpVeJa1XdrmksnJoEZGeU84UCPlqhDAkTVmm4xgRjFyjawJTkkbF

-- Dumped from database version 17.6 (Ubuntu 17.6-1.pgdg24.04+1)
-- Dumped by pg_dump version 17.6 (Ubuntu 17.6-1.pgdg24.04+1)

-- Started on 2025-09-23 18:26:35 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 16414)
-- Name: booking_app; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA booking_app;


ALTER SCHEMA booking_app OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 223 (class 1259 OID 16425)
-- Name: offer_images; Type: TABLE; Schema: booking_app; Owner: postgres
--

CREATE TABLE booking_app.offer_images (
    id bigint NOT NULL,
    image_url character varying(255) NOT NULL,
    offer_id bigint NOT NULL
);


ALTER TABLE booking_app.offer_images OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16424)
-- Name: offer_images_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: postgres
--

CREATE SEQUENCE booking_app.offer_images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE booking_app.offer_images_id_seq OWNER TO postgres;

--
-- TOC entry 3514 (class 0 OID 0)
-- Dependencies: 222
-- Name: offer_images_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: postgres
--

ALTER SEQUENCE booking_app.offer_images_id_seq OWNED BY booking_app.offer_images.id;


--
-- TOC entry 224 (class 1259 OID 16429)
-- Name: offer_tags; Type: TABLE; Schema: booking_app; Owner: postgres
--

CREATE TABLE booking_app.offer_tags (
    offer_id bigint NOT NULL,
    tag character varying(255) DEFAULT NULL::character varying
);


ALTER TABLE booking_app.offer_tags OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16416)
-- Name: offers; Type: TABLE; Schema: booking_app; Owner: postgres
--

CREATE TABLE booking_app.offers (
    id bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    description character varying(1000) DEFAULT NULL::character varying,
    end_date_time timestamp with time zone,
    price numeric(38,2) NOT NULL,
    service_type character varying(255) DEFAULT NULL::character varying,
    start_date_time timestamp with time zone,
    title character varying(255) NOT NULL
);


ALTER TABLE booking_app.offers OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16415)
-- Name: offers_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: postgres
--

CREATE SEQUENCE booking_app.offers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE booking_app.offers_id_seq OWNER TO postgres;

--
-- TOC entry 3515 (class 0 OID 0)
-- Dependencies: 220
-- Name: offers_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: postgres
--

ALTER SEQUENCE booking_app.offers_id_seq OWNED BY booking_app.offers.id;


--
-- TOC entry 227 (class 1259 OID 16441)
-- Name: reservation_additional_services; Type: TABLE; Schema: booking_app; Owner: postgres
--

CREATE TABLE booking_app.reservation_additional_services (
    reservation_id bigint NOT NULL,
    service character varying(255) DEFAULT NULL::character varying
);


ALTER TABLE booking_app.reservation_additional_services OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16445)
-- Name: reservation_tags; Type: TABLE; Schema: booking_app; Owner: postgres
--

CREATE TABLE booking_app.reservation_tags (
    reservation_id bigint NOT NULL,
    tag character varying(255) DEFAULT NULL::character varying
);


ALTER TABLE booking_app.reservation_tags OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16434)
-- Name: reservations; Type: TABLE; Schema: booking_app; Owner: postgres
--

CREATE TABLE booking_app.reservations (
    id bigint NOT NULL,
    adults bigint,
    children bigint,
    description character varying(1000) DEFAULT NULL::character varying,
    end_date_time timestamp with time zone NOT NULL,
    price numeric(38,2) NOT NULL,
    service_type character varying(255) NOT NULL,
    start_date_time timestamp with time zone NOT NULL,
    offer_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE booking_app.reservations OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16433)
-- Name: reservations_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: postgres
--

CREATE SEQUENCE booking_app.reservations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE booking_app.reservations_id_seq OWNER TO postgres;

--
-- TOC entry 3516 (class 0 OID 0)
-- Dependencies: 225
-- Name: reservations_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: postgres
--

ALTER SEQUENCE booking_app.reservations_id_seq OWNED BY booking_app.reservations.id;


--
-- TOC entry 230 (class 1259 OID 16450)
-- Name: users; Type: TABLE; Schema: booking_app; Owner: postgres
--

CREATE TABLE booking_app.users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL
);


ALTER TABLE booking_app.users OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16449)
-- Name: users_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: postgres
--

CREATE SEQUENCE booking_app.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE booking_app.users_id_seq OWNER TO postgres;

--
-- TOC entry 3517 (class 0 OID 0)
-- Dependencies: 229
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: postgres
--

ALTER SEQUENCE booking_app.users_id_seq OWNED BY booking_app.users.id;


--
-- TOC entry 3325 (class 2604 OID 16428)
-- Name: offer_images id; Type: DEFAULT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.offer_images ALTER COLUMN id SET DEFAULT nextval('booking_app.offer_images_id_seq'::regclass);


--
-- TOC entry 3322 (class 2604 OID 16419)
-- Name: offers id; Type: DEFAULT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.offers ALTER COLUMN id SET DEFAULT nextval('booking_app.offers_id_seq'::regclass);


--
-- TOC entry 3327 (class 2604 OID 16437)
-- Name: reservations id; Type: DEFAULT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.reservations ALTER COLUMN id SET DEFAULT nextval('booking_app.reservations_id_seq'::regclass);


--
-- TOC entry 3331 (class 2604 OID 16453)
-- Name: users id; Type: DEFAULT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.users ALTER COLUMN id SET DEFAULT nextval('booking_app.users_id_seq'::regclass);


--
-- TOC entry 3501 (class 0 OID 16425)
-- Dependencies: 223
-- Data for Name: offer_images; Type: TABLE DATA; Schema: booking_app; Owner: postgres
--

COPY booking_app.offer_images (id, image_url, offer_id) FROM stdin;
\.


--
-- TOC entry 3502 (class 0 OID 16429)
-- Dependencies: 224
-- Data for Name: offer_tags; Type: TABLE DATA; Schema: booking_app; Owner: postgres
--

COPY booking_app.offer_tags (offer_id, tag) FROM stdin;
1	WIFI
1	KULTURA
1	WELLNESS
1	SPA
1	BAZEN
2	BAZEN
2	WELLNESS
2	WIFI
3	BAZEN
3	WELLNESS
3	WIFI
4	BAZEN
4	WELLNESS
4	WIFI
5	BAZEN
5	WELLNESS
5	WIFI
20	BAZEN
20	WELLNESS
20	WIFI
12	BAZEN
12	WELLNESS
12	WIFI
10	BAZEN
10	WELLNESS
10	WIFI
7	BAZEN
7	WELLNESS
7	WIFI
11	BAZEN
11	WELLNESS
11	WIFI
9	BAZEN
9	WELLNESS
9	WIFI
8	BAZEN
8	WELLNESS
8	WIFI
6	BAZEN
6	WELLNESS
6	WIFI
13	BAZEN
13	WELLNESS
13	WIFI
19	BAZEN
19	WELLNESS
19	WIFI
14	BAZEN
14	WELLNESS
14	WIFI
15	BAZEN
15	WELLNESS
15	WIFI
17	BAZEN
17	WELLNESS
17	WIFI
18	BAZEN
18	WELLNESS
18	WIFI
16	BAZEN
16	WELLNESS
16	WIFI
\.


--
-- TOC entry 3499 (class 0 OID 16416)
-- Dependencies: 221
-- Data for Name: offers; Type: TABLE DATA; Schema: booking_app; Owner: postgres
--

COPY booking_app.offers (id, created_at, description, end_date_time, price, service_type, start_date_time, title) FROM stdin;
1	2025-09-12 13:24:58+02	Pobyt pre páry s wellness a krbom	2025-09-06 10:00:00+02	197.00	ROMANTICKY_VIKEND	2025-09-01 15:00:00+02	Romantický víkend v horách
2	2025-09-12 13:24:58+02	Relax pobyt s masážou a bazénom	2025-09-07 10:00:00+02	250.00	WELLNESS_VIKEND	2025-09-05 15:00:00+02	Wellness víkend v SPA rezorte
3	2025-09-12 13:24:58+02	Degustačný pobyt s miestnou kuchyňou	2025-09-12 10:00:00+02	180.00	GASTRONOMICKY_POBYT	2025-09-10 15:00:00+02	Gastronomický pobyt v meste
4	2025-09-12 13:24:58+02	Prehliadka pamiatok a múzeí	2025-09-17 10:00:00+02	150.00	KULTURNY_POBYT	2025-09-15 15:00:00+02	Kultúrny pobyt v historickom meste
5	2025-09-12 13:24:58+02	Turistika, bicyklovanie a športové aktivity	2025-09-22 10:00:00+02	200.00	AKTIVNY_ODPOCINOK	2025-09-20 15:00:00+02	Aktívny oddych v prírode
6	2025-09-12 13:24:58+02	Zábava pre celú rodinu, bazén a atrakcie	2025-09-27 10:00:00+02	220.00	RODINNY_POBYT	2025-09-25 15:00:00+02	Rodinný pobyt s deťmi
7	2025-09-12 13:24:58+02	Teambuildingové aktivity a semináre	2025-10-02 18:00:00+02	300.00	FIREMNY_TEAMBULDING	2025-10-01 09:00:00+02	Firemný teambuilding
8	2025-09-12 13:24:58+02	Luxusné izby, wellness a gurmánske večere	2025-10-10 10:00:00+02	500.00	LUXUSNY_POBYT	2025-10-05 15:00:00+02	Luxusný pobyt v rezorte
9	2025-09-12 13:24:58+02	Akcia pre páry, zľava 20%	2025-10-14 10:00:00+02	160.00	LAST_MINUTE	2025-10-12 15:00:00+02	Last minute romantický víkend
10	2025-09-12 13:24:58+02	Relax a aktívne športové programy	2025-10-18 10:00:00+02	270.00	WELLNESS_VIKEND	2025-10-15 15:00:00+02	Wellness & športový pobyt
11	2025-09-12 13:26:38+02	Bazén, wellness a aktivity pre deti	2025-10-23 10:00:00+02	240.00	RODINNY_POBYT	2025-10-20 15:00:00+02	Rodinný wellness pobyt
12	2025-09-12 13:26:38+02	Degustácia lokálnych jedál a vín	2025-10-27 10:00:00+01	210.00	GASTRONOMICKY_POBYT	2025-10-25 15:00:00+02	Gastronomický víkend
13	2025-09-12 13:26:38+02	Romantické izby s výhľadom na jazero	2025-10-31 10:00:00+01	230.00	ROMANTICKY_VIKEND	2025-10-28 15:00:00+01	Romantický pobyt pri jazere
14	2025-09-12 13:26:38+02	História, pamiatky a kultúrne zážitky	2025-11-03 10:00:00+01	170.00	KULTURNY_POBYT	2025-11-01 15:00:00+01	Kultúrny pobyt s prehliadkou múzeí
15	2025-09-12 13:26:38+02	Turistika, bike a športové aktivity	2025-11-07 10:00:00+01	190.00	AKTIVNY_ODPOCINOK	2025-11-05 15:00:00+01	Aktívny víkend v horách
16	2025-09-12 13:26:38+02	Relaxačný pobyt s masážami	2025-11-12 10:00:00+01	260.00	WELLNESS_VIKEND	2025-11-10 15:00:00+01	Wellness víkend pre páry
17	2025-09-12 13:26:38+02	Bazén, wellness a zábava pre deti	2025-11-18 10:00:00+01	230.00	RODINNY_POBYT	2025-11-15 15:00:00+01	Rodinný zážitkový pobyt
18	2025-09-12 13:26:38+02	Luxusné večere a vínna degustácia	2025-11-25 10:00:00+01	480.00	LUXUSNY_POBYT	2025-11-20 15:00:00+01	Luxusný gastronomický pobyt
19	2025-09-12 13:26:38+02	Workshopy a športové aktivity pre zamestnancov	2025-11-29 18:00:00+01	320.00	FIREMNY_TEAMBULDING	2025-11-28 09:00:00+01	Firemný víkendový teambuilding
20	2025-09-12 13:26:38+02	Zľava 15% pre rýchly nákup	2025-12-03 10:00:00+01	180.00	LAST_MINUTE	2025-12-01 15:00:00+01	Last minute wellness pobyt
\.


--
-- TOC entry 3505 (class 0 OID 16441)
-- Dependencies: 227
-- Data for Name: reservation_additional_services; Type: TABLE DATA; Schema: booking_app; Owner: postgres
--

COPY booking_app.reservation_additional_services (reservation_id, service) FROM stdin;
1	UBYTOVANIE
1	STRAVA_POLPENZIA
1	PARKOVANIE
1	MASAZ
1	SPORTOVE_VYBAVENIE
2	VYLET
2	STRAVA_RANAJKY
2	STRAVA_PLNA_PENZIA
2	PARKOVANIE
2	UBYTOVANIE
2	MASAZ
\.


--
-- TOC entry 3506 (class 0 OID 16445)
-- Dependencies: 228
-- Data for Name: reservation_tags; Type: TABLE DATA; Schema: booking_app; Owner: postgres
--

COPY booking_app.reservation_tags (reservation_id, tag) FROM stdin;
1	BAZEN
1	WIFI
1	WELLNESS
2	WELLNESS
2	BAZEN
2	WIFI
\.


--
-- TOC entry 3504 (class 0 OID 16434)
-- Dependencies: 226
-- Data for Name: reservations; Type: TABLE DATA; Schema: booking_app; Owner: postgres
--

COPY booking_app.reservations (id, adults, children, description, end_date_time, price, service_type, start_date_time, offer_id, user_id) FROM stdin;
1	2	0	Luxusné izby, wellness a gurmánske večere	2025-10-10 10:00:00+02	500.00	LUXUSNY_POBYT	2025-10-05 15:00:00+02	8	2
2	2	0	Luxusné izby, wellness a gurmánske večere	2025-10-10 10:00:00+02	500.00	LUXUSNY_POBYT	2025-10-05 15:00:00+02	8	2
\.


--
-- TOC entry 3508 (class 0 OID 16450)
-- Dependencies: 230
-- Data for Name: users; Type: TABLE DATA; Schema: booking_app; Owner: postgres
--

COPY booking_app.users (id, email, first_name, last_name, password, role) FROM stdin;
1	admin@test.com	admin	admin	$2a$10$KluVyXMaXICOd2mL.183n.eTqjm6IA2wls9bK6UU9MlO0ZWsRjwB2	ADMIN
2	peto7@azet.sk	Peter	Halaj	$2a$10$ghfy1R9uv2A/kkMT6HOR.eVHPPJyeYKfub3yoxUwqA1zbq2O4RtjG	USER
\.


--
-- TOC entry 3518 (class 0 OID 0)
-- Dependencies: 222
-- Name: offer_images_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: postgres
--

SELECT pg_catalog.setval('booking_app.offer_images_id_seq', 1, true);


--
-- TOC entry 3519 (class 0 OID 0)
-- Dependencies: 220
-- Name: offers_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: postgres
--

SELECT pg_catalog.setval('booking_app.offers_id_seq', 20, true);


--
-- TOC entry 3520 (class 0 OID 0)
-- Dependencies: 225
-- Name: reservations_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: postgres
--

SELECT pg_catalog.setval('booking_app.reservations_id_seq', 2, true);


--
-- TOC entry 3521 (class 0 OID 0)
-- Dependencies: 229
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: postgres
--

SELECT pg_catalog.setval('booking_app.users_id_seq', 2, true);


--
-- TOC entry 3333 (class 2606 OID 16472)
-- Name: offers idx_16416_primary; Type: CONSTRAINT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.offers
    ADD CONSTRAINT idx_16416_primary PRIMARY KEY (id);


--
-- TOC entry 3336 (class 2606 OID 16473)
-- Name: offer_images idx_16425_primary; Type: CONSTRAINT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.offer_images
    ADD CONSTRAINT idx_16425_primary PRIMARY KEY (id);


--
-- TOC entry 3341 (class 2606 OID 16474)
-- Name: reservations idx_16434_primary; Type: CONSTRAINT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.reservations
    ADD CONSTRAINT idx_16434_primary PRIMARY KEY (id);


--
-- TOC entry 3345 (class 2606 OID 16475)
-- Name: users idx_16450_primary; Type: CONSTRAINT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.users
    ADD CONSTRAINT idx_16450_primary PRIMARY KEY (id);


--
-- TOC entry 3334 (class 1259 OID 16459)
-- Name: idx_16425_fkckk2rrvvx52v2n7c4c9sjrfjw; Type: INDEX; Schema: booking_app; Owner: postgres
--

CREATE INDEX idx_16425_fkckk2rrvvx52v2n7c4c9sjrfjw ON booking_app.offer_images USING btree (offer_id);


--
-- TOC entry 3337 (class 1259 OID 16456)
-- Name: idx_16429_fkc7bsbawdiyqg62it3cs52ihuc; Type: INDEX; Schema: booking_app; Owner: postgres
--

CREATE INDEX idx_16429_fkc7bsbawdiyqg62it3cs52ihuc ON booking_app.offer_tags USING btree (offer_id);


--
-- TOC entry 3338 (class 1259 OID 16464)
-- Name: idx_16434_fkb5g9io5h54iwl2inkno50ppln; Type: INDEX; Schema: booking_app; Owner: postgres
--

CREATE INDEX idx_16434_fkb5g9io5h54iwl2inkno50ppln ON booking_app.reservations USING btree (user_id);


--
-- TOC entry 3339 (class 1259 OID 16462)
-- Name: idx_16434_fkjk5eau9ty1r4m4ibk0tat5ulr; Type: INDEX; Schema: booking_app; Owner: postgres
--

CREATE INDEX idx_16434_fkjk5eau9ty1r4m4ibk0tat5ulr ON booking_app.reservations USING btree (offer_id);


--
-- TOC entry 3342 (class 1259 OID 16461)
-- Name: idx_16441_fkfnrhpt71ucuf8y5sa9y897qtc; Type: INDEX; Schema: booking_app; Owner: postgres
--

CREATE INDEX idx_16441_fkfnrhpt71ucuf8y5sa9y897qtc ON booking_app.reservation_additional_services USING btree (reservation_id);


--
-- TOC entry 3343 (class 1259 OID 16458)
-- Name: idx_16445_fkgfdgc1k6jt22qp3y1emcx5udo; Type: INDEX; Schema: booking_app; Owner: postgres
--

CREATE INDEX idx_16445_fkgfdgc1k6jt22qp3y1emcx5udo ON booking_app.reservation_tags USING btree (reservation_id);


--
-- TOC entry 3346 (class 1259 OID 16465)
-- Name: idx_16450_uk_6dotkott2kjsp8vw4d0m25fb7; Type: INDEX; Schema: booking_app; Owner: postgres
--

CREATE UNIQUE INDEX idx_16450_uk_6dotkott2kjsp8vw4d0m25fb7 ON booking_app.users USING btree (email);


--
-- TOC entry 3349 (class 2606 OID 16486)
-- Name: reservations fkb5g9io5h54iwl2inkno50ppln; Type: FK CONSTRAINT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.reservations
    ADD CONSTRAINT fkb5g9io5h54iwl2inkno50ppln FOREIGN KEY (user_id) REFERENCES booking_app.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 3348 (class 2606 OID 16481)
-- Name: offer_tags fkc7bsbawdiyqg62it3cs52ihuc; Type: FK CONSTRAINT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.offer_tags
    ADD CONSTRAINT fkc7bsbawdiyqg62it3cs52ihuc FOREIGN KEY (offer_id) REFERENCES booking_app.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 3347 (class 2606 OID 16476)
-- Name: offer_images fkckk2rrvvx52v2n7c4c9sjrfjw; Type: FK CONSTRAINT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.offer_images
    ADD CONSTRAINT fkckk2rrvvx52v2n7c4c9sjrfjw FOREIGN KEY (offer_id) REFERENCES booking_app.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 3351 (class 2606 OID 16496)
-- Name: reservation_additional_services fkfnrhpt71ucuf8y5sa9y897qtc; Type: FK CONSTRAINT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.reservation_additional_services
    ADD CONSTRAINT fkfnrhpt71ucuf8y5sa9y897qtc FOREIGN KEY (reservation_id) REFERENCES booking_app.reservations(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 3352 (class 2606 OID 16501)
-- Name: reservation_tags fkgfdgc1k6jt22qp3y1emcx5udo; Type: FK CONSTRAINT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.reservation_tags
    ADD CONSTRAINT fkgfdgc1k6jt22qp3y1emcx5udo FOREIGN KEY (reservation_id) REFERENCES booking_app.reservations(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 3350 (class 2606 OID 16491)
-- Name: reservations fkjk5eau9ty1r4m4ibk0tat5ulr; Type: FK CONSTRAINT; Schema: booking_app; Owner: postgres
--

ALTER TABLE ONLY booking_app.reservations
    ADD CONSTRAINT fkjk5eau9ty1r4m4ibk0tat5ulr FOREIGN KEY (offer_id) REFERENCES booking_app.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


-- Completed on 2025-09-23 18:26:35 CEST

--
-- PostgreSQL database dump complete
--

\unrestrict hFUronZDihggpVeJa1XdrmksnJoEZGeU84UCPlqhDAkTVmm4xgRjFyjawJTkkbF

