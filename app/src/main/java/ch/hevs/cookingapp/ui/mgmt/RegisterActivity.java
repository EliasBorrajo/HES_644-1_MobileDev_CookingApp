package ch.hevs.cookingapp.ui.mgmt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.database.async.cook.CreateCook;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.ui.BaseActivity;
import ch.hevs.cookingapp.ui.MainActivity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

/**
 *  Register is the page to create a cook / user
 */
public class RegisterActivity extends AppCompatActivity
{
    private static final String TAG = "RegisterActivity";

    private Toast toast;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPwd1;
    private EditText etPwd2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeForm();
        toast = Toast.makeText(this, getString(R.string.cook_created), Toast.LENGTH_LONG);
    }

    private void initializeForm()
    {
        etFirstName = findViewById(R.id.firstName);
        etLastName  = findViewById(R.id.lastName);
        etEmail     = findViewById(R.id.email);
        etPhone     = findViewById(R.id.phoneNumber);
        etPwd1      = findViewById(R.id.password);
        etPwd2      = findViewById(R.id.passwordRep);

        Button saveBtn = findViewById(R.id.editButton);

        // Ne fait rien tant que on ne crée pas de user
        saveBtn.setOnClickListener(view -> saveChanges(
                // On récupère le texte du user
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etEmail.getText().toString(),
                etPhone.getText().toString(),
                etPwd1.getText().toString(),
                etPwd2.getText().toString()
        ));
    }

    /**
     *  Methode apellé lorsque on change du mode edit à show.
     *  Lorsque on switch de mode, on veut sauvegarder les data dans la DB.
     *  On commence par vérifier les paramètres entrées,
     *  puis si ils sont bons on les SET
     *
     *  @param : Seront les paramètres du UI que on get leur valeurs
     */
    private void saveChanges(String firstName, String lastName, String email, String phone, String pwd, String pwd2) {
        if (!pwd.equals(pwd2) || pwd.length() < 5) {
            etPwd1.setError(getString(R.string.error_invalid_password));
            etPwd1.requestFocus();
            etPwd1.setText("");
            etPwd2.setText("");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
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
            return;
        }

        CookEntity newCook = new CookEntity(email,firstName,lastName, pwd, phone, null);

        // On apelle Async --> CookDao --> Insert (cook)
        new CreateCook(getApplication(), new OnAsyncEventListener()
        {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createUserWithEmail: success");
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createUserWithEmail: failure", e);
                setResponse(false);
            }
        }).execute(newCook);
    }

    // En fonction de si on arrive à enregistrer le cook dans la ROOM, on peut ouvrir une nouvelle activité, ou rester sur la même fenêtre.
    private void setResponse(Boolean response)
    {   // Si on arrive a créer un Cook dans la ROOM
        if (response)
        {
            final SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
            editor.putString(BaseActivity.PREFS_USER, etEmail.getText().toString());
            editor.apply();
            toast.show();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class); // Une fois que on a fini de s'enregistrer sur le site, on est envoyé sur la fenêtre principale de l'app
            startActivity(intent);
        }
        // Si on n'arrive pas à acceder à la ROOM avec le nouveau cook
        else {
            etEmail.setError(getString(R.string.error_used_email));
            etEmail.requestFocus();
        }
    }
}