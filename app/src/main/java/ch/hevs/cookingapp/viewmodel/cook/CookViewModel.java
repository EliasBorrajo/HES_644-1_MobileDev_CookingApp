package ch.hevs.cookingapp.viewmodel.cook;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.database.repository.CookRepository;

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
}
