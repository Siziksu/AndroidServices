package com.siziksu.services.commons.mock

import android.content.Intent
import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import java.net.URL

class Mock {

    companion object {
        val urls: Array<URL> = arrayOf(URL("http://www.amazon.com/file.pdf"),
                                       URL("http://www.wrox.com/file.pdf"),
                                       URL("http://www.google.com/file.pdf"),
                                       URL("http://www.learn2develop.net/file.pdf"))

        fun pause(time: Long) {
            try {
                // Simulate taking some time to download a file
                Thread.sleep(time)
            } catch (e: InterruptedException) {
                Commons.error(e)
            }
        }

        fun putUrls(intent: Intent): Intent? {
            try {
                val urls = urls
                return intent.putExtra(Constants.EXTRAS_URL, urls)
            } catch (e: Exception) {
                Commons.error(e)
            }

            return null
        }

        fun fakeRequest(): String {
            return "{\"id\":74}"
        }

        fun fakeResponse(): String {
            return "[{\"id\":78945,\"name\":\"John\"},{\"id\":25897,\"name\":\"Andrew\"},{\"id\":35981,\"name\":\"Joe\"}]"
        }
    }
}