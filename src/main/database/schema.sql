--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = PUBLIC, pg_catalog;

DROP TABLE IF EXISTS sessions;
DROP TABLE IF EXISTS charity_programs;
DROP TABLE IF EXISTS charities;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS sessions_id_seq;
DROP SEQUENCE IF EXISTS charity_programs_id_seq;
DROP SEQUENCE IF EXISTS charities_id_seq;
DROP SEQUENCE IF EXISTS users_id_seq;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: charities_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE charities_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE public.charities_id_seq OWNER TO postgres;

--
-- Name: charity_programs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE charity_programs_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE public.charity_programs_id_seq OWNER TO postgres;

--
-- Name: sessions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sessions_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE public.sessions_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = FALSE;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE users (
	id         INTEGER DEFAULT nextval('users_id_seq' :: REGCLASS) NOT NULL,
	first_name CHARACTER VARYING(255),
	last_name  CHARACTER VARYING(255),
	email      CHARACTER VARYING(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: charities; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE charities (
	id           INTEGER DEFAULT nextval('charities_id_seq' :: REGCLASS) NOT NULL,
	name         CHARACTER VARYING(255),
	mission      CHARACTER VARYING(255),
	revenue      DECIMAL                                                 NOT NULL,
	has_chapters Boolean                                                 NOT NULL
);


ALTER TABLE public.charities OWNER TO postgres;


--
-- Name: charity_programs; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE charity_programs (
	id          INTEGER DEFAULT nextval('charity_programs_id_seq' :: REGCLASS) NOT NULL,
	charity_id  INTEGER                                                        NOT NULL,
	name        CHARACTER VARYING(255)                                         NOT NULL,
	description TEXT
);


ALTER TABLE public.charity_programs OWNER TO postgres;


--
-- Name: sessions; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE sessions (
	id               INTEGER DEFAULT nextval('sessions_id_seq' :: REGCLASS) NOT NULL,
	date_created     TIMESTAMP                                              NOT NULL DEFAULT NOW(),
	last_updated     TIMESTAMP                                              NULL DEFAULT NOW(),
	jsessionid       CHARACTER VARYING(255)                                 NOT NULL,
	returnid         CHARACTER VARYING(255)                                 NOT NULL,
	donorid          INTEGER                                                NULL,
	vendorid         INTEGER                                                NULL,
	affiliateid      INTEGER                                                NULL,
	is_authenticated Boolean                                                NOT NULL DEFAULT FALSE
);


ALTER TABLE public.sessions OWNER TO postgres;


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT into users (first_name, last_name, email) values ('Han', 'Solo', 'han@falcon.com');
INSERT into users (first_name, last_name, email) values ('Lando', 'Calrissian', 'lando@falcon.com');


--
-- Data for Name: charities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT into charities (name, mission, revenue, has_chapters) values (
	'Red Cross', 'Protect people', 156986340, true);
INSERT into charities (name, mission, revenue, has_chapters) values (
	'United Way', 'Help those In need', 148454256, true);
INSERT into charities (name, mission, revenue, has_chapters) values (
	'ASPCA', 'Protect animals', 23447256, false);

--
-- Data for Name: charity_programs; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT into charity_programs (charity_id, name, description) values (1, 'RedCross Health', 'provides education');
INSERT into charity_programs (charity_id, name, description) values (1, 'RedCross Safety', 'provides training');
INSERT into charity_programs (charity_id, name, description) values (3, 'Dogs', 'protects dogs');
INSERT into charity_programs (charity_id, name, description) values (3, 'Cats', 'protects cats');

--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--
