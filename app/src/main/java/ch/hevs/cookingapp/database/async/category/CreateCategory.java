package ch.hevs.cookingapp.database.async.category;

import android.app.Application;
import android.os.AsyncTask;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.CategoryEntity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

public class CreateCategory extends AsyncTask<CategoryEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateCategory(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(CategoryEntity... params) {
        try {
            for (CategoryEntity category : params)
                ((BaseApp) application).getDatabase().categoryDao().insert(category);
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
