package com.siziksu.services.data.service

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.mock.Mock
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class CommunicateFromIService : IntentService(Constants.TAG_COMMUNICATE_FROM_SERVICE) {

    private lateinit var intent: Intent

    override fun onBind(intent: Intent): IBinder? {
        Commons.log(Constants.TAG_COMMUNICATE_FROM_SERVICE, Constants.SERVICE_BOUND)
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        Commons.log(Constants.TAG_COMMUNICATE_FROM_SERVICE, Constants.SERVICE_UNBOUND)
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Commons.log(Constants.TAG_COMMUNICATE_FROM_SERVICE, Constants.SERVICE_CREATED)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        this.intent = intent
        Commons.log(Constants.TAG_COMMUNICATE_FROM_SERVICE, Constants.SERVICE_STARTED)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun stopService(name: Intent): Boolean {
        Commons.log(Constants.TAG_COMMUNICATE_FROM_SERVICE, Constants.SERVICE_STOPPED)
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(intent)
        Commons.log(Constants.TAG_COMMUNICATE_FROM_SERVICE, Constants.SERVICE_DESTROYED)
    }

    override fun onHandleIntent(intent: Intent) {
        task(Mock.urls)
    }

    private fun task(list: Array<String>) = runBlocking {
        var totalBytesDownloaded: Long = 0
        for (i in 0 until list.size) {
            totalBytesDownloaded += Mock.downloadFile(list[i]).toLong()
            Commons.log(Constants.TAG_COMMUNICATE_FROM_SERVICE, "${((i + 1) / list.size.toFloat() * 100).toInt()}% downloaded ($totalBytesDownloaded bytes)")
            delay(1000)
        }
        Commons.log(Constants.TAG_COMMUNICATE_FROM_SERVICE, "Downloaded $totalBytesDownloaded bytes")

        // Send a broadcast to inform the activity that the file has been downloaded
        val broadcast = Intent()
        broadcast.action = Constants.ACTION_FILES_DOWNLOADED
        broadcast.putExtra(Constants.EXTRAS_MESSAGE, Constants.BROADCAST_MESSAGE)
        baseContext.sendBroadcast(broadcast)
        // IntentService destroys automatically after the task ends
    }
}
