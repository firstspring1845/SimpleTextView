package net.firsp.textview

import android.app.Application
import android.graphics.Color
import android.preference.PreferenceManager
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class TextViewApp() : Application() {

    var encoding = "UTF-8"
    var themeId = 0

    fun getTextColor(): Int {
        return when (themeId) {
            0 -> Color.rgb(0, 0, 0)
            else -> Color.rgb(255, 255, 255)
        }
    }

    fun getBackgroundColor(alpha: Int = 128): Int {
        return when (themeId) {
            0 -> Color.argb(alpha, 255, 255, 255)
            else -> Color.argb(alpha, 0, 0, 0)
        }
    }

    fun getThemeResource(): Int {
        return when (themeId) {
            0 -> android.R.style.Theme_Holo_Light
            else -> android.R.style.Theme_Holo
        }
    }

    override fun onCreate() {
        themeId = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.theme_setting), "0").toInt()
        val ue = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            val f = File(getExternalFilesDir(null), "exception.txt")
            try {
                BufferedWriter(FileWriter(f, true)).use {
                    it.write(e.toString())
                    it.newLine()
                    e.getStackTrace().forEach { el ->
                        it.write(el.toString())
                        it.newLine()
                    }
                }
            } catch(e: Exception) {
                android.util.Log.d("hoge", "hoge~~")
            }
            ue.uncaughtException(t, e)
        }
    }
}
