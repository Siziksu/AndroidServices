package com.siziksu.services.data.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.Constants
import com.siziksu.services.data.service.binder.WeatherBinder

class WeatherService : Service() {

    private val binder = WeatherBinder()

    override fun onBind(intent: Intent): IBinder? {
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_BOUND)
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_UNBOUND)
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        binder.onCreate(this)
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_CREATED)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        binder.setIntent(intent)
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_STARTED)
        return START_STICKY
    }

    override fun stopService(name: Intent): Boolean {
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_STOPPED)
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_DESTROYED)
    }

    fun getForecast(listener: WeatherBinder.Listener, string: String) {
        binder.getForecast(listener, string)
    }
}
