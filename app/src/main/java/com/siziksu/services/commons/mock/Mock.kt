package com.siziksu.services.commons.mock

import android.content.Intent
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import kotlin.random.Random

class Mock {

    companion object {
        private const val TIME_BETWEEN_DOWNLOADS = 2000L

        val urls: Array<String> = arrayOf("http://www.amazon.com/file.pdf",
                                          "http://www.wrox.com/file.pdf",
                                          "http://www.google.com/file.pdf",
                                          "http://www.learn2develop.net/file.pdf")

        fun pause(time: Long) {
            try {
                // Simulate taking some time to download a file
                Thread.sleep(time)
            } catch (e: InterruptedException) {
                Commons.error(e)
            }
        }

        fun downloadFile(url: String): Int {
            Commons.log(Constants.TAG_TIMER_CLASS_SERVICE, "Downloading: $url")
            pause(TIME_BETWEEN_DOWNLOADS)
            // Return an arbitrary number representing the size of the file downloaded
            return Random.nextInt(50, 250)
        }

        fun putUrls(intent: Intent): Intent = intent.putExtra(Constants.EXTRAS_URL, urls)

        fun fakeRequest(): String = "{\"id\":74}"

        fun fakeResponse(): String = "[{\"id\":78945,\"name\":\"John\"},{\"id\":25897,\"name\":\"Andrew\"},{\"id\":35981,\"name\":\"Joe\"}]"
    }
}