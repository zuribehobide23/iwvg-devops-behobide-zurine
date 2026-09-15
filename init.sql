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
                       active BOOLEAN DEFAULT FALSE
);

-- Billable user
INSERT INTO users (
    first_name,
    family_name,
    email,
    identity,
    address,
    city,
    province,
    postal_code
) VALUES (
             'Zurine',
             'Behobide',
             'zurine@example.com',
             '12345678A',
             'Main Street 1',
             'Irun',
             'Gipuzkoa',
             '20300'
         );

-- Not billable: identity is empty
INSERT INTO users (
    first_name,
    family_name,
    email,
    identity,
    address,
    city,
    province,
    postal_code
) VALUES (
             'Oihana',
             'Example',
             'oihana@example.com',
             '',
             'Main Street 2',
             'Irun',
             'Gipuzkoa',
             '20300'
         );

-- Not billable: email is missing
INSERT INTO users (
    first_name,
    family_name,
    email,
    identity,
    address,
    city,
    province,
    postal_code
) VALUES (
             'Unax',
             'Example',
             NULL,
             '12345678B',
             'Main Street 3',
             'Irun',
             'Gipuzkoa',
             '20300'
         );
SELECT * FROM users;
