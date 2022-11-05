package ch.hevs.cookingapp.ui.mgmt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.database.async.cook.CreateCook;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.ui.BaseActivity;
import ch.hevs.cookingapp.ui.MainActivity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

public class RegisterActivity extends AppCompatActivity
{
    private static final String TAG = "RegisterActivity";

    private Toast toast;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
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
        etPwd1      = findViewById(R.id.password);
        etPwd2      = findViewById(R.id.passwordRep);

        Button saveBtn = findViewById(R.id.editButton);

        // Ne fait rien tant que on ne crée pas de user
        saveBtn.setOnClickListener(view -> saveChanges(
                // On récupère le texte du user
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etEmail.getText().toString(),
                etPwd1.getText().toString(),
                etPwd2.getText().toString()
        ));
    }

    private void saveChanges(String firstName, String lastName, String email, String pwd, String pwd2) {
        if (!pwd.equals(pwd2) || pwd.length() < 5) {
            etPwd1.setError(getString(R.string.error_invalid_password));
            etPwd1.requestFocus();
            etPwd1.setText("");
            etPwd2.setText("");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return;
        }

        CookEntity newCook = new CookEntity(email,firstName,lastName, pwd, "PHONE_NUMBER"); //TODO : Ajouter un champ PHONE NUMBER en graphique

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

    // TODO : Que fait ette methode ?
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