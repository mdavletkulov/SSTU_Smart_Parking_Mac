update job_position set name_position = N'Директор' where name_position = N'директор ЦИТиДО';
update job_position set name_position = N'Программист' where name_position = N'Программист ЦИТиДО';
update job_position set name_position = N'Начальник сектора виртуальных технологий' where name_position = N'Начальник сектора виртуальных технологий ЦИТиДО';

update person set subdivision = (Select id from subdivision where name=N'УБЦ АПТЭК СГТУ') where second_name = N'Анисимова';
update person set subdivision = (Select id from subdivision where name=N'ЦИТиДО') where second_name = N'Королев';
update person set subdivision = (Select id from subdivision where name=N'ЦИТиДО') where second_name = N'Синицын';
update person set subdivision = (Select id from subdivision where name=N'ЦИТиДО') where second_name = N'Круглов';

update person set division = (Select id from division where name=N'ИнПИТ') where second_name = N'Анисимова';
update person set division = (Select id from division where name=N'ИнПИТ') where second_name = N'Королев';
update person set division = (Select id from division where name=N'ИнПИТ') where second_name = N'Синицын';
update person set division = (Select id from division where name=N'ИнПИТ') where second_name = N'Круглов';