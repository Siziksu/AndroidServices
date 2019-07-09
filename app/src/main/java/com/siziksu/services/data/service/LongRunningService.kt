package com.siziksu.services.data.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.mock.Mock
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LongRunningService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        Commons.log(Constants.TAG_LONG_RUNNING_SERVICE, Constants.SERVICE_BOUND)
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        Commons.log(Constants.TAG_LONG_RUNNING_SERVICE, Constants.SERVICE_UNBOUND)
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Commons.log(Constants.TAG_LONG_RUNNING_SERVICE, Constants.SERVICE_CREATED)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Commons.log(Constants.TAG_LONG_RUNNING_SERVICE, Constants.SERVICE_STARTED)
        task(intent, Mock.urls)
        return START_STICKY
    }

    override fun stopService(name: Intent): Boolean {
        Commons.log(Constants.TAG_LONG_RUNNING_SERVICE, Constants.SERVICE_STOPPED)
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        Commons.log(Constants.TAG_LONG_RUNNING_SERVICE, Constants.SERVICE_DESTROYED)
    }

    private fun task(intent: Intent, list: Array<String>) {
        GlobalScope.launch {
            var totalBytesDownloaded: Long = 0
            for (i in 0 until list.size) {
                totalBytesDownloaded += Mock.downloadFile(list[i]).toLong()
                Commons.log(Constants.TAG_LONG_RUNNING_SERVICE, "${((i + 1) / list.size.toFloat() * 100).toInt()}% downloaded ($totalBytesDownloaded bytes)")
                delay(1000)
            }
            Commons.log(Constants.TAG_LONG_RUNNING_SERVICE, "Downloaded $totalBytesDownloaded bytes")

            stopService(intent) // This will stop the service after finishing the task
        }
    }
}
