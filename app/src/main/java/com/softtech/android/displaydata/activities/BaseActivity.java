package com.softtech.android.displaydata.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.softtech.android.displaydata.custom_views.CustomProgressDialog;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
    }

    public void setToolBarInfo(String title, String subTitle,
                           boolean backArrowActive) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(backArrowActive);

        if (!TextUtils.isEmpty(title))
            getSupportActionBar().setTitle(title);

        if (!TextUtils.isEmpty(subTitle))
            getSupportActionBar().setSubtitle(subTitle);
    }

    protected void getIntentData(){

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void showProgressLoading() {
        dialog = CustomProgressDialog.instance(this);
        dialog.show();
    }

    public void hideProgressLoading() {
        try {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            Log.d("OBASE", "hideProgressLoading : exp -> " + e.toString());
        }
    }

    protected void showToast(final String text) {
        final Activity activity = this;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
