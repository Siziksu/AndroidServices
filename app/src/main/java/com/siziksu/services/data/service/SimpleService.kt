package com.siziksu.services.data.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.Constants

class SimpleService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_BOUND)
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_UNBOUND)
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_CREATED)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_STARTED)
        task(intent)
        return START_STICKY
    }

    override fun stopService(name: Intent): Boolean {
        Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_STOPPED)
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_DESTROYED)
    }

    private fun task(intent: Intent) {
        val handler = Handler()
        val runnableCode = object : Runnable {

            private var count = 0

            override fun run() {
                if (count++ < 3) {
                    Commons.log(Constants.TAG_SIMPLE_SERVICE, "$count")
                    handler.postDelayed(this, 1000)
                } else {
                    stopService(intent)
                }
            }
        }
        runnableCode.run()
    }
}
