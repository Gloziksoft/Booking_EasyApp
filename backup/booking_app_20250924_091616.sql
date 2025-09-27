--
-- PostgreSQL database dump
--

\restrict 1Lh4lbSTnObxHaMdzSxEJqdoZI5CLTdQod4GSR3OgyJ64hmHs4PBs2wLGxsCelU

-- Dumped from database version 17.6 (Debian 17.6-1.pgdg13+1)
-- Dumped by pg_dump version 17.6 (Debian 17.6-1.pgdg13+1)

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
-- Name: booking_app; Type: SCHEMA; Schema: -; Owner: root
--

CREATE SCHEMA booking_app;


ALTER SCHEMA booking_app OWNER TO root;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: offer_images; Type: TABLE; Schema: booking_app; Owner: root
--

CREATE TABLE booking_app.offer_images (
    id bigint NOT NULL,
    image_url character varying(255) NOT NULL,
    offer_id bigint NOT NULL
);


ALTER TABLE booking_app.offer_images OWNER TO root;

--
-- Name: offer_images_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: root
--

CREATE SEQUENCE booking_app.offer_images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE booking_app.offer_images_id_seq OWNER TO root;

--
-- Name: offer_images_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: root
--

ALTER SEQUENCE booking_app.offer_images_id_seq OWNED BY booking_app.offer_images.id;


--
-- Name: offer_tags; Type: TABLE; Schema: booking_app; Owner: root
--

CREATE TABLE booking_app.offer_tags (
    offer_id bigint NOT NULL,
    tag character varying(255) DEFAULT NULL::character varying
);


ALTER TABLE booking_app.offer_tags OWNER TO root;

--
-- Name: offers; Type: TABLE; Schema: booking_app; Owner: root
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


ALTER TABLE booking_app.offers OWNER TO root;

--
-- Name: offers_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: root
--

CREATE SEQUENCE booking_app.offers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE booking_app.offers_id_seq OWNER TO root;

--
-- Name: offers_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: root
--

ALTER SEQUENCE booking_app.offers_id_seq OWNED BY booking_app.offers.id;


--
-- Name: reservation_additional_services; Type: TABLE; Schema: booking_app; Owner: root
--

CREATE TABLE booking_app.reservation_additional_services (
    reservation_id bigint NOT NULL,
    service character varying(255) DEFAULT NULL::character varying
);


ALTER TABLE booking_app.reservation_additional_services OWNER TO root;

--
-- Name: reservation_tags; Type: TABLE; Schema: booking_app; Owner: root
--

CREATE TABLE booking_app.reservation_tags (
    reservation_id bigint NOT NULL,
    tag character varying(255) DEFAULT NULL::character varying
);


ALTER TABLE booking_app.reservation_tags OWNER TO root;

--
-- Name: reservations; Type: TABLE; Schema: booking_app; Owner: root
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


ALTER TABLE booking_app.reservations OWNER TO root;

--
-- Name: reservations_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: root
--

CREATE SEQUENCE booking_app.reservations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE booking_app.reservations_id_seq OWNER TO root;

--
-- Name: reservations_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: root
--

ALTER SEQUENCE booking_app.reservations_id_seq OWNED BY booking_app.reservations.id;


--
-- Name: users; Type: TABLE; Schema: booking_app; Owner: root
--

CREATE TABLE booking_app.users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL
);


ALTER TABLE booking_app.users OWNER TO root;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: booking_app; Owner: root
--

CREATE SEQUENCE booking_app.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE booking_app.users_id_seq OWNER TO root;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_app; Owner: root
--

ALTER SEQUENCE booking_app.users_id_seq OWNED BY booking_app.users.id;


--
-- Name: offer_images; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.offer_images (
    id bigint NOT NULL,
    image_url character varying(255) NOT NULL,
    offer_id bigint NOT NULL
);


ALTER TABLE public.offer_images OWNER TO root;

--
-- Name: offer_images_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.offer_images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.offer_images_id_seq OWNER TO root;

--
-- Name: offer_images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.offer_images_id_seq OWNED BY public.offer_images.id;


--
-- Name: offer_tags; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.offer_tags (
    offer_id bigint NOT NULL,
    tag character varying(255)
);


ALTER TABLE public.offer_tags OWNER TO root;

--
-- Name: offers; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.offers (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    description character varying(1000),
    end_date_time timestamp(6) without time zone,
    price numeric(38,2) NOT NULL,
    service_type character varying(255),
    start_date_time timestamp(6) without time zone,
    title character varying(255) NOT NULL
);


ALTER TABLE public.offers OWNER TO root;

--
-- Name: offers_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.offers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.offers_id_seq OWNER TO root;

--
-- Name: offers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.offers_id_seq OWNED BY public.offers.id;


--
-- Name: reservation_additional_services; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.reservation_additional_services (
    reservation_id bigint NOT NULL,
    service character varying(255)
);


ALTER TABLE public.reservation_additional_services OWNER TO root;

--
-- Name: reservation_tags; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.reservation_tags (
    reservation_id bigint NOT NULL,
    tag character varying(255)
);


ALTER TABLE public.reservation_tags OWNER TO root;

--
-- Name: reservations; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.reservations (
    id bigint NOT NULL,
    adults integer,
    children integer,
    description character varying(1000),
    end_date_time timestamp(6) without time zone NOT NULL,
    price numeric(38,2) NOT NULL,
    service_type character varying(255) NOT NULL,
    start_date_time timestamp(6) without time zone NOT NULL,
    offer_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.reservations OWNER TO root;

--
-- Name: reservations_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.reservations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reservations_id_seq OWNER TO root;

--
-- Name: reservations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.reservations_id_seq OWNED BY public.reservations.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO root;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO root;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: offer_images id; Type: DEFAULT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.offer_images ALTER COLUMN id SET DEFAULT nextval('booking_app.offer_images_id_seq'::regclass);


--
-- Name: offers id; Type: DEFAULT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.offers ALTER COLUMN id SET DEFAULT nextval('booking_app.offers_id_seq'::regclass);


--
-- Name: reservations id; Type: DEFAULT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.reservations ALTER COLUMN id SET DEFAULT nextval('booking_app.reservations_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.users ALTER COLUMN id SET DEFAULT nextval('booking_app.users_id_seq'::regclass);


--
-- Name: offer_images id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.offer_images ALTER COLUMN id SET DEFAULT nextval('public.offer_images_id_seq'::regclass);


--
-- Name: offers id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.offers ALTER COLUMN id SET DEFAULT nextval('public.offers_id_seq'::regclass);


--
-- Name: reservations id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.reservations ALTER COLUMN id SET DEFAULT nextval('public.reservations_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: offer_images; Type: TABLE DATA; Schema: booking_app; Owner: root
--

COPY booking_app.offer_images (id, image_url, offer_id) FROM stdin;
\.


--
-- Data for Name: offer_tags; Type: TABLE DATA; Schema: booking_app; Owner: root
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
-- Data for Name: offers; Type: TABLE DATA; Schema: booking_app; Owner: root
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
-- Data for Name: reservation_additional_services; Type: TABLE DATA; Schema: booking_app; Owner: root
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
-- Data for Name: reservation_tags; Type: TABLE DATA; Schema: booking_app; Owner: root
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
-- Data for Name: reservations; Type: TABLE DATA; Schema: booking_app; Owner: root
--

COPY booking_app.reservations (id, adults, children, description, end_date_time, price, service_type, start_date_time, offer_id, user_id) FROM stdin;
1	2	0	Luxusné izby, wellness a gurmánske večere	2025-10-10 08:00:00+00	500.00	LUXUSNY_POBYT	2025-10-05 13:00:00+00	8	2
2	2	0	Luxusné izby, wellness a gurmánske večere	2025-10-10 08:00:00+00	500.00	LUXUSNY_POBYT	2025-10-05 13:00:00+00	8	2
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: booking_app; Owner: root
--

COPY booking_app.users (id, email, first_name, last_name, password, role) FROM stdin;
1	admin@test.com	admin	admin	$2a$10$KluVyXMaXICOd2mL.183n.eTqjm6IA2wls9bK6UU9MlO0ZWsRjwB2	ADMIN
2	peto7@azet.sk	Peter	Halaj	$2a$10$ghfy1R9uv2A/kkMT6HOR.eVHPPJyeYKfub3yoxUwqA1zbq2O4RtjG	USER
\.


--
-- Data for Name: offer_images; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.offer_images (id, image_url, offer_id) FROM stdin;
\.


--
-- Data for Name: offer_tags; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.offer_tags (offer_id, tag) FROM stdin;
\.


--
-- Data for Name: offers; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.offers (id, created_at, description, end_date_time, price, service_type, start_date_time, title) FROM stdin;
\.


--
-- Data for Name: reservation_additional_services; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.reservation_additional_services (reservation_id, service) FROM stdin;
\.


--
-- Data for Name: reservation_tags; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.reservation_tags (reservation_id, tag) FROM stdin;
\.


--
-- Data for Name: reservations; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.reservations (id, adults, children, description, end_date_time, price, service_type, start_date_time, offer_id, user_id) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.users (id, email, first_name, last_name, password, role) FROM stdin;
1	admin@test.sk	admin	admin	$2a$10$0Su2oaP/491rCDnCVuwI6ubyn2CwD98z1T7iwZzA7EB1ENlGNbVAW	USER
\.


--
-- Name: offer_images_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: root
--

SELECT pg_catalog.setval('booking_app.offer_images_id_seq', 1, true);


--
-- Name: offers_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: root
--

SELECT pg_catalog.setval('booking_app.offers_id_seq', 20, true);


--
-- Name: reservations_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: root
--

SELECT pg_catalog.setval('booking_app.reservations_id_seq', 2, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: booking_app; Owner: root
--

SELECT pg_catalog.setval('booking_app.users_id_seq', 2, true);


--
-- Name: offer_images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.offer_images_id_seq', 1, false);


--
-- Name: offers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.offers_id_seq', 1, false);


--
-- Name: reservations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.reservations_id_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);


--
-- Name: offers idx_16416_primary; Type: CONSTRAINT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.offers
    ADD CONSTRAINT idx_16416_primary PRIMARY KEY (id);


--
-- Name: offer_images idx_16425_primary; Type: CONSTRAINT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.offer_images
    ADD CONSTRAINT idx_16425_primary PRIMARY KEY (id);


--
-- Name: reservations idx_16434_primary; Type: CONSTRAINT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.reservations
    ADD CONSTRAINT idx_16434_primary PRIMARY KEY (id);


--
-- Name: users idx_16450_primary; Type: CONSTRAINT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.users
    ADD CONSTRAINT idx_16450_primary PRIMARY KEY (id);


--
-- Name: offer_images offer_images_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.offer_images
    ADD CONSTRAINT offer_images_pkey PRIMARY KEY (id);


--
-- Name: offers offers_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.offers
    ADD CONSTRAINT offers_pkey PRIMARY KEY (id);


--
-- Name: reservations reservations_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservations_pkey PRIMARY KEY (id);


--
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: idx_16425_fkckk2rrvvx52v2n7c4c9sjrfjw; Type: INDEX; Schema: booking_app; Owner: root
--

CREATE INDEX idx_16425_fkckk2rrvvx52v2n7c4c9sjrfjw ON booking_app.offer_images USING btree (offer_id);


--
-- Name: idx_16429_fkc7bsbawdiyqg62it3cs52ihuc; Type: INDEX; Schema: booking_app; Owner: root
--

CREATE INDEX idx_16429_fkc7bsbawdiyqg62it3cs52ihuc ON booking_app.offer_tags USING btree (offer_id);


--
-- Name: idx_16434_fkb5g9io5h54iwl2inkno50ppln; Type: INDEX; Schema: booking_app; Owner: root
--

CREATE INDEX idx_16434_fkb5g9io5h54iwl2inkno50ppln ON booking_app.reservations USING btree (user_id);


--
-- Name: idx_16434_fkjk5eau9ty1r4m4ibk0tat5ulr; Type: INDEX; Schema: booking_app; Owner: root
--

CREATE INDEX idx_16434_fkjk5eau9ty1r4m4ibk0tat5ulr ON booking_app.reservations USING btree (offer_id);


--
-- Name: idx_16441_fkfnrhpt71ucuf8y5sa9y897qtc; Type: INDEX; Schema: booking_app; Owner: root
--

CREATE INDEX idx_16441_fkfnrhpt71ucuf8y5sa9y897qtc ON booking_app.reservation_additional_services USING btree (reservation_id);


--
-- Name: idx_16445_fkgfdgc1k6jt22qp3y1emcx5udo; Type: INDEX; Schema: booking_app; Owner: root
--

CREATE INDEX idx_16445_fkgfdgc1k6jt22qp3y1emcx5udo ON booking_app.reservation_tags USING btree (reservation_id);


--
-- Name: idx_16450_uk_6dotkott2kjsp8vw4d0m25fb7; Type: INDEX; Schema: booking_app; Owner: root
--

CREATE UNIQUE INDEX idx_16450_uk_6dotkott2kjsp8vw4d0m25fb7 ON booking_app.users USING btree (email);


--
-- Name: reservations fkb5g9io5h54iwl2inkno50ppln; Type: FK CONSTRAINT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.reservations
    ADD CONSTRAINT fkb5g9io5h54iwl2inkno50ppln FOREIGN KEY (user_id) REFERENCES booking_app.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: offer_tags fkc7bsbawdiyqg62it3cs52ihuc; Type: FK CONSTRAINT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.offer_tags
    ADD CONSTRAINT fkc7bsbawdiyqg62it3cs52ihuc FOREIGN KEY (offer_id) REFERENCES booking_app.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: offer_images fkckk2rrvvx52v2n7c4c9sjrfjw; Type: FK CONSTRAINT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.offer_images
    ADD CONSTRAINT fkckk2rrvvx52v2n7c4c9sjrfjw FOREIGN KEY (offer_id) REFERENCES booking_app.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: reservation_additional_services fkfnrhpt71ucuf8y5sa9y897qtc; Type: FK CONSTRAINT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.reservation_additional_services
    ADD CONSTRAINT fkfnrhpt71ucuf8y5sa9y897qtc FOREIGN KEY (reservation_id) REFERENCES booking_app.reservations(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: reservation_tags fkgfdgc1k6jt22qp3y1emcx5udo; Type: FK CONSTRAINT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.reservation_tags
    ADD CONSTRAINT fkgfdgc1k6jt22qp3y1emcx5udo FOREIGN KEY (reservation_id) REFERENCES booking_app.reservations(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: reservations fkjk5eau9ty1r4m4ibk0tat5ulr; Type: FK CONSTRAINT; Schema: booking_app; Owner: root
--

ALTER TABLE ONLY booking_app.reservations
    ADD CONSTRAINT fkjk5eau9ty1r4m4ibk0tat5ulr FOREIGN KEY (offer_id) REFERENCES booking_app.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: reservations fkb5g9io5h54iwl2inkno50ppln; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT fkb5g9io5h54iwl2inkno50ppln FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: offer_tags fkc7bsbawdiyqg62it3cs52ihuc; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.offer_tags
    ADD CONSTRAINT fkc7bsbawdiyqg62it3cs52ihuc FOREIGN KEY (offer_id) REFERENCES public.offers(id);


--
-- Name: offer_images fkckk2rrvvx52v2n7c4c9sjrfjw; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.offer_images
    ADD CONSTRAINT fkckk2rrvvx52v2n7c4c9sjrfjw FOREIGN KEY (offer_id) REFERENCES public.offers(id);


--
-- Name: reservation_additional_services fkfnrhpt71ucuf8y5sa9y897qtc; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.reservation_additional_services
    ADD CONSTRAINT fkfnrhpt71ucuf8y5sa9y897qtc FOREIGN KEY (reservation_id) REFERENCES public.reservations(id);


--
-- Name: reservation_tags fkgfdgc1k6jt22qp3y1emcx5udo; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.reservation_tags
    ADD CONSTRAINT fkgfdgc1k6jt22qp3y1emcx5udo FOREIGN KEY (reservation_id) REFERENCES public.reservations(id);


--
-- Name: reservations fkjk5eau9ty1r4m4ibk0tat5ulr; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT fkjk5eau9ty1r4m4ibk0tat5ulr FOREIGN KEY (offer_id) REFERENCES public.offers(id);


--
-- PostgreSQL database dump complete
--

\unrestrict 1Lh4lbSTnObxHaMdzSxEJqdoZI5CLTdQod4GSR3OgyJ64hmHs4PBs2wLGxsCelU

