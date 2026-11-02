--
-- PostgreSQL database dump
--

-- Dumped from database version 14.7 (Debian 14.7-1.pgdg110+1)
-- Dumped by pg_dump version 14.7 (Debian 14.7-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: booking_app; Type: SCHEMA; Schema: -; Owner: booking
--

CREATE SCHEMA booking_app;


ALTER SCHEMA booking_app OWNER TO booking;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: offer_images; Type: TABLE; Schema: booking_app; Owner: booking
--

CREATE TABLE booking_app.offer_images (
    id bigint NOT NULL,
    image_url character varying(255) NOT NULL,
    offer_id bigint NOT NULL
);


ALTER TABLE booking_app.offer_images OWNER TO booking;

--
-- Name: offer_images_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: booking
--

CREATE SEQUENCE booking_app.offer_images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE booking_app.offer_images_id_seq OWNER TO booking;

--
-- Name: offer_images_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: booking
--

ALTER SEQUENCE booking_app.offer_images_id_seq OWNED BY booking_app.offer_images.id;


--
-- Name: offer_tags; Type: TABLE; Schema: booking_app; Owner: booking
--

CREATE TABLE booking_app.offer_tags (
    offer_id bigint NOT NULL,
    tag character varying(255) DEFAULT NULL::character varying
);


ALTER TABLE booking_app.offer_tags OWNER TO booking;

--
-- Name: offers; Type: TABLE; Schema: booking_app; Owner: booking
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


ALTER TABLE booking_app.offers OWNER TO booking;

--
-- Name: offers_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: booking
--

CREATE SEQUENCE booking_app.offers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE booking_app.offers_id_seq OWNER TO booking;

--
-- Name: offers_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: booking
--

ALTER SEQUENCE booking_app.offers_id_seq OWNED BY booking_app.offers.id;


--
-- Name: reservation_additional_services; Type: TABLE; Schema: booking_app; Owner: booking
--

CREATE TABLE booking_app.reservation_additional_services (
    reservation_id bigint NOT NULL,
    service character varying(255) DEFAULT NULL::character varying
);


ALTER TABLE booking_app.reservation_additional_services OWNER TO booking;

--
-- Name: reservation_tags; Type: TABLE; Schema: booking_app; Owner: booking
--

CREATE TABLE booking_app.reservation_tags (
    reservation_id bigint NOT NULL,
    tag character varying(255) DEFAULT NULL::character varying
);


ALTER TABLE booking_app.reservation_tags OWNER TO booking;

--
-- Name: reservations; Type: TABLE; Schema: booking_app; Owner: booking
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


ALTER TABLE booking_app.reservations OWNER TO booking;

--
-- Name: reservations_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: booking
--

CREATE SEQUENCE booking_app.reservations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE booking_app.reservations_id_seq OWNER TO booking;

--
-- Name: reservations_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: booking
--

ALTER SEQUENCE booking_app.reservations_id_seq OWNED BY booking_app.reservations.id;


--
-- Name: users; Type: TABLE; Schema: booking_app; Owner: booking
--

CREATE TABLE booking_app.users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    reset_token character varying(255),
    reset_token_expiration timestamp(6) without time zone,
    verification_token character varying(255),
    verification_token_expiration timestamp(6) without time zone
);


ALTER TABLE booking_app.users OWNER TO booking;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: booking
--

CREATE SEQUENCE booking_app.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE booking_app.users_id_seq OWNER TO booking;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: booking
--

ALTER SEQUENCE booking_app.users_id_seq OWNED BY booking_app.users.id;


--
-- Name: offer_images id; Type: DEFAULT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.offer_images ALTER COLUMN id SET DEFAULT nextval('booking_app.offer_images_id_seq'::regclass);


--
-- Name: offers id; Type: DEFAULT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.offers ALTER COLUMN id SET DEFAULT nextval('booking_app.offers_id_seq'::regclass);


--
-- Name: reservations id; Type: DEFAULT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.reservations ALTER COLUMN id SET DEFAULT nextval('booking_app.reservations_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.users ALTER COLUMN id SET DEFAULT nextval('booking_app.users_id_seq'::regclass);


--
-- Data for Name: offer_images; Type: TABLE DATA; Schema: booking_app; Owner: booking
--

COPY booking_app.offer_images (id, image_url, offer_id) FROM stdin;
\.


--
-- Data for Name: offer_tags; Type: TABLE DATA; Schema: booking_app; Owner: booking
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
-- Data for Name: offers; Type: TABLE DATA; Schema: booking_app; Owner: booking
--

COPY booking_app.offers (id, created_at, description, end_date_time, price, service_type, start_date_time, title) FROM stdin;
1	2025-09-12 11:24:58+00	Pobyt pre páry s wellness a krbom	2025-09-06 08:00:00+00	197.00	ROMANTICKY_VIKEND	2025-09-01 13:00:00+00	Romantický víkend v horách
2	2025-09-12 11:24:58+00	Relax pobyt s masážou a bazénom	2025-09-07 08:00:00+00	250.00	WELLNESS_VIKEND	2025-09-05 13:00:00+00	Wellness víkend v SPA rezorte
3	2025-09-12 11:24:58+00	Degustačný pobyt s miestnou kuchyňou	2025-09-12 08:00:00+00	180.00	GASTRONOMICKY_POBYT	2025-09-10 13:00:00+00	Gastronomický pobyt v meste
4	2025-09-12 11:24:58+00	Prehliadka pamiatok a múzeí	2025-09-17 08:00:00+00	150.00	KULTURNY_POBYT	2025-09-15 13:00:00+00	Kultúrny pobyt v historickom meste
5	2025-09-12 11:24:58+00	Turistika, bicyklovanie a športové aktivity	2025-09-22 08:00:00+00	200.00	AKTIVNY_ODPOCINOK	2025-09-20 13:00:00+00	Aktívny oddych v prírode
6	2025-09-12 11:24:58+00	Zábava pre celú rodinu, bazén a atrakcie	2025-09-27 08:00:00+00	220.00	RODINNY_POBYT	2025-09-25 13:00:00+00	Rodinný pobyt s deťmi
7	2025-09-12 11:24:58+00	Teambuildingové aktivity a semináre	2025-10-02 16:00:00+00	300.00	FIREMNY_TEAMBULDING	2025-10-01 07:00:00+00	Firemný teambuilding
8	2025-09-12 11:24:58+00	Luxusné izby, wellness a gurmánske večere	2025-10-10 08:00:00+00	500.00	LUXUSNY_POBYT	2025-10-05 13:00:00+00	Luxusný pobyt v rezorte
9	2025-09-12 11:24:58+00	Akcia pre páry, zľava 20%	2025-10-14 08:00:00+00	160.00	LAST_MINUTE	2025-10-12 13:00:00+00	Last minute romantický víkend
10	2025-09-12 11:24:58+00	Relax a aktívne športové programy	2025-10-18 08:00:00+00	270.00	WELLNESS_VIKEND	2025-10-15 13:00:00+00	Wellness & športový pobyt
11	2025-09-12 11:26:38+00	Bazén, wellness a aktivity pre deti	2025-10-23 08:00:00+00	240.00	RODINNY_POBYT	2025-10-20 13:00:00+00	Rodinný wellness pobyt
12	2025-09-12 11:26:38+00	Degustácia lokálnych jedál a vín	2025-10-27 09:00:00+00	210.00	GASTRONOMICKY_POBYT	2025-10-25 13:00:00+00	Gastronomický víkend
13	2025-09-12 11:26:38+00	Romantické izby s výhľadom na jazero	2025-10-31 09:00:00+00	230.00	ROMANTICKY_VIKEND	2025-10-28 14:00:00+00	Romantický pobyt pri jazere
14	2025-09-12 11:26:38+00	História, pamiatky a kultúrne zážitky	2025-11-03 09:00:00+00	170.00	KULTURNY_POBYT	2025-11-01 14:00:00+00	Kultúrny pobyt s prehliadkou múzeí
15	2025-09-12 11:26:38+00	Turistika, bike a športové aktivity	2025-11-07 09:00:00+00	190.00	AKTIVNY_ODPOCINOK	2025-11-05 14:00:00+00	Aktívny víkend v horách
16	2025-09-12 11:26:38+00	Relaxačný pobyt s masážami	2025-11-12 09:00:00+00	260.00	WELLNESS_VIKEND	2025-11-10 14:00:00+00	Wellness víkend pre páry
17	2025-09-12 11:26:38+00	Bazén, wellness a zábava pre deti	2025-11-18 09:00:00+00	230.00	RODINNY_POBYT	2025-11-15 14:00:00+00	Rodinný zážitkový pobyt
18	2025-09-12 11:26:38+00	Luxusné večere a vínna degustácia	2025-11-25 09:00:00+00	480.00	LUXUSNY_POBYT	2025-11-20 14:00:00+00	Luxusný gastronomický pobyt
19	2025-09-12 11:26:38+00	Workshopy a športové aktivity pre zamestnancov	2025-11-29 17:00:00+00	320.00	FIREMNY_TEAMBULDING	2025-11-28 08:00:00+00	Firemný víkendový teambuilding
20	2025-09-12 11:26:38+00	Zľava 15% pre rýchly nákup	2025-12-03 09:00:00+00	180.00	LAST_MINUTE	2025-12-01 14:00:00+00	Last minute wellness pobyt
\.


--
-- Data for Name: reservation_additional_services; Type: TABLE DATA; Schema: booking_app; Owner: booking
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
3	UBYTOVANIE
3	STRAVA_PLNA_PENZIA
3	VYLET
3	DOPRAVA
3	POZICOVNA_BICYKLOV
\.


--
-- Data for Name: reservation_tags; Type: TABLE DATA; Schema: booking_app; Owner: booking
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
-- Data for Name: reservations; Type: TABLE DATA; Schema: booking_app; Owner: booking
--

COPY booking_app.reservations (id, adults, children, description, end_date_time, price, service_type, start_date_time, offer_id, user_id) FROM stdin;
1	2	0	Luxusné izby, wellness a gurmánske večere	2025-10-10 08:00:00+00	500.00	LUXUSNY_POBYT	2025-10-05 13:00:00+00	8	2
2	2	0	Luxusné izby, wellness a gurmánske večere	2025-10-10 08:00:00+00	500.00	LUXUSNY_POBYT	2025-10-05 13:00:00+00	8	2
3	8	5	Pobyt pre páry s wellness a krbom	2025-09-06 08:00:00+00	197.00	ROMANTICKY_VIKEND	2025-09-01 13:00:00+00	1	2
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: booking_app; Owner: booking
--

COPY booking_app.users (id, email, first_name, last_name, password, role, reset_token, reset_token_expiration, verification_token, verification_token_expiration) FROM stdin;
1	admin@test.com	admin	admin	$2a$10$KluVyXMaXICOd2mL.183n.eTqjm6IA2wls9bK6UU9MlO0ZWsRjwB2	ADMIN	\N	\N	\N	\N
2	peto7@azet.sk	Peter	Halaj	$2a$10$ghfy1R9uv2A/kkMT6HOR.eVHPPJyeYKfub3yoxUwqA1zbq2O4RtjG	USER	b6b98c36-796f-4267-b941-6ef229536cd5	2026-09-14 14:03:45.400287	\N	\N
\.


--
-- Name: offer_images_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: booking
--

SELECT pg_catalog.setval('booking_app.offer_images_id_seq', 1, true);


--
-- Name: offers_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: booking
--

SELECT pg_catalog.setval('booking_app.offers_id_seq', 20, true);


--
-- Name: reservations_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: booking
--

SELECT pg_catalog.setval('booking_app.reservations_id_seq', 3, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: booking
--

SELECT pg_catalog.setval('booking_app.users_id_seq', 2, true);


--
-- Name: offers idx_16416_primary; Type: CONSTRAINT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.offers
    ADD CONSTRAINT idx_16416_primary PRIMARY KEY (id);


--
-- Name: offer_images idx_16425_primary; Type: CONSTRAINT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.offer_images
    ADD CONSTRAINT idx_16425_primary PRIMARY KEY (id);


--
-- Name: reservations idx_16434_primary; Type: CONSTRAINT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.reservations
    ADD CONSTRAINT idx_16434_primary PRIMARY KEY (id);


--
-- Name: users idx_16450_primary; Type: CONSTRAINT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.users
    ADD CONSTRAINT idx_16450_primary PRIMARY KEY (id);


--
-- Name: idx_16425_fkckk2rrvvx52v2n7c4c9sjrfjw; Type: INDEX; Schema: booking_app; Owner: booking
--

CREATE INDEX idx_16425_fkckk2rrvvx52v2n7c4c9sjrfjw ON booking_app.offer_images USING btree (offer_id);


--
-- Name: idx_16429_fkc7bsbawdiyqg62it3cs52ihuc; Type: INDEX; Schema: booking_app; Owner: booking
--

CREATE INDEX idx_16429_fkc7bsbawdiyqg62it3cs52ihuc ON booking_app.offer_tags USING btree (offer_id);


--
-- Name: idx_16434_fkb5g9io5h54iwl2inkno50ppln; Type: INDEX; Schema: booking_app; Owner: booking
--

CREATE INDEX idx_16434_fkb5g9io5h54iwl2inkno50ppln ON booking_app.reservations USING btree (user_id);


--
-- Name: idx_16434_fkjk5eau9ty1r4m4ibk0tat5ulr; Type: INDEX; Schema: booking_app; Owner: booking
--

CREATE INDEX idx_16434_fkjk5eau9ty1r4m4ibk0tat5ulr ON booking_app.reservations USING btree (offer_id);


--
-- Name: idx_16441_fkfnrhpt71ucuf8y5sa9y897qtc; Type: INDEX; Schema: booking_app; Owner: booking
--

CREATE INDEX idx_16441_fkfnrhpt71ucuf8y5sa9y897qtc ON booking_app.reservation_additional_services USING btree (reservation_id);


--
-- Name: idx_16445_fkgfdgc1k6jt22qp3y1emcx5udo; Type: INDEX; Schema: booking_app; Owner: booking
--

CREATE INDEX idx_16445_fkgfdgc1k6jt22qp3y1emcx5udo ON booking_app.reservation_tags USING btree (reservation_id);


--
-- Name: idx_16450_uk_6dotkott2kjsp8vw4d0m25fb7; Type: INDEX; Schema: booking_app; Owner: booking
--

CREATE UNIQUE INDEX idx_16450_uk_6dotkott2kjsp8vw4d0m25fb7 ON booking_app.users USING btree (email);


--
-- Name: reservations fkb5g9io5h54iwl2inkno50ppln; Type: FK CONSTRAINT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.reservations
    ADD CONSTRAINT fkb5g9io5h54iwl2inkno50ppln FOREIGN KEY (user_id) REFERENCES booking_app.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: offer_tags fkc7bsbawdiyqg62it3cs52ihuc; Type: FK CONSTRAINT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.offer_tags
    ADD CONSTRAINT fkc7bsbawdiyqg62it3cs52ihuc FOREIGN KEY (offer_id) REFERENCES booking_app.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: offer_images fkckk2rrvvx52v2n7c4c9sjrfjw; Type: FK CONSTRAINT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.offer_images
    ADD CONSTRAINT fkckk2rrvvx52v2n7c4c9sjrfjw FOREIGN KEY (offer_id) REFERENCES booking_app.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: reservation_additional_services fkfnrhpt71ucuf8y5sa9y897qtc; Type: FK CONSTRAINT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.reservation_additional_services
    ADD CONSTRAINT fkfnrhpt71ucuf8y5sa9y897qtc FOREIGN KEY (reservation_id) REFERENCES booking_app.reservations(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: reservation_tags fkgfdgc1k6jt22qp3y1emcx5udo; Type: FK CONSTRAINT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.reservation_tags
    ADD CONSTRAINT fkgfdgc1k6jt22qp3y1emcx5udo FOREIGN KEY (reservation_id) REFERENCES booking_app.reservations(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: reservations fkjk5eau9ty1r4m4ibk0tat5ulr; Type: FK CONSTRAINT; Schema: booking_app; Owner: booking
--

ALTER TABLE ONLY booking_app.reservations
    ADD CONSTRAINT fkjk5eau9ty1r4m4ibk0tat5ulr FOREIGN KEY (offer_id) REFERENCES booking_app.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- PostgreSQL database dump complete
--

