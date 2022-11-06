package ch.hevs.cookingapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.ui.recipe.RecipesActivity;

// Page principale de l'application, Activité classique comme les autres
public class MainActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //TODO matin midi soir avec scroll faire juste 3 boutons pour montrer toutes les recetttes du midi soir et matin
    private void initiateView() {
        Button breakfastBtn = findViewById(R.id.buttonBreakfast);
        breakfastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipeActivity = new Intent(MainActivity.this, RecipesActivity.class);
                startActivity(recipeActivity);
            }
        });

        Button lunchBtn = findViewById(R.id.buttonLunch);
        lunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipeActivity = new Intent(MainActivity.this, RecipesActivity.class);
                startActivity(recipeActivity);
            }
        });

        Button dinnerBtn = findViewById(R.id.buttonDinner);
        dinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipeActivity = new Intent(MainActivity.this, RecipesActivity.class);
                startActivity(recipeActivity);
            }
        });
    }

}