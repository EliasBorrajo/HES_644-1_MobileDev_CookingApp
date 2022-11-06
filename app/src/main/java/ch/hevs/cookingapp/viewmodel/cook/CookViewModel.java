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

public class CookViewModel extends AndroidViewModel {

    private Application application;

    private CookRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<CookEntity> observableCook;

    public CookViewModel(@NonNull Application application,
                         final String email, CookRepository cookRepository) {
       super(application);

       this.application = application;

       repository = cookRepository;

       observableCook = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCook.setValue(null);

        LiveData<CookEntity> account = repository.getCook(email, application);

        // observe the changes of the account entity from the database and forward them
        observableCook.addSource(account, observableCook::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;

        private final CookRepository repository;

        public Factory(@NonNull Application application, String email) {
            this.application = application;
            this.email = email;
            repository = ((BaseApp) application).getCookRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CookViewModel(application, email, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<CookEntity> getCook() {
        return observableCook;
    }

    public void createCook(CookEntity cook, OnAsyncEventListener callback) {
        repository.insert(cook, callback, application);
    }

    public void updateCook(CookEntity cook, OnAsyncEventListener callback) {
        repository.update(cook, callback, application);
    }

    public void deleteCook(CookEntity cook, OnAsyncEventListener callback) {
        repository.delete(cook, callback, application);

    }
}
