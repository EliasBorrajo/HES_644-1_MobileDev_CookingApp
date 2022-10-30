package ch.hevs.cookingapp.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.RecipeEntity;

public class RecipeRepository
{
    private static RecipeRepository instance;

    private RecipeRepository() {
    }

    public static RecipeRepository getInstance() {
        if (instance == null) {
            synchronized (CookRepository.class) {
                if (instance == null) {
                    instance = new RecipeRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<RecipeEntity> getRecipe(final Long accountId, Application application) {
        return ((BaseApp) application).getDatabase().recipeDao().getById(accountId);
    }

    public LiveData<List<RecipeEntity>> getRecipes(Application application) {
        return ((BaseApp) application).getDatabase().recipeDao().getAll();
    }
}
