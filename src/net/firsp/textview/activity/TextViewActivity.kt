package net.firsp.textview.activity

import android.app.Activity
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ListView
import net.firsp.textview.R
import net.firsp.textview.Util
import net.firsp.textview.adapter.TextViewAdapter
import java.io.BufferedReader
import java.io.FileReader

class TextViewActivity() : ListViewActivity(){

    val adapter = TextViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super<ListViewActivity>.onCreate(savedInstanceState)
        val v = findViewById(R.id.listView) as ListView
        v.setAdapter(adapter)
        v.setDivider(null)
        adapter.setText(readFile())
    }

    fun readFile() : Array<String> {
        return try{
            BufferedReader(FileReader(getIntent().getStringExtra("file"))).use{
                val l = linkedListOf<String>()
                var s = it.readLine()
                while(s != null){
                    l.add(s)
                    s = it.readLine()
                }
                l.toArray(arrayOf<String>())
            }
        }catch(e:Exception){
            arrayOf("Error:Reading File")
        }
    }
}
