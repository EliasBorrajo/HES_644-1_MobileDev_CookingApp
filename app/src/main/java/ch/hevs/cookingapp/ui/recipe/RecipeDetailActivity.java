package ch.hevs.cookingapp.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.text.NumberFormat;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.ui.BaseActivity;
import ch.hevs.cookingapp.viewmodel.recipe.RecipeViewModel;

public class RecipeDetailActivity extends BaseActivity
{
    // Constantes pour l'ordre dans la toolbar
    private static final int ADD_RECIPE = 1;       // C'est l'ID du menu. La toolbar sera modifié
    private static final String TAG = "RecipeDetailActivity";


    private RecipeEntity recipe;

    private RecipeViewModel viewModel;

    private TextView tvRecipeName;
    // TODO Ajouter photo
    private LinearLayout llPrepTime;
    private TextView tvTime;

    private CheckBox cbBreakfast;
    private CheckBox cbLunch;
    private CheckBox cbDinner;

    private CheckBox cbMeat;
    private CheckBox cbFish;
    private CheckBox cbVegetarian;
    private CheckBox cbVegan;

    private CheckBox cbNuts;
    private CheckBox cbPeanut;
    private CheckBox cbLactose;
    private CheckBox cbGluten;

    private TextView tvIngredients;
    private TextView tvPreparation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_recipe_detail, frameLayout);
        setTitle(getString(R.string.title_activity_myrecipes));
        navigationView.setCheckedItem(position);

        Long recipeId = getIntent().getLongExtra("recipeId", 0L);

        initiateView();

        RecipeViewModel.Factory factory = new RecipeViewModel.Factory(getApplication(), recipeId);
        viewModel = new ViewModelProvider((FragmentActivity) this, (ViewModelProvider.Factory) factory).get(RecipeViewModel.class);
        viewModel.getRecipe().observe(this, recipeEntity -> {
            if (recipeEntity != null)
            {
                recipe = recipeEntity;
                updateContent();
            }
        });
    }

    private void initiateView()
    {
        tvRecipeName = findViewById(R.id.tv_recipe_name);
        // TODO Ajouter photo
        llPrepTime = findViewById(R.id.ll_PrepTime);
        tvTime = findViewById(R.id.tv_time);

        cbBreakfast = findViewById(R.id.checkBoxBreakfast);
        cbLunch = findViewById(R.id.checkBoxLunch);
        cbDinner = findViewById(R.id.checkBoxDinner);

        cbMeat = findViewById(R.id.checkBoxMeat);
        cbFish = findViewById(R.id.checkBoxFish);
        cbVegetarian = findViewById(R.id.checkBoxVegetarian);
        cbVegan = findViewById(R.id.checkBoxVegan);

        cbNuts = findViewById(R.id.checkBoxNuts);
        cbLactose = findViewById(R.id.checkBoxLactose);
        cbPeanut = findViewById(R.id.checkBoxPeanuts);
        cbGluten = findViewById(R.id.checkBoxGluten);

        tvIngredients = findViewById(R.id.tv_ingredients);
        tvPreparation = findViewById(R.id.tv_preparation);
    }

    private void updateContent()
    {
        if (recipe != null)
        {
            tvRecipeName.setText(recipe.getName());
            // TODO Ajouter photo
            if (recipe.getPrepTime() == 0)
            {
                llPrepTime.setVisibility(View.VISIBLE);
                tvTime.setVisibility(View.VISIBLE);
                tvTime.setText(recipe.getPrepTime());
            }

            if (recipe.getMealTime().contains("Breakfast"))
            {
                cbBreakfast.setChecked(true);
            }
            if (recipe.getMealTime().contains("Lunch"))
            {
                cbLunch.setChecked(true);
            }
            if (recipe.getMealTime().contains("Dinner"))
            {
                cbDinner.setChecked(true);
            }

            if (recipe.getDiet().contains("Meat"))
            {
                cbMeat.setChecked(true);
            }
            if (recipe.getDiet().contains("Fish"))
            {
                cbFish.setChecked(true);
            }
            if (recipe.getDiet().contains("Vegetarian"))
            {
                cbVegetarian.setChecked(true);
            }
            if (recipe.getDiet().contains("Vegan"))
            {
                cbVegan.setChecked(true);
            }

            if (recipe.getAllergy().contains("Nuts"))
            {
                cbNuts.setChecked(true);
            }
            if (recipe.getAllergy().contains("Lactose"))
            {
                cbLactose.setChecked(true);
            }
            if (recipe.getAllergy().contains("Peanut"))
            {
                cbPeanut.setChecked(true);
            }
            if (recipe.getAllergy().contains("Gluten"))
            {
                cbGluten.setChecked(true);
            }

            tvIngredients.setText(recipe.getIngredients());
            tvPreparation.setText(recipe.getPreparation());
        }
    }
}