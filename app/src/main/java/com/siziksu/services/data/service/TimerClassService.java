package com.siziksu.services.data.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import com.siziksu.services.app.Constants;
import com.siziksu.services.commons.Commons;
import com.siziksu.services.commons.mock.Mock;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class TimerClassService extends Service {

    private static final int TIME_BETWEEN_DOWNLOADS = 2000;
    private static final int DELAY_TIME_TO_PUBLISH_PROGRESS = 500;
    private static final int TIMER_DELAY = 1000;
    private static final int TIMER_UPDATE_INTERVAL = 1000;

    private int counter = 0;
    private Timer timer = new Timer();

    @Override
    public IBinder onBind(Intent intent) {
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_BOUND);
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_UNBOUND);
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_CREATED);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_STARTED);
        // We want this service to continue running until it is explicitly stopped, so return sticky.
        doSomethingRepeatedly();
        new BackgroundTask(this, intent).execute(Mock.getInstance().getUrls());
        return START_STICKY;
    }

    private void doSomethingRepeatedly() {
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                String time = Commons.getInstance().getTime(++counter);
                Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, time, Constants.FLAG_TIME);
            }
        }, TIMER_DELAY, TIMER_UPDATE_INTERVAL);
    }

    @Override
    public boolean stopService(Intent name) {
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_STOPPED);
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, Constants.SERVICE_DESTROYED);
    }

    private int DownloadFile(URL url) {
        Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, "Downloading: " + url);
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
                totalBytesDownloaded += DownloadFile(urls[i]);
                // Calculate percentage downloaded and report its progress
                publishProgress((int) (((i + 1) / (float) count) * 100));
                Mock.getInstance().pause(DELAY_TIME_TO_PUBLISH_PROGRESS);
            }
            return totalBytesDownloaded;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, progress[0] + "% downloaded");
        }

        @Override
        protected void onPostExecute(Long result) {
            Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, "Downloaded " + result + " bytes");
            // This will stop the service after finishing the task
            service.stopService(intent);
        }
    }
}
