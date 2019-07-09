package com.siziksu.services.data.service

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.mock.Mock
import java.util.Timer
import java.util.TimerTask

class TimerClassService : Service() {

    private var counter: Long = 0
    private val timer = Timer()

    override fun onBind(intent: Intent): IBinder? {
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_BOUND)
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_UNBOUND)
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_CREATED)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_STARTED)
        // We want this service to continue running until it is explicitly stopped, so return sticky.
        doSomethingRepeatedly()
        val urls = Mock.urls
        urls.let { BackgroundTask(this, intent).execute(it) }
        return START_STICKY
    }

    private fun doSomethingRepeatedly() {
        timer.scheduleAtFixedRate(object : TimerTask() {

            override fun run() {
                val time = Commons.getTime(++counter)
                Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, time, Constants.FLAG_TIME)
            }
        }, TIMER_DELAY.toLong(), TIMER_UPDATE_INTERVAL.toLong())
    }

    override fun stopService(name: Intent): Boolean {
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_STOPPED)
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_DESTROYED)
    }

    companion object {

        private const val DELAY_TIME_TO_PUBLISH_PROGRESS = 500L
        private const val TIMER_DELAY = 1000
        private const val TIMER_UPDATE_INTERVAL = 1000
    }

    private inner class BackgroundTask(private val service: Service, private val intent: Intent) : AsyncTask<Array<String>, Int, Long>() {

        override fun doInBackground(vararg urls: Array<String>): Long? {
            val count = urls.size
            var totalBytesDownloaded: Long = 0
            for (i in 0 until count) {
                totalBytesDownloaded += Mock.downloadFile(urls[0][i]).toLong()
                // Calculate percentage downloaded and report its progress
                publishProgress(((i + 1) / count.toFloat() * 100).toInt())
                Mock.pause(DELAY_TIME_TO_PUBLISH_PROGRESS)
            }
            return totalBytesDownloaded
        }

        override fun onProgressUpdate(vararg progress: Int?) {
            Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, progress[0].toString() + "% downloaded")
        }

        override fun onPostExecute(result: Long?) {
            Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, "Downloaded $result bytes")
            // This will stop the service after finishing the task
            service.stopService(intent)
        }
    }
}
