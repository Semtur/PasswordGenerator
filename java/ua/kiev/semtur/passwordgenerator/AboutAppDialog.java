package ua.kiev.semtur.passwordgenerator;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by SemTur on 09.09.2017.
 */

public class AboutAppDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_about_app, null, false);

        TextView textRateApp = (TextView) view.findViewById(R.id.text_rate_app);
        textRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + getActivity().getPackageName()));
                startActivity(intent);
            }
        });

        TextView textGetProVersion = (TextView) view.findViewById(R.id.text_get_pro_version);
        textGetProVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=ua.kiev.semtur.passwordgeneratorpro"));
                startActivity(intent);
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.button_close, null)
                .create();
        return dialog;
    }
}
