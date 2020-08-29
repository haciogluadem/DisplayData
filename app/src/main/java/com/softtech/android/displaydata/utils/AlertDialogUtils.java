package com.softtech.android.displaydata.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.softtech.android.displaydata.R;
import com.softtech.android.displaydata.interfaces.IGenericItemClicked;


public class AlertDialogUtils {

    public static void showAlertDialogOneButton(final Context activity, String Title, String Message,
                                                final Button.OnClickListener clickedListener) {
        if (activity == null) return;
        if (activity instanceof Activity && ((Activity) activity).isFinishing()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setCancelable(false);

        String positiveText = activity.getString(R.string.ok);

        if (clickedListener == null) {

            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
        } else {
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            clickedListener.onClick(new View(activity));
                        }
                    });
        }


        AlertDialog dialog = builder.create();
        dialog.show();
    }

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

