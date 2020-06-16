insert into usr (username, password, enabled, first_name, second_name, middle_name, role)
values ('tolmachevvn@sstu.ru', '12345678Ab', 1, N'Владимир', N'Толмачев', N'Николаевич', 'ADMIN');

update usr set password = dbo.Bcrypt(password,8) where second_name = N'Толмачев';

insert into job_position(name_position)
values (N'Директор института');

insert into person(course, employee, first_name, second_name, middle_name, group_name, pass_end_date,
                   pass_num, special_status, student, job_position, subdivision, division)
values (null, 1, N'Ольга', N'Долинина', N'Николаевна', null, DATEADD(month, 6, current_timestamp), 25, 0, 0, (select id from job_position where name_position = N'Директор института'), 16, 4);
insert into automobile(model, number, color_id, person_id)
values ('Nissan X-Trail', 'H380HP64', 4, (select id from person where second_name = N'Долинина'));
