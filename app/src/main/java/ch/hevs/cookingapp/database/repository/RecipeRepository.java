package ch.hevs.cookingapp.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.database.firebase.RecipeLiveData;
import ch.hevs.cookingapp.database.firebase.RecipesListCreatorLiveData;
import ch.hevs.cookingapp.database.firebase.RecipesListMealLiveData;
import ch.hevs.cookingapp.database.pojo.CookWithRecipes;
import ch.hevs.cookingapp.util.B64Converter;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

/**
 * Repository class that works with local data source.
 * This class is used to access the database.
 * It is used by the ViewModel classes.
 */
public class RecipeRepository
{
    private static final String TAG = "RecipeRepository";

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

    public LiveData<RecipeEntity> getRecipe(final String id)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("recipes")
                .child(id);
        return new RecipeLiveData(reference);
    }

/* TODO    public LiveData<List<RecipeEntity>> getRecipes(Application application)
    {
        return ((BaseApp) application).getDatabase().recipeDao().getAll();
    }*/

    public LiveData<List<RecipeEntity>> getByCreator(final String creator)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("recipes");
        return new RecipesListCreatorLiveData(reference, creator);
    }

    public LiveData<List<RecipeEntity>> getByMeal(final String mealTime)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("recipes");
        return new RecipesListMealLiveData(reference, mealTime);
    }

    public void insert(final RecipeEntity recipe, final OnAsyncEventListener callback)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("recipes");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("recipes")
                .child(key)
                .setValue(recipe, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final RecipeEntity recipe, OnAsyncEventListener callback)
    {
        FirebaseDatabase.getInstance()
                .getReference("recipes")
                .child(recipe.getId())
                .updateChildren(recipe.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final RecipeEntity recipe, OnAsyncEventListener callback)
    {
        FirebaseDatabase.getInstance()
                .getReference("recipes")
                .child(recipe.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
