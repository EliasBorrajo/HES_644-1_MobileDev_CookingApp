package ch.hevs.cookingapp.database.async.recipe;

import android.app.Application;
import android.os.AsyncTask;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

/**
 * it is used to update a recipe in the database
 * It is called in the viewmodel class RecipeViewModel with the method updateRecipe
 * it uses the method update from the RecipeDao interface to update the recipe in the database
 */
public class UpdateRecipe extends AsyncTask<RecipeEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateRecipe(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(RecipeEntity... params) {
        try {
            for (RecipeEntity recipe : params)
                ((BaseApp) application).getDatabase().recipeDao().update(recipe);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}