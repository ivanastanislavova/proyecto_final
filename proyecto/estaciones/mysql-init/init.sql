CREATE TABLE IF NOT EXISTS ESTACION (
    id SERIAL PRIMARY KEY,
    direction VARCHAR(255),
    latitude FLOAT,
    longitude FLOAT
);

INSERT INTO ESTACION (id, direction, latitude, longitude) VALUES
(1, 'Avda. de Francia', 39.4615, -0.3431),
(2, 'Pista de Silla', 39.4370, -0.3765),
(3, 'Molí del Sol', 39.4789, -0.4056),
(4, 'Politècnic', 39.4815, -0.3408),
(5, 'Centre', 39.4699, -0.3763),
(6, 'Bulevard Sud', 39.4512, -0.3768),
(7, 'Vivers', 39.4750, -0.3610),
(8, 'Puerto de València', 39.4541, -0.3165);
