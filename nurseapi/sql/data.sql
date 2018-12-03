CREATE DATABASE nurse_db;

create table paciente(
  id SERIAL primary key not null,
  nombre VARCHAR(128) not null,
  ci VARCHAR(128) not null,
  edad INT not null,
  fecha_registro timestamp not null default current_timestamp,
  estado VARCHAR NOT NULL
);

create table signos_vitales(
  id SERIAL not null primary key,
  id_paciente BIGINT references paciente(id),
  presion_arterial_sistolica INT not null,
  presion_arterial_diastolica INT not null,
  frecuencia_cardiaca INT not null,
  fecha_registro timestamp not null default current_timestamp
);

create table rango_presion_arterial_normal(
  id SERIAL not null primary key,
  categoria VARCHAR(128) NOT NULL,
  sistolica varchar NOT NULL,
  diastolica varchar NOT NULL
);

create table rango_frecuencia_cardiaca_normal(
  id serial not null primary key,
  rango_menor INT not null,
  rango_mayor INT not null

);

INSERT INTO "public"."paciente" ("id", "nombre", "ci", "edad", "fecha_registro", "estado") VALUES (DEFAULT, 'Marcelo Lopez', '3860204', 23, DEFAULT, 'ACTIVO');
INSERT INTO "public"."paciente" ("id", "nombre", "ci", "edad", "fecha_registro", "estado") VALUES (DEFAULT, 'Silvio Cantero', '108108', 19, DEFAULT, 'ACTIVO');

INSERT INTO "public"."rango_frecuencia_cardiaca_normal" ("id", "rango_menor", "rango_mayor") VALUES (DEFAULT, 60, 100);

INSERT INTO "public"."rango_presion_arterial_normal" ("id", "categoria", "sistolica", "diastolica") VALUES (DEFAULT, 'BAJA', '0-90', '0-60');
INSERT INTO "public"."rango_presion_arterial_normal" ("id", "categoria", "sistolica", "diastolica") VALUES (DEFAULT, 'NORMAL', '90-120', '60-80');
INSERT INTO "public"."rango_presion_arterial_normal" ("id", "categoria", "sistolica", "diastolica") VALUES (DEFAULT, 'PRE-HIPERTENSION', '120-140', '80-90');
INSERT INTO "public"."rango_presion_arterial_normal" ("id", "categoria", "sistolica", "diastolica") VALUES (DEFAULT, 'HIPERTENSION FASE 1', '140-159', '90-99');
INSERT INTO "public"."rango_presion_arterial_normal" ("id", "categoria", "sistolica", "diastolica") VALUES (DEFAULT, 'HIPERTENSION FASE 2', '159-1000', '99-1000');


