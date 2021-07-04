drop table holl;
CREATE TABLE holl (
  id SERIAL PRIMARY KEY,
  row INT NOT NULL,
  cell INT NOT NULL
);
insert into holl(id, row, cell) VALUES (1, 1, 1);
insert into holl(id, row, cell) VALUES (2, 1, 2);
insert into holl(id, row, cell) VALUES (3, 1, 3);

insert into holl(id, row, cell) VALUES (4, 2, 1);
insert into holl(id, row, cell) VALUES (5, 2, 2);
insert into holl(id, row, cell) VALUES (6, 2, 3);

insert into holl(id, row, cell) VALUES (7, 3, 1);
insert into holl(id, row, cell) VALUES (8, 3, 2);
insert into holl(id, row, cell) VALUES (9, 3, 3);


drop table account;
CREATE TABLE account (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

insert into account(id, username, email, phone) VALUES (1, 'username1', 'email', 'phone1');
insert into account(id, username, email, phone) VALUES (2, 'username2', 'emai2', 'phone2');
insert into account(id, username, email, phone) VALUES (3, 'username3', 'emai3', 'phone3');


drop table ticket;
CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL,
    row INT NOT NULL,
    cell INT NOT NULL,
    account_id INT NOT NULL REFERENCES account(id) UNIQUE
 --   CONSTRAINT valid_session CHECK (session_id > 0 )
 --   CONSTRAINT valid_row CHECK (row > 0 and row < 4)
 --   CONSTRAINT valid_cell CHECK (cell > 0 and cell < 4)
 --   ,CONSTRAINT order_details UNIQUE (row, cell)
);

ALTER TABLE ticket ADD CONSTRAINT dist_id_zipcode_key UNIQUE (row, cell);

insert into ticket(id, session_id, row, cell, account_id) VALUES (1, 1, 1, 1, 1);
--insert into ticket(id, session_id, row, cell, account_id) VALUES (2, 1, 1, 2, 102);
--insert into ticket(id, session_id, row, cell, account_id) VALUES (3, 1, 1, 3, 103);

--insert into ticket(id, session_id, row, cell, account_id) VALUES (4, 1, 2, 1, 104);
insert into ticket(id, session_id, row, cell, account_id) VALUES (2, 1, 2, 2, 2);
--insert into ticket(id, session_id, row, cell, account_id) VALUES (6, 1, 2, 3, 106);

--insert into ticket(id, session_id, row, cell, account_id) VALUES (7, 1, 3, 1, 107);
--insert into ticket(id, session_id, row, cell, account_id) VALUES (8, 1, 3, 2, 108);
insert into ticket(id, session_id, row, cell, account_id) VALUES (3, 1, 3, 3, 3);
