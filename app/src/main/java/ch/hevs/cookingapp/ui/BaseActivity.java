package ch.hevs.cookingapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.ui.cook.CookActivity;
import ch.hevs.cookingapp.ui.mgmt.LoginActivity;
import ch.hevs.cookingapp.ui.mgmt.SettingsActivity;
import ch.hevs.cookingapp.ui.recipe.RecipesActivity;

// CE QUI EST EN COMMUN POUR TOUTES LES ACTIVITES : MENU / LAYOUT / BOUTONS / INTERRACTIONS
// C'est la fênetre FRAGMENT qui s'ouvre sur le coté gacuhe
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    /**
     * On aura accès à ces 2 variables, partout dans le code,
     * c'est comme un singleton en hashmap disponible pour accèder à ces informations
     * sans avir besoin de le mettre dans un intent à chaque fois
     */
    public static final String PREFS_NAME = "SharedPrefs";      // Nom de la HashMap
    public static final String PREFS_USER = "LoggedIn";         // Nom de la clé de la hashmap, on ajoutera une valeur au LogIn

    /**
     * Frame layout: Which is going to be used as parent layout for child activity layout.
     * This layout is protected so that child activity can access this
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
        // Make sure the toolbar exists in the activity and is not null
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = findViewById(R.id.flContent);

        drawerLayout = findViewById(R.id.base_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.base_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        BaseActivity.position = 0;
        super.onBackPressed();
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.action_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == BaseActivity.position)
        {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        BaseActivity.position = id;
        Intent intent = null;

        navigationView.setCheckedItem(id);

        if (id == R.id.nav_myProfile)
        {
            intent = new Intent(this, CookActivity.class);
        }
        else if (id == R.id.nav_myRecipe)
        {
            intent = new Intent(this, RecipesActivity.class);
        }
        else if (id == R.id.nav_home)
        {
            intent = new Intent(this, MainActivity.class);
        }
        else if (id == R.id.nav_logout)
        {
            logout();
        }
        if (intent != null)
        {
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NO_ANIMATION
            );
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START); // Fermer le drawer au changement d'activité
        return true;
    }

    public void logout()
    {
        SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
        editor.remove(BaseActivity.PREFS_USER);
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}