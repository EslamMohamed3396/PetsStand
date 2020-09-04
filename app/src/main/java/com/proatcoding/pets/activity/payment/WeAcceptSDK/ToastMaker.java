package com.proatcoding.pets.activity.payment.WeAcceptSDK;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastMaker {
    public static void displayLongToast(Activity activity, String msg) {
        displayToast(activity.getApplicationContext(), msg, Toast.LENGTH_LONG);
    }
    public static void displayShortToast(Activity activity, String msg) {
        displayToast(activity.getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }
    private static void displayToast(Context c, String msg, int length) {
        Toast.makeText(c, msg, length).show();
    }
}
