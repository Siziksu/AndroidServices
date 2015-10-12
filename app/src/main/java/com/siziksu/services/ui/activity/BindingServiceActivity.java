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
import com.siziksu.services.commons.Commons;
import com.siziksu.services.app.Constants;
import com.siziksu.services.commons.DeviceManager;
import com.siziksu.services.commons.mock.Mock;
import com.siziksu.services.data.service.BindingService;

public class BindingServiceActivity extends AppCompatActivity implements View.OnClickListener {

  private Button buttonStart;
  private Button buttonStop;

  private boolean bound;
  private BindingService service;
  private ServiceConnection serviceConnection = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
      Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_CONNECTED);
      service = ((BindingService.LocalBinder) binder).getService();
      service.setUrls(Mock.getInstance().getUrls());
      bound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_DISCONNECTED);
      service = null;
      bound = false;
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_two_buttons);
    ((TextView) findViewById(R.id.activityTitle)).setText(getIntent().getStringExtra(Constants.EXTRAS_TITLE));
    ((TextView) findViewById(R.id.activitySummary)).setText(getIntent().getStringExtra(Constants.EXTRAS_SUMMARY));
    buttonStart = (Button) findViewById(R.id.btnStartService);
    buttonStart.setOnClickListener(this);
    buttonStop = (Button) findViewById(R.id.btnStopService);
    buttonStop.setOnClickListener(this);
    DeviceManager.init(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (DeviceManager.getInstance().isServiceRunning(Constants.TAG_BINDING_SERVICE)) {
      buttonStart.setEnabled(false);
      buttonStop.setEnabled(true);
      Toast.makeText(this, Constants.SERVICE_RUNNING, Toast.LENGTH_SHORT).show();
    } else {
      buttonStop.setEnabled(false);
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
        buttonStop.setEnabled(true);
        startService();
        break;
      case R.id.btnStopService:
        buttonStop.setEnabled(false);
        stopService();
        break;
    }
  }

  private void startService() {
    Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_STARTING);
    startService(new Intent(getBaseContext(), BindingService.class));
  }

  private void stopService() {
    if (bound) {
      Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_STOPPING + " (bound)");
      service.stopService(new Intent(getBaseContext(), BindingService.class));
    } else {
      Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_STOPPING + " (not bound)");
      stopService(new Intent(getBaseContext(), BindingService.class));
    }
  }

  private void bindService() {
    if (!bound) {
      Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_BINDING);
      bindService(new Intent(getBaseContext(), BindingService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }
  }

  private void unbindService() {
    if (bound) {
      bound = false;
      Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_UNBINDING);
      unbindService(serviceConnection);
    }
  }
}
