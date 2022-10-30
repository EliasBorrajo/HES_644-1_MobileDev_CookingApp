package ch.hevs.cookingapp;

import android.app.Application;

import ch.hevs.cookingapp.database.AppDatabase;
import ch.hevs.cookingapp.database.repository.CookRepository;
import ch.hevs.cookingapp.database.repository.RecipeRepository;

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

    public RecipeRepository getClientRepository() {
        return RecipeRepository.getInstance();
    }
}
