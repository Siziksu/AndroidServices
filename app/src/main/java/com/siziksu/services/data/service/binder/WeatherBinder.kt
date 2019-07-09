package com.siziksu.services.data.service.binder

import android.content.Intent
import android.os.Binder
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import com.siziksu.services.data.service.WeatherService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherBinder : Binder() {

    var service: WeatherService? = null
        private set

    private var intent: Intent? = null

    fun onCreate(service: WeatherService) {
        this.service = service
    }

    fun setIntent(intent: Intent) {
        this.intent = intent
    }

    fun getForecast(listener: Listener, string: String) = task(string, listener)

    private fun task(string: String, listener: Listener) {
        GlobalScope.launch {
            Commons.log(Constants.TAG_WEATHER_SERVICE, "Getting Forecast")
            delay(2000)
            listener.updateForecast(String.format(string, "Spain", "cold"))
            intent?.let { service?.stopService(it) }
        }
    }

    interface Listener {

        fun updateForecast(forecast: String)
    }
}
