insert into job_position(name_position, type_job_position)
values (N'Главный бухгалтер', 'AUP'),
       (N'Начальник сектора виртуальных технологий ЦИТиДО', 'AUP'),
       (N'Программист ЦИТиДО', 'AUP'),
       (N'директор ЦИТиДО', 'AUP');


insert into person(course, employee, first_name, second_name, middle_name, group_name, pass_end_date,
                   pass_num, special_status, student, job_position, subdivision, division)
values (null, 1, N'Елена', N'Анисимова', N'Станиславовна', null, DATEADD(month, 6, current_timestamp), 15, 0, 0, (select id from job_position where name_position = N'Главный бухгалтер'), null, null),
       (null, 1, N'Александр', N'Ермаков', N'Вадимович', null, DATEADD(month, 6, current_timestamp), 7, 0, 0, 3, 16, 4),
       (null, 1, N'Михаил', N'Королев', N'Сергеевич', null, DATEADD(month, 4, current_timestamp), 8, 0, 0, (select id from job_position where name_position = N'Начальник сектора виртуальных технологий ЦИТиДО'), null, null),
       (null, 1, N'Андрей', N'Круглов', N'Дмитриевич', null, DATEADD(month, 2, current_timestamp), 9, 0, 0, (select id from job_position where name_position = N'Программист ЦИТиДО'), null, null),
       (null, 1, N'Ольга', N'Торопова', N'Дмитриевна', null, DATEADD(month, 4, current_timestamp), 10, 0, 0, 3, 15, 4),
       (null, 1, N'Светлана', N'Кумова', N'Валентиновна', null, DATEADD(month, 6, current_timestamp), 11, 0, 0, 3, 16, 4),
       (null, 1, N'Алексей', N'Ершов', N'Сергеевич', null, DATEADD(month, 6, current_timestamp), 12, 0, 0, 3, 18, 4),
       (null, 1, N'Евгений', N'Захаров', N'Евгеньевич', null, DATEADD(month, 4, current_timestamp), 13, 0, 0, 3, 17, 4),
       (null, 1, N'Ирина', N'Сытник', N'Федоровна', null, DATEADD(month, 6, current_timestamp), 14, 0, 0, 3, 15, 4),
       (null, 1, N'Игорь', N'Синицын', N'Николаевич', null, DATEADD(month, 2, current_timestamp), 15, 0, 0, (select id from job_position where name_position = N'Директор ЦИТиДО'), null, null);

insert into automobile(model, number, color_id, person_id)

values ('Ford Explorer', 'H380HP64', 4, (select id from person where second_name = N'Синицын')),
('Lada Calina', 'T314PO64', 4, (select id from person where second_name = N'Анисимова')),
       ('Skoda Rapid', 'B339BE64', 1, (select id from person where second_name = N'Ермаков')),
       ('Skoda Rapid', 'B741AP64', 3, (select id from person where second_name = N'Королев')),
       ('Lada Granta', 'A557OY64', 3, (select id from person where second_name = N'Круглов')),
       ('Hyundai Matrix', 'Y619HY64', 4, (select id from person where second_name = N'Торопова')),
       ('Toyota RAV4', 'M408MP64', 4, (select id from person where second_name = N'Кумова')),
       ('Nissan Primera', 'X605CB64', 3, (select id from person where second_name = N'Ершов')),
       ('Chery tiggo', 'A745KT64', 3, (select id from person where second_name = N'Захаров')),
       ('Opel Astra', 'P928OC64', 4, (select id from person where second_name = N'Сытник'));
