package ua.kiev.semtur.passwordgenerator;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SemTur on 01.09.2017.
 */

public class PasswordGeneratorFragment extends Fragment implements View.OnClickListener {
    private CheckBox mBoxUpperCase;
    private CheckBox mBoxLowerCase;
    private CheckBox mBoxNumbers;
    private CheckBox mBoxSpecialChars;
    private CheckBox mBoxDoNotRepeatChars;
    private CheckBox mBoxExcludeSimilarChars;

    private EditText mEditPasswordLength;
    private SeekBar mBarPasswordLength;

    private TextView mTextWarning;
    private TextView mTextUpperCase;
    private TextView mTextLowerCase;
    private TextView mTextNumbers;
    private TextView mTextSpecialChars;

    private Button mButtonGeneratePassword;

    private int mPasswordLength = 8;
    private int mPasswordLengthMax = 100;
    private Callback mCallback;

    public interface Callback {
        void generatePassword(char[] initialCharSet, int passwordLength, boolean doNotRepeatChars);

        void showSettings();
    }

    public static Fragment newInstance() {
        return new PasswordGeneratorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_generator, container, false);

        mBoxUpperCase = (CheckBox) view.findViewById(R.id.checkbox_upper_case);
        mBoxLowerCase = (CheckBox) view.findViewById(R.id.checkbox_lower_case);
        mBoxNumbers = (CheckBox) view.findViewById(R.id.checkbox_numbers);
        mBoxSpecialChars = (CheckBox) view.findViewById(R.id.checkbox_special_chars);
        mBoxDoNotRepeatChars = (CheckBox) view.findViewById(R.id.checkbox_do_not_repeat_chars);
        mBoxExcludeSimilarChars = (CheckBox) view.findViewById(R.id.checkBox_exclude_similar_chars);
        mBoxUpperCase.setOnClickListener(this);
        mBoxLowerCase.setOnClickListener(this);
        mBoxNumbers.setOnClickListener(this);
        mBoxSpecialChars.setOnClickListener(this);
        mBoxDoNotRepeatChars.setOnClickListener(this);

        mEditPasswordLength = (EditText) view.findViewById(R.id.edit_password_length);
        mEditPasswordLength.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mEditPasswordLength.getText().toString().equals("")) {
                    mPasswordLength = 0;
                    mBarPasswordLength.setProgress(mPasswordLength);
                    return;
                }
                mPasswordLength = Integer.parseInt(mEditPasswordLength.getText().toString());
                if (mPasswordLength < 1) {
                    mPasswordLength = 1;
                    mEditPasswordLength.setText(String.valueOf(mPasswordLength));
                }
                if (mPasswordLength > mPasswordLengthMax) {
                    mPasswordLength = mPasswordLengthMax;
                    mEditPasswordLength.setText(String.valueOf(mPasswordLength));
                }
                mBarPasswordLength.setProgress(mPasswordLength);
            }
        });

        mBarPasswordLength = (SeekBar) view.findViewById(R.id.seekBar_password_length);
        mBarPasswordLength.setMax(mPasswordLengthMax);
        mBarPasswordLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPasswordLength = seekBar.getProgress();
                mEditPasswordLength.setText(String.valueOf(mPasswordLength));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mTextWarning = (TextView) view.findViewById(R.id.text_warning);

        mTextUpperCase = (TextView) view.findViewById(R.id.text_upper_case);
        mTextLowerCase = (TextView) view.findViewById(R.id.text_lower_case);
        mTextNumbers = (TextView) view.findViewById(R.id.text_numbers);
        mTextSpecialChars = (TextView) view.findViewById(R.id.text_special_chars);

        mButtonGeneratePassword = (Button) view.findViewById(R.id.button_generate_password);
        mButtonGeneratePassword.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mBoxUpperCase.isChecked()) {
            mTextUpperCase.setVisibility(View.INVISIBLE);
        }
        if (!mBoxLowerCase.isChecked()) {
            mTextLowerCase.setVisibility(View.INVISIBLE);
        }
        if (!mBoxNumbers.isChecked()) {
            mTextNumbers.setVisibility(View.INVISIBLE);
        }
        if (mBoxSpecialChars.isChecked()) {
            mTextSpecialChars.setVisibility(View.VISIBLE);
        }
        if (mBoxDoNotRepeatChars.isChecked()) {
            mTextWarning.setText(getText(R.string.text_warning));
        }
        updateUI();
        showRateAppDialog();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                mCallback.showSettings();
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
            case R.id.checkbox_upper_case:
                if (mBoxUpperCase.isChecked()) {
                    mTextUpperCase.setVisibility(View.VISIBLE);
                } else {
                    mTextUpperCase.setVisibility(View.INVISIBLE);
                }
                updateUI();
                break;
            case R.id.checkbox_lower_case:
                if (mBoxLowerCase.isChecked()) {
                    mTextLowerCase.setVisibility(View.VISIBLE);
                } else {
                    mTextLowerCase.setVisibility(View.INVISIBLE);
                }
                updateUI();
                break;
            case R.id.checkbox_numbers:
                if (mBoxNumbers.isChecked()) {
                    mTextNumbers.setVisibility(View.VISIBLE);
                } else {
                    mTextNumbers.setVisibility(View.INVISIBLE);
                }
                updateUI();
                break;
            case R.id.checkbox_special_chars:
                if (mBoxSpecialChars.isChecked()) {
                    mTextSpecialChars.setVisibility(View.VISIBLE);
                } else {
                    mTextSpecialChars.setVisibility(View.INVISIBLE);
                }
                updateUI();
                break;
            case R.id.checkbox_do_not_repeat_chars:
                if (mBoxDoNotRepeatChars.isChecked()) {
                    mTextWarning.setText(getText(R.string.text_warning));
                } else {
                    mTextWarning.setText("");
                }
                updateUI();
                break;
            case R.id.button_generate_password:
                char[] initialCharSet = PasswordGenerator.getInitialCharSet(mBoxNumbers.isChecked(),
                        mBoxUpperCase.isChecked(), mBoxLowerCase.isChecked(), mBoxSpecialChars.isChecked());
                mCallback.generatePassword(initialCharSet, mPasswordLength, mBoxDoNotRepeatChars.isChecked());
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    private void updateUI() {
        char[] chars = PasswordGenerator.getInitialCharSet(mBoxNumbers.isChecked(),
                mBoxUpperCase.isChecked(), mBoxLowerCase.isChecked(), mBoxSpecialChars.isChecked());
        if (chars == null) {
            mEditPasswordLength.setEnabled(false);
            mBarPasswordLength.setEnabled(false);
            mButtonGeneratePassword.setEnabled(false);
            Toast.makeText(getActivity(), getResources().getText(R.string.toast_warning), Toast.LENGTH_SHORT).show();
        } else {
            mEditPasswordLength.setEnabled(true);
            mBarPasswordLength.setEnabled(true);
            mButtonGeneratePassword.setEnabled(true);

            if (mBoxDoNotRepeatChars.isChecked()) {
                mPasswordLengthMax = chars.length - 3 * chars.length / 10;
                mBarPasswordLength.setMax(mPasswordLengthMax);

                if (mPasswordLength > mPasswordLengthMax) {
                    mPasswordLength = mPasswordLengthMax;
                    mBarPasswordLength.setProgress(mPasswordLength);
                    mEditPasswordLength.setText(String.valueOf(mPasswordLength));
                }
            } else {
                mPasswordLengthMax = 100;
                mBarPasswordLength.setMax(mPasswordLengthMax);
            }
        }
    }

    private void showRateAppDialog() {
        long reminderDate = PasswordGeneratorPreferences.getRateAppReminderDate(getActivity());
        if (reminderDate == -1) {
            return;
        } else if (reminderDate == 0) {
            reminderDate = System.currentTimeMillis() + RateAppDialog.sDelayedReminderTime;
            PasswordGeneratorPreferences.setRateAppReminderDate(getActivity(), reminderDate);
            return;
        } else if (reminderDate <= System.currentTimeMillis()) {
            FragmentManager fm = getFragmentManager();
            RateAppDialog dialog = new RateAppDialog();
            dialog.show(fm, "RateAppDialog");
        }
    }
}
