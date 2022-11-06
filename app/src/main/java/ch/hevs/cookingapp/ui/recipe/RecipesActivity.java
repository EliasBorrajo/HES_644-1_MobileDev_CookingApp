package ch.hevs.cookingapp.ui.recipe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.adapter.RecyclerAdapter;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.ui.BaseActivity;
import ch.hevs.cookingapp.util.RecyclerViewItemClickListener;
import ch.hevs.cookingapp.viewmodel.recipe.RecipeListViewModel;

public class RecipesActivity extends AppCompatActivity {

    private static final String TAG = "RecipesActivity";

    private List<RecipeEntity> recipes;
    private RecyclerAdapter<RecipeEntity> adapter;
    private RecipeListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_recipe);
        getLayoutInflater().inflate(R.layout.activity_recipes, frameLayout);

        setTitle(getString(R.string.title_activity_recipes));
        //navigationView.setCheckedItem(position);

        RecyclerView recyclerView = findViewById(R.id.recipesRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settings.getString(BaseActivity.PREFS_USER, null);

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
        viewModel = ViewModelProviders.of(this, factory).get(RecipeListViewModel.class);
        //TODO get recipes matin ou midi ou soir
        viewModel.getOwnRecipes().observe(this, recipeEntities -> {
            if (recipeEntities != null) {
                recipes = recipeEntities;
                adapter.setData(recipes);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    //TODO onNavigationItemSelected

}