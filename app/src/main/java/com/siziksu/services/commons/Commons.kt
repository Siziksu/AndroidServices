package com.siziksu.services.commons

import android.text.TextUtils
import android.util.Log
import java.util.concurrent.TimeUnit

class Commons {

    companion object {

        fun log(owner: String, message: String, flag: String = "") =
            Log.d("LOG", "<message owner=\"$owner${(if (!TextUtils.isEmpty(flag)) " flag=\"$flag\"" else "")}>$message</message>")

        fun error(e: Exception) = Log.e("LOG", e.message, e)

        fun getTime(seconds: Long): String = millisToStringTime(seconds * 1000)

        private fun millisToStringTime(millis: Long): String = String.format("%02d:%02d:%02d",
                                                                             TimeUnit.MILLISECONDS.toHours(millis) % 24,
                                                                             TimeUnit.MILLISECONDS.toMinutes(millis) % 60,
                                                                             TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        )
    }
}
