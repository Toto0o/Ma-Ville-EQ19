# Feedback

## Révision

### Échéancier

Échéancier n'est pas à jour

### diagrammes CU 

Il manque un nouvel acteur "API de la ville".

Il manque un nouveau CU "Consulter ou Chercher les entraves routières".

### Diagrammes d'activités 

Diagrammes d'activités mis à jour 

### Analyse

Analyse mise à jour

## Architecture

"Ville de montréal" n'est pas un utilisateur.

Comment les utilisateurs accèdent-ils/interagissent-ils avec l'application ? En ligne de commande?

La frontière du système n'est pas claire et ne permet pas de distinguer les composantes du système des dépendances externes.

Les APIs de la ville ne sont pas présentes.

## Diagramme de classe

Le sens des compositions est inversé.

Il manques des classes. Ex: Notification et Requête

Il y a des classes vides.

La classe "SceneController" a un peu trop de responsabilités.

## Diagramme de séquence 

Les lignes de vie ne sont pas présentes.

Il y a des méthodes qui n'existent pas dans le diagramme de classe. Ex: rechercherEntraves()

Certains fragments "alt" manquent leur titre.

Les flèches de message n'ont pas de numéro.

### Consulter les entraves

Il manque des validations et des traitements d'erreurs lors de la soumission d'une requête.

### Soumettre une requête de travail

Le résident ne peut pas faire le suivi d'une requête de travail.

### Consulter la liste des requêtes de travail

L'intervenant ne peut pas soumettre sa candidature.

## Discussion design

Discussion design absente.

## Implémentation

On ne peut pas "Soumettre une requête de travail" et "Consulter la liste des requêtes de travail"

Le code ressemble moyennement à la conception.

## Tests unitiaire

Les tests ne sont pas exécutables de mon côté.

Certains tests sont mal nommés et mal structuré.

## Rapport et Git

Le diagramme de séquence ne s'affiche pas.