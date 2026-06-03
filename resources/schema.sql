DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS menu_items;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS drivers;
DROP TABLE IF EXISTS restaurants;

CREATE TABLE clients (
                         id INTEGER PRIMARY KEY,
                         name TEXT NOT NULL,
                         phone TEXT NOT NULL,
                         city TEXT,
                         street TEXT,
                         building TEXT,
                         details TEXT
);

CREATE TABLE drivers (
                         id INTEGER PRIMARY KEY,
                         name TEXT NOT NULL,
                         phone TEXT NOT NULL,
                         vehicle_number TEXT NOT NULL,
                         available INTEGER NOT NULL,
                         rating REAL NOT NULL
);

CREATE TABLE restaurants (
                             id INTEGER PRIMARY KEY,
                             name TEXT NOT NULL,
                             address TEXT NOT NULL,
                             category TEXT NOT NULL
);

CREATE TABLE menu_items (
                            id INTEGER PRIMARY KEY,
                            name TEXT NOT NULL,
                            price REAL NOT NULL,
                            available INTEGER NOT NULL,
                            restaurant_id INTEGER NOT NULL,
                            FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);

CREATE TABLE orders (
                        id INTEGER PRIMARY KEY,
                        client_id INTEGER NOT NULL,
                        restaurant_id INTEGER NOT NULL,
                        driver_id INTEGER,
                        status TEXT NOT NULL,
                        created_at TEXT NOT NULL,
                        FOREIGN KEY (client_id) REFERENCES clients(id),
                        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
                        FOREIGN KEY (driver_id) REFERENCES drivers(id)
);

CREATE TABLE order_items (
                             order_id INTEGER NOT NULL,
                             menu_item_id INTEGER NOT NULL,
                             quantity INTEGER NOT NULL,
                             PRIMARY KEY (order_id, menu_item_id),
                             FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                             FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
);