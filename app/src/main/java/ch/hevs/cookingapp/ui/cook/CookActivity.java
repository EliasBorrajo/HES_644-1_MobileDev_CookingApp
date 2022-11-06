package ch.hevs.cookingapp.ui.cook;


import android.os.Bundle;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.ui.BaseActivity;

public class CookActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_cook, frameLayout);

        setTitle(getString(R.string.title_activity_myCook));
    }
}