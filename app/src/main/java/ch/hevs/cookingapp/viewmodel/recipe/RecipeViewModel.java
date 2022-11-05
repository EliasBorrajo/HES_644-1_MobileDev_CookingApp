package ch.hevs.cookingapp.viewmodel.recipe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.database.repository.RecipeRepository;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

public class RecipeViewModel extends AndroidViewModel {
    private Application application;

    private RecipeRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<RecipeEntity> observableRecipe;

    public RecipeViewModel(@NonNull Application application,
                         final Long id, RecipeRepository recipeRepository) {
        super(application);

        this.application = application;

        repository = recipeRepository;

        observableRecipe = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableRecipe.setValue(null);

        LiveData<RecipeEntity> recipe = repository.getRecipe(id, application);

        // observe the changes of the account entity from the database and forward them
        observableRecipe.addSource(recipe, observableRecipe::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long id;

        private final RecipeRepository repository;

        public Factory(@NonNull Application application, Long id) {
            this.application = application;
            this.id = id;
            repository = ((BaseApp) application).getRecipeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new RecipeViewModel(application, id, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<RecipeEntity> getRecipe() {
        return observableRecipe;
    }

    public void createRecipe(RecipeEntity recipe, OnAsyncEventListener callback) {
        repository.insert(recipe, callback, application);
    }

    public void updateRecipe(RecipeEntity recipe, OnAsyncEventListener callback) {
        repository.update(recipe, callback, application);
    }

    public void deleteRecipe(RecipeEntity recipe, OnAsyncEventListener callback) {
        repository.delete(recipe, callback, application);

    }
}
