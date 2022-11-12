package ch.hevs.cookingapp.ui.recipe;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.adapter.RecyclerAdapter;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.database.enumeration.Diet;
import ch.hevs.cookingapp.database.enumeration.Meal;
import ch.hevs.cookingapp.database.pojo.CookWithRecipes;
import ch.hevs.cookingapp.ui.BaseActivity;
import ch.hevs.cookingapp.util.RecyclerViewItemClickListener;
import ch.hevs.cookingapp.viewmodel.recipe.RecipeListViewModel;

public class RecipesActivity extends BaseActivity {

    private static final String TAG = "RecipesActivity";

    private List<RecipeEntity> recipes;
    private RecyclerAdapter<RecipeEntity> adapter;
    private RecipeListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_recipes, frameLayout);

        setTitle(getString(R.string.title_activity_recipes));
        navigationView.setCheckedItem(position);

        RecyclerView recyclerView = findViewById(R.id.recipesRecyclerView);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settings.getString(BaseActivity.PREFS_USER, null);

        String meals = getIntent().getStringExtra(String.valueOf(R.string.meals));

        recipes = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + recipes.get(position).getName());

                Intent intent = new Intent(RecipesActivity.this, RecipeDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                        Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("recipeId", recipes.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        RecipeListViewModel.Factory factory = new RecipeListViewModel.Factory(
                getApplication(), user);
        viewModel = ViewModelProviders.of((FragmentActivity) this, (ViewModelProvider.Factory) factory).get(RecipeListViewModel.class);
        //TODO get recipes matin ou midi ou soir
        LiveData<List<RecipeEntity>> recipesMeals = null;

        System.out.println("Recipes for " + meals);

        switch (meals) {
            case "Breakfast":
                recipesMeals = viewModel.getBreakfastRecipes();
                break;
            case "Lunch":
                recipesMeals = viewModel.getLunchRecipes();
                break;
            case "Dinner":
                recipesMeals = viewModel.getDinnerRecipes();
                break;
            case "ownRecipes":
                recipesMeals = viewModel.getOwnRecipes();
                break;
        }
        recipesMeals.observe(this, recipeEntities -> {
            if (recipeEntities != null) {
                recipes = recipeEntities;
                adapter.setData(recipes);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}