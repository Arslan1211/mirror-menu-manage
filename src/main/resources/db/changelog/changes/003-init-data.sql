-- 1. Сначала создаем пользователей с паролем pass1 для ivan и pass2 для roman
INSERT INTO users (username, password, email, role) VALUES
    ('ivan', 'pass1', 'van@example.com', 'ROLE_USER'),
    ('roman', 'pass2', 'roma@example.com', 'ROLE_USER');

-- 2. Добавление блюд (created_by использует ID первого пользователя)
INSERT INTO dishes (name, description, category, price, quantity, created_by) VALUES
                                                                                  ('Борщ', 'Традиционный украинский борщ со сметаной', 'MAIN_COURSE', 350, 10, 1),
                                                                                  ('Цезарь', 'Салат с курицей, листьями айсберга и соусом цезарь', 'APPETIZER', 420, 15, 1),
                                                                                  ('Тирамису', 'Итальянский десерт с кофейной пропиткой', 'DESSERT', 280, 8, 1),
                                                                                  ('Мохито', 'Освежающий коктейль с лаймом и мятой', 'DRINK', 320, 20, 2),
                                                                                  ('Стейк', 'Говяжий стейк средней прожарки с овощами', 'MAIN_COURSE', 790, 5, 2);
