package com.siziksu.services.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.siziksu.services.common.Commons;
import com.siziksu.services.common.Constants;

import java.util.Timer;
import java.util.TimerTask;

public class SimpleService extends Service {

  private static final int TIMER_DELAY = 0;
  private static final int TIMER_UPDATE_INTERVAL = 1000;
  private static final int TIMER_MAXIMUM_ITERATIONS = 5;
  private static final int WHAT = 351654545;

  private TimerTask task;
  private Handler handler;

  @Override
  public IBinder onBind(Intent intent) {
    Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_BOUND);
    return null;
  }

  @Override
  public boolean onUnbind(Intent intent) {
    Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_UNBOUND);
    return super.onUnbind(intent);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_CREATED);
  }

  @Override
  public int onStartCommand(final Intent intent, int flags, int startId) {
    Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_STARTED);
    startTimer(intent);
    return START_STICKY;
  }

  @Override
  public boolean stopService(Intent name) {
    task.cancel();
    Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_STOPPED);
    return super.stopService(name);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_DESTROYED);
  }

  public void startTimer(Intent intent) {
    handler = new LocalHandler(this, intent);
    task = new TimerTask() {
      int count;

      @Override
      public void run() {
        Message msg = new Message();
        msg.what = WHAT;
        msg.arg1 = count;
        if (msg.arg1 != 0) {
          if (msg.arg1 > TIMER_MAXIMUM_ITERATIONS) {
            msg.obj = Constants.TASK_CANCELED;
          } else {
            msg.obj = String.valueOf(count);
          }
          handler.sendMessage(msg);
          if (msg.arg1 > TIMER_MAXIMUM_ITERATIONS) {
            cancel();
          }
        }
        count++;
      }
    };
    new Timer().scheduleAtFixedRate(task, TIMER_DELAY, TIMER_UPDATE_INTERVAL);
  }

  static class LocalHandler extends Handler {

    private Service service;
    private Intent intent;

    LocalHandler(Service service, Intent intent) {
      this.service = service;
      this.intent = intent;
    }

    @Override
    public void handleMessage(Message msg) {
      if (msg.what == WHAT) {
        Commons.log(
            Constants.TAG_SIMPLE_SERVICE,
            msg.obj != null ? (String) msg.obj : ""
        );
        if (msg.arg1 > TIMER_MAXIMUM_ITERATIONS) {
          service.stopService(intent);
        }
      }
    }
  }
}
