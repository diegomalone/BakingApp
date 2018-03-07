package com.diegomalone.bakingapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import timber.log.Timber;

/**
 * Created by malone on 07/03/18.
 */

public class Utils {
    public static String readFileFromAssets(Context context, String fileName) {
        System.out.println("Reading file");

        if (context == null) return "";

        System.out.println("File name: " + fileName);

        StringBuilder builder = new StringBuilder();
        try {
            InputStream stream = context.getAssets().open(fileName);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line = bReader.readLine();

            while (line != null) {
                builder.append(line);
                line = bReader.readLine();
            }
        } catch (IOException e) {
            Timber.e(e);
        }

        return builder.toString().substring(0);
    }
}
