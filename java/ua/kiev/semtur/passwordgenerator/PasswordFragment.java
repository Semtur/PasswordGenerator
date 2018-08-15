package ua.kiev.semtur.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SemTur on 01.09.2017.
 */

public class PasswordFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_INITIAL_CHAR_SET = "chars";
    private static final String ARG_PASSWORD_LENGTH = "passwordLength";
    private static final String ARG_DO_NOT_REPEAT_CHARS = "doNotRepeatChars";
    private static final String ARG_EXCLUDE_SIMILAR_CHARS = "excludeSimilarChars";
    private static final String ARG_IS_TABLET = "isTablet";

    private EditText mEditPassword;
    private Button mButtonCopyPasswordToClipboard;
    private Button mButtonGeneratePassword;
    private TextView mTextPasswordQuality;
    private PasswordStrengthBar mBarPasswordStrength;

    public static Fragment newInstance(char[] initialCharSet, int passwordLength, boolean isDoNotRepeatChars, boolean isExcludeSimilarChars, boolean isTablet) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        args.putCharArray(ARG_INITIAL_CHAR_SET, initialCharSet);
        args.putInt(ARG_PASSWORD_LENGTH, passwordLength);
        args.putBoolean(ARG_DO_NOT_REPEAT_CHARS, isDoNotRepeatChars);
        args.putBoolean(ARG_EXCLUDE_SIMILAR_CHARS, isExcludeSimilarChars);
        args.putBoolean(ARG_IS_TABLET, isTablet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getArguments().getBoolean(ARG_IS_TABLET, false)) {
            setHasOptionsMenu(true);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);

        mTextPasswordQuality = (TextView) view.findViewById(R.id.text_password_quality);

        mBarPasswordStrength = (PasswordStrengthBar) view.findViewById(R.id.bar_password_strength);
        mBarPasswordStrength.setValueUnit(getString(R.string.password_strength_bits));

        mEditPassword = (EditText) view.findViewById(R.id.edit_password);
        mEditPassword.setText(generatePassword());
        mEditPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                showPasswordQuality(mEditPassword.length());
            }
        });

        mButtonCopyPasswordToClipboard = (Button) view.findViewById(R.id.button_copy_password_to_clipboard);
        mButtonCopyPasswordToClipboard.setOnClickListener(this);

        mButtonGeneratePassword = (Button) view.findViewById(R.id.button_generate_password);
        mButtonGeneratePassword.setOnClickListener(this);
        boolean isTablet = getArguments().getBoolean(ARG_IS_TABLET, false);
        if (isTablet) {
            mButtonGeneratePassword.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                boolean isTablet = getArguments().getBoolean(ARG_IS_TABLET, false);
                PasswordGeneratorMenu.showSetting(getActivity(), isTablet);
                return true;
            case R.id.menu_item_about_app:
                PasswordGeneratorMenu.showAboutApp(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_copy_password_to_clipboard:
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", mEditPassword.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), getResources().getText(R.string.toast_copy_password_to_clipboard), Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_generate_password:
                mEditPassword.setText(generatePassword());
                break;
        }
    }

    private String generatePassword() {
        Bundle args = getArguments();
        char[] initialCharSet = args.getCharArray(ARG_INITIAL_CHAR_SET);
        int passwordLength = args.getInt(ARG_PASSWORD_LENGTH, 0);
        boolean isDoNotRepeatChars = args.getBoolean(ARG_DO_NOT_REPEAT_CHARS, false);
        boolean isExcludeSimilarChars = args.getBoolean(ARG_EXCLUDE_SIMILAR_CHARS, false);
        showPasswordQuality(passwordLength);
        return PasswordGenerator.generatePassword(initialCharSet, passwordLength, isDoNotRepeatChars, isExcludeSimilarChars);
    }

    private void showPasswordQuality(int passwordLength) {
        Bundle args = getArguments();
        char[] initialCharSet = args.getCharArray(ARG_INITIAL_CHAR_SET);
        int passwordStrength = PasswordGenerator.getPasswordStrength(initialCharSet.length, passwordLength);
        if (passwordStrength == 0) {
            mTextPasswordQuality.setText(getText(R.string.password_empty));
            mTextPasswordQuality.setTextColor(Color.BLACK);
        } else if (passwordStrength >= 1 && passwordStrength < 56) {
            mTextPasswordQuality.setText(getText(R.string.password_weak));
            mTextPasswordQuality.setTextColor(getResources().getColor(R.color.colorPasswordWeak));
        } else if (passwordStrength >= 56 && passwordStrength < 64) {
            mTextPasswordQuality.setText(getText(R.string.text_password_medium));
            mTextPasswordQuality.setTextColor(getResources().getColor(R.color.colorPasswordMedium));
        } else if (passwordStrength >= 64 && passwordStrength < 128) {
            mTextPasswordQuality.setText(getText(R.string.text_password_strong));
            mTextPasswordQuality.setTextColor(getResources().getColor(R.color.colorPasswordStrong));
        } else if (passwordStrength >= 128) {
            mTextPasswordQuality.setText(getText(R.string.text_password_best));
            mTextPasswordQuality.setTextColor(getResources().getColor(R.color.colorPasswordBest));
        }

        if (passwordStrength > mBarPasswordStrength.getMaxValue()) {
            mBarPasswordStrength.setMaxValue(passwordStrength);
        }
        mBarPasswordStrength.setValue(passwordStrength);
    }


}
