create table account ( id int8 not null, sum_available float8, sum_total float8, primary key (id) );

create table app_user ( id int8 not null, app_user_role varchar(255), email varchar(255), enabled boolean, instagram varchar(255), locked boolean,
                        password varchar(255), registration_date timestamp, username varchar(255), account_id int8, app_user_settings_id int8, primary key (id) );
create table app_user_settings ( id int8 not null, country_id int8, language_id int8, primary key (id) );
create table category ( id int8 not null, name varchar(255), primary key (id) );
create table comment ( id int8 not null, body varchar(255), app_user_id int8, pattern_id int8, primary key (id) );
create table country ( id int8 not null, name varchar(255), primary key (id) );
create table craft ( id int8 not null, name varchar(255), primary key (id) );
create table currency ( id int8 not null, name varchar(255), primary key (id) );
create table language ( id int8 not null, name varchar(255), primary key (id) );
create table live_row ( id int8 not null, is_info_row boolean, is_title_row boolean, row_number int4 not null, schema varchar(255), pattern_id int8, primary key (id) );
create table live_row_state (id int8 not null, app_user_id int8, live_row_id int8, primary key (id) );
create table pattern ( id int8 not null, abbreviations varchar(255),
                       avg_rate float8, difficulty_level varchar(255), image_path varchar(255), little_description varchar(255), name varchar(255), pdf_path varchar(255), price float8, category_id int8, craft_id int8, creator_id int8, currency_id int8, language_id int8, primary key (id) );
create table payment ( id int8 not null, payment float8, app_user_id int8, pattern_id int8, primary key (id) );
create table rate ( id int8 not null, value int4 not null, app_user_id int8, pattern_id int8, primary key (id) );
create table support_letter ( id int8 not null, body varchar(255), title varchar(255), app_user_id int8, primary key (id) );
-- Language: English, Russian
INSERT INTO language(name) VALUES ('English');
INSERT INTO language(name) VALUES ('Russian');
-- Currency: USD, BYN
INSERT INTO currency(name) VALUES ('USD');
INSERT INTO currency(name) VALUES ('BYN');
-- Country
INSERT INTO country(name) VALUES ('Belarus');
-- Craft: Crochet, Knitting
INSERT INTO craft(name) VALUES ('Crochet');
INSERT INTO craft(name) VALUES ('Knitting');
-- Category: Accessories, Clothing, Home, Pet, Toys
INSERT INTO category(name) VALUES('Accessories');
INSERT INTO category(name) VALUES('Clothing');
INSERT INTO category(name) VALUES('Home');
INSERT INTO category(name) VALUES('Pet');
INSERT INTO category(name) VALUES('Toys');

