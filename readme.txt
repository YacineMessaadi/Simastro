- Messaadi Yacine, Dautricourt Hugo, Tryoen Yohan, Lantoine Gautier | Groupe I

- Messaadi Yacine : (Back-End) Responsable des calculs de la simulation et de la gestion des astres au cours du temps. 
	Création des Threads et des contrôleurs ainsi que des fonctions de mise à jour de l'interface.
- Dautricourt Hugo : (Front-End) Responsable de l'affichage de la simulation au cours du temps et des éléments d'interaction utilisateur. 
	Création de l'interface et des fonctions d'actualisation de l'affichage et des entrées utilisateur.
- Tryoen Yohan : (Front-End) Responsable des éléments d'information de l'utilisateur et du design des widgets.
	Création des modules d'information sur le système et implémentation des illustrations de l'interface et de la simulation.
- Lantoine Gautier : (Back-End) Responsable des classes Système et des astres ainsi que des événements lors de la simulation.
	Création des classes des éléments de l'univers et des fonctions de gestion de leurs événements (comme les collisions et le carburant).
	
- Les Ellipses et Cercles sont totalement implémentés.
- RK4, EulerExplicite et Leapfrog sont implémentés
- Par défaut, RK4 est utilisé. Il est possible de choisir le mode de calcul grâce aux boutons à n'importe quel moment.
- Pour ouvrir une vue spécifique à un astre, il faut cliquer sur Ajouter puis sélectionner la vue puis choisir l'astre dans la liste déroulante
- Deux Objectifs sont implémentés : L'objectif Voyager du sujet et un objectif supplémentaire "Détruire" qui consiste à détruire la cible précisée dans le fichier astro grâce aux missiles que le vaisseau peut lancer.
- Le vaisseau peut lancer des projectiles en appuyant sur Espace. Il a un stock de 15 missiles.
- Le vaisseau perd de l'autonomie lorsqu'il est propulsé, il faut alors appuyer longuement sur la touche "R" pour recharger les batteries.
- Le delta-temps (dT) est modifiable, il faut cliquer sur "+" ou "-" pour le modifier avec un pas de 0,01.
- Les collisions sont prises en compte pour les tous les astres et vaisseaux.
- Les collisions produisent des éclats propulsés dans l'espace pour les collisions entre astres de masse supérieure à 2 et qui ne sont pas des missiles.
- Un précalcul de la position du vaisseau est effectué en temps réel selon les conditions actuelles de mouvement du vaisseau
- La position précalculée du vaisseau est affichée sur la simulation et la trajectoire jusqu'à cette position est tracée.
