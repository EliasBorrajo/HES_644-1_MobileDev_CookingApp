package ch.hevs.cookingapp.ui;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

// CE QUI EST EN COMMUN POUR TOUTES LES ACTIVITES : MENU / LAYOUT / BOUTONS / INTERRACTIONS
// C'est la fênetre FRAGMENT qui s'ouvre sur le coté gacuhe
// Toutes les activités vont hériter de cetlle ci, pour avoir les choses en commun nécessaires.
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        return false;
    }
}
