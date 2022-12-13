package ch.hevs.cookingapp.database.repository;


import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.database.firebase.RecipeLiveData;
import ch.hevs.cookingapp.database.firebase.RecipesListCreatorLiveData;
import ch.hevs.cookingapp.database.firebase.RecipesListMealLiveData;
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

    /**
     * Get a recipe from the database.
     * @param id : the id of the recipe to get. It is the key of the recipe in the database. It is a String. It is not null. It is unique
     * @return : the recipe with the id given in parameter if it exists in the database. Otherwise, it returns null.
     */
    public LiveData<RecipeEntity> getRecipe(final String id)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("recipes")
                .child(id);
        return new RecipeLiveData(reference);
    }

    /**
     * Get all the recipes from the connected user.
     * @param creator : the id of the connected user. It is a String. It is not null. It is unique
     * @return : a list of all the recipes from the connected user if it exists in the database. Otherwise, it returns null.
     */
    public LiveData<List<RecipeEntity>> getByCreator(final String creator)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("recipes");
        return new RecipesListCreatorLiveData(reference, creator);
    }

    /**
     * Get all the recipes from a specific meal time. It can be breakfast, lunch, dinner.
     * @param mealTime : the meal time of the recipes to get.
     * @return : a list of all the recipes from the meal time given in parameter if it exists in the database. Otherwise, it returns null.
     */
    public LiveData<List<RecipeEntity>> getByMeal(final String mealTime)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("recipes");
        return new RecipesListMealLiveData(reference, mealTime);
    }

    /**
     * Insert a recipe in the database. It is used when the user creates a recipe.
     * @param recipe : the recipe to insert in the database. It is not null.
     * @param callback : the callback to execute when the recipe is inserted in the database.
     */
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

    /**
     * Update a recipe in the database. It is used when the user modifies a recipe.
     * @param recipe : the recipe to update in the database. It is not null.
     * @param callback : the callback to execute when the recipe is updated in the database.
     */
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

    /**
     * Delete a recipe in the database. It is used when the user deletes a recipe.
     * @param recipe : the recipe to delete in the database. It is not null.
     * @param callback : the callback to execute when the recipe is deleted in the database.
     */
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
