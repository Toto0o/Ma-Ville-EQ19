# Feedback

## Introduction

Bonne introduction.

## Échéancier

Il serait bon d'ajouter les échéances à venir pour avoir une vision de ce qui reste à faire dans le projet. Bon échéancier dans l'ensemble.

## Glossaire 

Glossaire assez complet, par contre il manque 2 termes importants : "Application MaVille" et "Info Entraves et Travaux"

## Cas d'utilisation

Il manque la "Ville" et "utilisateur général" comme acteurs. 

Il manque "Envoyer une notification" pour l'intervenant et "S'abonner à un projet" pour le résident.

## Scénarios

### Préconditions

Dans certaines préconditions, le résident ou l'intervenant est inscrit dans le système, mais ils ne sont peut-être pas authentifiés. Ex: CU: Consulter des travaux en cours ou à venir

### CU: Consulter des travaux en cours ou à venir

Il n'y a pas de scénario alternatif pour l'extension "Rechercher des travaux"

2- Qu'est-ce que le système affiche s'il n'y a pas de travaux en cours ou à venir?

6- Pas besoin de reprendre à l'étape 2

### CU: Personnaliser son profil : modifier ses informations

4- Que se passe-t-il si les informations ne sont pas valides?

### CU: Laisser un avis

Incohérence dans une des préconditions est que le résident est abonné à un projet, mais celui-ci ne peut pas s'abonner à un projet.

### CU: Soumettre une requête de travail

L'intervenant n'a pas besoin d'être un acteur.

7 et 8- L'objectif de ce scénario est de soumettre une requête de travail, on ne cherche pas à accepter ou refuser la requête. 

### CU: Signaler un problème

L'intervenant n'a pas besoin d'être un acteur.

4- Le suivi du problème n'est pas fait par un intervenant.

### CU: Consulter la liste des requêtes

2- Qu'est-ce que le système affiche s'il n'y a pas de requêtes?

### CU: Soumettre sa candidature à un projet

6- Le système devrait valider les dates.

### CU: Soumettre un projet de travaux

5a.3. Le scénario reprend à l'étape 3.

### CU: Mettre à jour les informations d'un chantier

4- L'intervenant doit modifier des informations avant d'enregistrer.

## Diagrammes d'activités

Il manque 2 diagramme d'activités

### Diagramme d'activité principal : Connexion et Interactions du Menu Principal 

Utilisez un noeud de décision au lieu noeud de bifurcation

On peut boucler à l'infini lors de l'authentification.

### Diagramme d'activité pour Soumettre une requête de travail

Il n'y a pas de vérification pour savoir si le résident à déjà soumis une requête qui est encore en traitement.

## Analyse

### Risques

Manque de description et de justification pour les risques.

### Besoins non fonctionnels

Il n'y a pas de justifications.

### Besoins matériels

Sur quoi va-t-on développer l'application et combien tout le matériel va coûter?

### Solution de stockage

Quel est l'architecture de stockage des données?

### Solution d'intégration

Manque d'explication.

## Prototype

Fichier JAR fonctionnel.

On ne peut pas se connecter en tant qu'intervenant

## Git 

README et Release présents.

1/3 membres ont fait au moins 1 commit.

## Rapport 

Présence mineure de fautes