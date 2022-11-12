package ch.hevs.cookingapp.ui.cook;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.ui.BaseActivity;
import ch.hevs.cookingapp.viewmodel.cook.CookViewModel;

// Fenêtre qui aura 2 modes : Vue simple et editable
public class CookActivity extends BaseActivity {

    // Constantes pour l'ordre dans la toolbar
    private static final int EDIT_COOK = 1;       // C'est l'ID du menu. La toolbar sera modifié
    private static final int DELETE_COOK = 2;

    private Toast toast;

    private boolean isEditable;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPwd1;
    private EditText etPwd2;

    private CookViewModel viewModel;

    private CookEntity cook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_activity_myCook));
        navigationView.setCheckedItem(position);
        getLayoutInflater().inflate(R.layout.activity_cook, frameLayout);

        initiateView();

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settings.getString(PREFS_USER, null);

        CookViewModel.Factory factory = new CookViewModel.Factory(getApplication(), user);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) factory).get(CookViewModel.class); // Donne nous un ViewModel, grâce à la Factory, pour me donner un CookViewModel
        viewModel.getCook().observe(this, accountEntity ->
            {
                if (accountEntity != null) {
                    cook = accountEntity;
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
    }

    private void updateContent()
    {
        if (cook != null) {
            etFirstName.setText(cook.getFirstName());
            etLastName.setText(cook.getLastName());
            etEmail.setText(cook.getEmail());
            etPhone.setText(cook.getPhoneNumber());
        }
    }

    /*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        /*
        The activity has to be finished manually in order to guarantee the navigation hierarchy working.
        */
   /*     finish();
        return super.onNavigationItemSelected(item);
    }*/

    // On modifie la TOOLBAR en ajoutant 2 bouttons icones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, EDIT_COOK, Menu.NONE, getString(R.string.action_edit))
                .setIcon(R.drawable.ic_edit_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, DELETE_COOK, Menu.NONE, getString(R.string.action_delete))
                .setIcon(R.drawable.ic_delete_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
}