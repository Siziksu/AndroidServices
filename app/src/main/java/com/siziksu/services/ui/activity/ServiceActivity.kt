package com.siziksu.services.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.siziksu.services.R
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.DeviceManager
import com.siziksu.services.data.service.Service
import kotlinx.android.synthetic.main.activity_one_button.one_button_layout
import kotlinx.android.synthetic.main.section_single_button.btnStartService
import kotlinx.android.synthetic.main.section_title.activitySummary
import kotlinx.android.synthetic.main.section_title.activityTitle

class ServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_button)
        initializeViews()
    }

    override fun onResume() {
        super.onResume()
        if (DeviceManager.isServiceRunning(this, Constants.TAG_INTENT_SERVICE)) {
            btnStartService.isEnabled = false
            Snackbar.make(one_button_layout, Constants.SERVICE_RUNNING, Snackbar.LENGTH_SHORT).show()
        }
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
        Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_STARTING)
        startService(Intent(baseContext, Service::class.java))
    }
}
