package ch.hevs.cookingapp.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.CookEntity;

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

    public LiveData<CookEntity> getCook(final Long accountId, Application application) {
        return ((BaseApp) application).getDatabase().cookDao().getById(accountId);
    }

    public LiveData<List<CookEntity>> getCooks(Application application) {
        return ((BaseApp) application).getDatabase().cookDao().getAll();
    }
}
