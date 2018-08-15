package ua.kiev.semtur.passwordgenerator;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Locale;

/**
 * Created by SemTur on 28.04.2017.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private AdView mAdView;

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int appTheme = PasswordGeneratorPreferences.getAppTheme(this);
        setTheme(appTheme);
        String language = PasswordGeneratorPreferences.getAppLanguage(this);
        if (!language.isEmpty()) {
            Resources resources = getResources();
            android.content.res.Configuration conf = resources.getConfiguration();
            Locale locale;
            if (language.equals("default")) {
                locale = Locale.getDefault();
            } else {
                locale = new Locale(language);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                conf.setLocale(locale);
            } else {
                conf.locale = locale;
            }
            resources.updateConfiguration(conf, null);
            setTitle(R.string.app_name);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7383970161935002~1380337692");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.password_generator_fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.password_generator_fragment_container, fragment)
                    .commit();
        }
    }
}
