package com.siziksu.services.commons.mock;

import android.content.Intent;
import com.siziksu.services.app.Constants;
import java.net.URL;

public class Mock {

    private static Mock instance;

    public static Mock getInstance() {
        if (instance == null) {
            instance = new Mock();
        }
        return instance;
    }

    private Mock() {
    }

    public void pause(long time) {
        try {
            // Simulate taking some time to download a file
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Intent putUrls(Intent intent) {
        try {
            URL[] urls = getUrls();
            return intent.putExtra(Constants.EXTRAS_URL, urls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL[] getUrls() {
        try {
            return new URL[] {
                    new URL("http://www.amazon.com/file.pdf"),
                    new URL("http://www.wrox.com/file.pdf"),
                    new URL("http://www.google.com/file.pdf"),
                    new URL("http://www.learn2develop.net/file.pdf")
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String fakeRequest() {
        return "{\"id\":74}";
    }

    public String fakeResponse() {
        return "[{\"id\":78945,\"name\":\"John\"},{\"id\":25897,\"name\":\"Andrew\"},{\"id\":35981,\"name\":\"Joe\"}]";
    }
}
