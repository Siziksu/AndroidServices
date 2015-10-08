package com.siziksu.services.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import com.siziksu.services.common.Commons;
import com.siziksu.services.common.Constants;
import com.siziksu.services.common.Mock;

import java.net.URL;

public class IService extends IntentService {

  private static final int TIME_BETWEEN_DOWNLOADS = 2000;

  private Intent intent;
  private URL[] urls;

  public IService() {
    super(Constants.TAG_INTENT_SERVICE);
  }

  @Override
  public IBinder onBind(Intent intent) {
    Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_BOUND);
    return null;
  }

  @Override
  public boolean onUnbind(Intent intent) {
    Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_UNBOUND);
    return super.onUnbind(intent);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_CREATED);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    this.intent = intent;
    Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_STARTED);
    Object[] urls = (Object[]) intent.getExtras().get(Constants.EXTRAS_URL);
    if (urls != null) {
      this.urls = new URL[urls.length];
      for (int i = 0; i < urls.length; i++) {
        this.urls[i] = (URL) urls[i];
      }
    }
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public boolean stopService(Intent name) {
    Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_STOPPED);
    return super.stopService(name);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    stopService(intent);
    Commons.log(Constants.TAG_INTENT_SERVICE, Constants.SERVICE_DESTROYED);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    long totalBytesDownloaded = 0;
    for (int i = 0; i < urls.length; i++) {
      totalBytesDownloaded += DownloadFile(urls[i]);
      long progress = ((int) (((i + 1) / (float) urls.length) * 100));
      Commons.log(Constants.TAG_INTENT_SERVICE, progress + "% downloaded");
    }
    Commons.log(Constants.TAG_INTENT_SERVICE, "Downloaded " + totalBytesDownloaded + " bytes");
  }

  private int DownloadFile(URL url) {
    Commons.log(Constants.TAG_INTENT_SERVICE, "Downloading: " + url);
    Mock.getInstance().pause(TIME_BETWEEN_DOWNLOADS);
    // Return an arbitrary number representing the size of the file downloaded
    return 100;
  }
}
