--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.8
-- Dumped by pg_dump version 9.6.8

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- Name: fcdrschema; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA fcdrschema;


ALTER SCHEMA fcdrschema OWNER TO postgres;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: classification; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.classification (
    classification_id integer NOT NULL,
    classification_number text NOT NULL,
    classification_name text NOT NULL,
    classification_type text NOT NULL
);


ALTER TABLE fcdrschema.classification OWNER TO postgres;

--
-- Name: classification_classification_id_seq; Type: SEQUENCE; Schema: fcdrschema; Owner: postgres
--

CREATE SEQUENCE fcdrschema.classification_classification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fcdrschema.classification_classification_id_seq OWNER TO postgres;

--
-- Name: classification_classification_id_seq; Type: SEQUENCE OWNED BY; Schema: fcdrschema; Owner: postgres
--

ALTER SEQUENCE fcdrschema.classification_classification_id_seq OWNED BY fcdrschema.classification.classification_id;


--
-- Name: component; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.component (
    component_id integer NOT NULL,
    component_name text NOT NULL,
    nft_order integer NOT NULL,
    display_if_empty boolean NOT NULL,
    dv_reference_amount double precision,
    dv_unit_of_measure character varying(4)
);


ALTER TABLE fcdrschema.component OWNER TO postgres;

--
-- Name: component_component_id_seq; Type: SEQUENCE; Schema: fcdrschema; Owner: postgres
--

CREATE SEQUENCE fcdrschema.component_component_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fcdrschema.component_component_id_seq OWNER TO postgres;

--
-- Name: component_component_id_seq; Type: SEQUENCE OWNED BY; Schema: fcdrschema; Owner: postgres
--

ALTER SEQUENCE fcdrschema.component_component_id_seq OWNED BY fcdrschema.component.component_id;


--
-- Name: image; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.image (
    image_id integer NOT NULL,
    image_name text NOT NULL,
    package_id_fkey integer NOT NULL,
    extension character varying(6) NOT NULL,
    image_path text NOT NULL
);


ALTER TABLE fcdrschema.image OWNER TO postgres;

--
-- Name: image_image_id_seq; Type: SEQUENCE; Schema: fcdrschema; Owner: postgres
--

CREATE SEQUENCE fcdrschema.image_image_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fcdrschema.image_image_id_seq OWNER TO postgres;

--
-- Name: image_image_id_seq; Type: SEQUENCE OWNED BY; Schema: fcdrschema; Owner: postgres
--

ALTER SEQUENCE fcdrschema.image_image_id_seq OWNED BY fcdrschema.image.image_id;


--
-- Name: label_temp; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.label_temp (
    record_id double precision,
    label_upc character varying(20),
    nielsen_item_rank character varying(20),
    nielsen_category text,
    label_description text,
    label_brand text,
    label_manufacturer text,
    label_country text,
    package_size double precision,
    package_size_unit_of_measure character varying(4),
    number_of_units integer,
    storage_type text,
    storage_statement text,
    collection_date date,
    health_claims text,
    nutrient_claims text,
    other_package_statements text,
    suggested_directions text,
    ingredients text,
    multipart boolean,
    nutrition_fact_table text,
    common_household_measure text,
    per_serving_amount_as_sold double precision,
    per_serving_amount_unit_of_measure_as_sold character varying(4),
    per_serving_amount_as_prepared double precision,
    per_serving_amount_unit_of_measure_as_prepared character varying(4),
    energy_kcal_as_sold double precision,
    energy_kcal_as_prepared double precision,
    energy_kj_as_sold double precision,
    energy_kj_as_prepared double precision,
    fat_as_sold double precision,
    fat_daily_value_as_sold double precision,
    fat_daily_value_as_prepared double precision,
    saturated_fat_as_sold double precision,
    trans_fat_as_sold double precision,
    saturated_plus_trans_daily_value_as_sold double precision,
    saturated_plus_trans_daily_value_as_prepared double precision,
    fat_polyunsatured_as_sold double precision,
    fat_polyunsaturated_omega_6_as_sold double precision,
    fat_polyunsaturated_omega_3_as_sold double precision,
    fat_monounsaturated_as_sold double precision,
    carbohydrates_as_sold double precision,
    carbohydrates_daily_value_as_sold double precision,
    carbohydrates_daily_value_as_prepared double precision,
    fibre_as_sold double precision,
    fibre_daily_value_sold double precision,
    fibre_daily_value_as_prepared double precision,
    soluble_fibre_as_sold double precision,
    insoluble_fibre_as_sold double precision,
    sugar_total_sold double precision,
    sugar_total_daily_value_as_sold double precision,
    sugar_total_daily_value_as_prepared double precision,
    sugar_alcohols_as_sold double precision,
    starch_as_sold double precision,
    protein_as_sold double precision,
    cholesterol_as_sold double precision,
    cholesterol_daily_value_as_sold double precision,
    cholesterol_daily_value_as_prepared double precision,
    sodium_as_sold double precision,
    sodium_daily_value_as_sold double precision,
    sodium_daily_value_as_prepared double precision,
    potassium_as_sold double precision,
    potassium_daily_value_as_sold double precision,
    potassium_daily_value_as_prepared double precision,
    calcium_as_sold double precision,
    calcium_daily_value_as_sold double precision,
    calcium_daily_value_as_prepared double precision,
    iron_as_sold double precision,
    iron_daily_value_as_sold double precision,
    iron_daily_value_as_prepared double precision,
    vitamin_a_as_sold double precision,
    vitamin_a_daily_value_as_sold double precision,
    vitamin_a_daily_value_as_prepared double precision,
    vitamin_c_as_sold double precision,
    vitamin_c_daily_value_as_sold double precision,
    vitamin_c_daily_value_as_prepared double precision,
    vitamin_d_as_sold double precision,
    vitamin_d_daily_value_as_sold double precision,
    vitamin_d_daily_value_as_prepared double precision,
    vitamin_e_as_sold double precision,
    vitamin_e_daily_value_as_sold double precision,
    vitamin_e_daily_value_as_prepared double precision,
    vitamin_k_as_sold double precision,
    vitamin_k_daily_value_as_sold double precision,
    vitamin_k_daily_value_as_prepared double precision,
    thiamine_as_sold double precision,
    thiamine_daily_value_as_sold double precision,
    thiamine_daily_value_as_prepared double precision,
    riboflavin_as_sold double precision,
    riboflavin_daily_value_as_sold double precision,
    riboflavin_daily_value_as_prepared double precision,
    niacin_as_sold double precision,
    niacin_daily_value_as_sold double precision,
    niacin_daily_value_as_prepared double precision,
    vitamin_b6_as_sold double precision,
    vitamin_b6_daily_value_as_sold double precision,
    vitamin_b6_daily_value_as_prepared double precision,
    folate_as_sold double precision,
    folate_daily_value_as_sold double precision,
    folate_daily_value_as_prepared double precision,
    vitamin_b12_as_sold double precision,
    vitamin_b12_daily_value_as_sold double precision,
    vitamin_b12_daily_value_as_prepared double precision,
    biotin_as_sold double precision,
    biotin_daily_value_as_sold double precision,
    biotin_daily_value_as_prepared double precision,
    choline_as_sold double precision,
    choline_daily_value_as_sold double precision,
    choline_daily_value_as_prepared double precision,
    pantothenate_as_sold double precision,
    pantothenate_daily_value_as_sold double precision,
    pantothenate_daily_value_as_prepared double precision,
    phosphorus_as_sold double precision,
    phosphorus_daily_value_as_sold double precision,
    phosphorus_daily_value_as_prepared double precision,
    iodide_as_sold double precision,
    iodide_daily_value_as_sold double precision,
    iodide_daily_value_as_prepared double precision,
    magnesium_as_sold double precision,
    magnesium_daily_value_as_sold double precision,
    magnesium_daily_value_as_prepared double precision,
    zinc_as_sold double precision,
    zinc_daily_value_as_sold double precision,
    zinc_daily_value_as_prepared double precision,
    selenium_as_sold double precision,
    selenium_daily_value_as_sold double precision,
    selenium_daily_value_as_prepared double precision,
    copper_as_sold double precision,
    copper_daily_value_as_sold double precision,
    copper_daily_value_as_prepared double precision,
    manganese_as_sold double precision,
    manganese_daily_value_as_sold double precision,
    manganese_daily_value_as_prepared double precision,
    chromium_as_sold double precision,
    chromium_daily_value_as_sold double precision,
    chromium_daily_value_as_prepared double precision,
    molybdenum_as_sold double precision,
    molybdenum_daily_value_as_sold double precision,
    molybdenum_daily_value_as_prepared double precision,
    chloride_as_sold double precision,
    chloride_daily_value_as_sold double precision,
    chloride_daily_value_as_prepared double precision,
    per_serving_amount_in_grams_as_sold double precision,
    per_serving_amount_in_grams_as_prepared double precision,
    label_comment text,
    label_source text,
    product_description text,
    type text,
    type_of_restaurant text,
    informed_dining_program boolean,
    nft_last_update_date date,
    child_item boolean,
    product_grouping double precision,
    classification_number text
);


ALTER TABLE fcdrschema.label_temp OWNER TO postgres;

--
-- Name: foo_a_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.foo_a_seq
    START WITH 12345
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.foo_a_seq OWNER TO postgres;

--
-- Name: package; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.package (
    package_id integer DEFAULT nextval('public.foo_a_seq'::regclass) NOT NULL,
    package_description text,
    package_upc character varying(20),
    package_brand text,
    package_manufacturer text,
    package_country text,
    package_size double precision,
    package_size_unit_of_measure character varying(4),
    storage_type text,
    storage_statements text,
    health_claims text,
    other_package_statements text,
    suggested_directions text,
    ingredients text,
    multi_part_flag boolean,
    nutrition_fact_table text,
    as_prepared_per_serving_amount double precision,
    as_prepared_unit_of_measure character varying(4),
    as_sold_per_serving_amount double precision,
    as_sold_unit_of_measure character varying(4),
    as_prepared_per_serving_amount_in_grams double precision,
    as_sold_per_serving_amount_in_grams double precision,
    package_comment text,
    package_source text,
    package_product_description text,
    package_collection_date date,
    number_of_units integer,
    creation_date timestamp without time zone,
    last_edit_date timestamp without time zone,
    edited_by text,
    package_product_id_fkey integer,
    informed_dining_program boolean,
    nft_last_update_date date,
    product_grouping double precision,
    child_item boolean,
    package_classification_number text,
    package_classification_name text,
    nielsen_item_rank character varying(20),
    package_nielsen_category text,
    nutrient_claims text,
    common_household_measure text,
    calculated boolean
);


ALTER TABLE fcdrschema.package OWNER TO postgres;

--
-- Name: package_package_id_seq; Type: SEQUENCE; Schema: fcdrschema; Owner: postgres
--

CREATE SEQUENCE fcdrschema.package_package_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fcdrschema.package_package_id_seq OWNER TO postgres;

--
-- Name: package_package_id_seq; Type: SEQUENCE OWNED BY; Schema: fcdrschema; Owner: postgres
--

ALTER SEQUENCE fcdrschema.package_package_id_seq OWNED BY fcdrschema.package.package_id;


--
-- Name: product; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.product (
    product_id integer NOT NULL,
    product_description text NOT NULL,
    product_brand text,
    product_country text,
    cluster_number double precision,
    product_comment text,
    product_manufacturer text,
    cnf_code integer,
    creation_date timestamp without time zone,
    last_edit_date timestamp without time zone,
    edited_by text,
    restaurant_type text,
    type text
);


ALTER TABLE fcdrschema.product OWNER TO postgres;

--
-- Name: product_classification; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.product_classification (
    product_classification_product_id_fkey integer NOT NULL,
    product_classification_classification_id_fkey integer NOT NULL
);


ALTER TABLE fcdrschema.product_classification OWNER TO postgres;

--
-- Name: product_component; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.product_component (
    package_id integer NOT NULL,
    component_id integer NOT NULL,
    amount double precision,
    amount_unit_of_measure character varying(4),
    percentage_daily_value double precision,
    as_ppd_flag boolean NOT NULL,
    amount_per100g double precision
);


ALTER TABLE fcdrschema.product_component OWNER TO postgres;

--
-- Name: product_product_id_seq; Type: SEQUENCE; Schema: fcdrschema; Owner: postgres
--

CREATE SEQUENCE fcdrschema.product_product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fcdrschema.product_product_id_seq OWNER TO postgres;

--
-- Name: product_product_id_seq; Type: SEQUENCE OWNED BY; Schema: fcdrschema; Owner: postgres
--

ALTER SEQUENCE fcdrschema.product_product_id_seq OWNED BY fcdrschema.product.product_id;


--
-- Name: restaurant_types; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.restaurant_types (
    restaurant_type_id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE fcdrschema.restaurant_types OWNER TO postgres;

--
-- Name: restaurant_types_restaurant_type_id_seq; Type: SEQUENCE; Schema: fcdrschema; Owner: postgres
--

CREATE SEQUENCE fcdrschema.restaurant_types_restaurant_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fcdrschema.restaurant_types_restaurant_type_id_seq OWNER TO postgres;

--
-- Name: restaurant_types_restaurant_type_id_seq; Type: SEQUENCE OWNED BY; Schema: fcdrschema; Owner: postgres
--

ALTER SEQUENCE fcdrschema.restaurant_types_restaurant_type_id_seq OWNED BY fcdrschema.restaurant_types.restaurant_type_id;


--
-- Name: sales; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.sales (
    sales_id integer NOT NULL,
    sales_description text NOT NULL,
    sales_upc character varying(20) NOT NULL,
    sales_brand text,
    sales_manufacturer text,
    dollar_rank double precision,
    dollar_volume double precision,
    dollar_share double precision,
    dollar_volume_percentage_change double precision,
    kilo_volume double precision NOT NULL,
    kilo_share double precision,
    kilo_volume_percentage_change double precision,
    average_ac_dist double precision,
    average_retail_price double precision,
    sales_source text,
    nielsen_category text,
    sales_year smallint,
    control_label_flag boolean,
    kilo_volume_total double precision,
    kilo_volume_rank double precision,
    dollar_volume_total double precision,
    cluster_number double precision,
    product_grouping double precision,
    sales_product_description text,
    classification_number text,
    classification_type text,
    sales_comment text,
    sales_collection_date date,
    number_of_units integer,
    creation_date timestamp without time zone,
    last_edit_date timestamp without time zone,
    edited_by text,
    sales_product_id_fkey integer,
    kilo_rank double precision
);


ALTER TABLE fcdrschema.sales OWNER TO postgres;

--
-- Name: sales_sales_id_seq; Type: SEQUENCE; Schema: fcdrschema; Owner: postgres
--

CREATE SEQUENCE fcdrschema.sales_sales_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fcdrschema.sales_sales_id_seq OWNER TO postgres;

--
-- Name: sales_sales_id_seq; Type: SEQUENCE OWNED BY; Schema: fcdrschema; Owner: postgres
--

ALTER SEQUENCE fcdrschema.sales_sales_id_seq OWNED BY fcdrschema.sales.sales_id;


--
-- Name: sales_temp; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.sales_temp (
    record_id double precision,
    sales_description text,
    sales_upc character varying(20),
    sales_brand text,
    sales_manufacturer text,
    dollar_rank double precision,
    dollar_volume double precision,
    dollar_share double precision,
    dollar_volume_percentage_change double precision,
    kilo_volume double precision,
    kilo_share double precision,
    kilo_volume_percentage_change double precision,
    average_ac_dist double precision,
    average_retail_price double precision,
    sales_source text,
    nielsen_category text,
    sales_year smallint,
    control_label_flag boolean,
    kilo_volume_total double precision,
    kilo_volume_rank double precision,
    dollar_volume_total double precision,
    product_grouping double precision,
    sales_product_description text,
    sales_comment text,
    sales_collection_date date,
    cluster_number double precision,
    classification_number text,
    classification_type text,
    kilo_rank double precision
);


ALTER TABLE fcdrschema.sales_temp OWNER TO postgres;

--
-- Name: types; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.types (
    type_id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE fcdrschema.types OWNER TO postgres;

--
-- Name: types_type_id_seq; Type: SEQUENCE; Schema: fcdrschema; Owner: postgres
--

CREATE SEQUENCE fcdrschema.types_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fcdrschema.types_type_id_seq OWNER TO postgres;

--
-- Name: types_type_id_seq; Type: SEQUENCE OWNED BY; Schema: fcdrschema; Owner: postgres
--

ALTER SEQUENCE fcdrschema.types_type_id_seq OWNED BY fcdrschema.types.type_id;


--
-- Name: unit_of_measure; Type: TABLE; Schema: fcdrschema; Owner: postgres
--

CREATE TABLE fcdrschema.unit_of_measure (
    unit_of_measure_id integer NOT NULL,
    unit_of_measure_name character varying(4) NOT NULL
);


ALTER TABLE fcdrschema.unit_of_measure OWNER TO postgres;

--
-- Name: unit_of_measure_unit_of_measure_id_seq; Type: SEQUENCE; Schema: fcdrschema; Owner: postgres
--

CREATE SEQUENCE fcdrschema.unit_of_measure_unit_of_measure_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fcdrschema.unit_of_measure_unit_of_measure_id_seq OWNER TO postgres;

--
-- Name: unit_of_measure_unit_of_measure_id_seq; Type: SEQUENCE OWNED BY; Schema: fcdrschema; Owner: postgres
--

ALTER SEQUENCE fcdrschema.unit_of_measure_unit_of_measure_id_seq OWNED BY fcdrschema.unit_of_measure.unit_of_measure_id;


--
-- Name: restaurant_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.restaurant_types (
    restaurant_type_id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE public.restaurant_types OWNER TO postgres;

--
-- Name: restaurant_types_restaurant_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.restaurant_types_restaurant_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.restaurant_types_restaurant_type_id_seq OWNER TO postgres;

--
-- Name: restaurant_types_restaurant_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.restaurant_types_restaurant_type_id_seq OWNED BY public.restaurant_types.restaurant_type_id;


--
-- Name: types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.types (
    type_id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE public.types OWNER TO postgres;

--
-- Name: types_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.types_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.types_type_id_seq OWNER TO postgres;

--
-- Name: types_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.types_type_id_seq OWNED BY public.types.type_id;


--
-- Name: classification classification_id; Type: DEFAULT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.classification ALTER COLUMN classification_id SET DEFAULT nextval('fcdrschema.classification_classification_id_seq'::regclass);


--
-- Name: component component_id; Type: DEFAULT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.component ALTER COLUMN component_id SET DEFAULT nextval('fcdrschema.component_component_id_seq'::regclass);


--
-- Name: image image_id; Type: DEFAULT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.image ALTER COLUMN image_id SET DEFAULT nextval('fcdrschema.image_image_id_seq'::regclass);


--
-- Name: product product_id; Type: DEFAULT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.product ALTER COLUMN product_id SET DEFAULT nextval('fcdrschema.product_product_id_seq'::regclass);


--
-- Name: restaurant_types restaurant_type_id; Type: DEFAULT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.restaurant_types ALTER COLUMN restaurant_type_id SET DEFAULT nextval('fcdrschema.restaurant_types_restaurant_type_id_seq'::regclass);


--
-- Name: sales sales_id; Type: DEFAULT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.sales ALTER COLUMN sales_id SET DEFAULT nextval('fcdrschema.sales_sales_id_seq'::regclass);


--
-- Name: types type_id; Type: DEFAULT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.types ALTER COLUMN type_id SET DEFAULT nextval('fcdrschema.types_type_id_seq'::regclass);


--
-- Name: unit_of_measure unit_of_measure_id; Type: DEFAULT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.unit_of_measure ALTER COLUMN unit_of_measure_id SET DEFAULT nextval('fcdrschema.unit_of_measure_unit_of_measure_id_seq'::regclass);


--
-- Name: restaurant_types restaurant_type_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restaurant_types ALTER COLUMN restaurant_type_id SET DEFAULT nextval('public.restaurant_types_restaurant_type_id_seq'::regclass);


--
-- Name: types type_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.types ALTER COLUMN type_id SET DEFAULT nextval('public.types_type_id_seq'::regclass);


--
-- Name: classification classification_pkey; Type: CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.classification
    ADD CONSTRAINT classification_pkey PRIMARY KEY (classification_id);


--
-- Name: component component_nft_order_key; Type: CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.component
    ADD CONSTRAINT component_nft_order_key UNIQUE (nft_order);


--
-- Name: component component_pkey; Type: CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.component
    ADD CONSTRAINT component_pkey PRIMARY KEY (component_id);


--
-- Name: image image_id_pkey; Type: CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.image
    ADD CONSTRAINT image_id_pkey PRIMARY KEY (image_id);


--
-- Name: package package_package_id_pk; Type: CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.package
    ADD CONSTRAINT package_package_id_pk PRIMARY KEY (package_id);


--
-- Name: product_classification product_classification_pkey; Type: CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.product_classification
    ADD CONSTRAINT product_classification_pkey PRIMARY KEY (product_classification_product_id_fkey, product_classification_classification_id_fkey);


--
-- Name: product_component product_component_pk; Type: CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.product_component
    ADD CONSTRAINT product_component_pk PRIMARY KEY (package_id, component_id, as_ppd_flag);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (product_id);


--
-- Name: sales sales_pkey; Type: CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.sales
    ADD CONSTRAINT sales_pkey PRIMARY KEY (sales_id);


--
-- Name: product_classification_classification_id; Type: INDEX; Schema: fcdrschema; Owner: postgres
--

CREATE INDEX product_classification_classification_id ON fcdrschema.product_classification USING btree (product_classification_classification_id_fkey);


--
-- Name: product_classification_product_id_idx; Type: INDEX; Schema: fcdrschema; Owner: postgres
--

CREATE INDEX product_classification_product_id_idx ON fcdrschema.product_classification USING btree (product_classification_product_id_fkey);


--
-- Name: product_compoment_component_id_idx; Type: INDEX; Schema: fcdrschema; Owner: postgres
--

CREATE INDEX product_compoment_component_id_idx ON fcdrschema.product_component USING btree (component_id);


--
-- Name: product_component_package_id_idx; Type: INDEX; Schema: fcdrschema; Owner: postgres
--

CREATE INDEX product_component_package_id_idx ON fcdrschema.product_component USING btree (package_id);


--
-- Name: sales_product_id_idx; Type: INDEX; Schema: fcdrschema; Owner: postgres
--

CREATE INDEX sales_product_id_idx ON fcdrschema.sales USING btree (sales_product_id_fkey);


--
-- Name: sales_sales_upc_idx; Type: INDEX; Schema: fcdrschema; Owner: postgres
--

CREATE INDEX sales_sales_upc_idx ON fcdrschema.sales USING btree (sales_upc);


--
-- Name: image package_id_fkey; Type: FK CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.image
    ADD CONSTRAINT package_id_fkey FOREIGN KEY (package_id_fkey) REFERENCES fcdrschema.package(package_id);


--
-- Name: package package_product_id_fkey; Type: FK CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.package
    ADD CONSTRAINT package_product_id_fkey FOREIGN KEY (package_product_id_fkey) REFERENCES fcdrschema.product(product_id);


--
-- Name: product_classification product_classification_product_classification_classificati_fkey; Type: FK CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.product_classification
    ADD CONSTRAINT product_classification_product_classification_classificati_fkey FOREIGN KEY (product_classification_classification_id_fkey) REFERENCES fcdrschema.classification(classification_id);


--
-- Name: product_classification product_classification_product_classification_product_id_f_fkey; Type: FK CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.product_classification
    ADD CONSTRAINT product_classification_product_classification_product_id_f_fkey FOREIGN KEY (product_classification_product_id_fkey) REFERENCES fcdrschema.product(product_id);


--
-- Name: product_component product_component_component_id_fk; Type: FK CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.product_component
    ADD CONSTRAINT product_component_component_id_fk FOREIGN KEY (component_id) REFERENCES fcdrschema.component(component_id);


--
-- Name: product_component product_component_package_id_fk; Type: FK CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.product_component
    ADD CONSTRAINT product_component_package_id_fk FOREIGN KEY (package_id) REFERENCES fcdrschema.package(package_id);


--
-- Name: sales sales_sales_product_id_fkey_fkey; Type: FK CONSTRAINT; Schema: fcdrschema; Owner: postgres
--

ALTER TABLE ONLY fcdrschema.sales
    ADD CONSTRAINT sales_sales_product_id_fkey_fkey FOREIGN KEY (sales_product_id_fkey) REFERENCES fcdrschema.product(product_id);


--
-- PostgreSQL database dump complete
--

