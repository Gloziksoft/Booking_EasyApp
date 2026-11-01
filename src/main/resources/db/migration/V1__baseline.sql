--
--




--
-- Name: booking_db; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA IF NOT EXISTS booking_db;




--
-- Name: offer_images; Type: TABLE; Schema: booking_db; Owner: -
--

CREATE TABLE booking_db.offer_images (
    id bigint NOT NULL,
    image_url character varying(255) NOT NULL,
    offer_id bigint NOT NULL
);


--
-- Name: offer_images_id_seq; Type: SEQUENCE; Schema: booking_db; Owner: -
--

CREATE SEQUENCE booking_db.offer_images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: offer_images_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_db; Owner: -
--

ALTER SEQUENCE booking_db.offer_images_id_seq OWNED BY booking_db.offer_images.id;


--
-- Name: offer_tags; Type: TABLE; Schema: booking_db; Owner: -
--

CREATE TABLE booking_db.offer_tags (
    offer_id bigint NOT NULL,
    tag character varying(255) DEFAULT NULL::character varying
);


--
-- Name: offers; Type: TABLE; Schema: booking_db; Owner: -
--

CREATE TABLE booking_db.offers (
    id bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    description character varying(1000) DEFAULT NULL::character varying,
    end_date_time timestamp with time zone,
    price numeric(38,2) NOT NULL,
    service_type character varying(255) DEFAULT NULL::character varying,
    start_date_time timestamp with time zone,
    title character varying(255) NOT NULL
);


--
-- Name: offers_id_seq; Type: SEQUENCE; Schema: booking_db; Owner: -
--

CREATE SEQUENCE booking_db.offers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: offers_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_db; Owner: -
--

ALTER SEQUENCE booking_db.offers_id_seq OWNED BY booking_db.offers.id;


--
-- Name: reservation_additional_services; Type: TABLE; Schema: booking_db; Owner: -
--

CREATE TABLE booking_db.reservation_additional_services (
    reservation_id bigint NOT NULL,
    service character varying(255) DEFAULT NULL::character varying
);


--
-- Name: reservations; Type: TABLE; Schema: booking_db; Owner: -
--

CREATE TABLE booking_db.reservations (
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


--
-- Name: reservations_id_seq; Type: SEQUENCE; Schema: booking_db; Owner: -
--

CREATE SEQUENCE booking_db.reservations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: reservations_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_db; Owner: -
--

ALTER SEQUENCE booking_db.reservations_id_seq OWNED BY booking_db.reservations.id;


--
-- Name: users; Type: TABLE; Schema: booking_db; Owner: -
--

CREATE TABLE booking_db.users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    reset_token character varying(255),
    reset_token_expiration timestamp(6) without time zone,
    email_verified boolean DEFAULT false NOT NULL,
    verification_token character varying(255),
    verification_token_expiration timestamp without time zone
);


--
-- Name: users_id_seq; Type: SEQUENCE; Schema: booking_db; Owner: -
--

CREATE SEQUENCE booking_db.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: booking_db; Owner: -
--

ALTER SEQUENCE booking_db.users_id_seq OWNED BY booking_db.users.id;


--
-- Name: offer_images id; Type: DEFAULT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.offer_images ALTER COLUMN id SET DEFAULT nextval('booking_db.offer_images_id_seq'::regclass);


--
-- Name: offers id; Type: DEFAULT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.offers ALTER COLUMN id SET DEFAULT nextval('booking_db.offers_id_seq'::regclass);


--
-- Name: reservations id; Type: DEFAULT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.reservations ALTER COLUMN id SET DEFAULT nextval('booking_db.reservations_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.users ALTER COLUMN id SET DEFAULT nextval('booking_db.users_id_seq'::regclass);


--
-- Name: offers idx_16416_primary; Type: CONSTRAINT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.offers
    ADD CONSTRAINT idx_16416_primary PRIMARY KEY (id);


--
-- Name: offer_images idx_16425_primary; Type: CONSTRAINT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.offer_images
    ADD CONSTRAINT idx_16425_primary PRIMARY KEY (id);


--
-- Name: reservations idx_16434_primary; Type: CONSTRAINT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.reservations
    ADD CONSTRAINT idx_16434_primary PRIMARY KEY (id);


--
-- Name: users idx_16450_primary; Type: CONSTRAINT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.users
    ADD CONSTRAINT idx_16450_primary PRIMARY KEY (id);


--
-- Name: idx_16425_fkckk2rrvvx52v2n7c4c9sjrfjw; Type: INDEX; Schema: booking_db; Owner: -
--

CREATE INDEX idx_16425_fkckk2rrvvx52v2n7c4c9sjrfjw ON booking_db.offer_images USING btree (offer_id);


--
-- Name: idx_16429_fkc7bsbawdiyqg62it3cs52ihuc; Type: INDEX; Schema: booking_db; Owner: -
--

CREATE INDEX idx_16429_fkc7bsbawdiyqg62it3cs52ihuc ON booking_db.offer_tags USING btree (offer_id);


--
-- Name: idx_16434_fkb5g9io5h54iwl2inkno50ppln; Type: INDEX; Schema: booking_db; Owner: -
--

CREATE INDEX idx_16434_fkb5g9io5h54iwl2inkno50ppln ON booking_db.reservations USING btree (user_id);


--
-- Name: idx_16434_fkjk5eau9ty1r4m4ibk0tat5ulr; Type: INDEX; Schema: booking_db; Owner: -
--

CREATE INDEX idx_16434_fkjk5eau9ty1r4m4ibk0tat5ulr ON booking_db.reservations USING btree (offer_id);


--
-- Name: idx_16441_fkfnrhpt71ucuf8y5sa9y897qtc; Type: INDEX; Schema: booking_db; Owner: -
--

CREATE INDEX idx_16441_fkfnrhpt71ucuf8y5sa9y897qtc ON booking_db.reservation_additional_services USING btree (reservation_id);


--
-- Name: idx_16450_uk_6dotkott2kjsp8vw4d0m25fb7; Type: INDEX; Schema: booking_db; Owner: -
--

CREATE UNIQUE INDEX idx_16450_uk_6dotkott2kjsp8vw4d0m25fb7 ON booking_db.users USING btree (email);


--
-- Name: reservations fkb5g9io5h54iwl2inkno50ppln; Type: FK CONSTRAINT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.reservations
    ADD CONSTRAINT fkb5g9io5h54iwl2inkno50ppln FOREIGN KEY (user_id) REFERENCES booking_db.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: offer_tags fkc7bsbawdiyqg62it3cs52ihuc; Type: FK CONSTRAINT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.offer_tags
    ADD CONSTRAINT fkc7bsbawdiyqg62it3cs52ihuc FOREIGN KEY (offer_id) REFERENCES booking_db.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: offer_images fkckk2rrvvx52v2n7c4c9sjrfjw; Type: FK CONSTRAINT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.offer_images
    ADD CONSTRAINT fkckk2rrvvx52v2n7c4c9sjrfjw FOREIGN KEY (offer_id) REFERENCES booking_db.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: reservation_additional_services fkfnrhpt71ucuf8y5sa9y897qtc; Type: FK CONSTRAINT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.reservation_additional_services
    ADD CONSTRAINT fkfnrhpt71ucuf8y5sa9y897qtc FOREIGN KEY (reservation_id) REFERENCES booking_db.reservations(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: reservations fkjk5eau9ty1r4m4ibk0tat5ulr; Type: FK CONSTRAINT; Schema: booking_db; Owner: -
--

ALTER TABLE ONLY booking_db.reservations
    ADD CONSTRAINT fkjk5eau9ty1r4m4ibk0tat5ulr FOREIGN KEY (offer_id) REFERENCES booking_db.offers(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
--


