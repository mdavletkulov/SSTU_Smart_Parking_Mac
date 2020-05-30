create table automobile
(
    id        bigint identity not null,
    model     varchar(50),
    number    varchar(12),
    color_id  bigint,
    person_id bigint,
    primary key (id)
)

create table color
(
    id   bigint  identity not null,
    name varchar(255) not null,
    primary key (id)
)

create table division
(
    id   bigint identity not null,
    name varchar(30) not null,
    primary key (id)
)

create table job_position
(
    id                bigint identity not null,
    name_position     varchar(60)  not null,
    type_job_position varchar(255) not null,
    primary key (id)
)

create table parking
(
    id           bigint identity not null,
    description  varchar(150),
    image_name varchar(255),
    primary key (id)
)

create table parking_event
(
    id            bigint identity not null,
    end_time      datetime2,
    start_time    datetime2 not null,
    automobile_id bigint      ,
    place_id      bigint   ,
    status_violation       bit default 0,
    pass_num_violation        bit default 0,
    person_violation bit default 0,
    auto_violation bit default 0,
    person_id bigint default null,
    primary key (id)
)

create table parking_place
(
    id           bigint identity not null,
    place_number int not null,
    parking_id   bigint not null,
    special_status       bit         not null,
    primary key (id)
)

create table person
(
    id             bigint identity not null,
    course         int,
    employee       bit         not null,
    first_name     varchar(60) not null,
    group_name     varchar(10),
    middle_name    varchar(60),
    pass_end_date  datetime2,
    pass_num       int,
    second_name    varchar(60),
    special_status bit         not null,
    student        bit         not null,
    job_position   bigint,
    division    bigint,
    subdivision    bigint,
    primary key (id)
)

create table subdivision
(
    id          bigint identity not null,
    name        varchar(100) not null,
    division_id bigint,
    primary key (id)
)

alter table automobile
    add constraint automobileColor_FK foreign key (color_id) references color on delete set null

alter table automobile
    add constraint personAutomobile_FK foreign key (person_id) references person on delete set null

alter table parking_event
    add constraint eventAutomobile_FK foreign key (automobile_id) references automobile on delete set null

alter table parking_event
    add constraint eventPerson_FK foreign key (person_id) references person on delete set null

alter table parking_event
    add constraint evenPlace_FK foreign key (place_id) references parking_place on delete set null

alter table parking_place
    add constraint placeParking_FK foreign key (parking_id) references parking on delete cascade

alter table person
    add constraint personJob_FK foreign key (job_position) references job_position on delete set null

alter table person
    add constraint personDivision_FK foreign key (division) references division on delete set null

alter table person
    add constraint personSubdivision_FK foreign key (subdivision) references subdivision on delete set null

alter table subdivision
    add constraint subdivision_division_FK foreign key (division_id) references division on delete set null