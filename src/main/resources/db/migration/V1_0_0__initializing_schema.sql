-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create the person table
CREATE TABLE person (
    person_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    ssn BYTEA NOT NULL, -- Encrypted SSN
    email VARCHAR(150) UNIQUE NOT NULL,
    date_of_birth DATE NOT NULL
);

-- Create the gross_income table
CREATE TABLE gross_income (
    grossincome_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    gross_income_value DECIMAL(15,2) NOT NULL,
    person_id UUID NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(person_id) ON DELETE CASCADE
);

-- Create the spend_category table
CREATE TABLE spend_category (
    category_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    category_name VARCHAR(100) UNIQUE NOT NULL
);

-- Create the spends table
CREATE TABLE spends (
    spend_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    spend_amount DECIMAL(15,2) NOT NULL,
    date_spend DATE NOT NULL,
    person_id UUID NOT NULL,
    category_id UUID NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(person_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES spend_category(category_id) ON DELETE CASCADE
);