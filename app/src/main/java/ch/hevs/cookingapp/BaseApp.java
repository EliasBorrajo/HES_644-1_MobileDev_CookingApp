package ch.hevs.cookingapp;

import android.app.Application;

import ch.hevs.cookingapp.database.repository.CookRepository;
import ch.hevs.cookingapp.database.repository.RecipeRepository;


/**
 * Class that will have the methods to get the information in the ROOM DB.
 *
 * We will call it when we getDatabase with the DAO to the UI part.
 *
 * WARNING : Add " android:name".BaseApp" " in the MANIFEST
 * It defines which class of type Application (or in this case, a class that extends Application) is going to run everything
 */
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /* TODO : SUPPRIMER. Remplcer par un truc firebase ?
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }*/

    public CookRepository getCookRepository() {
        return CookRepository.getInstance();
    }

    public RecipeRepository getRecipeRepository() {
        return RecipeRepository.getInstance();
    }

}
