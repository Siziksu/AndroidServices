package com.siziksu.services.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.siziksu.services.R
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.Constants
import com.siziksu.services.commons.DeviceManager
import com.siziksu.services.data.service.IntentServiceToBroadcast
import kotlinx.android.synthetic.main.activity_one_button.one_button_layout
import kotlinx.android.synthetic.main.section_single_button.btnStartService
import kotlinx.android.synthetic.main.section_title.activitySummary
import kotlinx.android.synthetic.main.section_title.activityTitle

class IntentServiceToBroadcastActivity : AppCompatActivity() {

    private val intentReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            intent.extras?.let {
                if (it.containsKey(Constants.EXTRAS_MESSAGE)) {
                    val message = intent.getStringExtra(Constants.EXTRAS_MESSAGE)
                    Commons.log(Constants.TAG_BROADCAST_RECEIVER, message)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_button)
        initializeViews()
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(Constants.ACTION_FILES_DOWNLOADED)
        registerReceiver(intentReceiver, filter)
        if (DeviceManager.isServiceRunning(this, Constants.TAG_INTENT_SERVICE_TO_BROADCAST)) {
            btnStartService.isEnabled = false
            Snackbar.make(one_button_layout, Constants.SERVICE_RUNNING, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(intentReceiver)
    }

    private fun initializeViews() {
        activityTitle.text = intent.getStringExtra(Constants.EXTRAS_TITLE)
        activitySummary.text = intent.getStringExtra(Constants.EXTRAS_SUMMARY)
        btnStartService.setOnClickListener {
            btnStartService.isEnabled = false
            startService()
        }
    }

    private fun startService() {
        Commons.log(Constants.TAG_INTENT_SERVICE_TO_BROADCAST, Constants.SERVICE_STARTING)
        startService(Intent(baseContext, IntentServiceToBroadcast::class.java))
    }
}
