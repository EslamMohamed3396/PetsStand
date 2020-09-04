package com.proatcoding.pets.activity.payment.WeAcceptSDK;

import java.util.Date;
import java.util.Locale;


class FormChecker {
    static Boolean checkCVV(String cvvString) {
        return (cvvString != null && cvvString.length() == 3);
    }
    static Boolean checkCardName(String nameString) {
        return (nameString != null && nameString.length() != 0);
    }
    static Boolean checkCardNumber(String numberString) {
        return (numberString != null && numberString.length() == 16);
    }
    static Boolean checkDate(String monthString, String yearString) {
        if (monthString == null || monthString.length() != 2 || yearString == null || yearString.length() != 2) {
            return false;
        } else {
            Integer yearDiff = Integer.parseInt(yearString) - Integer.parseInt(new java.text.SimpleDateFormat("yy", Locale.GERMANY).format(new Date()));
            Integer monthDiff = Integer.parseInt(monthString) - Integer.parseInt(new java.text.SimpleDateFormat("MM", Locale.GERMANY).format(new Date()));
            return (yearDiff > 0) || (yearDiff == 0 && monthDiff >= 0);
        }
    }
}

