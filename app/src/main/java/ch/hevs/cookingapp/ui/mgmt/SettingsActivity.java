package ch.hevs.cookingapp.ui.mgmt;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.ui.BaseActivity;

public class SettingsActivity extends BaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Récuperer les élements à l'écran
        Switch theme = findViewById(R.id.switch_Theme);

        //setSwitchOnPreferenceTheme(theme);

    }

    // Action du switch
    public void changeTheme(View view)
    {
        boolean checked = ((Switch) view).isChecked();

        if (checked)
        {
            // On dit quelle thème on veut utiliser
            int modeNightYes = AppCompatDelegate.MODE_NIGHT_YES;

            // On le sauvegarde dans les préferences
            SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_THEME, 0).edit();
            editor.remove(BaseActivity.PREFS_THEME);
            editor.putString(BaseActivity.PREFS_THEME, String.valueOf(modeNightYes));
            editor.apply();

            AppCompatDelegate.setDefaultNightMode(modeNightYes);       // On l'applique.

        }
        else
        {
            // On dit quelle thème on veut utiliser
            int modeNightNo = AppCompatDelegate.MODE_NIGHT_NO;

            // On le sauvegarde dans les préferences
            SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_THEME, 0).edit();
            editor.remove(BaseActivity.PREFS_THEME);
            editor.putString(BaseActivity.PREFS_THEME, String.valueOf(modeNightNo));
            editor.apply();

            AppCompatDelegate.setDefaultNightMode(modeNightNo);       // On l'applique.

        }


    }

    private void setSwitchOnPreferenceTheme(Switch themeSwitch)
    {
        // On dit quelle thème on veut utiliser
        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_THEME, 0);
        String themeMode = settings.getString(PREFS_THEME, "1");
        int modeNight = Integer.parseInt(themeMode);

        System.out.println("Set SWITCH CHECKED: "+ themeSwitch.isChecked());
        System.out.println("Set SWITCH THEME: "+themeMode);

        switch (modeNight)
        {
            case 1:     // Light THeme == 1
                if (!themeSwitch.isChecked())
                    themeSwitch.setChecked(false);
                else
                    themeSwitch.setChecked(true);
                break;
            case 2:     // Dark Theme == 2
                if (!themeSwitch.isChecked())
                    themeSwitch.setChecked(false);
                else
                    themeSwitch.setChecked(true);
                break;
            default:
                System.out.println("DEFAULT ELIAS ERROR");
                break;
        }


    }
}