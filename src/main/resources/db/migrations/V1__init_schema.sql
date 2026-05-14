CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    enabled BOOLEAN NOT NULL,
    password VARCHAR(255),
    username VARCHAR(255)
);

CREATE TABLE distributors (
    id BIGSERIAL PRIMARY KEY,
    nit BIGINT NOT NULL UNIQUE,
    street VARCHAR(255),
    city VARCHAR(255),
    neighborhood VARCHAR(255),
    email VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(255),
    user_id BIGINT UNIQUE
);

CREATE TABLE catalogs (
    id BIGSERIAL PRIMARY KEY,
    distributor_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    catalog_id BIGINT NOT NULL
);

CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE PRECISION,
    unit VARCHAR(255),
    category_product_id BIGINT,
    distributor_id BIGINT
);

CREATE TABLE categories_products (
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT,
    product_id UUID
);

CREATE TABLE deliveries (
    id BIGSERIAL PRIMARY KEY,
    document_number BIGINT,
    document_type VARCHAR(255),
    license_number VARCHAR(255),
    license_type VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(255),
    distributor_id BIGINT,
    user_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE delivery_routes (
    id BIGSERIAL PRIMARY KEY,
    delivery_id BIGINT,
    payment_receipt_id UUID UNIQUE
);

CREATE TABLE presales (
    id BIGSERIAL PRIMARY KEY,
    document_number BIGINT,
    document_type VARCHAR(255),
    email VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(255),
    distributor_id BIGINT,
    user_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE presales_routes (
    id BIGSERIAL PRIMARY KEY,
    presales_id BIGINT NOT NULL
);

CREATE TABLE stores (
    id BIGSERIAL PRIMARY KEY,
    nit BIGINT NOT NULL UNIQUE,
    claim_status VARCHAR(255) NOT NULL,
    street VARCHAR(255),
    city VARCHAR(255),
    neighborhood VARCHAR(255),
    email VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(255),
    presales_route_id BIGINT,
    user_id BIGINT UNIQUE
);

CREATE TABLE stores_distributors (
    id BIGSERIAL PRIMARY KEY,
    internal_client_code VARCHAR(255),
    distributor_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL
);

CREATE TABLE orders (
    id UUID PRIMARY KEY,
    distributor_id BIGINT,
    iva_percent DOUBLE PRECISION,
    status VARCHAR(255),
    total DOUBLE PRECISION,
    presales_id BIGINT,
    store_id BIGINT NOT NULL
);

CREATE TABLE order_details (
    quantity INT NOT NULL,
    unit_price DOUBLE PRECISION NOT NULL,
    product_id UUID NOT NULL,
    order_id UUID NOT NULL,
    PRIMARY KEY (order_id, product_id)
);

CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(255),
    model VARCHAR(255),
    vehicle_plate VARCHAR(255),
    distributor_id BIGINT
);

CREATE TABLE vehicle_delivery (
    vehicle_id BIGINT NOT NULL,
    delivery_id BIGINT NOT NULL
);

CREATE TABLE user_entity_roles (
    user_entity_id BIGINT NOT NULL,
    roles VARCHAR(255)
);

-- =========================
-- FOREIGN KEYS
-- =========================

ALTER TABLE distributors
    ADD FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE catalogs
    ADD FOREIGN KEY (distributor_id) REFERENCES distributors(id);

ALTER TABLE categories
    ADD FOREIGN KEY (catalog_id) REFERENCES catalogs(id);

ALTER TABLE categories_products
    ADD FOREIGN KEY (category_id) REFERENCES categories(id);

ALTER TABLE categories_products
    ADD FOREIGN KEY (product_id) REFERENCES products(id);

ALTER TABLE products
    ADD FOREIGN KEY (distributor_id) REFERENCES distributors(id);

ALTER TABLE products
    ADD FOREIGN KEY (category_product_id) REFERENCES categories_products(id);

ALTER TABLE deliveries
    ADD FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE deliveries
    ADD FOREIGN KEY (distributor_id) REFERENCES distributors(id);

ALTER TABLE delivery_routes
    ADD FOREIGN KEY (delivery_id) REFERENCES deliveries(id);

ALTER TABLE delivery_routes
    ADD FOREIGN KEY (payment_receipt_id) REFERENCES orders(id);

ALTER TABLE presales
    ADD FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE presales
    ADD FOREIGN KEY (distributor_id) REFERENCES distributors(id);

ALTER TABLE presales_routes
    ADD FOREIGN KEY (presales_id) REFERENCES presales(id);

ALTER TABLE stores
    ADD FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE stores
    ADD FOREIGN KEY (presales_route_id) REFERENCES presales_routes(id);

ALTER TABLE stores_distributors
    ADD FOREIGN KEY (store_id) REFERENCES stores(id);

ALTER TABLE stores_distributors
    ADD FOREIGN KEY (distributor_id) REFERENCES distributors(id);

ALTER TABLE orders
    ADD FOREIGN KEY (presales_id) REFERENCES presales(id);

ALTER TABLE orders
    ADD FOREIGN KEY (store_id) REFERENCES stores(id);

ALTER TABLE order_details
    ADD FOREIGN KEY (product_id) REFERENCES products(id);

ALTER TABLE order_details
    ADD FOREIGN KEY (order_id) REFERENCES orders(id);

ALTER TABLE vehicles
    ADD FOREIGN KEY (distributor_id) REFERENCES distributors(id);

ALTER TABLE vehicle_delivery
    ADD FOREIGN KEY (vehicle_id) REFERENCES vehicles(id);

ALTER TABLE vehicle_delivery
    ADD FOREIGN KEY (delivery_id) REFERENCES deliveries(id);

ALTER TABLE user_entity_roles
    ADD FOREIGN KEY (user_entity_id) REFERENCES users(id);
