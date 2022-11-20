package ch.hevs.cookingapp.ui.mgmt;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.ui.BaseActivity;

/**
 * Settings are here to change the default Theme of the app
 */
public class SettingsActivity extends BaseActivity
{
    Switch themeSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Récuperer les élements à l'écran
        themeSwitch = findViewById(R.id.switch_Theme);

        setSwitchOnPreferenceTheme();
    }


    /**
     * Action liée au switch, permet de changer le thème de l'application
     * @param view : Switch de l'UI
     */
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

    /**
     * Permet de savoir si on doit enclencher le switch ou non
     * lorsque on arrive sur l'activité, selon les préferences du user.
     */
    private void setSwitchOnPreferenceTheme()
    {
        // On dit quelle thème on veut utiliser
        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_THEME, 0);
        String themeMode = settings.getString(PREFS_THEME, "1");
        int modeNight = Integer.parseInt(themeMode);


        switch (modeNight)
        {
            case 1: // Light THeme == 1
                themeSwitch.setChecked(false);
                break;

            case 2: // Dark Theme == 2
                themeSwitch.setChecked(true);
                break;
        }
    }
}