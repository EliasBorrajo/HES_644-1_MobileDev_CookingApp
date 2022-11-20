# HES_644-1_MobileDev_CookingApp

## Spécifications pour run : 
	• Android Studio : Device Manager --> Pixel 3a API 30 - Android 11 x86 R en mode portrait.
	• Il est nécessaire de prendre des photos avec l'app Photos du téléphone pour les avoir dans la gallerie avant de pouvoir les utiliser en tant qu'images de recettes ou de profil.


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
  

## Améliorations futures : 
  	• Lors de la release de l'application sur un app store, supprimer le bouton de "reset database" qui se trouve sur le login. 
  


