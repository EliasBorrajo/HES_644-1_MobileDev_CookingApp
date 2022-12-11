package ch.hevs.cookingapp.ui.mgmt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.database.repository.CookRepository;
import ch.hevs.cookingapp.ui.BaseActivity;
import ch.hevs.cookingapp.ui.MainActivity;

/**
 * Login activity.
 * You can either log in, Register, or reset data of the DB (only for demo purpose)
 */
public class LoginActivity extends AppCompatActivity
{

    private AutoCompleteTextView emailView;
    private EditText passwordView;

    private CookRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_login);

        setContentView(R.layout.activity_login);

        repository = ((BaseApp) getApplication()).getCookRepository();

        // Set up the login form.
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.password);

        Button emailSignInButton = findViewById(R.id.email_sign_in_button);
        emailSignInButton.setOnClickListener(view -> attemptLogin());

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(view -> startActivity(
                new Intent(LoginActivity.this, RegisterActivity.class))
        );

        Button demoDataButton = findViewById(R.id.demo_data_button);        // Only for demo purpose. Here the button is hide because we want to keep it for future improvement
        demoDataButton.setOnClickListener(view -> reinitializeDatabase());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
    }

    /**
     * Click listener method for the LOGIN
     */
    private void attemptLogin()
    {
        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password))
        {
            passwordView.setError(getString(R.string.error_invalid_password));
            passwordView.setText("");
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email))
        {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        }
        else if (!isEmailValid(email))
        {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else
        {
            repository.signIn(email, password, task ->
            {
                if (task.isSuccessful())
                {
                    /** TODO : Voir si on en a encore besoin des SharedPreferences
                     // We need an Editor object to make preference changes.
                     // All objects are from android.context.Context
                     SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();  // On vient editer notre HASHTABLE LOGIN
                     editor.putString(BaseActivity.PREFS_USER, cookEntity.getEmail());       // ON MET LA VALEUR A LA CLE
                     editor.apply();                                                         // On n'oublie pas d'appliquer les changements
                     */

                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(LoginActivity.this, "Authentication successful.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    emailView.setText("");
                    passwordView.setText("");
                }
                else
                {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                    passwordView.setError(getString(R.string.error_incorrect_password));
                    passwordView.requestFocus();
                    passwordView.setText("");
                }
            });
            /* TODO : Voir si ça sert encore
            repository.getCook(email).observe(LoginActivity.this, cookEntity ->
            {
                if (cookEntity != null)
                {
                    if (cookEntity.getPassword().equals(password))
                    {
                        // We need an Editor object to make preference changes.
                        // All objects are from android.context.Context
                        SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();  // On vient editer notre HASHTABLE LOGIN
                        editor.putString(BaseActivity.PREFS_USER, cookEntity.getEmail());       // ON MET LA VALEUR A LA CLE
                        editor.apply();                                                         // On n'oublie pas d'appliquer les changements

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        emailView.setText("");
                        passwordView.setText("");
                    }
                    else
                    {
                        passwordView.setError(getString(R.string.error_incorrect_password));
                        passwordView.requestFocus();
                        passwordView.setText("");
                    }
                }
                else
                {
                    emailView.setError(getString(R.string.error_invalid_email));
                    emailView.requestFocus();
                    passwordView.setText("");
                }
            });

             */
        }
    }

    /**
     * Verificaton with an API that the EMAIl is in a valid format
     *
     * @param email : email to verify
     * @return : true if it is valis
     */
    private boolean isEmailValid(String email)
    {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Check the password lenght
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password)
    {
        return password.length() > 4;
    }

    /**
     * Reinitialise DB for DEMO PURPOSE
     */
    private void reinitializeDatabase()
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.action_demo_data));
        alertDialog.setCancelable(false);
        alertDialog.setMessage(getString(R.string.reset_msg));
        // Syntaxe : setButton (param1, param2, param3-->Lambda)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_reset), (dialog, which) ->
                {
                    //initializeData(AppDatabase.getInstance(this)); // TODO Supprimer cette ligne
                    Toast.makeText(this, getString(R.string.demo_data_initiated), Toast.LENGTH_LONG).show();
                }
        );
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.show();
    }
}