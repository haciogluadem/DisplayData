package com.softtech.android.displaydata.custom_views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.softtech.android.displaydata.R;

public class CustomProgressDialog extends ProgressDialog {
    private Activity activity;
    private TextView messageTv;
    private String message;

    public CustomProgressDialog(Activity context) {
        super(context);
    }

    public CustomProgressDialog(Activity context, int theme) {
        super(context, theme);
    }

    public static CustomProgressDialog instance(Activity context) {
        CustomProgressDialog dialog = new CustomProgressDialog(context);
        dialog.activity = context;
        dialog.setIndeterminate(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_indicator);
        messageTv = findViewById(R.id.message_tv);
        messageTv.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(message)){
            messageTv.setText(message);
            messageTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setMessage(CharSequence message){
        this.message = (String) message;
    }

    public void setMessageTextColor(@ColorRes int colorId){
        messageTv.setTextColor(activity.getResources().getColor(colorId));
    }

    @Override
    public void show() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed())
                return;
        }

        if (activity.isFinishing())
            return;
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}

