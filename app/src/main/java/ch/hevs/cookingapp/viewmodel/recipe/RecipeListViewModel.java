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
import ch.hevs.cookingapp.database.repository.RecipeRepository;

/**
 * Object that we use in the activities, to link our graphic objects to our DB objects.
 * We have an Observer that will observe the changes in the DB to update the UI.
 */
public class RecipeListViewModel extends AndroidViewModel
{

    private Application application;

    private RecipeRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    // On a plusieurs listes de MEALS différents, alors on aura un MediatorLiveData pour chacun.
    private final MediatorLiveData<List<RecipeEntity>> observableOwnRecipes;
    private final MediatorLiveData<List<RecipeEntity>> observableRecipesBreakfast;
    private final MediatorLiveData<List<RecipeEntity>> observableRecipesLunch;
    private final MediatorLiveData<List<RecipeEntity>> observableRecipeDinner;

    public RecipeListViewModel(@NonNull Application application,
                               final String creator,
                               RecipeRepository recipeRepository)
    {
        super(application);

        this.application = application;

        repository = recipeRepository;


        observableOwnRecipes = new MediatorLiveData<>();
        observableRecipesBreakfast = new MediatorLiveData<>();
        observableRecipesLunch = new MediatorLiveData<>();
        observableRecipeDinner = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableOwnRecipes.setValue(null);
        observableRecipesBreakfast.setValue(null);
        observableRecipesLunch.setValue(null);
        observableRecipeDinner.setValue(null);

        // User selects the type of meal he wants, we send the request with the method of the Repository giving him which type of meal we want to retrieve.
        // If the value in the "Meals" column matches, it is one of the rows we want to retrieve from the DB
        // BreakFast / Lunch / Dinner are the values we want to evaluate in the "Meals" column of the DB.
        LiveData<List<RecipeEntity>> ownRecipes = recipeRepository.getByCreator(creator, application);
        LiveData<List<RecipeEntity>> breakfastRecipes = recipeRepository.getByMeal("Breakfast", application);
        LiveData<List<RecipeEntity>> lunchRecipes = recipeRepository.getByMeal("Lunch", application);
        LiveData<List<RecipeEntity>> dinnerRecipes = recipeRepository.getByMeal("Dinner", application);

        // observe the changes of the entities from the database and forward them
        // On ajoute nos différentes sources que l'on veut observer.
        observableOwnRecipes.addSource(ownRecipes, observableOwnRecipes::setValue);
        observableRecipesBreakfast.addSource(breakfastRecipes, observableRecipesBreakfast::setValue);
        observableRecipesLunch.addSource(lunchRecipes, observableRecipesLunch::setValue);
        observableRecipeDinner.addSource(dinnerRecipes, observableRecipeDinner::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory
    {

        @NonNull
        private final Application application;

        private final String ownerId;

        private final RecipeRepository recipeRepository;

        public Factory(@NonNull Application application, String ownerId)
        {
            this.application = application;
            this.ownerId = ownerId;
            recipeRepository = ((BaseApp) application).getRecipeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass)
        {
            //noinspection unchecked
            return (T) new RecipeListViewModel(application, ownerId, recipeRepository);
        }
    }

    /**
     * Expose the LiveData RecipeEntities query so the UI can observe it.
     */
    public LiveData<List<RecipeEntity>> getOwnRecipes()
    {
        return observableOwnRecipes;
    }

    public LiveData<List<RecipeEntity>> getBreakfastRecipes()
    {
        return observableRecipesBreakfast;
    }

    public LiveData<List<RecipeEntity>> getLunchRecipes()
    {
        return observableRecipesLunch;
    }

    public LiveData<List<RecipeEntity>> getDinnerRecipes()
    {
        return observableRecipeDinner;
    }

}
