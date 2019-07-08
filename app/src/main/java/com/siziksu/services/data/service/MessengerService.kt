package com.siziksu.services.data.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.mock.Mock

class MessengerService : Service() {

    private val messenger = Messenger(RequestHandler())

    override fun onBind(intent: Intent): IBinder? {
        Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_BOUND)
        return messenger.binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_UNBOUND)
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_CREATED)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_STARTED)
        return START_STICKY
    }

    override fun stopService(name: Intent): Boolean {
        Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_STOPPED)
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_DESTROYED)
    }

    internal class RequestHandler : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSENGER_SERVICE_TEST -> if (msg.data.containsKey(Constants.EXTRAS_JSON)) {
                    val json = msg.data.getString(Constants.EXTRAS_JSON)
                    Commons.log(Constants.TAG_MESSENGER_SERVICE, json ?: "")
                    Mock.pause(DELAY_TIME_TO_PUBLISH_REQUEST_RECEIVED)
                    val response = Message.obtain(null, MESSENGER_SERVICE_TEST)
                    val bundle = Bundle()
                    bundle.putString(Constants.EXTRAS_JSON, Mock.fakeResponse())
                    response.data = bundle
                    try {
                        msg.replyTo.send(response)
                    } catch (e: Exception) {
                        Commons.error(e)
                    }
                }

                else -> super.handleMessage(msg)
            }
        }
    }

    companion object {

        const val MESSENGER_SERVICE_TEST = 0
        private const val DELAY_TIME_TO_PUBLISH_REQUEST_RECEIVED = 2000L
    }

    interface Listener {

        fun onMessage(response: String)
    }
}
