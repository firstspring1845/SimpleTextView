package net.firsp.textview.activity

import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ListView
import net.firsp.textview.R
import net.firsp.textview.TextViewApp
import net.firsp.textview.Util

abstract class ListViewActivity() : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val a = getApplication() as TextViewApp
        setTheme(a.getThemeResource())
        super<Activity>.onCreate(savedInstanceState)
        setContentView(R.layout.listview)
        val c = ColorDrawable(a.getBackgroundColor())
        val v = findViewById(R.id.listView) as ListView
        v.setBackground(c)
        v.setScrollingCacheEnabled(false)
        v.setFastScrollEnabled(true)
    }

    override fun onWindowFocusChanged(has: Boolean) {
        setBackgroundImage(PreferenceManager.getDefaultSharedPreferences(this).getString("background", ""))
    }

    fun setBackgroundImage(path: String) {
        try {
            val v = findViewById(R.id.listViewLayout)
            v.setBackground(BitmapDrawable(Util.loadBackgroundBitmap(path, v)))
        } catch(e: Exception) {
        }
    }

}
