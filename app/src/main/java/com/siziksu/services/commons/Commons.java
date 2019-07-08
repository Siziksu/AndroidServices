package com.siziksu.services.commons;

import android.text.TextUtils;
import android.util.Log;
import java.util.concurrent.TimeUnit;

public class Commons {

    private static Commons instance;

    private Commons() {
    }

    public static Commons getInstance() {
        if (instance == null) {
            instance = new Commons();
        }
        return instance;
    }

    public static void log(String owner, String message) {
        log(owner, message, "");
    }

    public static void log(String owner, String message, String flag) {
        Log.d("LOG",
                "<message owner=\"" + owner + (!TextUtils.isEmpty(flag) ? " flag=\"" + flag + "\""
                        : "") + ">" + message + "</message>");
    }

    public String getTime(long seconds) {
        return millisToStringTime(seconds * 1000);
    }

    private String millisToStringTime(long millis) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis) % 24,
                TimeUnit.MILLISECONDS.toMinutes(millis) % 60,
                TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        );
    }
}
