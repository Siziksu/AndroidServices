package com.siziksu.services.data.service

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.mock.Mock
import java.net.URL

class Service : IntentService(Constants.TAG_INTENT_SERVICE) {

    private var intent: Intent? = null
    private var urls: Array<URL>? = null

    override fun onBind(intent: Intent): IBinder? {
        Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_BOUND)
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_UNBOUND)
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_CREATED)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        this.intent = intent
        Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_STARTED)
        this.urls = intent?.extras?.get(Constants.EXTRAS_URL) as Array<URL>
        return super.onStartCommand(intent, flags, startId)
    }

    override fun stopService(name: Intent?): Boolean {
        Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_STOPPED)
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(intent)
        Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_DESTROYED)
    }

    override fun onHandleIntent(intent: Intent?) {
        var totalBytesDownloaded: Long = 0
        urls?.let {
            for (i in it.indices) {
                totalBytesDownloaded += downloadFile(it[i]).toLong()
                val progress = ((i + 1) / it.size.toFloat() * 100).toInt().toLong()
                Commons.log(Constants.TAG_INTENT_SERVICE, "$progress% downloaded")
            }
        }
        Commons.log(Constants.TAG_INTENT_SERVICE, "Downloaded $totalBytesDownloaded bytes")
    }

    private fun downloadFile(url: URL): Int {
        Commons.log(Constants.TAG_INTENT_SERVICE, "Downloading: $url")
        Mock.pause(TIME_BETWEEN_DOWNLOADS)
        // Return an arbitrary number representing the size of the file downloaded
        return 100
    }

    companion object {

        private const val TIME_BETWEEN_DOWNLOADS = 2000L
    }
}
