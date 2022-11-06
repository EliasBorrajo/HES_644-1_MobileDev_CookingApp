package ch.hevs.cookingapp.ui.cook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.ui.BaseActivity;

public class CookEditActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_cook, frameLayout);

        setTitle(getString(R.string.title_activity_myCook));
    }
}