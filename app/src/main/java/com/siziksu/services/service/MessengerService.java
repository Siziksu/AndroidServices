package com.siziksu.services.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import com.siziksu.services.common.Commons;
import com.siziksu.services.common.Constants;
import com.siziksu.services.common.Mock;

public class MessengerService extends Service {

  public static final int MESSENGER_SERVICE_TEST = 0;
  private static final int DELAY_TIME_TO_PUBLISH_REQUEST_RECEIVED = 2000;

  private Messenger messenger = new Messenger(new RequestHandler());

  @Override
  public IBinder onBind(Intent intent) {
    Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_BOUND);
    return messenger.getBinder();
  }

  @Override
  public boolean onUnbind(Intent intent) {
    Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_UNBOUND);
    return super.onUnbind(intent);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_CREATED);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_STARTED);
    return START_STICKY;
  }

  @Override
  public boolean stopService(Intent name) {
    Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_STOPPED);
    return super.stopService(name);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Commons.log(Constants.TAG_MESSENGER_SERVICE, Constants.SERVICE_DESTROYED);
  }

  static class RequestHandler extends Handler {

    public RequestHandler() {}

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case MESSENGER_SERVICE_TEST:
          if (msg.getData().containsKey(Constants.EXTRAS_JSON)) {
            String json = msg.getData().getString(Constants.EXTRAS_JSON);
            Commons.log(Constants.TAG_MESSENGER_SERVICE, json);
            Mock.getInstance().pause(DELAY_TIME_TO_PUBLISH_REQUEST_RECEIVED);
            Message response = Message.obtain(null, MESSENGER_SERVICE_TEST);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.EXTRAS_JSON, Mock.getInstance().fakeResponse());
            response.setData(bundle);
            try {
              msg.replyTo.send(response);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          break;

        default:
          super.handleMessage(msg);
      }
    }
  }

  public interface Listener {

    void onMessage(String response);
  }
}