package com.proatcoding.pets.activity.payment.WeAcceptSDK;

class StringEditor {
    public static String insertPeriodically(String text, String insert, int period)
    {
        StringBuilder builder = new StringBuilder(
                text.length() + insert.length() * (text.length()/period)+1);
        int index = 0;
        String prefix = "";
        while (index < text.length()) {
            // Don't put the insert in the very first iteration.
            // This is easier than appending it *after* each substring
            builder.append(prefix);
            prefix = insert;
            builder.append(text.substring(index,
                    Math.min(index + period, text.length())));
            index += period;
        }
        return builder.toString();
    }

    /**
     it returns a 2-digit month string
     */
    public static String monthString(int month){
        if (month < 1 || month > 12 )
            return null;
        else if (month < 10)
            return ("0"+ month);
        else
            return (Integer.toString(month));
    }
    /**
     it returns a 2-digit year string
     */
    public static String yearString(int year) {
        return Integer.toString(year).substring(2, 4);
    }

}
