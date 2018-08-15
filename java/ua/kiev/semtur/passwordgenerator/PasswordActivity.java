package ua.kiev.semtur.passwordgenerator;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by SemTur on 01.09.2017.
 */

public class PasswordActivity extends SingleFragmentActivity {
    private static final String EXTRA_INITIAL_CHAR_SET = "chars";
    private static final String EXTRA_PASSWORD_LENGTH = "passwordLength";
    private static final String EXTRA_DO_NOT_REPEAT_CHARS = "doNotRepeatChars";
    private static final String EXTRA_EXCLUDE_SIMILAR_CHARS = "excludeSimilarChars";

    public static Intent newIntent(Context context, char[] initialCharSet, int passwordLength, boolean isDoNotRepeatChars, boolean isExcludeSimilarChars) {
        Intent intent = new Intent(context, PasswordActivity.class);
        intent.putExtra(EXTRA_INITIAL_CHAR_SET, initialCharSet);
        intent.putExtra(EXTRA_PASSWORD_LENGTH, passwordLength);
        intent.putExtra(EXTRA_DO_NOT_REPEAT_CHARS, isDoNotRepeatChars);
        intent.putExtra(EXTRA_EXCLUDE_SIMILAR_CHARS, isExcludeSimilarChars);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        char[] initialCharSet = intent.getCharArrayExtra(EXTRA_INITIAL_CHAR_SET);
        int passwordLength =  intent.getIntExtra(EXTRA_PASSWORD_LENGTH, 0);
        boolean doNotRepeatChars = intent.getBooleanExtra(EXTRA_DO_NOT_REPEAT_CHARS, false);
        return PasswordFragment.newInstance(initialCharSet, passwordLength, doNotRepeatChars, false);
    }
}
