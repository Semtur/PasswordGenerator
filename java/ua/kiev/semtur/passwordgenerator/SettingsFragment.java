package ua.kiev.semtur.passwordgenerator;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SemTur on 09.09.2017.
 */

public class SettingsFragment extends DialogFragment implements View.OnClickListener {
    private static final String ARG_IS_TABLET = "isTablet";

    private RadioButton mRadioButtonThemeLight;
    private RadioButton mRadioButtonThemeDark;
    private Spinner mSpinnerAppLanguage;
    private Button mButtonOptionsSave;
    private Button mButtonOptionsClose;
    private TextView mTextOptions;

    private boolean mIsTablet;

    public static SettingsFragment newInstance(boolean isTablet) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_TABLET, isTablet);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsTablet = getArguments().getBoolean(ARG_IS_TABLET, false);
        if (!mIsTablet) {
            setHasOptionsMenu(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mRadioButtonThemeLight = (RadioButton) view.findViewById(R.id.radioButton_theme_light);
        mRadioButtonThemeDark = (RadioButton) view.findViewById(R.id.radioButton_theme_dark);
        mSpinnerAppLanguage = (Spinner) view.findViewById(R.id.spinner_language);
        mButtonOptionsSave = (Button) view.findViewById(R.id.button_options_save);
        mButtonOptionsClose = (Button) view.findViewById(R.id.button_options_close);
        mTextOptions = (TextView) view.findViewById(R.id.text_options);

        if (mIsTablet) {
            mTextOptions.setVisibility(View.VISIBLE);
        }

        mButtonOptionsSave.setOnClickListener(this);
        mButtonOptionsClose.setOnClickListener(this);


        int appTheme = PasswordGeneratorPreferences.getAppTheme(getActivity());
        if (appTheme == R.style.AppTheme) {
            mRadioButtonThemeLight.setChecked(true);
        } else if (appTheme == R.style.AppThemeDark) {
            mRadioButtonThemeDark.setChecked(true);
        }

        String appLanguage = PasswordGeneratorPreferences.getAppLanguage(getActivity());
        switch (appLanguage) {
            case "":
            case "default":
                mSpinnerAppLanguage.setSelection(0);
                break;
            case "en":
                mSpinnerAppLanguage.setSelection(1);
                break;
            case "ru":
                mSpinnerAppLanguage.setSelection(2);
                break;
            case "uk":
                mSpinnerAppLanguage.setSelection(3);
                break;
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
            case R.id.button_options_save:
                if (mRadioButtonThemeLight.isChecked()) {
                    PasswordGeneratorPreferences.setAppTheme(getActivity(), R.style.AppTheme);
                } else {
                    PasswordGeneratorPreferences.setAppTheme(getActivity(), R.style.AppThemeDark);
                }
                switch (mSpinnerAppLanguage.getSelectedItemPosition()) {
                    case 0:
                        PasswordGeneratorPreferences.setPrefAppLanguage(getActivity(), "default");
                        break;
                    case 1:
                        PasswordGeneratorPreferences.setPrefAppLanguage(getActivity(), "en");
                        break;
                    case 2:
                        PasswordGeneratorPreferences.setPrefAppLanguage(getActivity(), "ru");
                        break;
                    case 3:
                        PasswordGeneratorPreferences.setPrefAppLanguage(getActivity(), "uk");
                        break;
                }
                Toast.makeText(getActivity(), getText(R.string.toast_options_save),Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_options_close:
                if (mIsTablet) {
                    dismiss();
                } else {
                    getActivity().finish();
                }
        }
    }
}
