package ch.hevs.cookingapp.viewmodel.cook;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.database.repository.CookRepository;
import ch.hevs.cookingapp.database.repository.RecipeRepository;
import ch.hevs.cookingapp.viewmodel.recipe.RecipeListViewModel;

public class CookListViewModel extends AndroidViewModel
{
    private Application application;
    private CookRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<CookEntity>> observableCooks;


    public CookListViewModel(@NonNull Application application,
                             CookRepository cookRepository)
    {
        super(application);
        this.application = application;

        repository = cookRepository;

        observableCooks = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableCooks.setValue(null);

        LiveData<List<CookEntity>> allCooks = cookRepository.getCooks(application);

        // observe the changes of the entities from the database and forward them
        observableCooks.addSource(allCooks, observableCooks::setValue);
    }

    /**
     * A user is used to inject the account email into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory
    {
        @NonNull
        private final Application application;

        private final CookRepository cookRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            cookRepository = ((BaseApp) application).getCookRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CookListViewModel(application, cookRepository);
        }
    }

    /**
     * Expose the LiveData CookEntities query so the UI can observe it.
     */
    public LiveData<List<CookEntity>> getCooks() {
        return observableCooks;
    }


}
