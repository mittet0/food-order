CREATE TABLE foodDB
(
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
food_name VARCHAR(24) NOT NULL,
food_description VARCHAR(200),
food_cost DOUBLE NOT NULL,
CONSTRAINT primary_key PRIMARY KEY (id)
) ;

CREATE TABLE users
(
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
user_name VARCHAR(20) NOT NULL UNIQUE,
name VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL,
user_address VARCHAR(200),
phone_number VARCHAR(20) NOT NULL,
email VARCHAR(60) NOT NULL,
is_admin_role INTEGER NOT NULL DEFAULT 0
) ;

CREATE TABLE orders
(
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
food_name VARCHAR(40) NOT NULL,
food_description VARCHAR(200),
food_cost DOUBLE NOT NULL,
user_address VARCHAR(200)
) ;

INSERT INTO FOODDB ("FOOD_NAME", "FOOD_DESCRIPTION", "FOOD_COST") values ('Musaka', 'Bulgarian traditional meal', 3)
INSERT INTO FOODDB ("FOOD_NAME", "FOOD_DESCRIPTION", "FOOD_COST") values ('Tarator', 'Bulgarian traditional meal', 2)

INSERT INTO USERS ("USER_NAME", "NAME", "PASSWORD", "USER_ADDRESS", "PHONE_NUMBER", "EMAIL") values ('esin_faik', 'Esin Faik', '123456', 'Tervel, ul.N.Vapcarov', '0894528006', 'batesin@abv.bg')

ALTER TABLE contacts ADD email VARCHAR(60) AFTER name;