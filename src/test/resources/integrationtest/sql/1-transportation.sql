INSERT INTO economicsimulator.transport (transport_make_id, transport_model, transport_name, year_available, cost, annual_maintenance, max_load, load_type_cargo_id, fuel_economy_id, acceleration_id, reliability_id, passenger_appeal)
VALUES (0, "A1A-2", "General Pershing Zephyr", 1939, 10000, 6000, 8, 2, 0, 1, 5, 10),
       (1, "Triple E class", "Emma Mærsk", 2006, 80000000, 10000, 10000, 0, 5, 1, 10, 0),
       (2, "359 Peterbilt", "Good Buddy", 1967, 80000000, 10000, 2, 0, 3, 4, 2, 0),
       (3, "Boing 747", "Pan Am 249", 1970, 80000000, 10000, 1, 1, 2, 10, 10, 7);

INSERT INTO economicsimulator.speed_chart (transport_id, cargo_id, grade_type, container_count, speed)
VALUES (3, 0, 0, 0, 300),
       (3, 1, 0, 0, 350),
       (2, 0, 0, 0, 60),
       (2, 0, 1, 0, 59),
       (2, 0, 2, 0, 55),
       (2, 0, 3, 0, 45),
       (2, 0, 0, 1, 55),
       (2, 0, 1, 1, 54),
       (2, 0, 2, 1, 50),
       (2, 0, 3, 1, 40),
       (2, 0, 0, 2, 55),
       (2, 0, 1, 2, 51),
       (2, 0, 2, 2, 45),
       (2, 0, 3, 2, 35),
       (1, 0, 0, -1, 13),
       (0, 2, 0, 0, 25),
       (0, 0, 0, 1, 22),
       (0, 0, 0, 2, 18),
       (0, 0, 0, 3, 14),
       (0, 0, 0, 4, 11),
       (0, 0, 0, 5, 9),
       (0, 0, 0, 6, 7),
       (0, 0, 0, 7, 5),
       (0, 0, 0, 8, 4),
       (0, 1, 0, 1, 24),
       (0, 1, 0, 2, 21),
       (0, 1, 0, 3, 18),
       (0, 1, 0, 4, 15),
       (0, 1, 0, 5, 13),
       (0, 1, 0, 6, 11),
       (0, 1, 0, 7, 10),
       (0, 1, 0, 8, 8),
       (0, 2, 0, 1, 23),
       (0, 2, 0, 2, 19),
       (0, 2, 0, 3, 15),
       (0, 2, 0, 4, 13),
       (0, 2, 0, 5, 10),
       (0, 2, 0, 6, 9),
       (0, 2, 0, 7, 7),
       (0, 2, 0, 8, 6),
       (0, 2, 1, 0, 18),
       (0, 0, 1, 1, 15),
       (0, 0, 1, 2, 10),
       (0, 0, 1, 3, 7),
       (0, 0, 1, 4, 4),
       (0, 0, 1, 5, 3),
       (0, 0, 1, 6, 2),
       (0, 0, 1, 7, 1),
       (0, 0, 1, 8, 1),
       (0, 1, 1, 1, 17),
       (0, 1, 1, 2, 13),
       (0, 1, 1, 3, 10),
       (0, 1, 1, 4, 8),
       (0, 1, 1, 5, 6),
       (0, 1, 1, 6, 5),
       (0, 1, 1, 7, 4),
       (0, 1, 1, 8, 3),
       (0, 2, 1, 1, 16),
       (0, 2, 1, 2, 11),
       (0, 2, 1, 3, 8),
       (0, 2, 1, 4, 6),
       (0, 2, 1, 5, 4),
       (0, 2, 1, 6, 3),
       (0, 2, 1, 7, 2),
       (0, 2, 1, 8, 1),
       (0, 2, 2, 0, 15),
       (0, 0, 2, 1, 9),
       (0, 0, 2, 2, 5),
       (0, 0, 2, 3, 3),
       (0, 0, 2, 4, 1),
       (0, 0, 2, 5, 1),
       (0, 0, 2, 6, 1),
       (0, 0, 2, 7, 1),
       (0, 0, 2, 8, 1),
       (0, 1, 2, 1, 12),
       (0, 1, 2, 2, 8),
       (0, 1, 2, 3, 5),
       (0, 1, 2, 4, 3),
       (0, 1, 2, 5, 2),
       (0, 1, 2, 6, 2),
       (0, 1, 2, 7, 1),
       (0, 1, 2, 8, 1),
       (0, 2, 2, 1, 10),
       (0, 2, 2, 2, 6),
       (0, 2, 2, 3, 4),
       (0, 2, 2, 4, 2),
       (0, 2, 2, 5, 1),
       (0, 2, 2, 6, 1),
       (0, 2, 2, 7, 1),
       (0, 2, 2, 8, 1),
       (0, 2, 3, 0, 8),
       (0, 0, 3, 1, 5),
       (0, 0, 3, 2, 2),
       (0, 0, 3, 3, 1),
       (0, 0, 3, 4, 1),
       (0, 0, 3, 5, 1),
       (0, 0, 3, 6, 1),
       (0, 0, 3, 7, 1),
       (0, 0, 3, 8, 1),
       (0, 1, 3, 1, 7),
       (0, 1, 3, 2, 4),
       (0, 1, 3, 3, 2),
       (0, 1, 3, 4, 1),
       (0, 1, 3, 5, 1),
       (0, 1, 3, 6, 1),
       (0, 1, 3, 7, 1),
       (0, 1, 3, 8, 1),
       (0, 2, 3, 1, 6),
       (0, 2, 3, 2, 3),
       (0, 2, 3, 3, 1),
       (0, 2, 3, 4, 1),
       (0, 2, 3, 5, 1),
       (0, 2, 3, 6, 1),
       (0, 2, 3, 7, 1),
       (0, 2, 3, 8, 1);