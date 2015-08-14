package net.firsp.textview

import android.app.Application
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class TextViewApp() : Application() {

    var encoding = "UTF-8"

    override fun onCreate() {
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
