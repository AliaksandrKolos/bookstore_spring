INSERT INTO covers (cover_name)
VALUES ('PAPERBACK'),
       ('HARDCOVER'),
       ('LEATHER'),
       ('DUST_JACKET');




INSERT INTO books (author, isbn, title, genre, year, pages, price, cover_id)
VALUES ('Harper Lee', '9780061220084', 'To Kill a Mockingbird', 'Fiction', 1960, 336, 7.19,
        (SELECT id FROM covers WHERE cover_name = 'PAPERBACK')),
       ('George Orwell', '9780351524935', '1984', 'Dystopian', 1949, 328, 7.48,
        (SELECT id FROM covers WHERE cover_name = 'HARDCOVER')),
       ('F. Scott Fitzgerald', '1780743273565', 'The Great Gatsby', 'Classic', 1925, 180, 6.89,
        (SELECT id FROM covers WHERE cover_name = 'LEATHER')),
       ('Jane Austen', '9781503250563', 'Pride and Prejudice', 'Romance', 1813, 279, 9.99,
        (SELECT id FROM covers WHERE cover_name = 'DUST_JACKET')),
       ('J.K. Rowling', '9780439639601', 'Harry Potter and the Goblet of Fire', 'Fantasy', 2000, 734, 9.99,
        (SELECT id FROM covers WHERE cover_name = 'PAPERBACK')),
       ('Mark Twain', '9780486283615', 'The Adventures of Huckleberry Finn', 'Adventure', 1884, 224, 4.99,
        (SELECT id FROM covers WHERE cover_name = 'HARDCOVER')),
       ('Mary Shelley', '9780486282114', 'Frankenstein', 'Horror', 1818, 166, 5.95,
        (SELECT id FROM covers WHERE cover_name = 'LEATHER')),
       ('Charles Dickens', '9781503219762', 'A Tale of Two Cities', 'Historical', 1859, 489, 7.99,
        (SELECT id FROM covers WHERE cover_name = 'HARDCOVER')),
       ('Arthur Conan Doyle', '9780451524935', 'Sherlock Holmes: The Complete Novels and Stories', 'Mystery', 1887,
        1088, 14.99, (SELECT id FROM covers WHERE cover_name = 'DUST_JACKET')),
       ('J.R.R. Tolkien', '9780261103324', 'The Hobbit', 'Fantasy', 1937, 320, 10.99,
        (SELECT id FROM covers WHERE cover_name = 'HARDCOVER')),
       ('Stephen King', '9781501152979', 'It', 'Horror', 1986, 1138, 19.99,
        (SELECT id FROM covers WHERE cover_name = 'PAPERBACK')),
       ('Agatha Christie', '9780022073563', 'Murder on the Orient Express', 'Mystery', 1934, 256, 8.99,
        (SELECT id FROM covers WHERE cover_name = 'DUST_JACKET')),
       ('Leo Tolstoy', '9781400077988', 'War and Peace', 'Historical', 1869, 1392, 18.00,
        (SELECT id FROM covers WHERE cover_name = 'LEATHER')),
       ('Ernest Hemingway', '9780884801223', 'The Old Man and the Sea', 'Literary', 1952, 127, 9.99,
        (SELECT id FROM covers WHERE cover_name = 'HARDCOVER')),
       ('Victor Hugo', '9780486452424', 'Les Misérables', 'Classic', 1862, 1472, 18.95,
        (SELECT id FROM covers WHERE cover_name = 'PAPERBACK')),
       ('Aldous Huxley', '9780060450524', 'Brave New World', 'Science Fiction', 1932, 288, 10.29,
        (SELECT id FROM covers WHERE cover_name = 'LEATHER')),
       ('Gabriel García Márquez', '1780060883287', 'One Hundred Years of Solitude', 'Literary', 1967, 417, 10.99,
        (SELECT id FROM covers WHERE cover_name = 'DUST_JACKET')),
       ('John Steinbeck', '9780142040670', 'Of Mice and Men', 'Fiction', 1937, 112, 8.99,
        (SELECT id FROM covers WHERE cover_name = 'LEATHER')),
       ('Charlotte Brontë', '9780142441146', 'Jane Eyre', 'Classic', 1847, 532, 10.95,
        (SELECT id FROM covers WHERE cover_name = 'LEATHER')),
       ('Herman Melville', '9781503480786', 'Moby-Dick', 'Adventure', 1851, 720, 11.99,
        (SELECT id FROM covers WHERE cover_name = 'HARDCOVER'));



INSERT INTO roles (role_name)
VALUES ('ADMIN'),
       ('MANAGER'),
       ('USER');



INSERT INTO users (first_name, last_name, email, password, role_id)
VALUES ('Bob', 'Kolos', 'admin', '$2a$10$F5yHCHB03pg9HvDMdhzNFezZPliL8DPn.SrN6BUS6PkdPHMGMlxpK', (SELECT id FROM roles WHERE role_name = 'ADMIN')),
       ('Sam', 'Smith', 'manager', '$2a$10$apVDSLKMbcHNCw96n6lQyOcyN5rzkUpznni.u6KMBIePLaZxiPxnm', (SELECT id FROM roles WHERE role_name = 'MANAGER')),
       ('Michael', 'Johnson', 'user', '$2a$10$V2E7Me71IleVB4nZuSGUz.OcqG27Ze2r6inthfS6j4HSMGyrBBHOa', (SELECT id FROM roles WHERE role_name = 'USER')),
       ('Alice', 'Brown', 'alice.brown1@example.com', 'FF37A98A9963D347E9749A5C1B3936A4A245A6FF', (SELECT id FROM roles WHERE role_name = 'ADMIN')),
       ('Charlie', 'Davis', 'charlie.davis1@example.com', '91DFD9DDB4198AFFC5C194CD8CE6D338FDE470E2', (SELECT id FROM roles WHERE role_name = 'MANAGER')),
       ('Eve', 'Wilson', 'eve.wilson1@example.com', 'EEE36A0F470074E37D6A545FBA4AAD0EF5F32413', (SELECT id FROM roles WHERE role_name = 'USER')),
       ('Michael', 'Taylor', 'michael.taylor1@example.com', 'E79A8581C65A2A45E5162F01187DB0842C0DBC4C', (SELECT id FROM roles WHERE role_name = 'ADMIN')),
       ('Lily', 'Anderson', 'lily.anderson1@example.com', 'E8AAEC882684217820E20E68B676289C4393290F', (SELECT id FROM roles WHERE role_name = 'MANAGER')),
       ('James', 'Thomas', 'james.thomas1@example.com', '2BE8BDFA335F748873719D3AC82AD76277794E4D', (SELECT id FROM roles WHERE role_name = 'USER')),
       ('Emily', 'Jackson', 'emily.jackson1@example.com', '2BE8BDFA335F748873719D3AC82AD76277794E4D', (SELECT id FROM roles WHERE role_name = 'ADMIN')),
       ('David', 'White', 'david.white1@example.com', '8715A2028130918155F187F5A1A6C1586BC38DA7', (SELECT id FROM roles WHERE role_name = 'USER')),
       ('Sophia', 'Harris', 'sophia.harris1@example.com', '53C1AD7F613532B1282D21506F093F87BDCDEAAF', (SELECT id FROM roles WHERE role_name = 'USER')),
       ('Chris', 'Martin', 'chris.martin1@example.com', '2146AAB3D145EEF1FDE1DE08BE7DB8D220432F79', (SELECT id FROM roles WHERE role_name = 'ADMIN')),
       ('Jessica', 'Lee', 'jessica.lee1@example.com', '72E658E2BA50024FB0FEEDF5B4085E2ACF5F6E1', (SELECT id FROM roles WHERE role_name = 'USER')),
       ('Daniel', 'Walker', 'daniel.walker1@example.com', '339FFD82B9BAD08E83D977EFF21C2AB5CF465B0C', (SELECT id FROM roles WHERE role_name = 'USER')),
       ('Mia', 'Lewis', 'mia.lewis1@example.com', '5B8C60EF3A2C1DA93E33267344A1DAFEC54A711B', (SELECT id FROM roles WHERE role_name = 'ADMIN')),
       ('Alex', 'Roberts', 'alex.roberts1@example.com', '8451394AA1CD8E4F4404D965426EAD702113139A', (SELECT id FROM roles WHERE role_name = 'USER')),
       ('Olivia', 'Clark', 'olivia.clark1@example.com', 'D4382FD0F5DD2AFEFB05ED4BD5C07840BEE99598', (SELECT id FROM roles WHERE role_name = 'USER')),
       ('Lucas', 'Hall', 'lucas.hall1@example.com', '5600CB662642EB7233E2DFACAD5C950422605CEB', (SELECT id FROM roles WHERE role_name = 'USER')),
       ('Ava', 'Young', 'ava.young1@example.com', 'A09ABAFF6E2306726A75D48C5070613E1B724BC4', (SELECT id FROM roles WHERE role_name = 'USER'));



INSERT INTO status (status_name)
VALUES ('PENDING'),
       ('PAID'),
       ('DELIVERED'),
       ('CANCELLED');


INSERT INTO orders (user_id, cost, status_id)
VALUES ((SELECT id FROM users WHERE email = 'admin'), 49.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'manager'), 29.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'user'), 19.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'alice.brown1@example.com'), 39.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'charlie.davis1@example.com'), 24.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'eve.wilson1@example.com'), 14.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'michael.taylor1@example.com'), 34.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'lily.anderson1@example.com'), 44.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'james.thomas1@example.com'), 54.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'emily.jackson1@example.com'), 64.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'david.white1@example.com'), 74.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'sophia.harris1@example.com'), 84.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'chris.martin1@example.com'), 94.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'jessica.lee1@example.com'), 104.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'daniel.walker1@example.com'), 114.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'mia.lewis1@example.com'), 124.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'alex.roberts1@example.com'), 134.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'olivia.clark1@example.com'), 144.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'lucas.hall1@example.com'), 154.99,
        (SELECT id FROM status WHERE status_name = 'PENDING')),
       ((SELECT id FROM users WHERE email = 'ava.young1@example.com'), 164.99,
        (SELECT id FROM status WHERE status_name = 'PENDING'));







INSERT INTO order_items (book_id, quantity, price, order_id)
VALUES
    ((SELECT id FROM books WHERE isbn = '9780061220084'), 1, 7.19, 1),
    ((SELECT id FROM books WHERE isbn = '9780351524935'), 1, 7.48, 2),
    ((SELECT id FROM books WHERE isbn = '1780743273565'), 1, 6.89, 3),
    ((SELECT id FROM books WHERE isbn = '9781503250563'), 1, 9.99, 4),
    ((SELECT id FROM books WHERE isbn = '9780439639601'), 1, 9.99, 5),
    ((SELECT id FROM books WHERE isbn = '9780486283615'), 1, 4.99, 6),
    ((SELECT id FROM books WHERE isbn = '9780486282114'), 1, 5.95, 7),
    ((SELECT id FROM books WHERE isbn = '9781503219762'), 1, 7.99, 8),
    ((SELECT id FROM books WHERE isbn = '9780451524935'), 1, 14.99, 9),
    ((SELECT id FROM books WHERE isbn = '9780261103324'), 1, 10.99, 10),
    ((SELECT id FROM books WHERE isbn = '9781501152979'), 1, 19.99, 11),
    ((SELECT id FROM books WHERE isbn = '9780022073563'), 1, 8.99, 12),
    ((SELECT id FROM books WHERE isbn = '9781400077988'), 1, 18.00, 13),
    ((SELECT id FROM books WHERE isbn = '9780884801223'), 1, 9.99, 14),
    ((SELECT id FROM books WHERE isbn = '9780486452424'), 1, 18.95, 15),
    ((SELECT id FROM books WHERE isbn = '9780060450524'), 1, 10.29, 16),
    ((SELECT id FROM books WHERE isbn = '1780060883287'), 1, 10.99, 17),
    ((SELECT id FROM books WHERE isbn = '9780142040670'), 1, 8.99, 18),
    ((SELECT id FROM books WHERE isbn = '9780142441146'), 1, 10.95, 19),
    ((SELECT id FROM books WHERE isbn = '9781503480786'), 1, 11.99, 20);
