package com.diegomalone.bakingapp.utils;

import android.content.Context;

import java.text.DecimalFormat;

/**
 * Created by malone on 12/02/18.
 */

public class QuantityUtils {

    public static String getIngredientQuantity(float quantity) {
        String suffix = "";

        float fractionalPart = getFractionalPart(quantity);

        if (fractionalPart == 0.5f) {
            suffix = "½";
            quantity = (float) Math.floor(quantity);
        }

        String formatted = new DecimalFormat("#.##").format(quantity);

        if ("0".equals(formatted)) {
            formatted = "";
        }

        return formatted + suffix;
    }

    public static String getMeasureText(Context context, String measure) {
        String measureText = getMeasureStringByName(context, measure);

        if (measureText == null) {
            return measure;
        }

        return measureText;
    }

    private static float getFractionalPart(float value) {
        return value - (int) value;
    }

    private static String getMeasureStringByName(Context context, String aString) {
        String packageName = context.getPackageName();
        int stringResourceId = context.getResources().getIdentifier("measure_unit_" +
                aString.toLowerCase(), "string", packageName);

        if (stringResourceId == 0) {
            return null;
        }

        return context.getString(stringResourceId);
    }
}
