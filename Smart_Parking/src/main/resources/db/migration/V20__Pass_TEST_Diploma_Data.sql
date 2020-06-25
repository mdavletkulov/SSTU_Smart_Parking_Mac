insert into person(course, employee, first_name, second_name, middle_name, group_name, pass_end_date,
                   pass_num, special_status, student, job_position, subdivision, division)
values (4, 0, N'Максим', N'Давлеткулов', N'Артурович', N'б2-ПИНФ41', null, null, 0, 1, null, 16, 4);

insert into automobile(model, number, color_id, person_id)
values ('Chevrolet Aveo', 'B008AK164', 4, (select id from person where second_name = N'Давлеткулов'));

insert into parking_event(end_time, start_time, automobile_id, place_id, status_violation, pass_num_violation, person_violation, auto_violation, person_id, photo_name, unknown_num)
 values ('2020-06-15 16:10:26.00000', '2020-06-15 9:02:12.00000', null, 5, 0, 0, 0, 0, null, null, 'B636MP164'),
 ('2020-06-15 16:00:54.00000', '2020-06-15 8:57:19.00000', null, 6, 0, 0, 0, 0, null, null, 'A771TT164'),
 ('2020-06-15 18:01:37.00000', '2020-06-15 8:21:49.00000', 1, 7, 0, 0, 0, 0, null, null, null),
 ('2020-06-15 17:33:43.00000', '2020-06-15 8:36:36.00000', null, 8, 0, 0, 0, 0, null, null, 'B518OK164'),
 ('2020-06-15 18:13:22.00000', '2020-06-15 8:48:09.00000', 7, 9, 0, 0, 0, 0, null, null, null),
 ('2020-06-01 15:13:22.00000', '2020-06-01 9:48:09.00000', (select id from automobile where number = 'B008AK164'), 7, 0, 0, 0, 0, null, null, null);

insert into usr (enabled, first_name, middle_name, password, role, second_name, username)
values (1, N'Максим', N'Антонович', '123', 'ADMIN', N'Емельянов', 'sabbkull@gmail.com');