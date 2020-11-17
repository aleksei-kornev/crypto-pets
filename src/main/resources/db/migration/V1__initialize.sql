drop table if exists currencies cascade;
create table currencies (id bigserial, title varchar(255), description varchar(5000), price int, primary key(id));
insert into currencies
(title, description, price) values
('BTC', 'Bitcoin', 17040),
('ETH', 'Ethereum', 471),
('Tether', 'Fresh apples', 1),
('XRP', 'Fresh bread', 0.30);

drop table if exists categories cascade;
create table categories (id bigserial, title varchar(255), primary key(id));
insert into categories
(title) values
('Cryptocurrencies'),
('Stock');

drop table if exists currencies_categories cascade;
create table currencies_categories (currency_id bigint not null, category_id bigint not null, primary key(currency_id, category_id),
foreign key (currency_id) references currencies(id), foreign key (category_id) references categories(id));
insert into currencies_categories (currency_id, category_id) values (1, 1), (2, 1), (3, 1), (4, 2);

drop table if exists users;
create table users (
  id                    bigserial,
  phone                 VARCHAR(30) not null UNIQUE,
  password              VARCHAR(80) not null,
  email                 VARCHAR(50) UNIQUE,
  first_name            VARCHAR(50),
  last_name             VARCHAR(50),
  PRIMARY KEY (id)
);

drop table if exists roles;
create table roles (
  id                    serial,
  name                  VARCHAR(50) not null,
  primary key (id)
);

drop table if exists users_roles;
create table users_roles (
  user_id               INT NOT NULL,
  role_id               INT NOT NULL,
  primary key (user_id, role_id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (id)
);

insert into roles (name)
values
('ROLE_CUSTOMER'), ('ROLE_MANAGER'), ('ROLE_ADMIN');

insert into users (phone, password, first_name, last_name, email)
values
('11111111','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i','admin','admin','admin@gmail.com');

insert into users_roles (user_id, role_id)
values
(1, 1),
(1, 2),
(1, 3);