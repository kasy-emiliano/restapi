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

Swagger UI : http://localhost:8080/swagger-ui.html

Logins par défaut :

```json
{ "username": "admin",  "password": "password123" }
{ "username": "client", "password": "password123" }
```

---

### Accès public (aucune authentification requise)

#### Authentification
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/auth/signin` | Se connecter — retourne un token JWT |
| POST | `/api/auth/signup` | S'inscrire (rôle CLIENT par défaut) |


---

### Accès authentifié — CLIENT ou ADMIN (token JWT requis)

#### Agences
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/agences` | Liste de toutes les agences |
| GET | `/api/agences/{id}` | Détails d'une agence |
| GET | `/api/agences/{id}/voitures` | Voitures rattachées à une agence |
| POST | `/api/agences` | Créer une agence |
| PUT | `/api/agences/{id}` | Modifier une agence |
| DELETE | `/api/agences/{id}` | Supprimer une agence |

#### Voitures
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/voitures` | Ajouter une voiture |
| PUT | `/api/voitures/{id}` | Modifier une voiture |
| DELETE | `/api/voitures/{id}` | Supprimer une voiture |

#### Locations
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/locations` | Créer une location (vérifie la disponibilité et calcule le prix) |
| GET | `/api/locations/user` | Historique des locations de l'utilisateur connecté |
| GET | `/api/locations/disponible/{voitureId}` | Vérifier la disponibilité d'une voiture sur une plage de dates (`?dateDebut=&dateFin=`) |

---


### Accès réservé — ADMIN uniquement

#### Statistiques
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/stats/global` | Statistiques globales (total voitures, taux de disponibilité) |
| GET | `/api/stats/agence/{agenceId}` | Statistiques par agence (revenus, prix moyen) |


#### Utilisateurs
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/users` | Liste de tous les utilisateurs |
| POST | `/api/users` | Créer un utilisateur manuellement |
| DELETE | `/api/users/{id}` | Supprimer un utilisateur |