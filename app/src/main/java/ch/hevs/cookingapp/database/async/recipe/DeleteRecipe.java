package ch.hevs.cookingapp.database.async.recipe;

import android.app.Application;
import android.os.AsyncTask;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

/**
 * it is used to delete a recipe in the database
 * It is called in the viewmodel class RecipeViewModel with the method deleteRecipe
 * it uses the method delete from the RecipeDao interface to delete the recipe in the database
 */
public class DeleteRecipe extends AsyncTask<RecipeEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteRecipe(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(RecipeEntity... params) {
        try {
            for (RecipeEntity recipe : params)
                ((BaseApp) application).getDatabase().recipeDao().delete(recipe);
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
