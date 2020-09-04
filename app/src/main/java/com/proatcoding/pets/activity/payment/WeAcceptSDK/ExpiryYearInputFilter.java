package com.proatcoding.pets.activity.payment.WeAcceptSDK;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.Date;
import java.util.Locale;

class ExpiryYearInputFilter implements InputFilter {
    private final String currentYearLastTwoDigits;

    public ExpiryYearInputFilter() {
        currentYearLastTwoDigits  = new java.text.SimpleDateFormat("yy", Locale.GERMANY).format(new Date());
    }

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
            final char currYearFirstChar = currentYearLastTwoDigits.charAt(0);
            if (inputChar < currYearFirstChar)
                return "";
            else
                return source;
        }
        else if (dstart == 1){
            final String inputYear = "" + dest.charAt(dest.length() - 1) + source.toString();
            if (inputYear.compareTo(currentYearLastTwoDigits) < 0)
                return "";
        }
        return source;
    }
}
