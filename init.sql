CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100) NULL
);

CREATE TABLE IF NOT EXISTS users_qr (
    qr_code UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO users (last_name, first_name, middle_name)
VALUES ('Смирнова', 'Анна', 'Федоровна'),
       ('Попова', 'Виктория', 'Федоровна'),
       ('Иванов', 'Олег', 'Семенович');

INSERT INTO users_qr (user_id)
VALUES (1), (2), (3);
       