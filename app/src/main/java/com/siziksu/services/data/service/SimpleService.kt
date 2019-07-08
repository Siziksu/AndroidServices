package com.siziksu.services.data.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import java.util.Timer
import java.util.TimerTask

class SimpleService : Service() {

    private var task: TimerTask? = null
    private var handler: Handler? = null

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
        startTimer(intent)
        return START_STICKY
    }

    override fun stopService(name: Intent): Boolean {
        task?.cancel()
        Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_STOPPED)
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_DESTROYED)
    }

    private fun startTimer(intent: Intent) {
        handler = LocalHandler(this, intent)
        task = object : TimerTask() {
            var count: Int = 0

            override fun run() {
                val msg = Message()
                msg.what = WHAT
                msg.arg1 = count
                if (msg.arg1 != 0) {
                    if (msg.arg1 > TIMER_MAXIMUM_ITERATIONS) {
                        msg.obj = Constants.TASK_CANCELED
                    } else {
                        msg.obj = count.toString()
                    }
                    handler?.sendMessage(msg)
                    if (msg.arg1 > TIMER_MAXIMUM_ITERATIONS) {
                        cancel()
                    }
                }
                count++
            }
        }
        Timer().scheduleAtFixedRate(task, TIMER_DELAY.toLong(), TIMER_UPDATE_INTERVAL.toLong())
    }

    internal class LocalHandler(private val service: Service, private val intent: Intent) : Handler() {

        override fun handleMessage(msg: Message) {
            if (msg.what == WHAT) {
                Commons.log(Constants.TAG_SIMPLE_SERVICE, msg.obj?.let { msg.obj as String } ?: "")
                if (msg.arg1 > TIMER_MAXIMUM_ITERATIONS) {
                    service.stopService(intent)
                }
            }
        }
    }

    companion object {

        private const val TIMER_DELAY = 0
        private const val TIMER_UPDATE_INTERVAL = 1000
        private const val TIMER_MAXIMUM_ITERATIONS = 5
        private const val WHAT = 351654545
    }
}
