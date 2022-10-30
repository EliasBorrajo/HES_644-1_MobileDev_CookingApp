package ch.hevs.cookingapp.database.async.cook;

import android.app.Application;
import android.os.AsyncTask;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

public class UpdateCook extends AsyncTask<CookEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener calback;
    private Exception exception;

    public UpdateCook(Application application, OnAsyncEventListener callback) {
        this.application = application;
        calback = callback;
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
        if (calback != null) {
            if (exception == null) {
                calback.onSuccess();
            } else {
                calback.onFailure(exception);
            }
        }
    }
}
