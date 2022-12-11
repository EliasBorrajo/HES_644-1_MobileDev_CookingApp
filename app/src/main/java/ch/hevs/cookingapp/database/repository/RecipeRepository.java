package ch.hevs.cookingapp.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

/**
 * Repository class that works with local data source.
 * This class is used to access the database.
 * It is used by the ViewModel classes.
 */
public class RecipeRepository
{
    private static RecipeRepository instance;

    private RecipeRepository()
    {
    }

    public static RecipeRepository getInstance()
    {
        if (instance == null)
        {
            synchronized (RecipeRepository.class)
            {
                if (instance == null)
                {
                    instance = new RecipeRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<RecipeEntity> getRecipe(final Long id, Application application)
    {
        return ((BaseApp) application).getDatabase().recipeDao().getById(id);
    }

    public LiveData<List<RecipeEntity>> getRecipes(Application application)
    {
        return ((BaseApp) application).getDatabase().recipeDao().getAll();
    }

    public LiveData<List<RecipeEntity>> getByCreator(final String creator, Application application)
    {
        return ((BaseApp) application).getDatabase().recipeDao().getOwned(creator);
    }

    public LiveData<List<RecipeEntity>> getByMeal(final String mealTime, Application application)
    {
        return ((BaseApp) application).getDatabase().recipeDao().getByMeal(mealTime);
    }

    public void insert(final RecipeEntity recipe, OnAsyncEventListener callback,
                       Application application)
    {
        new CreateRecipe(application, callback).execute(recipe);
    }

    public void update(final RecipeEntity recipe, OnAsyncEventListener callback,
                       Application application)
    {
        new UpdateRecipe(application, callback).execute(recipe);
    }

    public void delete(final RecipeEntity recipe, OnAsyncEventListener callback,
                       Application application)
    {
        new DeleteRecipe(application, callback).execute(recipe);
    }

 */
}
