package ch.hevs.cookingapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ch.hevs.cookingapp.R;

// Page principale de l'application, Activité classique comme les autres
public class MainActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        setTitle(getString(R.string.app_name));
        navigationView.setCheckedItem(R.id.nav_none);
    }
}