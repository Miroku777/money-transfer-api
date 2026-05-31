INSERT INTO "user" (name, date_of_birth, password)
VALUES ('user', '2026-05-30', '$2a$10$hmAC4aQEhD3i69YyTtC05.GrjgeE9X8r8z2nKYKt9vrRw.GB/rvDC');
INSERT INTO account (initial_balance, balance, user_id, version)
VALUES (450.00, 40.00, 1, 0);
INSERT INTO email_data (email, user_id)
VALUES ('test@mail.ru', 1);
INSERT INTO phone_data (phone, user_id)
VALUES ('79207865432', 1);
INSERT INTO "user" (name, date_of_birth, password)
VALUES ('Alice Smith', '1995-03-20', '$2a$10$NkMwpUoXGgFqKqXgYqXgYqXgYqXgYqXgYqXgYq');
INSERT INTO account (initial_balance, balance, user_id, version)
VALUES (5000.00, 110.00, 2, 0);
INSERT INTO email_data (email, user_id)
VALUES ('alice@example.com', 2);
INSERT INTO phone_data (phone, user_id)
VALUES ('79123456789', 2);
INSERT INTO "user" (name, date_of_birth, password)
VALUES ('Bob Johnson', '1988-11-15', '$2a$10$NkMwpUoXGgFqKqXgYqXgYqXgYqXgYqXgYqXgYq');
INSERT INTO account (initial_balance, balance, user_id, version)
VALUES (110.00, 110.00, 3, 0);
INSERT INTO email_data (email, user_id)
VALUES ('bob@example.com', 3);
INSERT INTO phone_data (phone, user_id)
VALUES ('79876543210', 3);


select u.id, u.name, e.email, p.phone, initial_balance, balance
from "user" u
join account a on u.id=a.user_id
join email_data e on u.id = e.user_id
join phone_data p on u.id=p.user_id;