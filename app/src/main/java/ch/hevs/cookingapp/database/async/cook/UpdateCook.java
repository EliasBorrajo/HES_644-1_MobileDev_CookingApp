package ch.hevs.cookingapp.database.async.cook;

import android.app.Application;
import android.os.AsyncTask;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

public class UpdateCook extends AsyncTask<CookEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateCook(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(CookEntity... params) {
        try {
            for (CookEntity cook : params)
                ((BaseApp) application).getDatabase().cookDao().update(cook);
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
