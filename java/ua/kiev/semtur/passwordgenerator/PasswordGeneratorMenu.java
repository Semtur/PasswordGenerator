package ua.kiev.semtur.passwordgenerator;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by SemTur on 09.09.2017.
 */

public class PasswordGeneratorMenu {
    private static final String DIALOG_SETTINGS = " dialogSettings";
    private static final String DIALOG_ABOUT_APP = " dialogAboutApp";

    public static void showSetting(FragmentActivity activity, boolean isTablet) {
        if (isTablet) {
            FragmentManager fm = activity.getSupportFragmentManager();
            SettingsFragment fragment = SettingsFragment.newInstance(isTablet);
            fragment.show(fm, DIALOG_SETTINGS);
        } else {
            Intent intent = SettingsActivity.newIntent(activity);
            activity.startActivity(intent);
        }
    }

    public static void showAboutApp(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        AboutAppDialog dialog = new AboutAppDialog();
        dialog.show(fm, DIALOG_ABOUT_APP);
    }
}
