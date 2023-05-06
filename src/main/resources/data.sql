INSERT INTO "role" VALUES (1, 'Пользователь', 'USER') ON CONFLICT DO NOTHING;
INSERT INTO "role" VALUES (2, 'Администратор', 'ADMIN') ON CONFLICT DO NOTHING;

INSERT INTO "deps" VALUES (1, 'Руководитель организации') ON CONFLICT DO NOTHING;
INSERT INTO "deps" VALUES (2, 'Администратор ИБ') ON CONFLICT DO NOTHING;
INSERT INTO "deps" VALUES (3, 'Менеджер ИБ') ON CONFLICT DO NOTHING;
INSERT INTO "deps" VALUES (4, 'Нормативный отдел') ON CONFLICT DO NOTHING;
INSERT INTO "deps" VALUES (5, 'ГРИИБ') ON CONFLICT DO NOTHING;
INSERT INTO "deps" VALUES (6, 'Отдел аудита') ON CONFLICT DO NOTHING;
INSERT INTO "deps" VALUES (7, 'Отдел внедрения ИС') ON CONFLICT DO NOTHING;
INSERT INTO "deps" VALUES (8, 'Наша организация') ON CONFLICT DO NOTHING;

INSERT INTO users (id, active, address, email, job_position, name, "password", patronymic, reg_date, surname, department_id) VALUES(1, true, 'г. Саратов', 'head@gmail.com', 'Генеральный директор', 'Маргарита', '$2a$10$XDNIBDS1AWxANKP9/o9VvOZsBaJ9xloPtU648IK07Hf7oT.hFwv2y', 'Алексеевна', '2023-04-23 14:20:43.719', 'Петрова', 1) ON CONFLICT DO NOTHING;
INSERT INTO users (id, active, address, email, job_position, name, "password", patronymic, reg_date, surname, department_id) VALUES(2, true, 'г. Саратов', 'adm@gmail.com', 'Начальник управления по безопасности', 'Захар', '$2a$10$XDNIBDS1AWxANKP9/o9VvOZsBaJ9xloPtU648IK07Hf7oT.hFwv2y', 'Константинович', '2023-04-23 14:20:43.719', 'Иванов', 2) ON CONFLICT DO NOTHING;
INSERT INTO users (id, active, address, email, job_position, name, "password", patronymic, reg_date, surname, department_id) VALUES(3, true, 'г. Саратов', 'mngr@gmail.com', 'Руководитель подразделения ИБ', 'Арсений', '$2a$10$XDNIBDS1AWxANKP9/o9VvOZsBaJ9xloPtU648IK07Hf7oT.hFwv2y', 'Серафимович', '2023-04-23 14:20:43.719', 'Романов', 3) ON CONFLICT DO NOTHING;
INSERT INTO users (id, active, address, email, job_position, name, "password", patronymic, reg_date, surname, department_id) VALUES(4, true, 'г. Саратов', 'oper_regulator@gmail.com', 'Оператор нормативного отдела', 'Ясмина', '$2a$10$XDNIBDS1AWxANKP9/o9VvOZsBaJ9xloPtU648IK07Hf7oT.hFwv2y', 'Марковна', '2023-04-23 14:20:43.719', 'Кузнецова', 4) ON CONFLICT DO NOTHING;
INSERT INTO users (id, active, address, email, job_position, name, "password", patronymic, reg_date, surname, department_id) VALUES(5, true, 'г. Саратов', 'oper_isirt@gmail.com', 'Оператор ГРИИБ', 'Владислав', '$2a$10$XDNIBDS1AWxANKP9/o9VvOZsBaJ9xloPtU648IK07Hf7oT.hFwv2y', 'Даниилович', '2023-04-23 14:20:43.719', 'Румянцев', 5) ON CONFLICT DO NOTHING;
INSERT INTO users (id, active, address, email, job_position, name, "password", patronymic, reg_date, surname, department_id) VALUES(6, true, 'г. Саратов', 'oper_audit@gmail.com', 'Оператор аудитор', 'Валерия', '$2a$10$XDNIBDS1AWxANKP9/o9VvOZsBaJ9xloPtU648IK07Hf7oT.hFwv2y', 'Адамовна', '2023-04-23 14:20:43.719', 'Михайлова', 6) ON CONFLICT DO NOTHING;
INSERT INTO users (id, active, address, email, job_position, name, "password", patronymic, reg_date, surname, department_id) VALUES(7, true, 'г. Саратов', 'oper_isid@gmail.com', 'Оператор отдела внедрения ИС', 'Денис', '$2a$10$XDNIBDS1AWxANKP9/o9VvOZsBaJ9xloPtU648IK07Hf7oT.hFwv2y', 'Маркович', '2023-04-23 14:20:43.719', 'Васильев', 7) ON CONFLICT DO NOTHING;
INSERT INTO users (id, active, address, email, job_position, name, "password", patronymic, reg_date, surname, department_id) VALUES(8, true, 'г. Саратов', 'user@gmail.com', 'Java разработчик', 'Мирон', '$2a$10$XDNIBDS1AWxANKP9/o9VvOZsBaJ9xloPtU648IK07Hf7oT.hFwv2y', 'Глебович', '2023-04-23 14:20:43.719', 'Тимофеев', 8) ON CONFLICT DO NOTHING;
