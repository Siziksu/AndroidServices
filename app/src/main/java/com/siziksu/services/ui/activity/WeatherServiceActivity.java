package com.siziksu.services.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.siziksu.services.R;
import com.siziksu.services.app.Constants;
import com.siziksu.services.commons.Commons;
import com.siziksu.services.commons.DeviceManager;
import com.siziksu.services.data.service.WeatherService;
import com.siziksu.services.data.service.binder.WeatherBinder;

public class WeatherServiceActivity extends AppCompatActivity
        implements View.OnClickListener, WeatherBinder.WeatherListener {

    private Button buttonStart;

    private boolean bound;
    private WeatherService service;
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_CONNECTED);
            WeatherServiceActivity.this.service = ((WeatherBinder) service).getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_DISCONNECTED);
            service = null;
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_button);
        ((TextView) findViewById(R.id.activityTitle)).setText(
                getIntent().getStringExtra(Constants.EXTRAS_TITLE));
        ((TextView) findViewById(R.id.activitySummary)).setText(
                getIntent().getStringExtra(Constants.EXTRAS_SUMMARY));
        buttonStart = findViewById(R.id.btnStartService);
        buttonStart.setOnClickListener(this);
        DeviceManager.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DeviceManager.getInstance().isServiceRunning(Constants.TAG_WEATHER_SERVICE)) {
            buttonStart.setEnabled(false);
            Toast.makeText(this, Constants.SERVICE_RUNNING, Toast.LENGTH_SHORT).show();
        }
        bindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartService:
                buttonStart.setEnabled(false);
                startService();
                break;
        }
    }

    private void startService() {
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_STARTING);
        startService(new Intent(getBaseContext(), WeatherService.class));
        service.getForecast(WeatherServiceActivity.this,
                getString(R.string.weather_text_to_format));
    }

    private void bindService() {
        if (!bound) {
            Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_BINDING);
            bindService(new Intent(getBaseContext(), WeatherService.class), serviceConnection,
                    Context.BIND_AUTO_CREATE);
        }
    }

    private void unbindService() {
        if (bound) {
            bound = false;
            Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_UNBINDING);
            unbindService(serviceConnection);
        }
    }

    @Override
    public void updateForecast(String forecast) {
        Commons.log(Constants.TAG_WEATHER_SERVICE, forecast);
    }

    @Override
    public void onForecastError(Exception e) {
        e.printStackTrace();
    }
}
