CREATE TABLE menu_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    price DOUBLE
);
 
CREATE TABLE inventory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    quantity INT
);
 
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    table_no INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
 
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    item_name VARCHAR(100),
    quantity INT,
    price DOUBLE,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
 
 
CREATE TABLE recipe_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dish_name VARCHAR(100),      -- eşleşme için
    ingredient_name VARCHAR(100),
    quantity INT
);
 
INSERT INTO recipe_items (dish_name, ingredient_name, quantity) VALUES
('Cheeseburger', 'Bun', 1),
('Cheeseburger', 'Cheese', 1),
('Cheeseburger', 'Beef Patty', 1),
('Cheeseburger', 'Lettuce', 1),
('Margherita Pizza', 'Cheese', 2),
('Margherita Pizza', 'Tomatoes', 3);
 
CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50),
password VARCHAR(50),
role VARCHAR(20) -- örnek: 'admin', 'garson'
);
 
INSERT INTO users (username, password, role) VALUES
('admin', 'admin', 'admin'),
('a', 'a', 'admin'),
('garson', '1234', 'garson');

INSERT INTO menu_items (name, price) VALUES
('Cheeseburger', 10.0),
('Margherita Pizza', 12.0),
('Caesar Salad', 9.5),
('Cheesecake', 7.5),
('Tomato Soup', 8.0),
('Spaghetti Bolognese', 14.0),
('Grilled Salmon', 18.5),
('Sushi', 22.0);

INSERT INTO inventory (name, quantity) VALUES
-- Cheeseburger için
('Bun', 50),
('Cheese', 40),
('Beef Patty', 30),
('Lettuce', 30),

-- Margherita Pizza için
('Dough', 25),
('Tomatoes', 35),
('Cheese', 20),
('Basil', 15),

-- Caesar Salad için
('Lettuce', 25),
('Croutons', 20),
('Parmesan', 15),
('Caesar Dressing', 10),

-- Tomato Soup için
('Tomatoes', 30),
('Onions', 20),
('Cream', 10),

-- Spaghetti Bolognese için
('Spaghetti', 40),
('Ground Beef', 25),
('Tomato Sauce', 20),
('Onions', 15),

-- Grilled Salmon için
('Salmon Fillet', 20),
('Lemon', 15),
('Butter', 10),

-- Sushi için
('Sushi Rice', 30),
('Nori', 20),
('Raw Fish', 25),
('Cucumber', 20),

-- Cheesecake için
('Cream Cheese', 15),
('Sugar', 20),
('Butter', 10),
('Biscuit Crumbs', 10);




