package com.example.whereismybus;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Chargement {
    private Activity activity;
    private AlertDialog alert;

    Chargement(Activity myActivity) {
        activity = myActivity;
    }

    void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_loading, null));
        builder.setCancelable(false);

        alert = builder.create();
        alert.show();
    }

    void dismissDialog() {
        alert.dismiss();
    }
}
