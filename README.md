# restapi

## Contexte du Projet

Ce projet est une API RESTful développée avec Spring Boot destinée à gérer une plateforme de "location de voitures". Le système est conçu pour fonctionner avec plusieurs agences et propose une gestion différenciée selon les rôles utilisateurs (Admin et Client).

L'application assure la gestion du parc automobile (voitures, catégories), la gestion géographique (agences), ainsi que le processus complet de réservation (vérification de disponibilité, calcul automatique des coûts en fonction de la durée). L'API respecte les principes HATEOAS pour la navigabilité et est sécurisée via des tokens JWT (JSON Web Tokens).

### Fonctionnalités Principales

*   Gestion des Utilisateurs & Sécurité : Inscription, authentification sécurisée (JWT), et gestion des rôles (Admin pour la gestion, Client pour les réservations).
*   Gestion de Flotte : CRUD complet sur les voitures et les catégories de véhicules (Luxe, 4x4, Léger, etc.).
*   Gestion Multi-Agences : Localisation des véhicules par agence.
*   Système de Réservation : Création de locations avec vérification automatique de la disponibilité des véhicules pour les dates choisies et calcul du prix total.
*   Statistiques : Endpoints dédiés au suivi de l'activité (taux de disponibilité, revenus par agence).

---

## Documentation de l'API
Lien vers swagger: http://localhost:8000/swagger-ui.html

### 1. Authentification (http://localhost:8080)
POST /api/auth/signin : Se connecter (récupérer le token JWT).

Voici les logins par défaut :

{
    "username" : "admin",
    "password": "password123"
}

{
    "username" : "client",
    "password": "password123"
}

POST /api/auth/signup : S'inscrire (crée un utilisateur avec le rôle CLIENT par défaut).

### 2. Agences (http://localhost:8080)
GET /api/agences : Récupérer la liste de toutes les agences.

GET /api/agences/{id} : Récupérer une agence par son ID.

POST /api/agences : Créer une nouvelle agence.

PUT /api/agences/{id} : Mettre à jour une agence existante.

DELETE /api/agences/{id} : Supprimer une agence.

### 3. Catégories (http://localhost:8080)
GET /api/categories : Liste des catégories.

GET /api/categories/{id} : Détails d'une catégorie.

POST /api/categories : Créer une catégorie (Admin).

PUT /api/categories/{id} : Modifier une catégorie (Admin).

DELETE /api/categories/{id} : Supprimer une catégorie (Admin).

GET /api/categories/nom/{nom} : Rechercher une catégorie par son nom.

### 4. Locations (http://localhost:8080)
GET /api/locations : Liste de toutes les locations (Admin).

GET /api/locations/{id} : Détails d'une location.

POST /api/locations : Créer une location (vérifie la disponibilité et calcule le prix).

PUT /api/locations/{id} : Modifier une location (Admin).

DELETE /api/locations/{id} : Supprimer une location.

GET /api/locations/user : Récupérer l'historique des locations de l'utilisateur connecté.

GET /api/locations/voiture/{voitureId} : Locations associées à une voiture spécifique (Admin).

GET /api/locations/disponible/{voitureId} : Vérifier si une voiture est disponible sur une plage de dates.

### 5. Voitures (http://localhost:8080)
GET /api/voitures : Liste de toutes les voitures.

GET /api/voitures/{id} : Détails d'une voiture.

POST /api/voitures : Ajouter une voiture (Admin).

PUT /api/voitures/{id} : Modifier une voiture (Admin).

DELETE /api/voitures/{id} : Supprimer une voiture (Admin).

GET /api/voitures/disponibles : Liste uniquement les voitures disponibles.

GET /api/voitures/agence/{agenceId} : Liste les voitures d'une agence spécifique.

### 6. Utilisateurs (http://localhost:8080)
GET /api/users : Liste des utilisateurs (Admin).

GET /api/users/{id} : Détails d'un utilisateur.

POST /api/users : Créer un utilisateur manuellement (Admin).

PUT /api/users/{id} : Modifier un utilisateur.

DELETE /api/users/{id} : Supprimer un utilisateur (Admin).

### 7. Statistiques (http://localhost:8080)
GET /api/stats/global : Statistiques globales (nombre de voitures, taux de disponibilité).

GET /api/stats/agence/{agenceId} : Statistiques par agence (nombre de voitures, revenus, prix moyen).