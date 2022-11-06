package ch.hevs.cookingapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

import ch.hevs.cookingapp.R;

// CE QUI EST EN COMMUN POUR TOUTES LES ACTIVITES : MENU / LAYOUT / BOUTONS / INTERRACTIONS
// C'est la fênetre FRAGMENT qui s'ouvre sur le coté gacuhe
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";

    /**
     *  Frame layout: Which is going to be used as parent layout for child activity layout.
     *  This layout is protected so that child activity can access this
     */
    protected FrameLayout frameLayout;          //is designed to block out an area on the screen to display a single item

    protected DrawerLayout drawerLayout;        // acts as a top-level container for window content that allows for
                                                // interactive "drawer" views to be pulled out from one or both vertical edges of the window.

    protected NavigationView navigationView;    //Represents a standard navigation menu for application. The menu contents can be populated by a menu resource file.

    /**
     * Static variable for selected item position. Which can be used in child activity to know which item is selected from the list.
     */
    protected static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);// Display icon in the toolbar

        /*
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        */
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Boutton retour en arrière
        //getActionBar().setDisplayHomeAsUpEnabled(true);         // Boutton retour en arrière

        frameLayout = findViewById(R.id.flContent);

        drawerLayout = findViewById(R.id.base_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.base_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Menu icons are inflated
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        BaseActivity.position = id;
        Intent intent = null;

        navigationView.setCheckedItem(id);
/*
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