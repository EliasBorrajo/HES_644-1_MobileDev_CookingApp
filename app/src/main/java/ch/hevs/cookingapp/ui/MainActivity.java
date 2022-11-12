package ch.hevs.cookingapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;

import java.util.List;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.ui.recipe.RecipeCreateActivity;
import ch.hevs.cookingapp.ui.recipe.RecipesActivity;

// Page principale de l'application, Activité classique comme les autres
public class MainActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        setTitle(getString(R.string.app_name));

        navigationView.setCheckedItem(R.id.nav_none);

        initiateView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getString(R.string.app_name));
        navigationView.setCheckedItem(R.id.nav_none);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.action_logout));
        alertDialog.setCancelable(false);
        alertDialog.setMessage(getString(R.string.logout_msg));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_logout), (dialog, which) -> logout());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.show();
    }

    //TODO matin midi soir avec scroll faire juste 3 boutons pour montrer toutes les recetttes du midi soir et matin
    private void initiateView() {
        Button breakfastBtn = findViewById(R.id.buttonBreakfast);
        breakfastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipesActivity = new Intent(MainActivity.this, RecipesActivity.class);
                recipesActivity.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                recipesActivity.putExtra(String.valueOf(R.string.meals), "Breakfast");
                startActivity(recipesActivity);
            }
        });

        Button lunchBtn = findViewById(R.id.buttonLunch);
        lunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipeActivity = new Intent(MainActivity.this, RecipesActivity.class);
                recipeActivity.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                recipeActivity.putExtra(String.valueOf(R.string.meals), "Lunch");
                startActivity(recipeActivity);
            }
        });

        Button dinnerBtn = findViewById(R.id.buttonDinner);
        dinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipeActivity = new Intent(MainActivity.this, RecipesActivity.class);
                recipeActivity.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                recipeActivity.putExtra(String.valueOf(R.string.meals), "Dinner");
                startActivity(recipeActivity);
            }
        });

        Button newRecipeBtn = findViewById(R.id.buttonNewRecipe);
        newRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipeActivity = new Intent(MainActivity.this, RecipeCreateActivity.class);
                startActivity(recipeActivity);
            }
        });
    }

}