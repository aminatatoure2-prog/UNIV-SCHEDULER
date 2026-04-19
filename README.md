# UNIV-SCHEDULER

Application de Gestion Intelligente des Salles et des Emplois du Temps à l'Université

## Problématique

Dans une université, la gestion des salles et la planification des emplois du temps est un casse-tête :

- Conflits de réservation de salles
- Salles surchargées ou sous-utilisées
- Difficulté à trouver rapidement une salle disponible
- Absence de visibilité en temps réel
- Gestion manuelle complexe avec Excel/Paperboard

## Objectifs

- Centraliser toutes les informations sur les salles et cours
- Automatiser la détection des conflits horaires
- Faciliter la recherche de salles disponibles
- Optimiser l'utilisation des ressources (salles, équipements)
- Visualiser en temps réel l'occupation des salles
- Générer des rapports d'utilisation

## Fonctionnalités

- Planification et gestion des cours
- Détection automatique des conflits
- Gestion des salles et équipements
- Consultation des emplois du temps par classe/enseignant

## Technologies

- Java
- MySQL
- Swing
- JDBC

## Lancement

```bash
java -jar UNIV-SCHEDULER.jar
```

## Documentation

Le projet inclut une documentation Javadoc complète pour toutes les classes.

## Structure

```
src/univ_scheduler/
├── dao/          # Accès aux données
├── model/         # Entités métiers
├── service/       # Logique métier
├── view/          # Interfaces graphiques
└── Main.java      # Point d'entrée principal
```

## Base de données

Tables complètes :

- cours (informations sur les cours)
- crenaux (créneaux horaires)
- salles (salles disponibles)
- classes (classes d'étudiants)
- enseignants (informations sur les enseignants)
- matieres (matières enseignées)
- batiments (bâtiments universitaires)
- equipements (équipements des salles)
- salle_equipement (liaison salles-équipements)
- utilisateurs (utilisateurs du système)
- notifications (notifications système)
- reservations_ponctuelles (réservations ponctuelles)
- signalement (signalements d'incidents)
