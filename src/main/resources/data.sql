INSERT INTO "role" (id, display_name, "name") VALUES(1, 'Пользователь', 'USER') ON CONFLICT DO NOTHING;
INSERT INTO "role" (id, display_name, "name") VALUES(2, 'Администратор', 'ADMIN') ON CONFLICT DO NOTHING;
