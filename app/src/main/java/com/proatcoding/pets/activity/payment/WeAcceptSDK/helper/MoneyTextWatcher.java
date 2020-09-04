package com.proatcoding.pets.activity.payment.WeAcceptSDK.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;


public class MoneyTextWatcher implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;

    public MoneyTextWatcher(EditText mEditText) {
        editTextWeakReference = new WeakReference<EditText>(mEditText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        EditText editTex = editTextWeakReference.get();
        if (!s.toString().equals(editTex.getText())) {
            editTex.removeTextChangedListener(this);
            String cleanString = s.toString().replaceAll("[$,.]", "");
            double parsed = Double.parseDouble(cleanString.replaceAll("[^\\d]", ""));
            String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));
            formatted =
            formatted = formatted.replaceAll("$", "");
            formatted = formatted.substring(1);
            if (formatted.indexOf(".") == formatted.length() - 2) {
                formatted = formatted + "0";
            }
            editTex.setText(formatted);
            editTex.setSelection(formatted.length());
            editTex.addTextChangedListener(this);

        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}