package net.firsp.textview.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import net.firsp.textview.R
import net.firsp.textview.Util
import net.firsp.textview.adapter.FileListAdapter
import net.firsp.textview.dialog.FileDialog
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class FileListActivity() : ListViewActivity(), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    val adapter = FileListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super<ListViewActivity>.onCreate(savedInstanceState)
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
        val v = findViewById(R.id.listView) as ListView
        v.setAdapter(adapter)
        v.setOnItemClickListener(this)
        v.setOnItemLongClickListener(this)
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val f = parent!!.getItemAtPosition(position) as File
        when {
            f.isDirectory() -> {
                adapter.setDirectory(f)
                (findViewById(R.id.listView) as ListView).setSelection(0)
            }
            f.isFile() -> {
                val i = Intent(this, javaClass<TextViewActivity>())
                i.putExtra("file", f.getAbsolutePath())
                startActivity(i)
            }
        }
    }

    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        val p = (parent!!.getItemAtPosition(position) as File).getAbsolutePath()
        val d = FileDialog()
        val b = Bundle()
        b.putString("path", p)
        d.setArguments(b)
        d.show(getFragmentManager(), "File")
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!File("/").equals(adapter.getDirectory())) {
                val p = adapter.getDirectory().getParentFile()
                adapter.setDirectory(p)
                (findViewById(R.id.listView) as ListView).setSelection(0)
                return true
            }
        }
        return super<ListViewActivity>.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            val encodeMenu = menu.addSubMenu(0, 1024, 0, "Encode")
            encodeMenu.add(0, 0, 0, "UTF-8")
            val ja = encodeMenu.addSubMenu(0, 1025, 1, "Japanese")
            ja.add(0, 1, 0, "EUC-JP")
            ja.add(0, 2, 1, "ShiftJIS")
            ja.add(0, 3, 2, "ISO-2022-JP")
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val e = PreferenceManager.getDefaultSharedPreferences(this).edit()
        if (item != null) {
            when (item.getGroupId()) {
                0 -> when (item.getItemId()) {
                    0 -> e.putString("encode", "UTF-8")
                    1 -> e.putString("encode", "EUC_JP")
                    2 -> e.putString("encode", "SJIS")
                    3 -> e.putString("encode", "ISO2022JP")
                }
            }
        }
        e.commit()
        return true
    }
}

