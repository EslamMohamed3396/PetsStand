package com.proatcoding.pets.activity.payment.WeAcceptSDK.helper;

import android.view.View;
import android.widget.EditText;

public class MoneyClickListener implements View.OnClickListener {
    EditText editText;

    public MoneyClickListener(EditText e) {
        editText = e;
    }
    @Override
    public void onClick(View v) {
        editText.setSelection(editText.getText().length());
    }
}
