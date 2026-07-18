INSERT INTO "user" (name, date_of_birth, password)
VALUES ('User', '2026-05-30', '$2a$10$D0QWc66kJNtO2CBZU0uYvO.4JQcwu3ePboFMYU7B9eRPIBxiNNSZ6');
INSERT INTO account (initial_balance, balance, user_id)
VALUES (450.00, 40.00, 1);
INSERT INTO email_data (email, user_id)
VALUES ('user@mail.ru', 1);
INSERT INTO phone_data (phone, user_id)
VALUES ('79207865432', 1);
INSERT INTO "user" (name, date_of_birth, password)
VALUES ('Alice Smith', '1995-03-20', '$2a$10$4CXhBsALTPxHbTH47OcLfey0mOHPL1KR6w7D8F39oD0jLpjPo44Ou');
INSERT INTO account (initial_balance, balance, user_id)
VALUES (5000.00, 110.00, 2);
INSERT INTO email_data (email, user_id)
VALUES ('alice@example.com', 2);
INSERT INTO phone_data (phone, user_id)
VALUES ('79123456789', 2);
INSERT INTO "user" (name, date_of_birth, password)
VALUES ('Bob Johnson', '1988-11-15', '$2a$10$LbZMWKpzkTM0iUZZcGUEnu5e3S/nxtFIBH4qdaBgzhXsJIiFDzcaS');
INSERT INTO account (initial_balance, balance, user_id)
VALUES (110.00, 110.00, 3);
INSERT INTO email_data (email, user_id)
VALUES ('bob@example.com', 3);
INSERT INTO phone_data (phone, user_id)
VALUES ('79876543210', 3);