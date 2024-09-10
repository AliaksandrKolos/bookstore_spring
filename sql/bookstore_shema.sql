CREATE TABLE IF NOT EXISTS covers
(
    id   SERIAL PRIMARY KEY,
    cover_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS books
(
    id       BIGSERIAL PRIMARY KEY,
    author   VARCHAR(200),
    isbn     VARCHAR(13) NOT NULL,
    title    VARCHAR(255),
    genre    VARCHAR(255),
    year     INTEGER,
    pages    INTEGER,
    price    DECIMAL(10, 2),
    cover_id INTEGER,
    deleted  BOOLEAN  DEFAULT FALSE,
    FOREIGN KEY (cover_id) REFERENCES covers (id)
);

-- /////////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS roles
(
    id   SERIAL PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(70),
    last_name  VARCHAR(70),
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(60)  NOT NULL,
    role_id    INTEGER,
    deleted    BOOLEAN  DEFAULT FALSE,
    FOREIGN KEY (role_id) REFERENCES roles (id)
);


-- /////////////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS status
(
    id   SERIAL PRIMARY KEY,
    status_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
    id        BIGSERIAL PRIMARY KEY,
    user_id   BIGINT         NOT NULL,
    cost      DECIMAL(10, 2) NOT NULL,
    status_id INTEGER       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (status_id) REFERENCES status (id)
);


CREATE TABLE IF NOT EXISTS order_items
(
    id       BIGSERIAL PRIMARY KEY,
    book_id  BIGINT,
    quantity INTEGER        NOT NULL,
    price    DECIMAL(10, 2) NOT NULL,
    order_id BIGINT,
    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (order_id) REFERENCES orders (id)
);