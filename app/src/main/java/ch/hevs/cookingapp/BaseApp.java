package ch.hevs.cookingapp;

import android.app.Application;

import ch.hevs.cookingapp.database.AppDatabase;
import ch.hevs.cookingapp.database.repository.CookRepository;
import ch.hevs.cookingapp.database.repository.RecipeRepository;

// Classe qui AURA LES METHODES POUR ALLER CHERCHER LES INFORMATIONS DANS LA ROOM & DB.
// On l'epellera quand on vet getDatabase avec la DAO vers la partie UI
// ATTENTION : Ajouter "  android:name".BaseApp"  " dans le MANIFEST
// ça définit quelle classe de type Application (ou dans ce cas, une classe qui étends Application) va faire tout tourner
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public CookRepository getCookRepository() {
        return CookRepository.getInstance();
    }

    public RecipeRepository getRecipeRepository() {
        return RecipeRepository.getInstance();
    }


}
