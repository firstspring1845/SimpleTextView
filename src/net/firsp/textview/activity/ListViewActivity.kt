package net.firsp.textview.activity

import android.app.Activity
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ListView
import net.firsp.textview.R
import net.firsp.textview.Util

abstract class ListViewActivity() : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)
        setContentView(R.layout.listview)
        val v = findViewById(R.id.listView) as ListView
        v.setScrollingCacheEnabled(false)
        v.setFastScrollEnabled(true)
    }

    override fun onWindowFocusChanged(has:Boolean) {
        setBackgroundImage(getSharedPreferences("config", Context.MODE_PRIVATE).getString("background", ""))
    }

    fun setBackgroundImage(path:String){
        try{
            val v = findViewById(R.id.listViewLayout)
            v.setBackground(BitmapDrawable(Util.loadBackgroundBitmap(path, v)))
        }catch(e:Exception){
        }
    }

}
