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
        setContentView(R.layout.activity_main);
    }
}