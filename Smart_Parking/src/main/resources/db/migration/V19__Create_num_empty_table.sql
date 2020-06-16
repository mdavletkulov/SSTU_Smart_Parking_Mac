create table num_empty
(
    id           bigint identity not null,
    place_id bigint not null,
    count   int not null,
    primary key (id)
)

alter table num_empty
    add constraint numEmptyPlace_FK foreign key (place_id) references parking_place