package com.diegomalone.bakingapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Diego Malone on 21/01/18.
 */

public class ToastUtils {

    private static Toast toast;

    public static void showToast(Context context, String message) {
        Toast newToast = Toast.makeText(context, message, Toast.LENGTH_LONG);

        if (toast != null) {
            toast.cancel();
        }

        toast = newToast;
        toast.show();
    }
}
