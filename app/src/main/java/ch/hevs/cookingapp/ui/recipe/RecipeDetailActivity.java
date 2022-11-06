package ch.hevs.cookingapp.ui.recipe;

import android.os.Bundle;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.ui.BaseActivity;

public class RecipeDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
    }
}