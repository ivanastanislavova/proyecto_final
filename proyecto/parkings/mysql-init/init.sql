CREATE TABLE IF NOT EXISTS PARKING (
		id SERIAL PRIMARY KEY,
		direction VARCHAR(255),
		bikesCapacity INT,
		latitude FLOAT,
		longitude FLOAT
);

INSERT INTO PARKING (id, direction, bikesCapacity, latitude, longitude) VALUES
(1, 'Calle A, 123', 30, 39.4700, -0.3760),
(2, 'Avenida B, 45', 25, 39.4710, -0.3770),
(3, 'Calle C, 678', 40, 39.4725, -0.3785),
(4, 'Paseo D, 89', 35, 39.4695, -0.3745),
(5, 'Calle E, 10', 20, 39.4680, -0.3790),
(6, 'Avenida F, 111', 50, 39.4735, -0.3800),
(7, 'Calle G, 56', 15, 39.4740, -0.3755),
(8, 'Paseo H, 300', 28, 39.4755, -0.3730),
(9, 'Calle I, 99', 32, 39.4705, -0.3725),
(10, 'Avenida J, 150', 45, 39.4760, -0.3710);
