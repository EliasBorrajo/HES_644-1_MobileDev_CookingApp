package ch.hevs.cookingapp.database.async.Category;


import android.app.Application;
import android.os.AsyncTask;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.CategoryEntity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

public class UpdateCategory extends AsyncTask<CategoryEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener calback;
    private Exception exception;

    public UpdateCategory(Application application, OnAsyncEventListener callback) {
        this.application = application;
        calback = callback;
    }

    @Override
    protected Void doInBackground(CategoryEntity... params) {
        try {
            for (CategoryEntity category : params)
                ((BaseApp) application).getDatabase().categoryDao().update(category);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (calback != null) {
            if (exception == null) {
                calback.onSuccess();
            } else {
                calback.onFailure(exception);
            }
        }
    }
}
