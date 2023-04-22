INSERT INTO "role" (id, display_name, "name") VALUES (1, 'Пользователь', 'USER') ON CONFLICT DO NOTHING;
INSERT INTO "role" (id, display_name, "name") VALUES (2, 'Администратор', 'ADMIN') ON CONFLICT DO NOTHING;

INSERT INTO "deps" (id, "name") VALUES (1, 'Руководитель организации') ON CONFLICT DO NOTHING;
INSERT INTO "deps" (id, "name") VALUES (2, 'Администратор ИБ') ON CONFLICT DO NOTHING;
INSERT INTO "deps" (id, "name") VALUES (3, 'Менеджер ИБ') ON CONFLICT DO NOTHING;
INSERT INTO "deps" (id, "name") VALUES (4, 'Нормативный отдел') ON CONFLICT DO NOTHING;
INSERT INTO "deps" (id, "name") VALUES (5, 'ГРИИБ') ON CONFLICT DO NOTHING;
INSERT INTO "deps" (id, "name") VALUES (6, 'Отдел аудита') ON CONFLICT DO NOTHING;
INSERT INTO "deps" (id, "name") VALUES (7, 'Отдел внедрения ИС') ON CONFLICT DO NOTHING;
INSERT INTO "deps" (id, "name") VALUES (8, 'Наша организация') ON CONFLICT DO NOTHING;