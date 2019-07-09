package com.siziksu.services.commons

import android.text.TextUtils
import android.util.Log

class Commons {

    companion object {

        fun log(owner: String, message: String, flag: String = "") =
            Log.d("LOG", "<message owner=\"$owner${(if (!TextUtils.isEmpty(flag)) " flag=\"$flag\"" else "")}>$message</message>")

        fun error(e: Exception) = Log.e("LOG", e.message, e)
    }
}
