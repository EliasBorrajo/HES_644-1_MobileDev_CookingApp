# 🍳 CookingApp

> *A recipe‑sharing app developed as part of the Mobile Development course at **************************HES‑SO Valais‑Wallis************************** (Autumn 2022‑23). The goal was to explore local persistence with **************************Room************************** and cloud synchronisation with **************************Firebase************************** in a real‑world Android project context.*

---

## 📚 Project Description

CookingApp lets users register, publish their own recipes with photos, browse other cooks’ creations and edit their own profile.

A **single‑activity MVVM** architecture coupled with Jetpack **Navigation Component** and **LiveData** delivers a clean and reactive experience. The project was developed in two academic milestones:

> **Phase 1 –** Full offline CRUD with Room (`Rendu 1`, 20 Nov 2022)
> 
> **Phase 2 –** Cloud sync with Firebase (Firestore + Storage) & Google Sign‑In (`Rendu 2`, 13 Dec 2022)

---

## 🧪 Technologies Used

| Type               | Tool / Library                                       | Version  |
| ------------------ | ---------------------------------------------------- | -------- |
| **Language**       | Java (source 8, compatible JDK 11)                   | 11       |
| **Gradle Wrapper** | Gradle Wrapper                                       | 7.4      |
| **Build Plugin**   | Android Gradle Plugin                                | 7.3.0    |
| **Frameworks**     | Jetpack Navigation (Component), ViewModel, LiveData  | *-*      |
| **Local DB**       | Room                                                 | 2.4.3    |
| **Cloud**          | Firebase BoM (Firestore / Storage)                   | 31.0.0   |
|                    |                                                      |          |
| **IDE**            | Android Studio **Dolphin 2021.3.1** (bundled JDK 11) | 2021.3.1 |
| **Emulator**       | Pixel 3a • API 30 (Android 11 R)                     | API 30   |
| **SDK Levels**     | `compileSdk` 32 · `targetSdk` 32 · `minSdk` 30       | 32 / 30  |

---

## 🎯 Learning Objectives

* Use **Room** for local persistence
* Integrate **Firebase Authentication** & **Cloud Firestore**
* Apply clean **MVVM + Navigation** architecture
* Manage activity lifecycles and reactive UI components

---

## 🏗 Architecture

The app follows a **single‑activity MVVM** pattern with a clear separation of concerns:

* **UI Layer** – `MainActivity` and multiple *Fragments* orchestrated by Jetpack **Navigation Component** (*dependency pending*).
* **ViewModel Layer** – Holds UI state, exposes **LiveData / StateFlow**, handles navigation events.
* **Repository Layer** – Mediates between local (**Room DAOs**) and remote (**Firebase Services**) data sources.
* **Data Layer** –

  * **Room 2.4.3** entities & DAOs for offline persistence.
  * **Firebase Firestore** for cloud documents. *TODO: integration pending in this branch*
  * **Firebase Storage** for recipe images. *TODO*
  * **Firebase Auth** for user identity. *TODO* 

All data operations run on **Dispatchers.IO** coroutines; results are marshalled back to the main thread via **LiveData**.

###

---

## 🔧 Features

* Email/Password registration & Google Sign‑In 
* Create / Read / Update / Delete recipes with gallery images
* Filter recipes by allergies, diet and meal time
* Profile editing with password re‑authentication
* Automatic cleanup of orphan data when a user is deleted

### 🔧 Bonus Features

* Single‑activity Navigation architecture
* Cascade deletion for data integrity (delete a Cook → delete all his recipes)

---

## ✅ Tests & Validation

* Manual validation on **Pixel 3a API 30** emulator (Android 11).

---

## 👤 Authors

* **Elias Borrajo**
* **Milena Lonfat**

---

### Academic Info

_Course: 644‑1 – Mobile Development_
*Instructors: Dr Michael I. Schumacher & Yvan Pannatier – HES‑SO Valais‑Wallis*
*Context: Bachelor of Science in Business IT, 4th semester*

---

## Project Status

📁 **Archived** — educational prototype, no further maintenance planned.

---

<details>
	<summary>
		<h2>
		Original Readme (FR)
		</h2>
	</summary>

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

	
 
</details>



