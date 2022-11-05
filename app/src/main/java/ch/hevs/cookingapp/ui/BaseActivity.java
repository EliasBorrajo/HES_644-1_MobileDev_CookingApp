package ch.hevs.cookingapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import ch.hevs.cookingapp.R;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
    /*    int id = item.getItemId();

        if (id == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        BaseActivity.position = id;
        Intent intent = null;

        navigationView.setCheckedItem(id);

        // fait le lien entre le menu et l'activité à ouvrir par après
        if (id == R.id.nav_client) {
            intent = new Intent(this, ClientActivity.class);
        } else if (id == R.id.nav_accounts) {
            intent = new Intent(this, AccountsActivity.class);
        } else if (id == R.id.nav_transaction) {
            intent = new Intent(this, TransactionActivity.class);
        } else if (id == R.id.nav_logout) {
            logout();
        }
        if (intent != null) {
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NO_ANIMATION
            );
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START); // Fermer le drawer au changement d'activité
        return true;
     */
        return false;
    }
}