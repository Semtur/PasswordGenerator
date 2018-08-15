package ua.kiev.semtur.passwordgenerator;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by SemTur on 08.09.2017.
 */

public class SettingsActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        setTitle(R.string.menu_settings);
        return SettingsFragment.newInstance(false);
    }
}
