create table country
(
    id bigserial not null primary key,
    name varchar(255) not null unique
);

create index on country (name);

create table genre
(
    id bigserial not null primary key,
    name varchar(255) not null unique
);

create index on genre (name);

create table film
(
    id bigserial not null primary key,
    name varchar(255) not null,
    year smallint not null,
    country_id bigint not null references country (id),
    budget bigint,
    constraint name_country_year_c unique (name, year, country_id)
);

create index on film (name, year, country_id);

create table film_genre
(
    id bigserial not null primary key,
    film_id bigint not null references film (id),
    genre_id bigint not null references genre (id),
    constraint film_genre_c unique (film_id, genre_id)
);

create index on film_genre (film_id, genre_id);
