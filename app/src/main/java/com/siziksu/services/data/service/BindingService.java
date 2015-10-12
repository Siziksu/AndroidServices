package com.siziksu.services.data.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import com.siziksu.services.commons.Commons;
import com.siziksu.services.app.Constants;
import com.siziksu.services.commons.mock.Mock;

import java.net.URL;

public class BindingService extends Service {

  private static final int TIME_BETWEEN_DOWNLOADS = 2000;
  private static final int DELAY_TIME_TO_PUBLISH_PROGRESS = 500;

  private URL[] urls;

  private final IBinder binder = new LocalBinder();
  private boolean stopService;

  public class LocalBinder extends Binder {

    public BindingService getService() {
      return BindingService.this;
    }
  }

  @Override
  public IBinder onBind(Intent intent) {
    Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_BOUND);
    return binder;
  }

  @Override
  public boolean onUnbind(Intent intent) {
    Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_UNBOUND);
    return super.onUnbind(intent);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_CREATED);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (urls == null) {
      Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_NOT_STARTED);
      stopSelf();
      return START_STICKY;
    } else {
      stopService = false;
      Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_STARTED);
      new BackgroundTask(this, intent).execute(urls);
      // We want this service to continue running until it is explicitly stopped, so return sticky.
      return START_STICKY;
    }
  }

  @Override
  public boolean stopService(Intent name) {
    Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_STOPPED);
    stopService = true;
    return super.stopService(name);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Commons.log(Constants.TAG_BINDING_SERVICE, Constants.SERVICE_DESTROYED);
  }

  public void setUrls(URL[] urls) {
    this.urls = urls;
  }

  private int DownloadFile(URL url) {
    Commons.log(Constants.TAG_BINDING_SERVICE, "Downloading: " + url);
    Mock.getInstance().pause(TIME_BETWEEN_DOWNLOADS);
    // Return an arbitrary number representing the size of the file downloaded
    return 100;
  }

  private class BackgroundTask extends AsyncTask<URL, Integer, Long> {

    private Service service;
    private Intent intent;

    public BackgroundTask(Service service, Intent intent) {
      this.service = service;
      this.intent = intent;
    }

    @Override
    protected Long doInBackground(URL... urls) {
      int count = urls.length;
      long totalBytesDownloaded = 0;
      for (int i = 0; i < count; i++) {
        if (stopService) {
          cancel(true);
          break;
        } else {
          totalBytesDownloaded += DownloadFile(urls[i]);
          // Calculate percentage downloaded and report its progress
          publishProgress((int) (((i + 1) / (float) count) * 100));
          Mock.getInstance().pause(DELAY_TIME_TO_PUBLISH_PROGRESS);
        }
      }
      return totalBytesDownloaded;
    }

    @Override
    protected void onCancelled() {
      super.onCancelled();
      Commons.log(Constants.TAG_BINDING_SERVICE, "Download task canceled");
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
      Commons.log(Constants.TAG_BINDING_SERVICE, progress[0] + "% downloaded");
    }

    @Override
    protected void onPostExecute(Long result) {
      Commons.log(Constants.TAG_BINDING_SERVICE, "Downloaded " + result + " bytes");
      // This will stop the service after finishing the task
      service.stopService(intent);
    }
  }
}