package com.proatcoding.pets.activity.payment.WeAcceptSDK;

import android.text.InputFilter;
import android.text.Spanned;

class ExpiryMonthInputFilter implements InputFilter {
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        //if backspace, only delete last character
        if (source.equals(""))
            if (dstart + 1 < dest.length())
                return dest.subSequence(dstart, dend);
            else
                return "";

        //do not insert if length is already 2
        if (dest != null && dest.toString().length() >= 2)
            return "";
        //do not insert more than 1 character at a time
        if (source.length() > 1)
            return source;
        //only allow character to be inserted at the end of the current text
        if (dest != null && source.length() > 1 && dstart != dest.length())
            return "";
        if (source.equals(" "))
            return "";

        //At this point, `source` is a single character being inserted at `dstart`.
        //`dstart` is at the end of the current text.

        final char inputChar = source.charAt(0);

        if (dstart == 0) {
            //first month digit
            if (inputChar > '1')
                return ("0" + inputChar);
            else
                return source;
        }
        else if (dstart == 1) {
            //second month digit
            final char firstMonthChar = dest.charAt(0);
            if (firstMonthChar == '0' && inputChar == '0')
                return "";
            else if (firstMonthChar == '1' && inputChar > '2')
                return "";
        }
        return source;
    }
}
