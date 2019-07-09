package com.siziksu.services.commons.mock

import com.siziksu.services.app.Constants
import com.siziksu.services.commons.Commons
import kotlin.random.Random

class Mock {

    companion object {

        val urls: Array<String> = arrayOf("http://www.amazon.com/file.pdf",
                                          "http://www.wrox.com/file.pdf",
                                          "http://www.google.com/file.pdf",
                                          "http://www.learn2develop.net/file.pdf",
                                          "http://www.britzel.com/file.pdf")

        fun pause(time: Long) {
            try {
                // Simulate taking some time to download a file
                Thread.sleep(time)
            } catch (e: InterruptedException) {
                Commons.error(e)
            }
        }

        fun downloadFile(url: String): Int {
            Commons.log(Constants.TAG_MOCK_FILE, "Downloading: $url")
            // Return an arbitrary number representing the size of the file downloaded
            return Random.nextInt(50, 250)
        }

        fun fakeRequest(): String = "{\"id\":74}"

        fun fakeResponse(): String = "[{\"id\":78945,\"name\":\"John\"},{\"id\":25897,\"name\":\"Andrew\"},{\"id\":35981,\"name\":\"Joe\"}]"
    }
}