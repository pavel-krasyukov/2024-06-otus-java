create table client
(
    id         bigserial,
    name       varchar(50),
    address_id bigint
);
create table address
(
    id     bigserial,
    street varchar(50)
);
create table phone
(
    id        bigserial,
    number    varchar(50),
    client_id bigint not null
);
create sequence client_seq start with 1 increment by 1;
create sequence address_seq start with 1 increment by 1;
create sequence phone_seq start with 1 increment by 1;
