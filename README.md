# Ma-Ville-EQ19 -- Prototype

Cette branche contient les fichiers java du prototype. Celui-ci est crée avec JavaFX. Un fichier .jar sera remis sur Studium afin de l'executer avec les modules correspondants. 

L'application Ma Ville permet aux utilisateurs de consulter les projets ou travaux en cours :
  - À partir des données de la ville de Montréal pour les projets soumis hors du système
  - À partir des données du système pour les projets soumis localement
Elle permet aussi de consulter les entraves routières. Les données proviennent seulment des données de la ville de Montréal

De plus, les utilisateurs résident peuvent soumettre des requêtes de travaux. Un intervenant peu ensuite aposer sa candidature et un nouveau projet sera créé.

Finalement, il est possible pour les utilisateurs de modifier leur profil ainsi que d'ajouter une préférence de plage horaire

Le package est divisé comme suis :
  - Main : lance l'application
  - MaVille : démarre l'interface graphique
  - controllers : contient le controlleur de scene et le controlleur d'API
  - entraves : contient la classe d'entrave
  - notifications : contient la classe de notification
  - projects : contient les classes de notifications et de projets
  - Scenes : contient les différentes scenes (pages) du menu
      - general/consult : contient les scenes de consultation des projets, entraves et notifications ainsi que la scene de recherche de projet
      - general/login : contient la scene d'authentification
      - general/menu : contient la scene de menu et la scene au démarrage (launch)
      - general/register : contient la scene de selection du rôle et la scene d'enregistrement
      - general/settings : contient les scenes relatives aux réglages de compte
      - intervenants : les scenes de consultation des requêtes et des projets associés
      - résidents : la scene pour créer une nouvelle requête
  - services : contient toutes les classes permettant la connexion avec les bases de données
  - users : contient les classes relatives aux utilisateurs

Les fonctionnalités implémentés pour les résidents sont :
  - Enregistrement
  - Authentification
  - Consultation des projets en cours
  - Consultation des entraves routières
  - Consultation des notifications
  - Soumission d'une nouvelle requête
  - Modification du profil :
      - Modification des préférences de plage horaire
      - Modification des informations

Les fonctionnalités implémentés pour les intervenants sont :
  - Enregistrement
  - Authentification
  - Consultation des projets en cours (tous les projets)
  - Consultation des entraves routières
  - Consultation des notifications
  - Consultation des requêtes de travail soumises par les résidents, ainsi que la possibilité d'apposer sa candidature
  - Consultation des projets associés à l'intervenant, ainsi que la possibilité de modifier les informations
  - Modification du profil :
      - Modification des préférences de plage horaire
      - Modification des informations
        
  ** Note : le fichier jar semble avoir des problème dû à une configuration maven pour la création de l'executable, mais le code source peut être "run" dans un IDE classique (ça fontionne très bien même)!**

