package com.siziksu.services.commons;

import android.app.ActivityManager;
import android.content.Context;
import com.siziksu.services.app.Constants;

public class DeviceManager {

    private static DeviceManager instance;

    private Context context;

    public static DeviceManager init(Context context) {
        if (instance == null) {
            instance = new DeviceManager(context);
        }
        return instance;
    }

    public static DeviceManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("Device must be initialized");
        }
        return instance;
    }

    private DeviceManager(Context context) {
        this.context = context;
    }

    public boolean isServiceRunning(String name) {
        ActivityManager manager =
                (ActivityManager) context.getSystemService((Context.ACTIVITY_SERVICE));
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if ((Constants.APP_PACKAGE + Constants.SERVICE_PACKAGE + "." + name).equals(
                    service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
