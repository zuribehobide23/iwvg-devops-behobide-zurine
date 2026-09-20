DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100),
    family_name VARCHAR(100),
    email VARCHAR(150),
    identity VARCHAR(100),
    address VARCHAR(150),
    city VARCHAR(100),
    province VARCHAR(100),
    postal_code VARCHAR(20),
    active BOOLEAN DEFAULT FALSE,
    role VARCHAR(20) NOT NULL,
    mobile VARCHAR(20)
);

INSERT INTO users (
    first_name,
    family_name,
    email,
    identity,
    address,
    city,
    province,
    postal_code,
    active,
    role,
    mobile
) VALUES (
             'Zurine',
             'Behobide',
             'zurine@example.com',
             '12345678A',
             'Main Street 1',
             'Irun',
             'Gipuzkoa',
             '20300',
             FALSE,
             'ADMIN',
             '600000001'
         );

INSERT INTO users (
    first_name,
    family_name,
    email,
    identity,
    address,
    city,
    province,
    postal_code,
    active,
    role,
    mobile
) VALUES (
             'Oihana',
             'Example',
             'oihana@example.com',
             '',
             'Main Street 2',
             'Irun',
             'Gipuzkoa',
             '20300',
             FALSE,
             'MANAGER',
             '600000002'
         );

INSERT INTO users (
    first_name,
    family_name,
    email,
    identity,
    address,
    city,
    province,
    postal_code,
    active,
    role,
    mobile
) VALUES (
             'Unax',
             'Example',
             NULL,
             '12345678B',
             'Main Street 3',
             'Irun',
             'Gipuzkoa',
             '20300',
             FALSE,
             'MANAGER',
             '600000003'
         );

SELECT * FROM users;
