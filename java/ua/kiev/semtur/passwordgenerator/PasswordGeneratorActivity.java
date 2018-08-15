package ua.kiev.semtur.passwordgenerator;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.ads.MobileAds;

public class PasswordGeneratorActivity extends SingleFragmentActivity implements PasswordGeneratorFragment.Callback {
    @Override
    protected Fragment createFragment() {
        return PasswordGeneratorFragment.newInstance();
    }

    @Override
    public void generatePassword(char[] initialCharSet, int passwordLength, boolean isDoNotRepeatChars, boolean isExcludeSimilarChars) {
        if (findViewById(R.id.password_fragment_container) == null) {
            Intent intent = PasswordActivity.newIntent(this, initialCharSet, passwordLength, isDoNotRepeatChars, isExcludeSimilarChars);
            startActivity(intent);
        } else {
            Fragment newPassword = PasswordFragment.newInstance(initialCharSet, passwordLength, isDoNotRepeatChars, isExcludeSimilarChars, true);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.password_fragment_container, newPassword).commit();
        }
    }

    @Override
    public void showSettings() {
        if (findViewById(R.id.password_fragment_container) == null) {
            PasswordGeneratorMenu.showSetting(this, false);
        } else {
            PasswordGeneratorMenu.showSetting(this, true);
        }
    }
}
