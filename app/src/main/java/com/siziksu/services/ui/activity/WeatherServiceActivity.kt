package com.siziksu.services.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.siziksu.services.R
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.Constants
import com.siziksu.services.commons.DeviceManager
import com.siziksu.services.data.service.WeatherService
import com.siziksu.services.data.service.binder.WeatherBinder
import kotlinx.android.synthetic.main.activity_one_button.one_button_layout
import kotlinx.android.synthetic.main.section_single_button.btnStartService
import kotlinx.android.synthetic.main.section_title.activitySummary
import kotlinx.android.synthetic.main.section_title.activityTitle

class WeatherServiceActivity : AppCompatActivity(), WeatherBinder.Listener {

    private var bound: Boolean = false
    private var service: WeatherService? = null
    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_CONNECTED)
            this@WeatherServiceActivity.service = (service as WeatherBinder).service
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_DISCONNECTED)
            service = null
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_button)
        initializeViews()
    }

    override fun onResume() {
        super.onResume()
        if (DeviceManager.isServiceRunning(this, Constants.TAG_WEATHER_SERVICE)) {
            btnStartService.isEnabled = false
            Snackbar.make(one_button_layout, Constants.SERVICE_RUNNING, Snackbar.LENGTH_SHORT).show()
        }
        bindService()
    }

    override fun onPause() {
        super.onPause()
        unbindService()
    }

    override fun updateForecast(forecast: String) {
        Commons.log(Constants.TAG_WEATHER_SERVICE, forecast)
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
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_STARTING)
        startService(Intent(baseContext, WeatherService::class.java))
        service?.getForecast(this@WeatherServiceActivity, getString(R.string.weather_text_to_format))
    }

    private fun bindService() {
        if (!bound) {
            Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_BINDING)
            bindService(Intent(baseContext, WeatherService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun unbindService() {
        if (bound) {
            bound = false
            Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_UNBINDING)
            unbindService(serviceConnection)
        }
    }
}
