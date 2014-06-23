--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

DROP TABLE IF EXISTS charity_programs;
DROP TABLE IF EXISTS charities;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS charity_program_id_seq;
DROP SEQUENCE IF EXISTS charity_id_seq;
DROP SEQUENCE IF EXISTS user_id_seq;

--
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO postgres;

--
-- Name: charity_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE charity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.charity_id_seq OWNER TO postgres;

--
-- Name: charity_program_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE charity_program_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.charity_program_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE users (
    id integer DEFAULT nextval('user_id_seq'::regclass) NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    email character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: charities; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE charities (
    id integer DEFAULT nextval('charity_id_seq'::regclass) NOT NULL,
    name character varying(255),
    mission character varying(255),
    revenue decimal not null,
    has_chapters bit not null
);


ALTER TABLE public.charities OWNER TO postgres;



--
-- Name: charity_programs; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE charity_programs (
    id integer DEFAULT nextval('charity_program_id_seq'::regclass) NOT NULL,
    charity_id integer NOT NULL,
    name character varying(255) NOT NULL,
    description text
);


ALTER TABLE public.charity_programs OWNER TO postgres;


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (first_name, last_name, email) FROM stdin;
Han	Solo	han@falcon.com
Lando	Calrissian	lando@falcon.com
\.

--
-- Data for Name: charities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY charities (name, mission, revenue, has_chapters) FROM stdin;
Red Cross	Protect people	156986340	1
United Way	Help those in need	148454256	1
ASPCA	Protect animals	23447256	0
\.

--
-- Data for Name: charity_programs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY charity_programs (charity_id, name, description) FROM stdin;
1	RedCross Health	provides education
1	RedCross Safety	provides training
3	Dogs	protects dogs
3	Cats	protects cats
\.

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
