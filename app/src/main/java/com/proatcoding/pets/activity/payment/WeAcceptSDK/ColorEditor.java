package com.proatcoding.pets.activity.payment.WeAcceptSDK;

import android.content.res.ColorStateList;

import androidx.appcompat.widget.AppCompatCheckBox;

class ColorEditor {
    @SuppressWarnings("RestrictedApi")
    public static void setAppCompatCheckBoxColors(final AppCompatCheckBox _checkbox, final int _uncheckedColor, final int _checkedColor) {
        int[][] states = new int[][]{new int[]{-android.R.attr.state_checked}, new int[]{android.R.attr.state_checked}};
        int[] colors = new int[]{_uncheckedColor, _checkedColor};
        _checkbox.setSupportButtonTintList(new ColorStateList(states, colors));
    }
}
