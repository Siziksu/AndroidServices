package com.siziksu.services.data.service.binder

import android.content.Intent
import android.os.AsyncTask
import android.os.Binder
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import com.siziksu.services.commons.mock.Mock
import com.siziksu.services.data.service.WeatherService

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

    fun getForecast(listener: WeatherListener, string: String) {
        FetchForecastTask(listener).execute(string)
    }

    interface WeatherListener {

        fun updateForecast(forecast: String)

        fun onForecastError(e: Exception)
    }

    companion object {

        private const val TIME_ELAPSED_GETTING_FORECAST = 2000L
    }

    private inner class FetchForecastTask(val listener: WeatherListener) : AsyncTask<String, Void, String>() {

        private var exception: Exception? = null

        override fun doInBackground(vararg string: String): String {
            try {
                Commons.log(Constants.TAG_WEATHER_SERVICE, "Getting Forecast")
                Mock.pause(TIME_ELAPSED_GETTING_FORECAST)
                return String.format(string[0], "Spain", "cold")
            } catch (e: Exception) {
                this.exception = e
            }
            return ""
        }

        override fun onPostExecute(string: String) {
            super.onPostExecute(string)
            intent?.let { service?.stopService(it) }
            exception?.let {
                listener.onForecastError(it)
            } ?: listener.updateForecast(string)
        }
    }
}

