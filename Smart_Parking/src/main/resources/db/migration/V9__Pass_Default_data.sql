insert into division (name)
values (N'ИнЭТС'),
       (N'ИММ'),
       (N'ИнЭТиП'),
       (N'ИнПИТ'),
       (N'ФТИ'),
       (N'УРБАС'),
       (N'ИСПМ');

insert into color (name)
values (N'Красный'),
       (N'Синий'),
       (N'Белый'),
       (N'Серый'),
       (N'Черный'),
       (N'Желтый'),
       (N'Коричневый'),
       (N'Оранжевый'),
       (N'Зеленый');

insert into job_position(name_position)
values (N'Заведующий кафедрой'),
       (N'Профессор'),
       (N'Доцент'),
       (N'Ассистент'),
       (N'Старший преподаватель'),
       (N'Президент университета'),
       (N'Первый проректор'),
       (N'Ректор');

insert into parking(description, image_name)
values (N'Парковка у первого корпуса', 'first_Parking.jpg'),
       (N'Парковка у второго корпуса', 'parking_2.jpg');
