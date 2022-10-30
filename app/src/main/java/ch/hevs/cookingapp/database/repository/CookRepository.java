package ch.hevs.cookingapp.database.repository;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import java.util.List;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.CookEntity;

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

    public LiveData<CookEntity> getCook(final Long accountId, Application application) {
        return ((BaseApp) application).getDatabase().cookDao().getById(accountId);
    }

    public LiveData<List<CookEntity>> getCooks(Application application) {
        return ((BaseApp) application).getDatabase().cookDao().getAll();
    }

    public void insert(final CookEntity account, OnAsyncEventListener callback,
                       Application application) {
        new CreateCook(application, callback).execute(account);
    }

    public void update(final CookEntity account, OnAsyncEventListener callback,
                       Application application) {
        new UpdateCook(application, callback).execute(account);
    }

    public void delete(final CookEntity account, OnAsyncEventListener callback,
                       Application application) {
        new DeleteCook(application, callback).execute(account);
    }
}
