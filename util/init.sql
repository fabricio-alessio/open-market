DROP TABLE IF EXISTS public.market;
DROP TABLE IF EXISTS public.district;
DROP TABLE IF EXISTS public.sub_city_hall;

CREATE TABLE public.district (
 id SERIAL CONSTRAINT pk_id_district PRIMARY KEY,
 code int4 NOT NULL UNIQUE,
 name varchar(50) NOT NULL
);

CREATE INDEX district_name_idx ON public.district (name);

CREATE TABLE public.sub_city_hall (
 id SERIAL CONSTRAINT pk_id_sub_city_hall PRIMARY KEY,
 code int4 NOT NULL UNIQUE,
 name varchar(50) NOT NULL
);

CREATE TABLE public.market (
 id SERIAL CONSTRAINT pk_id_market PRIMARY KEY,
 code int8 NOT NULL UNIQUE,
 lng int8 NOT NULL,
 lat int8 NOT NULL,
 setcens int8 NOT NULL,
 areap int8 NOT NULL,
 market_name varchar(50) NOT NULL,
 registry varchar(20) NOT NULL,
 street varchar(50) NOT NULL,
 number varchar(20) NOT NULL,
 neighborhood varchar(30) NOT NULL,
 reference varchar(50) NULL,
 district_id int4 NOT NULL references district(id),
 sub_city_hall_id int4 NOT NULL references sub_city_hall(id),
 region5 varchar(20) NOT NULL,
 region8 varchar(20) NOT NULL
);

CREATE INDEX market_filter_idx ON public.market (district_id, region5, market_name, neighborhood);