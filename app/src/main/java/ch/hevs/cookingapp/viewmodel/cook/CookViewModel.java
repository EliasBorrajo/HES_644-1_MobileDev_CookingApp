package ch.hevs.cookingapp.viewmodel.cook;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.database.repository.CookRepository;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

/**
 * Object that we use in the activities, to link our graphic objects to our DB objects.
 * We have an Observer that will observe the changes in the DB to update the UI.
 */
public class CookViewModel extends AndroidViewModel
{

    private CookRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    // Possède les informtions sources de la DB
    private final MediatorLiveData<CookEntity> observableCook;

    public CookViewModel(@NonNull Application application,
                         final String email, CookRepository cookRepository)
    {
        super(application);

        repository = cookRepository;

        observableCook = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCook.setValue(null);


        LiveData<CookEntity> cook = repository.getCook(email);

        // observe the changes of the account entity from the database and forward them
        observableCook.addSource(cook, observableCook::setValue);

    }

    /**
     * A email is used to inject the cook id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory
    {

        @NonNull
        private final Application application;

        private final String email;

        private final CookRepository repository;

        public Factory(@NonNull Application application, String email)
        {
            this.application = application;
            this.email = email;
            repository = ((BaseApp) application).getCookRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass)
        {
            //noinspection unchecked
            return (T) new CookViewModel(application, email, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<CookEntity> getCook()
    {
        return observableCook;
    }


    public void updateCook(CookEntity cook, OnAsyncEventListener callback)
    {
        repository.update(cook, callback);
    }

    public void deleteCook(CookEntity cook, OnAsyncEventListener callback)
    {
        repository.delete(cook, callback);

    }
}
