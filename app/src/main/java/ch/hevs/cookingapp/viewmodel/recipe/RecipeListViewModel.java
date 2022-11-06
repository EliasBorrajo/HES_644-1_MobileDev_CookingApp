package ch.hevs.cookingapp.viewmodel.recipe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.database.pojo.CookWithRecipes;
import ch.hevs.cookingapp.database.repository.RecipeRepository;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

public class RecipeListViewModel extends AndroidViewModel {

    private Application application;

    private RecipeRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<RecipeEntity>> observableOwnRecipes;

    public RecipeListViewModel(@NonNull Application application,
                                final String creator,
                                RecipeRepository recipeRepository) {
        super(application);

        this.application = application;

        repository = recipeRepository;

        observableOwnRecipes = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableOwnRecipes.setValue(null);

        LiveData<List<RecipeEntity>> ownRecipes = repository.getByCreator(creator, application);

        // observe the changes of the entities from the database and forward them
        observableOwnRecipes.addSource(ownRecipes, observableOwnRecipes::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String ownerId;

        private final RecipeRepository recipeRepository;

        public Factory(@NonNull Application application, String ownerId) {
            this.application = application;
            this.ownerId = ownerId;
            recipeRepository = ((BaseApp) application).getRecipeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new RecipeListViewModel(application, ownerId, recipeRepository);
        }
    }

    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */
    public LiveData<List<RecipeEntity>> getOwnRecipes() {
        return observableOwnRecipes;
    }

    public void deleteRecipe(RecipeEntity recipe, OnAsyncEventListener callback) {
        repository.delete(recipe, callback, application);
    }

}
