CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(160) NOT NULL,
    phone VARCHAR(30),
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uk_customers_email UNIQUE (email)
);

ALTER TABLE claims
ADD COLUMN customer_id BIGINT;

INSERT INTO customers (
    first_name,
    last_name,
    email,
    phone,
    created_at
)
VALUES (
    'Demo',
    'Customer',
    'demo.customer@example.com',
    NULL,
    NOW()
);

UPDATE claims
SET customer_id = (
    SELECT id
    FROM customers
    WHERE email = 'demo.customer@example.com'
);

ALTER TABLE claims
ALTER COLUMN customer_id SET NOT NULL;

ALTER TABLE claims
ADD CONSTRAINT fk_claims_customer
FOREIGN KEY (customer_id)
REFERENCES customers(id);