package ua.kiev.semtur.passwordgenerator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by SemTur on 14.09.2017.
 */

public class RateAppDialog extends DialogFragment {
    static final long sDelayedReminderTime = 1000 * 3600 * 24 * 7; // Delayed reminder time is seven days (in milliseconds)

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_rate_app, null, false);

        TextView textGetProVersion = (TextView) view.findViewById(R.id.text_get_pro_version);
        textGetProVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + getActivity().getPackageName()));
                startActivity(intent);
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.please_rate)
                .setView(view)
                .setPositiveButton(R.string.rate_it_now, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PasswordGeneratorPreferences.setRateAppReminderDate(getActivity(), -1);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=" + getActivity().getPackageName()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.remind_me_later, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        long  reminderDate = System.currentTimeMillis() + sDelayedReminderTime;
                        PasswordGeneratorPreferences.setRateAppReminderDate(getActivity(), reminderDate);
                    }
                })
                .setNeutralButton(R.string.never_remind, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PasswordGeneratorPreferences.setRateAppReminderDate(getActivity(), -1);
                        dismiss();
                    }
                })
                .create();
        return dialog;
    }
}
