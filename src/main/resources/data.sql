-- Données de test pour l'application

-- Agences
INSERT INTO agence (id, nom, ville) VALUES 
(1, 'Location Plus Antananarivo', 'Antananarivo'),
(2, 'Auto Service Toamasina', 'Toamasina');

-- Catégories
INSERT INTO categorie (id, nom) VALUES 
(1, 'Luxe'),
(2, 'Leger'),
(3, '4x4');

-- Utilisateurs
INSERT INTO users (id, username, email, password, role) VALUES 
(1, 'admin', 'admin@location.com', 'password123', 'ADMIN'),
(2, 'client', 'client@email.com', 'password123', 'CLIENT');

-- Voitures
INSERT INTO voiture (id, marque, modele, prix_jour, disponible, agence_id) VALUES 
(1, 'Toyota', 'RAV4', 75000.00, true, 1),
(2, 'Honda', 'CR-V', 80000.00, true, 1),
(3, 'Renault', 'Clio', 45000.00, false, 2);

-- Relations voiture-categorie
INSERT INTO voiture_categorie (voiture_id, categorie_id) VALUES 
(1, 3),  
(2, 1),  
(3, 2);

-- Locations
INSERT INTO location (id, date_debut, date_fin, total, user_id, voiture_id) VALUES 
(1, '2026-01-15', '2026-01-20', 375000.00, 2, 1),
(2, '2026-02-10', '2026-02-12', 90000.00, 2, 3);


ALTER TABLE agence ALTER COLUMN id RESTART WITH 3;
ALTER TABLE categorie ALTER COLUMN id RESTART WITH 4;
ALTER TABLE users ALTER COLUMN id RESTART WITH 3;
ALTER TABLE voiture ALTER COLUMN id RESTART WITH 4;
ALTER TABLE location ALTER COLUMN id RESTART WITH 3;
