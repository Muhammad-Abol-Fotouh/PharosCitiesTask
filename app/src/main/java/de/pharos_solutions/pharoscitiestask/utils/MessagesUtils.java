package de.pharos_solutions.pharoscitiestask.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by muhammadkorany on 10/5/17.
 */

public class MessagesUtils {


    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
