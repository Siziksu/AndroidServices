package com.siziksu.services.data.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.mock.Mock
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BindingService : Service() {

    private var urls: Array<String>? = null
    private var onStopListener: (() -> Unit)? = null

    private val binder = LocalBinder()
    private var serviceStopped: Boolean = false

    override fun onBind(intent: Intent): IBinder? {
        Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_BOUND)
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_UNBOUND)
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_CREATED)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        urls?.let {
            Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_STARTED)
            serviceStopped = false
            task(intent, it)
        } ?: stop()
        return START_STICKY // We want this service to continue running until it is explicitly stopped, so return sticky.
    }

    override fun stopService(name: Intent): Boolean {
        Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_STOPPED)
        serviceStopped = true
        onStopListener?.invoke()
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_DESTROYED)
    }

    fun setUrls(urls: Array<String>) {
        this.urls = urls
    }

    fun setOnStopListener(listener: (() -> Unit)) {
        onStopListener = listener
    }

    private fun stop() {
        Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_NOT_STARTED)
        stopSelf()
    }

    private fun task(intent: Intent, list: Array<String>) {
        GlobalScope.launch {
            var totalBytesDownloaded: Long = 0
            for (i in 0 until list.size) {
                if (serviceStopped) {
                    break
                }
                totalBytesDownloaded += Mock.downloadFile(list[i]).toLong()
                Commons.log(Constants.TAG_LONG_RUNNING_SERVICE, "${((i + 1) / list.size.toFloat() * 100).toInt()}% downloaded ($totalBytesDownloaded bytes)")
                delay(1000)
            }
            Commons.log(Constants.TAG_LONG_RUNNING_SERVICE, "Downloaded $totalBytesDownloaded bytes")
            // This will stop the service after finishing the task
            stopService(intent)
        }
    }

    inner class LocalBinder : Binder() {

        val service: BindingService
            get() = this@BindingService
    }
}