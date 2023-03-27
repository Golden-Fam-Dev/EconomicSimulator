CREATE SCHEMA IF NOT EXISTS economicsimulator;

CREATE TABLE economicsimulator.resource (
    resource_id                 SMALLINT        NOT NULL    AUTO_INCREMENT,
    resource_name               VARCHAR(25)     NOT NULL,
    year_available              YEAR            NOT NULL,
    median_price                INTEGER         NOT NULL,
    delivery_time_sensitivity   INTEGER         NOT NULL,
    PRIMARY KEY (resource_id)
);

CREATE TABLE economicsimulator.functional_industry (
    industry_id                 SMALLINT        NOT NULL    AUTO_INCREMENT,
    industry_name               VARCHAR(50)     NOT NULL,
    introduction_year           YEAR            NOT NULL,
    industry_cost               INTEGER         NOT NULL,
    overhead                    INTEGER         NOT NULL,
    minimum_city_sized          SMALLINT        NOT NULL,
    relative_profitability      INTEGER,
    PRIMARY KEY (industry_id)
);

CREATE TABLE economicsimulator.production_industry (
    resource_id                 SMALLINT        NOT NULL,
    industry_id                 SMALLINT        NOT NULL,
    PRIMARY KEY (resource_id, industry_id),
    CONSTRAINT production_resource  FOREIGN KEY (resource_id)               REFERENCES resource (resource_id),
    CONSTRAINT production_industry  FOREIGN KEY (industry_id)               REFERENCES functional_industry (industry_id)
);

CREATE TABLE economicsimulator.consumption_industry (
    resource_id                 SMALLINT        NOT NULL,
    industry_id                 SMALLINT        NOT NULL,
    until                       YEAR,
    PRIMARY KEY (resource_id, industry_id),
    CONSTRAINT consumption_resource FOREIGN KEY (resource_id)               REFERENCES resource (resource_id),
    CONSTRAINT consumption_industry FOREIGN KEY (industry_id)               REFERENCES functional_industry (industry_id)
);

CREATE TABLE economicsimulator.industrial_industry (
    industry_id                 SMALLINT        NOT NULL,
    industry_type               SMALLINT        NOT NULL,
    retail_price                INTEGER         NOT NULL,
    min_price                   INTEGER         NOT NULL,
    production                  INTEGER         NOT NULL,
    rating                      VARCHAR(10)     NOT NULL,
    min_employee                SMALLINT        NOT NULL,
    max_employee                SMALLINT        NOT NULL,
    water_based                 BOOLEAN         NOT NULL,
    PRIMARY KEY (industry_id),
    CONSTRAINT industrial_industry  FOREIGN KEY (industry_id)               REFERENCES functional_industry (industry_id)
);

CREATE TABLE economicsimulator.residential (
    industry_id                 SMALLINT        NOT NULL,
    max_residence               SMALLINT        NOT NULL,
    retail_cost                 INTEGER         NOT NULL,
    PRIMARY KEY (industry_id),
    CONSTRAINT residential_industry FOREIGN KEY (industry_id)               REFERENCES functional_industry (industry_id)
);

CREATE TABLE economicsimulator.transport (
    transport_id                SMALLINT        NOT NULL    AUTO_INCREMENT,
    transport_make_id           SMALLINT        NOT NULL,
    transport_model             VARCHAR(25)     NOT NULL,
    year_available              YEAR            NOT NULL,
    cost                        INTEGER         NOT NULL,
    annual_maintenance          INTEGER         NOT NULL,
    fuel_economy_id             SMALLINT        NOT NULL,
    acceleration_id             SMALLINT        NOT NULL,
    reliability_id              SMALLINT        NOT NULL,
    passenger_appeal            SMALLINT        NOT NULL,
    PRIMARY KEY (transport_id)
);

CREATE TABLE economicsimulator.grade (
    grade_type                  CHAR(2)         NOT NULL,
    grade                       INTEGER         NOT NULL,
    PRIMARY KEY (grade_type)
);

CREATE TABLE economicsimulator.container_count (
    container_count_id          SMALLINT        NOT NULL    AUTO_INCREMENT,
    countainer_count            SMALLINT        NOT NULL,
    PRIMARY KEY (container_count_id)
);

CREATE TABLE economicsimulator.speed_chart (
    transport_id                SMALLINT        NOT NULL,
    cargo_id                    SMALLINT        NOT NULL,
    grade_type                  CHAR(2)         NOT NULL,
    container_count_id          SMALLINT        NOT NULL,
    speed                       SMALLINT        NOT NULL,
    PRIMARY KEY (transport_id)
);

CREATE TABLE economicsimulator.city (
    city_id                     SMALLINT        NOT NULL    AUTO_INCREMENT,
    latitude                    DECIMAL(8, 6)   NOT NULL,
    longitude                   DECIMAL(9, 6)   NOT NULL,
    population                  INTEGER         NOT NULL,
    PRIMARY KEY (city_id)
);

CREATE TABLE economicsimulator.generated_location (
    generated_location_id       INTEGER         NOT NULL    AUTO_INCREMENT,
    industry_id                 SMALLINT        NOT NULL,
    located_city_id             SMALLINT        NOT NULL,
    number_of_employees         SMALLINT        NOT NULL,
    PRIMARY KEY (generated_location_id),
    CONSTRAINT generated_industry   FOREIGN KEY (industry_id)               REFERENCES functional_industry (industry_id),
    CONSTRAINT generated_city       FOREIGN KEY (located_city_id)           REFERENCES city (city_id)
);

CREATE TABLE economicsimulator.owned_industry (
    generated_location_id       INTEGER         NOT NULL,
    PRIMARY KEY (generated_location_id),
    CONSTRAINT owned_industry       FOREIGN KEY (generated_location_id)     REFERENCES functional_industry (industry_id)
);



