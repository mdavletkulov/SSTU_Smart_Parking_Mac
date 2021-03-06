insert into subdivision(name, division_id, type_job_position)
values (N'Промышленная теплотехника', 1, 'PPS'),
       (N'Электроэнергетика и электротехника', 1, 'PPS'),
       (N'Тепловая и атомная энергетика', 1, 'PPS'),
       (N'Организация перевозок, безопасность движения и сервис автомобилей', 1, 'PPS'),
       (N'Транспортное строительство', 1, 'PPS'),
       (N'Сварка и металлургия', 2, 'PPS'),
       (N'Технология и системы управления в машиностроении', 2, 'PPS'),
       (N'Техническая механика и мехатроника', 2, 'PPS'),
       (N'Материаловедение и биомедицинская инженерия', 2, 'PPS'),
       (N'Инженерная геометрия и основы САПР', 2, 'PPS'),
       (N'Системотехника и управление в технических системах', 3, 'PPS'),
       (N'Радиоэлектроника и телекоммуникации', 3, 'PPS'),
       (N'Приборостроение', 3, 'PPS'),
       (N'Электронные приборы и устройства', 3, 'PPS'),
       (N'Информационно-коммуникационные системы и программная инженерия', 4, 'PPS'),
       (N'Прикладные информационные технологии', 4, 'PPS'),
       (N'Медиакоммуникации', 4, 'PPS'),
       (N'Информационная безопасность автоматизированных систем', 4, 'PPS'),
       (N'Математика и моделирование', 5, 'PPS'),
       (N'Прикладная математика и системный анализ', 5, 'PPS'),
       (N'Физика', 5, 'PPS'),
       (N'Химия и химическая технология материалов', 5, 'PPS'),
       (N'Природная и техносферная безопасность', 5, 'PPS'),
       (N'Архитектура', 6, 'PPS'),
       (N'Дизайн архитектурной среды', 6, 'PPS'),
       (N'Теория сооружений и строительных конструкций', 6, 'PPS'),
       (N'Теплогазоснабжение, вентиляция, водообеспечение и прикладная гидрогазодинамика', 6, 'PPS'),
       (N'Строительные материалы и технологии', 6, 'PPS'),
       (N'Геоэкология и инженерная геология', 6, 'PPS'),
       (N'Экономика труда и производственных комплексов', 7, 'PPS'),
       (N'Экономическая безопасность и управление инновациями', 7, 'PPS'),
       (N'Менеджмент и логистика', 7, 'PPS'),
       (N'Коммерция и инжиниринг бизнес-процессов', 7, 'PPS'),
       (N'Физическая культура и спорт', 7, 'PPS'),
       (N'История Отечества и культуры', 7, 'PPS'),
       (N'Философия', 7, 'PPS'),
       (N'Психология и прикладная социология', 7, 'PPS'),
       (N'Иностранные языки и профессиональная коммуникация', 7, 'PPS');

insert into parking_place(place_number, parking_id, special_status)
values (1, 1, 1),
       (2, 1, 1),
       (3, 1, 0),
       (4, 1, 0),
       (1, 2, 1),
       (2, 2, 0),
       (3, 2, 0),
       (4, 2, 1),
       (5, 2, 0);