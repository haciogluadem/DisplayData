package com.softtech.android.displaydata.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.softtech.android.displaydata.R;
import com.softtech.android.displaydata.interfaces.IGenericItemClicked;


public class AlertDialogUtils {

    public static void showAlertListWithoutButtons(final Activity activity, String Title, int defaultSelected, final Object[] ListItems, final IGenericItemClicked iGenericItemClicked) {
        if (activity == null || activity.isFinishing()) return;

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setTitle(Title);
        builderSingle.setSingleChoiceItems((CharSequence[]) ListItems, defaultSelected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (iGenericItemClicked != null)
                    iGenericItemClicked.onItemClick(ListItems[i], i, -1);
                dialogInterface.dismiss();
            }
        });

        String negativeText = activity.getString(R.string.back);
        builderSingle.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builderSingle.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}

