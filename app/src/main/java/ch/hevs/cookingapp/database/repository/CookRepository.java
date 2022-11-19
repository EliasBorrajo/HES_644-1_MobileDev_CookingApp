package ch.hevs.cookingapp.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.async.cook.CreateCook;
import ch.hevs.cookingapp.database.async.cook.DeleteCook;
import ch.hevs.cookingapp.database.async.cook.UpdateCook;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

/**
 * Repository class that works with local data source.
 * This class is used to access the database.
 * It is used by the ViewModel classes.
 */
public class CookRepository
{
    private static CookRepository instance;

    private CookRepository() {
    }

    public static CookRepository getInstance() {
        if (instance == null) {
            synchronized (CookRepository.class) {
                if (instance == null) {
                    instance = new CookRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<CookEntity> getCook(final String accountId, Application application) {
        return ((BaseApp) application).getDatabase().cookDao().getById(accountId);
    }

    public LiveData<List<CookEntity>> getCooks(Application application) {
        return ((BaseApp) application).getDatabase().cookDao().getAll();
    }

    public void insert(final CookEntity cook, OnAsyncEventListener callback,
                       Application application) {
        new CreateCook(application, callback).execute(cook);
    }

    public void update(final CookEntity cook, OnAsyncEventListener callback,
                       Application application) {
        new UpdateCook(application, callback).execute(cook);
    }

    public void delete(final CookEntity cook, OnAsyncEventListener callback,
                       Application application) {
        new DeleteCook(application, callback).execute(cook);
    }
}
