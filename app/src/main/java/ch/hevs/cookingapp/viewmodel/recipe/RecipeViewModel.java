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

/**
 * Object that we use in the activities, to link our graphic objects to our DB objects.
 * We have an Observer that will observe the changes in the DB to update the UI.
 */
public class RecipeViewModel extends AndroidViewModel
{
    private RecipeRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<RecipeEntity> observableRecipe;

    public RecipeViewModel(@NonNull Application application,
                           final String id, RecipeRepository recipeRepository)
    {
        super(application);

        repository = recipeRepository;

        observableRecipe = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableRecipe.setValue(null);

        LiveData<RecipeEntity> recipe = repository.getRecipe(id);

        // observe the changes of the account entity from the database and forward them
        observableRecipe.addSource(recipe, observableRecipe::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory
    {

        @NonNull
        private final Application application;

        private final String id;

        private final RecipeRepository repository;

        public Factory(@NonNull Application application, String id)
        {
            this.application = application;
            this.id = id;
            repository = ((BaseApp) application).getRecipeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass)
        {
            //noinspection unchecked
            return (T) new RecipeViewModel(application, id, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<RecipeEntity> getRecipe()
    {
        return observableRecipe;
    }

    public void createRecipe(RecipeEntity recipe, OnAsyncEventListener callback)
    {
        repository.insert(recipe, callback);
    }

    public void updateRecipe(RecipeEntity recipe, OnAsyncEventListener callback)
    {
        repository.update(recipe, callback);
    }

    public void deleteRecipe(RecipeEntity recipe, OnAsyncEventListener callback)
    {
        repository.delete(recipe, callback);

    }
}
