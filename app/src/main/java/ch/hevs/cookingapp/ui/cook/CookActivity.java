package ch.hevs.cookingapp.ui.cook;


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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.ui.BaseActivity;
import ch.hevs.cookingapp.ui.MainActivity;
import ch.hevs.cookingapp.ui.recipe.RecipeDetailActivity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;
import ch.hevs.cookingapp.viewmodel.cook.CookViewModel;

// Fenêtre qui aura 2 modes : Vue simple et editable
public class CookActivity extends BaseActivity {

    // Constantes pour l'ordre dans la toolbar
    private static final int EDIT_COOK = 1;       // C'est l'ID du menu. La toolbar sera modifié
    private static final int DELETE_COOK = 2;

    private static String userConnected;
    private Toast toast;

    private boolean isEditable;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPwd1;
    private EditText etPwd2;
    private ImageButton imageCook;
    private byte[] bytes;

    private CookViewModel viewModel_Cook;

    private CookEntity cook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_activity_myCook));
        navigationView.setCheckedItem(position);
        getLayoutInflater().inflate(R.layout.activity_cook, frameLayout);

        initiateView();

        // Vérifier quel user il faut afficher
        Intent intent = getIntent();
        // On aura la même KEY des 2 sources intent: Depuis liste ou Menu
        String intent_selectedUserSource = intent.getStringExtra(String.valueOf(R.string.selectedCook));

        // Afficher mon profile connecté
        if(intent_selectedUserSource.equals(String.valueOf(R.string.clickSourceMyProfile)))
        {
            SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
            userConnected = settings.getString(PREFS_USER, null);
        }
        // Si un user est selectionné
        else
        {
            userConnected = intent_selectedUserSource; // get email of cook
        }



        CookViewModel.Factory factory = new CookViewModel.Factory(getApplication(), userConnected);

        // Créer un viewModel
        viewModel_Cook = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) factory).get(CookViewModel.class); // Donne nous un ViewModel, grâce à la Factory, pour me donner un CookViewModel
        // Recuperer les données de la DB
        viewModel_Cook.getCook().observe(this, cookEntity ->
            {
                if (cookEntity != null) {
                    cook = cookEntity;
                    updateContent(); //Si il y a un changement en DB, on UPDATE
                }
            });
    }


    private void initiateView()
    {
        isEditable = false;
        etFirstName = findViewById(R.id.et_firstName);
        etLastName = findViewById(R.id.et_lastName);
        etEmail = findViewById(R.id.et_mail);
        etPhone = findViewById(R.id.et_phone);
        etPwd1 = findViewById(R.id.password);
        etPwd2 = findViewById(R.id.passwordRep);
        imageCook = findViewById(R.id.image_profilePicture);
    }

    private void updateContent()
    {
        if (cook != null) {
            etFirstName.setText(cook.getFirstName());
            etLastName.setText(cook.getLastName());
            etPhone.setText(cook.getPhoneNumber());
            etEmail.setText(cook.getEmail());
            etPhone.setText(cook.getPhoneNumber());
            imageCook.setClickable(false);
            imageCook.setFocusable(false);
            if(cook.getImage() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(cook.getImage(), 0, cook.getImage().length);
                imageCook.setImageBitmap(bitmap);
            }
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

        //  Si c'est bien moi-même qui visite mon profil, la toolbar va avoir le bouton EDIT & DELETE en plus
        // Si c'est un autre user qui vient voir mon profil, ces bouttons ne s'affichent pas.
        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settings.getString(PREFS_USER, null);

        if(user.equals(etEmail.getText().toString()) )
        {
            menu.add(0, EDIT_COOK, Menu.NONE, getString(R.string.action_edit))
                    .setIcon(R.drawable.ic_edit_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

            menu.add(0, DELETE_COOK, Menu.NONE, getString(R.string.action_delete))
                    .setIcon(R.drawable.ic_delete_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    // Quand on sélectionne les bouttons dans la TOOLBAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_COOK)
        {
            if (isEditable)
            {
                item.setIcon(R.drawable.ic_edit_white_24dp);
                switchEditableMode();
            }
            else    // Quand je passe de Classic à Edit
            {
                item.setIcon(R.drawable.ic_done_white_24dp);
                switchEditableMode();
            }
        }
        if (item.getItemId() == DELETE_COOK) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
                viewModel_Cook.deleteCook(cook, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        logout();
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

    private void switchEditableMode() {
        // EDIT
        if (!isEditable)
        {
            LinearLayout linearLayout = findViewById(R.id.layout_passwordLayout);
            linearLayout.setVisibility(View.VISIBLE);
            etFirstName.setFocusable(true);
            etFirstName.setEnabled(true);
            etFirstName.setFocusableInTouchMode(true);

            etLastName.setFocusable(true);
            etLastName.setEnabled(true);
            etLastName.setFocusableInTouchMode(true);

            etEmail.setFocusable(true);
            etEmail.setEnabled(true);
            etEmail.setFocusableInTouchMode(true);
            etEmail.requestFocus();             // Affiche le clavier

            etPhone.setFocusable(true);
            etPhone.setEnabled(true);
            etPhone.setFocusableInTouchMode(true);

            imageCook.setClickable(true);
            imageCook.setFocusable(true);
        }
        // Vue en mode CALSSIC
        else
        {
            // Recupere les entrées du EDIT, et les sauvegarde si possible.
            saveChanges(
                    etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    etEmail.getText().toString(),
                    etPhone.getText().toString(),
                    etPwd1.getText().toString(),
                    etPwd2.getText().toString(),
                    bytes
            );

            LinearLayout linearLayout_Password = findViewById(R.id.layout_passwordLayout);
            linearLayout_Password.setVisibility(View.GONE);
            etFirstName.setFocusable(false);
            etFirstName.setEnabled(false);

            etLastName.setFocusable(false);
            etLastName.setEnabled(false);

            etEmail.setFocusable(false);
            etEmail.setEnabled(false);

            etPhone.setFocusable(false);
            etPhone.setEnabled(false);

            imageCook.setClickable(false);
            imageCook.setFocusable(false);
        }
        // Toggle pour dire que on va changer de vue
        isEditable = !isEditable;
    }


    // En mode EDIT, recupere les données entrées
    private void saveChanges(String firstName, String lastName, String email, String phone  , String pwd, String pwd2, byte[] bytes) {
        // Vérification des inputs
        if (!pwd.equals(pwd2) || pwd.length() < 5) {
            toast = Toast.makeText(this, getString(R.string.error_edit_invalid_password), Toast.LENGTH_LONG);
            toast.show();
            etPwd1.setText("");
            etPwd2.setText("");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {            // c'est un REGEX de si on a bien un mail ou non
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            Toast.makeText(this,R.string.error_invalid_email_format,Toast.LENGTH_LONG).show();
            return;
        }
        // Verification que le phone number est bon
        // Phone Regex Pattern : upto length 10 - 13 and including character "+" infront.
        Pattern regex = Pattern.compile("^[+]?[0-9]{10,13}$");
        Matcher phonePattern = regex.matcher(phone);
        if (!phonePattern.find())
        {
            etPhone.setError(getString(R.string.error_invalid_phone));
            etPhone.requestFocus();
            Toast.makeText(this,R.string.error_invalid_phone_format,Toast.LENGTH_LONG).show();
            return;
        }

        // On recupère les paramètres pour mettre avec SET à notre entité
        cook.setEmail(email);
        cook.setFirstName(firstName);
        cook.setLastName(lastName);
        cook.setPhoneNumber(phone);
        cook.setPassword(pwd);
        if(bytes != null) {
            cook.setImage(bytes);
        }

        viewModel_Cook.updateCook(cook, new OnAsyncEventListener()
        {
            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }
        });

        return;
    }

    private void setResponse(Boolean response) {
        if (response) {
            updateContent();
            toast = Toast.makeText(this, getString(R.string.cook_updated), Toast.LENGTH_LONG);
            toast.show();
        } else {
            etEmail.setError(getString(R.string.error_used_email));
            etEmail.requestFocus();
        }
    }

    public void onProfileEdit(View view) {
        if (ContextCompat.checkSelfPermission(CookActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            // when permission is nor granted
            // request permission
            ActivityCompat.requestPermissions(CookActivity.this
                    , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
        else
        {
            // clear previous data
            imageCook.setImageBitmap(null);
            // Initialize intent
            Intent intent = new Intent(Intent.ACTION_PICK);
            // set type
            intent.setType("image/*");
            // start activity result
            startActivityForResult(Intent.createChooser(intent,"Select Image"),100);
        }
    }

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
                imageCook.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String prefs_UserConnected = settings.getString(PREFS_USER, null);

        // Mon user est connecté
        if ( userConnected.equals(prefs_UserConnected) )
        {
            startActivity(new Intent(this, MainActivity.class));
        }
        else // La vue est d'un autre user
        {
            startActivity(new Intent(this, CooksActivity.class)
                              .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }

    }
}