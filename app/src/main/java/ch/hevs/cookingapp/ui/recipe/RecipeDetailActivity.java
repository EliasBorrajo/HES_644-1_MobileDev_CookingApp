package ch.hevs.cookingapp.ui.recipe;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.database.enumeration.Allergy;
import ch.hevs.cookingapp.database.enumeration.Diet;
import ch.hevs.cookingapp.database.enumeration.Meal;
import ch.hevs.cookingapp.ui.BaseActivity;
import ch.hevs.cookingapp.ui.MainActivity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;
import ch.hevs.cookingapp.viewmodel.recipe.RecipeViewModel;


/**
 * Display of the recipe.
 * If the recipe is mine, I can edit it, otherwise the delete & edit options are not displayed in the toolbar
 */
public class RecipeDetailActivity extends BaseActivity
{
    private static final String TAG = "RecipeDetailActivity";
    // Constantes pour l'ordre dans la toolbar
    // C'est l'ID du menu. La toolbar sera modifié
    private static final int EDIT_RECIPE = 1;
    private static final int DELETE_RECIPE = 2;
    private static String recipeCreator;

    private Toast toast;

    private String meals;

    private boolean isEditable;

    private RecipeEntity recipe;
    private RecipeViewModel viewModel;

    private EditText etRecipeName;

    private ImageButton imageRecipe;
    private byte[] bytes;

    private TextView tvPrepTime;
    private EditText etTime;

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

    private EditText etIngredients;
    private EditText etPreparation;

    private String dietSelection;
    private String allergySelection;
    private String mealTimeSelection;

    private TextView tvCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_recipe_detail, frameLayout);
        setTitle(getString(R.string.title_activity_myrecipes));
        navigationView.setCheckedItem(position);

        String recipeId = getIntent().getStringExtra("recipeId");
        meals = getIntent().getStringExtra(String.valueOf(R.string.meals));

        initiateView();

        RecipeViewModel.Factory factory = new RecipeViewModel.Factory(getApplication(), recipeId);

        viewModel = ViewModelProviders.of((FragmentActivity) this, (ViewModelProvider.Factory) factory).get(RecipeViewModel.class);
        viewModel.getRecipe().observe(this, recipeEntity -> {
            if (recipeEntity != null)
            {
                recipe = recipeEntity;
                // Savoir à qui est la recette
                recipeCreator = recipe.getCreator();
                updateContent();
            }
        });
    }

    /**
     * Initialisation des élements de l'UI
     */
    private void initiateView()
    {
        isEditable = false;
        etRecipeName = findViewById(R.id.et_recipeDetail_recipe_name);
        imageRecipe = findViewById(R.id.imageButton_recipeDetail);
        tvPrepTime = findViewById(R.id.tv_recipeDetail_prep_time);
        etTime = findViewById(R.id.et_recipeDetail_time);

        cbBreakfast = findViewById(R.id.cb_recipeDetail_Breakfast);
        cbLunch = findViewById(R.id.cb_recipeDetail_Lunch);
        cbDinner = findViewById(R.id.cb_recipeDetail_Dinner);

        cbMeat = findViewById(R.id.cb_recipeDetail_Meat);
        cbFish = findViewById(R.id.cb_recipeDetail_Fish);
        cbVegetarian = findViewById(R.id.cb_recipeDetail_Vegetarian);
        cbVegan = findViewById(R.id.cb_recipeDetail_Vegan);

        cbNuts = findViewById(R.id.cb_recipeDetail_Nuts);
        cbLactose = findViewById(R.id.cb_recipeDetail_Lactose);
        cbPeanut = findViewById(R.id.cb_recipeDetail_Peanuts);
        cbGluten = findViewById(R.id.cb_recipeDetail_Gluten);

        etIngredients = findViewById(R.id.et_recipeDetail_ingredients);
        etPreparation = findViewById(R.id.et_recipeDetail_preparation);
        tvCreator = findViewById(R.id.tv_message_recipeby);
    }


    private void updateContent()
    {
        if (recipe != null)
        {
            etRecipeName.setText(recipe.getName());
            imageRecipe.setClickable(false);
            imageRecipe.setFocusable(false);
            if(recipe.getImage() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(recipe.getImage(), 0, recipe.getImage().length);
                imageRecipe.setImageBitmap(bitmap);
            }
            if (recipe.getPrepTime() != 0)
            {
                tvPrepTime.setVisibility(View.VISIBLE);
                etTime.setVisibility(View.VISIBLE);
                etTime.setText(Integer.toString(recipe.getPrepTime()));
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
            etIngredients.setText(recipe.getIngredients());
            etPreparation.setText(recipe.getPreparation());
            tvCreator.setText("Created by " + recipe.getCreator());

            dietSelection = recipe.getDiet();
            allergySelection = recipe.getAllergy();
            mealTimeSelection = recipe.getMealTime();
            Log.i(TAG, "Activity populated.");
        }
    }

    // Quand on clique dans la toolbar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == BaseActivity.position)
        {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        /*
        The activity has to be finished manually in order to guarantee the navigation hierarchy working.
        */
        finish();
        return super.onNavigationItemSelected(item);
    }

    // On modifie la TOOLBAR en ajoutant 2 bouttons icones
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        // Si c'est bien moi-même qui visite ma recette, la toolbar va avoir le bouton EDIT & DELETE en plus
        // Si c'est un autre user qui vient voir ma recette, ces bouttons ne s'affichent pas.
        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settings.getString(PREFS_USER, null);

        if(user.equals(recipeCreator))
        {
            menu.add(0, EDIT_RECIPE, Menu.NONE, getString(R.string.action_edit))
                    .setIcon(R.drawable.ic_edit_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

            menu.add(0, DELETE_RECIPE, Menu.NONE, getString(R.string.action_delete))
                    .setIcon(R.drawable.ic_delete_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return true;
    }

    // Quand on sélectionne les bouttons dans la TOOLBAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_RECIPE) {
            if (isEditable) {
                item.setIcon(R.drawable.ic_edit_white_24dp);
                switchEditableMode();
            } else {
                item.setIcon(R.drawable.ic_done_white_24dp);
                switchEditableMode();
            }
        }
        if (item.getItemId() == DELETE_RECIPE) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.delete_recipe_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
                viewModel.deleteRecipe(recipe, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(RecipeDetailActivity.this, MainActivity.class);
                        toast = Toast.makeText(RecipeDetailActivity.this, getString(R.string.recipe_deleted), Toast.LENGTH_LONG);
                        toast.show();
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Exception e) {}
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Permet de switch entre le mode edit & show.
     * si durant le "edit", les informations ne sont pas entrées correctement, et on change de mode,
     * Les données ne seront pas enregistrés et au reload de la page les data initiales seront affichés
     */
    private void switchEditableMode() {
        // Edite mode
        if (!isEditable)
        {
            etRecipeName.setFocusable(true);
            etRecipeName.setEnabled(true);
            etRecipeName.setFocusableInTouchMode(true);

            imageRecipe.setClickable(true);
            imageRecipe.setFocusable(true);

            tvPrepTime.setVisibility(View.VISIBLE);
            etTime.setVisibility(View.VISIBLE);
            etTime.setFocusable(true);
            etTime.setEnabled(true);
            etTime.setFocusableInTouchMode(true);

            cbBreakfast.setFocusable(true);
            cbBreakfast.setEnabled(true);
            cbLunch.setFocusable(true);
            cbLunch.setEnabled(true);
            cbDinner.setFocusable(true);
            cbDinner.setEnabled(true);

            cbMeat.setFocusable(true);
            cbMeat.setEnabled(true);
            cbFish.setFocusable(true);
            cbFish.setEnabled(true);
            cbVegetarian.setFocusable(true);
            cbVegetarian.setEnabled(true);
            cbVegan.setFocusable(true);
            cbVegan.setEnabled(true);

            cbNuts.setFocusable(true);
            cbNuts.setEnabled(true);
            cbPeanut.setFocusable(true);
            cbPeanut.setEnabled(true);
            cbLactose.setFocusable(true);
            cbLactose.setEnabled(true);
            cbGluten.setFocusable(true);
            cbGluten.setEnabled(true);

            etIngredients.setFocusable(true);
            etIngredients.setEnabled(true);
            etIngredients.setFocusableInTouchMode(true);

            etPreparation.setFocusable(true);
            etPreparation.setEnabled(true);
            etPreparation.setFocusableInTouchMode(true);
        }
        // Classic mode
        else
        {
            saveChanges(
                    etRecipeName.getText().toString(),
                    etTime.getText().toString().equals("")?0:Integer.parseInt(etTime.getText().toString()),
                    etIngredients.getText().toString(),
                    etPreparation.getText().toString(),
                    dietSelection,
                    allergySelection,
                    mealTimeSelection,
                    bytes
            );
            etRecipeName.setFocusable(false);
            etRecipeName.setEnabled(false);

            if (recipe.getPrepTime() != 0)
            {
                tvPrepTime.setVisibility(View.VISIBLE);
                etTime.setVisibility(View.VISIBLE);
            } else {
                tvPrepTime.setVisibility(View.GONE);
                etTime.setVisibility(View.GONE);
            }
            etTime.setFocusable(false);
            etTime.setEnabled(false);

            imageRecipe.setClickable(false);
            imageRecipe.setFocusable(false);

            cbBreakfast.setFocusable(false);
            cbBreakfast.setEnabled(false);
            cbLunch.setFocusable(false);
            cbLunch.setEnabled(false);
            cbDinner.setFocusable(false);
            cbDinner.setEnabled(false);

            cbMeat.setFocusable(false);
            cbMeat.setEnabled(false);
            cbFish.setFocusable(false);
            cbFish.setEnabled(false);
            cbVegetarian.setFocusable(false);
            cbVegetarian.setEnabled(false);
            cbVegan.setFocusable(false);
            cbVegan.setEnabled(false);

            cbNuts.setFocusable(false);
            cbNuts.setEnabled(false);
            cbPeanut.setFocusable(false);
            cbPeanut.setEnabled(false);
            cbLactose.setFocusable(false);
            cbLactose.setEnabled(false);
            cbGluten.setFocusable(false);
            cbGluten.setEnabled(false);

            etIngredients.setFocusable(false);
            etIngredients.setEnabled(false);

            etPreparation.setFocusable(false);
            etPreparation.setEnabled(false);
        }
        isEditable = !isEditable;
    }

    /**
     *  Methode apellé lorsque on change du mode edit à show.
     *  Lorsque on switch de mode, on veut sauvegarder les data dans la DB.
     *  On commence par vérifier les paramètres entrées,
     *  puis si ils sont bons on les SET
     *
     *  @param : Seront les paramètres du UI que on get leur valeurs
     */
    private void saveChanges(String name, int time, String ingredients, String preparation, String diet, String allergy, String mealTime, byte[] bytes) {
        // Vérification des inputs
        if(name.equals("")) {
            etRecipeName.setError(getString(R.string.error_empty_recipe_name));
            etRecipeName.requestFocus();
            Toast.makeText(this, R.string.MessageSaveChanges, Toast.LENGTH_LONG).show();
            return;
        }
        if(ingredients.equals("")) {
            etIngredients.setError(getString(R.string.error_empty_ingredient));
            etIngredients.requestFocus();
            Toast.makeText(this, R.string.MessageSaveChanges, Toast.LENGTH_LONG).show();
            return;
        }
        if(preparation.equals("")) {
            etPreparation.setError(getString(R.string.error_empty_preparation));
            etPreparation.requestFocus();
            Toast.makeText(this, R.string.MessageSaveChanges, Toast.LENGTH_LONG).show();
            return;
        }

        if(mealTime.equals("")) {
            etPreparation.setError(getString(R.string.error_empty_meal_time));
            etPreparation.requestFocus();
            Toast.makeText(this, R.string.MessageSaveChanges, Toast.LENGTH_LONG).show();
            return;
        }
        // On recupère les paramètres à SET à notre entité
        recipe.setName(name);
        recipe.setPrepTime(time);
        recipe.setIngredients(ingredients);
        recipe.setPreparation(preparation);
        recipe.setDiet(diet);
        recipe.setAllergy(allergy);
        recipe.setMealTime(mealTime);
        if(bytes != null) {
            recipe.setImage(bytes);
        }

        viewModel.updateRecipe(recipe, new OnAsyncEventListener()
        {
            @Override
            public void onSuccess() {
                Log.d(TAG, "editRecipe: success");
                updateContent();
                toast = Toast.makeText(RecipeDetailActivity.this, getString(R.string.recipe_updated), Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "editRecipe: failure", e);
            }
        });
    }

    /**
     * Est apellé par le ImageButton de la View.
     * On vérifie si on a les permissions d'acceder au storage externe,
     * puis on ouvre un nouvel interface pour choisir une image de la gallery du téléphone à SET.
     *
     * @param view : Image Button de l'UI
     */
    public void onImageEdit(View view) {
        if (ContextCompat.checkSelfPermission(RecipeDetailActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            // when permission is nor granted
            // request permission
            ActivityCompat.requestPermissions(RecipeDetailActivity.this
                    , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
        else
        {
            // clear previous data
            imageRecipe.setImageBitmap(null);
            // Initialize intent
            Intent intent = new Intent(Intent.ACTION_PICK);
            // set type
            intent.setType("image/*");
            // start activity result
            startActivityForResult(Intent.createChooser(intent,"Select Image"),100);
        }
    }

    /**
     * Une fois qu'une photo a été sélectionné par le User, cette methode est apellée.
     * On va set l'image à la DB.
     *
     * @param requestCode : La requête de la photo à selectionner
     * @param resultCode  : resultat de la séléction de l'image choisie du telephone
     * @param data : La photo sélectionnée
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check condition
        if (requestCode==100 && resultCode==RESULT_OK && data!=null)
        {
            // when result is ok
            // initialize uri
            Uri uri=data.getData();
            // Initialize bitmap
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                // initialize byte stream
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                // compress Bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                // Initialize byte array
                bytes = stream.toByteArray();
                // get base64 encoded string
                String sImage= Base64.encodeToString(bytes,Base64.DEFAULT);
                // decode base64 string
                bytes = Base64.decode(sImage,Base64.DEFAULT);
                // Initialize bitmap
                bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                // set bitmap on imageView
                imageRecipe.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Methode apellé par les checkbox
     * @param view : Chechbox
     */
    public void onCheckedMealEdit(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.cb_recipeDetail_Breakfast:
                if (checked)
                {
                    mealTimeSelection = mealTimeSelection + Meal.BREAKFAST;
                    System.out.println("Meal time: " + mealTimeSelection);
                } else
                    mealTimeSelection = mealTimeSelection.replaceAll("Breakfast","");
                break;
            case R.id.cb_recipeDetail_Lunch:
                if (checked)
                {
                    mealTimeSelection = mealTimeSelection + Meal.LUNCH;
                    System.out.println("Meal time: " + mealTimeSelection);
                } else
                    mealTimeSelection = mealTimeSelection.replaceAll("Lunch","");
                break;
            case R.id.cb_recipeDetail_Dinner:
                if (checked){
                    mealTimeSelection = mealTimeSelection + Meal.DINNER;
                    System.out.println("Meal time: " + mealTimeSelection);
                } else
                    mealTimeSelection = mealTimeSelection.replaceAll("Dinner","");
                break;
        }
    }

    /**
     * Methode apellé par les checkbox
     * @param view : Chechbox
     */
    public void onCheckedDietEdit(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.cb_recipeDetail_Vegan:
                if (checked)
                {
                    dietSelection = dietSelection + Diet.VEGAN;
                    System.out.println("Diet: " + dietSelection);
                } else
                    dietSelection = dietSelection.replaceAll("Vegan","");
                break;
            case R.id.cb_recipeDetail_Meat:
                if (checked)
                {
                    dietSelection = dietSelection + Diet.MEAT;
                    System.out.println("Diet: " + dietSelection);
                } else
                    dietSelection = dietSelection.replaceAll("Meat","");
                break;
            case R.id.cb_recipeDetail_Fish:
                if (checked){
                    dietSelection = dietSelection + Diet.FISH;
                    System.out.println("Diet: " + dietSelection);
                } else
                    dietSelection = dietSelection.replaceAll("Fish","");
                break;
            case R.id.cb_recipeDetail_Vegetarian:
                if (checked){
                    dietSelection = dietSelection + Diet.VEGETARIAN;
                    System.out.println("Diet: " + dietSelection);
                } else
                    dietSelection = dietSelection.replaceAll("Vegetarian","");
                break;
        }
    }

    /**
     * Methode apellé par les checkbox
     * @param view : Chechbox
     */
    public void onCheckedAllegryEdit(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.cb_recipeDetail_Lactose:
                if (checked)
                {
                    allergySelection = allergySelection + Allergy.LACTOSE;
                    System.out.println("Allegry: " + allergySelection);
                } else
                    allergySelection = allergySelection.replaceAll("Lactose","");
                break;
            case R.id.cb_recipeDetail_Gluten:
                if (checked)
                {
                    allergySelection = allergySelection + Allergy.GLUTEN;
                    System.out.println("Allegry: " + allergySelection);
                } else
                    allergySelection = allergySelection.replaceAll("Gluten","");
                break;
            case R.id.cb_recipeDetail_Nuts:
                if (checked)
                {
                    allergySelection = allergySelection + Allergy.NUT;
                    System.out.println("Allegry: " + allergySelection);
                } else
                    allergySelection = allergySelection.replaceAll("Nuts","");
                break;
            case R.id.cb_recipeDetail_Peanuts:
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
        startActivity(new Intent(RecipeDetailActivity.this, RecipesActivity.class)
                                .putExtra(String.valueOf(R.string.meals), meals)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}