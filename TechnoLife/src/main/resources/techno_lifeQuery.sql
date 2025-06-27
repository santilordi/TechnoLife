create database TechnoLifedb;
use TechnoLifedb;

-- Productos
INSERT INTO products (name, description, price, category, stock) VALUES
('Notebook Lenovo', 'Notebook Lenovo 15.6" 8GB RAM 512GB SSD', 650000, 'Tecnología', 10),
('Smartphone Samsung', 'Samsung Galaxy S23 128GB', 800000, 'Tecnología', 15),
('Auriculares Bluetooth', 'Auriculares inalámbricos con micrófono', 35000, 'Accesorios', 30),
('Mouse Gamer', 'Mouse óptico RGB 7200DPI', 18000, 'Accesorios', 25),
('Monitor LG', 'Monitor LG 24" Full HD', 120000, 'Tecnología', 8);

-- Admin default
INSERT INTO client (name, last_name, email, password, rol)
VALUES ('Admin', 'Principal', 'admin@admin.com', 'admin123', 'ADMIN');