# HES_644-1_MobileDev_CookingApp
Authors : Borrajo Elias & Lonfat Milena
Rendu 1 : ROOM Database - 20.11.2022
Rendu 2 : Cloud Firestore   - 13.12.2022


Ce projet a été réalisé dans le cadre de notre troisième année de formation en informatique de gestion au sein de la HES-SO Valais-Wallis. Durant le cours "Développement Mobile". Il nous a été demandé de réaliser une application sur un thème choisi.

Nous avons choisis de développer une application qui partage des recettes créées par les utilisateurs.
un utilisateur crée un compte avec lequels il va pouvoir partager ses recettes. Il peut voir les comptes des autres utilisateurs ainsi que leur recette. Il ne peut modifier que ces propres recettes et son profil.

## Spécifications pour run : 
	• Android Studio : Device Manager --> Pixel 3a API 30 - Android 11 x86 R en mode portrait.
	• Il est nécessaire de prendre des photos avec l'app Photos du téléphone pour les avoir dans la gallerie avant de pouvoir les utiliser en tant qu'images de recettes ou de profil.

# Rendu ROOM 
## Fonctionalitées en plus du cahier des charges initial : 
	• Utiliser les images depuis la galerie du télphone pour photos : des recettes / profil.
	• Pas de Stack des vues inutiles (ACTIVITY FLAG CLEAR TOP) --> lifecycle.
	• Même interface pour SHOW pour difféerents Cooks, et si c'est mon propre profil je peux EDIT les informations
	• Même interface pour SHOW pour difféerents Recipes, et si c'est une de mes recettes je peux EDIT les informations
	• Login & Register au départ.
  	• Si on supprime un profil Cook, toute ses recettes liés sont supprimés de la DB afin de supprimer les data liées au profil.
	• Input Type dans les Edit text. Password / Phone / Numbers. --> Meilleure UX
	• Afficher la liste des recettes utilise la même activité, mais on filtre les recettes selon : Allergies / Diet / MealTime 
	• Quand on update le profil, vérification de l'identité du user en demandant de taper son mot de passe.
    	• UI attractif et beau, avec une belle image sur l'écran d'accueil qui met tout de suite en appétit.

# Rendu CLOUD Firebase
## Fonctionalitées en plus du cahier des charges initial : 
	• Firebase Database : 
		- Stocker des images dans une String en Base 64
		- Modification des règles, tout est public
		
	• Firebase Authentication : 
		- Stocke les mail & password pour des raisons de sécurité.
		- Supression d'un cook supprime aussi son compte sous Authentication, permet de re-créer un profil neuf.
		- Configuration de conection via Google activé
			- Empreinte de certificat SHA 1 & 256 configurés pour le projet firebase.

	
