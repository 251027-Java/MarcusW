-- Active: 1762362216248@@127.0.0.1@5432@mysampledb@mysampleschema
CREATE DATABASE mySampleDb;

-- Connect to mySampleDb database

CREATE SCHEMA mySampleSchema;

CREATE TABLE myNewTable (
    --id INTEGER UNIQUE NOT NULL, --Doesn't make it a primary key, but means it could be
    id INTEGER PRIMARY KEY,
    email VARCHAR(100),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, --If not provided in insert statement, just give me one anyway
    salary FLOAT CHECK (salary > 0), --Constraint
    --secondSalary DOUBLE,
    thirdSalary DECIMAL,
    fourthSalary NUMERIC (10, 2) --precision (total number of digits), scale (total number of digits to the right of the decimal)
)

CREATE TABLE mySampleSchema.anotherSimpleTable (
    id INTEGER PRIMARY KEY,
    name VARCHAR(100)
);
-- Usually create basic tables at first. Get them built!
-- Then we will alter adding references or constraints. 
ALTER TABLE mysampleschema.AnotherSimpleTable
ADD COLUMN newTable_id INTEGER;

--Common to see this after creation
ALTER TABLE mysampleschema.AnotherSimpleTable
ADD CONSTRAINT fk_newTable_id
FOREIGN KEY (newTable_id) REFERENCES mysampleschema.myNewTable (id);

INSERT INTO mysampleschema.myNewTable (id, email, salary, thirdSalary, fourthSalary)
VALUES (1, 'john.doe@example.com', 50000, 60000, 70000),
(2, 'john.doe2@example.com', 50000, 60000, 70000),
(3, 'john.doe3@example.com', 50000, 60000, 70000),
(4, 'john.doe4@example.com', 50000, 60000, 70000),
(5, 'john.doe5@example.com', 50000, 60000, 70000);


-- Order matters for dropping because other objects may depend on it. 
-- DROP TABLE mysampleschema.AnotherSimpleTable;
-- DROP TABLE mysampleschema.myNewTable;

-- DROP CONSTRAINT fk_newTable_id; -- if we do this first, then we can drop tables in any order. 
