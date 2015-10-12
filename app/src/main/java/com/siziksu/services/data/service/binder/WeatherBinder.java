package com.siziksu.services.data.service.binder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;

import com.siziksu.services.commons.Commons;
import com.siziksu.services.app.Constants;
import com.siziksu.services.commons.mock.Mock;
import com.siziksu.services.data.service.WeatherService;

public class WeatherBinder extends Binder {

  private static final int TIME_ELAPSED_GETTING_FORECAST = 2000;

  private WeatherService service;
  private Intent intent;

  public WeatherService getService() {
    return service;
  }

  public void onCreate(WeatherService service) {
    this.service = service;
  }

  public void setIntent(Intent intent) {
    this.intent = intent;
  }

  public void getForecast(WeatherListener listener, String string) {
    new FetchForecastTask(listener).execute(string);
  }

  private class FetchForecastTask extends AsyncTask<String, Void, String> {

    private Exception e = null;
    private WeatherListener listener = null;

    public FetchForecastTask(WeatherListener listener) {
      this.listener = listener;
    }

    @Override
    protected String doInBackground(String... string) {
      try {
        Commons.log(Constants.TAG_WEATHER_SERVICE, "Getting Forecast");
        Mock.getInstance().pause(TIME_ELAPSED_GETTING_FORECAST);
        return String.format(string[0], "Spain", "cold");
      } catch (Exception e) {
        this.e = e;
      }
      return "";
    }

    @Override
    protected void onPostExecute(String string) {
      super.onPostExecute(string);
      service.stopService(intent);
      if (e != null) {
        listener.onForecastError(e);
      } else {
        if (listener != null) {
          listener.updateForecast(string);
        }
      }
    }
  }

  public interface WeatherListener {

    void updateForecast(String forecast);

    void onForecastError(Exception e);
  }

}

