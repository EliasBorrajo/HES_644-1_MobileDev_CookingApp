package ch.hevs.cookingapp.database.async.cook;

import android.app.Application;
import android.os.AsyncTask;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

/**
 * Async task to create a cook in the database.
 * it is used in the CookViewModel
 * it uses the CookDao to create the cook in the database
 * it uses the OnAsyncEventListener to return the result of the operation
 */
public class CreateCook extends AsyncTask<CookEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateCook(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(CookEntity... params) {
        try {
            for (CookEntity cook : params)
                ((BaseApp) application).getDatabase().cookDao().insert(cook);
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
