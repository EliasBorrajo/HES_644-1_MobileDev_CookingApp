package ch.hevs.cookingapp.ui.recipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.view.GravityCompat;
import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.database.async.recipe.CreateRecipe;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.database.enumeration.Allergy;
import ch.hevs.cookingapp.database.enumeration.Diet;
import ch.hevs.cookingapp.database.enumeration.Meal;
import ch.hevs.cookingapp.ui.BaseActivity;
import ch.hevs.cookingapp.ui.MainActivity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;
public class RecipeCreateActivity extends BaseActivity {

    private static final String TAG = "RecipeCreateActivity";

    private String cook;
    private EditText etRecipeName;
    private EditText etTime;
    private EditText etIngredients;
    private EditText etPreparation;

    private String dietSelection;
    private String allergySelection;
    private String mealTimeSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_recipe_create, frameLayout);

        navigationView.setCheckedItem(position);

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        cook = settings.getString(BaseActivity.PREFS_USER, null);

        initiateView();
    }

    private void initiateView()
    {
        dietSelection = "";
        allergySelection = "";
        mealTimeSelection = "";

        etRecipeName = findViewById(R.id.et_createRecipe_RecipeName);
        etTime = findViewById(R.id.et_createRecipe_PrepTime);
        etIngredients = findViewById(R.id.et_createRecipe_ingredients);
        etPreparation = findViewById(R.id.et_createRecipe_preparation);

        Button saveBtn = findViewById(R.id.btn_createRecipe_save);
        saveBtn.setOnClickListener(view -> saveRecipe(
                cook,
                etRecipeName.getText().toString(),
                etTime.getText().toString().equals("")?0:Integer.parseInt(etTime.getText().toString()),
                etIngredients.getText().toString(),
                etPreparation.getText().toString(),
                dietSelection,
                allergySelection,
                mealTimeSelection
        ));
    }

    private void saveRecipe(String creator, String name, int time, String ingredients, String preparation, String diet, String allergy, String mealTime) {
        if(name.equals("")) {
            etRecipeName.setError(getString(R.string.error_empty_recipe_name));
            etRecipeName.requestFocus();
            return;
        }
        if(ingredients.equals("")) {
            etIngredients.setError(getString(R.string.error_empty_ingredient));
            etIngredients.requestFocus();
            return;
        }
        if(preparation.equals("")) {
            etPreparation.setError(getString(R.string.error_empty_preparation));
            etPreparation.requestFocus();
            return;
        }

        RecipeEntity newRecipe = new RecipeEntity(creator, name, time, ingredients, preparation, diet, allergy, mealTime);

        new CreateRecipe(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createNewRecipe: success");
                Toast.makeText(RecipeCreateActivity.this, getString(R.string.recipe_created), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RecipeCreateActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createNewRecipe: failure", e);
            }
        }).execute(newRecipe);
    }

    public void onCheckedMeal(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.cb_createRecipe_Breakfast:
                if (checked)
                {
                    mealTimeSelection = mealTimeSelection + Meal.BREAKFAST;
                    System.out.println("Meal time: " + mealTimeSelection);
                } else
                    mealTimeSelection = mealTimeSelection.replaceAll("Breakfast","");
                break;
            case R.id.cb_createRecipe_Lunch:
                if (checked)
                {
                    mealTimeSelection = mealTimeSelection + Meal.LUNCH;
                    System.out.println("Meal time: " + mealTimeSelection);
                } else
                    mealTimeSelection = mealTimeSelection.replaceAll("Lunch","");
                break;
            case R.id.cb_createRecipe_Dinner:
                if (checked){
                    mealTimeSelection = mealTimeSelection + Meal.DINNER;
                    System.out.println("Meal time: " + mealTimeSelection);
                } else
                    mealTimeSelection = mealTimeSelection.replaceAll("Dinner","");
                break;
        }
    }

    public void onCheckedDiet(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.cb_createRecipe_Vegan:
                if (checked)
                {
                    dietSelection = dietSelection + Diet.VEGAN;
                    System.out.println("Diet: " + dietSelection);
                } else
                    dietSelection = dietSelection.replaceAll("Vegan","");
                break;
            case R.id.cb_createRecipe_Meat:
                if (checked)
                {
                    dietSelection = dietSelection + Diet.MEAT;
                    System.out.println("Diet: " + dietSelection);
                } else
                    dietSelection = dietSelection.replaceAll("Meat","");
                break;
            case R.id.cb_createRecipe_Fish:
                if (checked){
                    dietSelection = dietSelection + Diet.FISH;
                    System.out.println("Diet: " + dietSelection);
                } else
                    dietSelection = dietSelection.replaceAll("Fish","");
                break;
            case R.id.cb_createRecipe_Vegetarian:
                if (checked){
                    dietSelection = dietSelection + Diet.VEGETARIAN;
                    System.out.println("Diet: " + dietSelection);
                } else
                    dietSelection = dietSelection.replaceAll("Vegetarian","");
                break;
        }
    }

    public void onCheckedAllegry(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.cb_createRecipe_Lactose:
                if (checked)
                {
                    allergySelection = allergySelection + Allergy.LACTOSE;
                    System.out.println("Allegry: " + allergySelection);
                } else
                    allergySelection = allergySelection.replaceAll("Lactose","");
                break;
            case R.id.cb_createRecipe_Gluten:
                if (checked)
                {
                    allergySelection = allergySelection + Allergy.GLUTEN;
                    System.out.println("Allegry: " + allergySelection);
                } else
                    allergySelection = allergySelection.replaceAll("Gluten","");
                break;
            case R.id.cb_createRecipe_Nuts:
                if (checked)
                {
                    allergySelection = allergySelection + Allergy.NUT;
                    System.out.println("Allegry: " + allergySelection);
                } else
                    allergySelection = allergySelection.replaceAll("Nuts","");
                break;
            case R.id.cb_createRecipe_Peanuts:
                if (checked)
                {
                    allergySelection = allergySelection + Allergy.PEANUT;
                    System.out.println("Allegry: " + allergySelection);
                } else
                    allergySelection = allergySelection.replaceAll("Peanut","");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}