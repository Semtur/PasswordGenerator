package ua.kiev.semtur.passwordgenerator;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by SemTur on 09.09.2017.
 */

public class PasswordGeneratorPreferences {
    private static final String PREF_APP_THEME = "appTheme";
    private static final String PREF_APP_LANGUAGE = "appLanguage";
    private static final String PREF_RATE_APP_REMINDER_DATE = "rateAppReminderDate";

    public static int getAppTheme(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_APP_THEME, R.style.AppTheme);
    }

    public static void setAppTheme(Context context, int appTheme) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_APP_THEME, appTheme)
                .apply();
    }

    public static String getAppLanguage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_APP_LANGUAGE, "");
    }

    public static void setPrefAppLanguage(Context context, String language) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_APP_LANGUAGE, language)
                .apply();
    }

    public static long getRateAppReminderDate(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(PREF_RATE_APP_REMINDER_DATE, 0);
    }

    public static void setRateAppReminderDate(Context context, long date) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putLong(PREF_RATE_APP_REMINDER_DATE, date)
                .apply();
    }

}
