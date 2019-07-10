package com.siziksu.services.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.siziksu.services.R
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.Constants
import com.siziksu.services.commons.DeviceManager
import com.siziksu.services.commons.mock.Mock
import com.siziksu.services.data.service.MessengerService
import kotlinx.android.synthetic.main.activity_one_button.one_button_layout
import kotlinx.android.synthetic.main.section_single_button.btnStartService
import kotlinx.android.synthetic.main.section_title.activitySummary
import kotlinx.android.synthetic.main.section_title.activityTitle

class MessengerServiceActivity : AppCompatActivity(), MessengerService.Listener {

    private var bound: Boolean = false
    private var messenger: Messenger? = null

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_CONNECTED)
            messenger = Messenger(service)
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_DISCONNECTED)
            messenger = null
            bound = false
        }
    }

    override fun onMessage(response: String) {
        Commons.log(Constants.TAG_MESSENGER_SERVICE, response)
        btnStartService.isEnabled = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_button)
        initializeViews()
    }

    override fun onResume() {
        super.onResume()
        if (DeviceManager.isServiceRunning(this, Constants.TAG_BINDING_SERVICE)) {
            btnStartService.isEnabled = false
            Snackbar.make(one_button_layout, Constants.SERVICE_RUNNING, Snackbar.LENGTH_SHORT).show()
        }
        bindService()
    }

    override fun onPause() {
        super.onPause()
        unbindService()
    }

    private fun bindService() {
        if (!bound) {
            Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_BINDING)
            val intent = Intent(this, MessengerService::class.java)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun unbindService() {
        if (bound) {
            Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_UNBINDING)
            unbindService(serviceConnection)
            bound = false
        }
    }

    private fun initializeViews() {
        activityTitle.text = intent.getStringExtra(Constants.EXTRAS_TITLE)
        activitySummary.text = intent.getStringExtra(Constants.EXTRAS_SUMMARY)
        btnStartService.setOnClickListener {
            btnStartService.isEnabled = false
            serviceRequest()
        }
    }

    private fun serviceRequest() {
        if (bound) {
            val json = Mock.fakeRequest()
            val msg = Message.obtain(null, MessengerService.MESSENGER_SERVICE_WHAT) // Prepares the message to be sent

            msg.replyTo = Messenger(ResponseHandler(this)) // Sets the callback

            val bundle = Bundle()
            bundle.putString(Constants.EXTRAS_JSON, json)
            msg.data = bundle // Sets the data to be sent

            try {
                messenger?.send(msg) // Sends the data
            } catch (e: RemoteException) {
                Commons.error(e)
            }
        } else {
            Log.d(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_NOT_BOUND)
        }
    }

    internal class ResponseHandler(private val listener: MessengerService.Listener) : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MessengerService.MESSENGER_SERVICE_WHAT -> {
                    msg.data?.let {
                        if (it.containsKey(Constants.EXTRAS_JSON)) {
                            val json = it.getString(Constants.EXTRAS_JSON)
                            listener.onMessage(json ?: "")
                        }
                    }
                }
            }
        }
    }
}
