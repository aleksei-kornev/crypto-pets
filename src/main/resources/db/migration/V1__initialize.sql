drop table if exists currencies cascade;
create table currencies (id bigserial, ticker varchar(255), name varchar(255), description varchar(5000), USD real, primary key(id));
insert into currencies
(ticker, name, description, USD) values
('BTC', 'Bitcoin', '', 17040.2),
('ETH', 'Ethereum', '', 471.1),
('USDT', 'Tether', '', 1),
('XRP', 'Ripple', '', 0.30);

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

drop table if exists portfolios cascade;
create table portfolios (id bigserial, user_id bigserial, public boolean, cost real, last_update timestamp, primary key(id));
insert into portfolios
(user_id, public, cost) values
(1, true, 100),
(2, true, 200);

drop table if exists positions cascade;
create table positions (id bigserial, amount bigserial not null, currency_id bigserial not null, portfolio_id bigserial not null, primary key(id),
foreign key (currency_id) references currencies(id), foreign key (portfolio_id) references portfolios(id));
insert into positions
(amount, currency_id, portfolio_id) values
(3, 1, 1),
(30, 3, 1),
(49, 2, 1);

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