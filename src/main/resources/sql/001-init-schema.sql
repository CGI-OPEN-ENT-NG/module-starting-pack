DROP SCHEMA IF EXISTS module_starting_pack CASCADE;
CREATE SCHEMA module_starting_pack;
CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE TABLE module_starting_pack.scripts
(
    filename character varying(255) NOT NULL,
    passed   timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT scripts_pkey PRIMARY KEY (filename)
);

CREATE TABLE module_starting_pack.todo_item
(
    id      bigserial,
    is_done boolean NOT NULL DEFAULT false,
    label   text
);