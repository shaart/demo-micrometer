-- auto-generated definition
create table otp
(
    id         uuid        not null
        constraint otp_pk
            primary key,
    phone      varchar(20) not null,
    code       varchar(6)  not null,
    created_at timestamp   not null
)

