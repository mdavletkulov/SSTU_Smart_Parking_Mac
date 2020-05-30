create sequence hibernate_sequence start with 1 increment by 1

create table message
(
    id        bigint not null,
    file_name varchar(255),
    tag       varchar(255),
    text      varchar(2048),
    user_id   bigint,
    primary key (id)
)

create table usr
(
    id              bigint identity not null,
    activation_code varchar(255),
    enabled         bit    not null,
    first_name      varchar(255) not null ,
    middle_name     varchar(255),
    password        varchar(255) not null ,
    role            varchar(255) not null ,
    second_name     varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
)

create table usr_temp
(
    id              bigint identity not null,
    activation_code varchar(255),
    enabled         bit    not null,
    first_name      varchar(255) not null ,
    middle_name     varchar(255),
    password        varchar(255) not null ,
    role            varchar(255) not null ,
    second_name     varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
)

alter table message
    add constraint user_message_fk foreign key (user_id) references usr