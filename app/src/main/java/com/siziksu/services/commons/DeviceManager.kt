package com.siziksu.services.commons

import android.app.ActivityManager
import android.content.Context

class DeviceManager {

    companion object {

        fun isServiceRunning(context: Context, name: String): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                if (Constants.APP_PACKAGE + Constants.SERVICE_PACKAGE + "." + name == service.service.className) {
                    return true
                }
            }
            return false
        }
    }
}
