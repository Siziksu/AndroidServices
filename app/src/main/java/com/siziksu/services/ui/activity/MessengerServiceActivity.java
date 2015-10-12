package com.siziksu.services.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.siziksu.services.R;
import com.siziksu.services.commons.Commons;
import com.siziksu.services.app.Constants;
import com.siziksu.services.commons.DeviceManager;
import com.siziksu.services.commons.mock.Mock;
import com.siziksu.services.data.service.MessengerService;

public class MessengerServiceActivity extends AppCompatActivity implements View.OnClickListener, MessengerService.Listener {

  private Button buttonStart;

  private boolean bound;
  private Messenger messenger;

  private ServiceConnection serviceConnection = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_CONNECTED);
      messenger = new Messenger(service);
      bound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_DISCONNECTED);
      messenger = null;
      bound = false;
    }
  };

  static class ResponseHandler extends Handler {

    private MessengerService.Listener listener;

    public ResponseHandler(MessengerService.Listener listener) {
      this.listener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case MessengerService.MESSENGER_SERVICE_TEST: {
          if (msg.getData() != null && msg.getData().containsKey(Constants.EXTRAS_JSON)) {
            String json = msg.getData().getString(Constants.EXTRAS_JSON);
            listener.onMessage(json);
          }
        }
      }
    }
  }

  @Override
  public void onMessage(String response) {
    if (response != null) {
      Commons.log(Constants.TAG_MESSENGER_SERVICE, response);
      buttonStart.setEnabled(true);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_one_button);
    ((TextView) findViewById(R.id.activityTitle)).setText(getIntent().getStringExtra(Constants.EXTRAS_TITLE));
    ((TextView) findViewById(R.id.activitySummary)).setText(getIntent().getStringExtra(Constants.EXTRAS_SUMMARY));
    buttonStart = (Button) findViewById(R.id.btnStartService);
    buttonStart.setOnClickListener(this);
    DeviceManager.init(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (DeviceManager.getInstance().isServiceRunning(Constants.TAG_BINDING_SERVICE)) {
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

  private void bindService() {
    if (!bound) {
      Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_BINDING);
      Intent intent = new Intent(this, MessengerService.class);
      bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
  }

  private void unbindService() {
    if (bound) {
      Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_UNBINDING);
      unbindService(serviceConnection);
      bound = false;
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnStartService:
        buttonStart.setEnabled(false);
        serviceRequest();
        break;
    }
  }

  private void serviceRequest() {
    if (bound) {
      String json = Mock.getInstance().fakeRequest();
      Message msg = Message.obtain(null, MessengerService.MESSENGER_SERVICE_TEST);
      msg.replyTo = new Messenger(new ResponseHandler(this));
      Bundle bundle = new Bundle();
      bundle.putString(Constants.EXTRAS_JSON, json);
      msg.setData(bundle);
      try {
        messenger.send(msg);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    } else {
      Log.d(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_NOT_BOUND);
    }
  }
}
