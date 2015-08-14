package net.firsp.textview.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import net.firsp.textview.R
import net.firsp.textview.TextViewApp
import net.firsp.textview.adapter.FileListAdapter
import net.firsp.textview.dialog.FavDialog
import net.firsp.textview.dialog.FileDialog
import java.io.File

class FileListActivity() : ListViewActivity(), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    val adapter = FileListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super<ListViewActivity>.onCreate(savedInstanceState)

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
            val ja = encodeMenu.addSubMenu(0, 1025, 0, "Japanese")
            ja.add(0, 1, 0, "EUC-JP")
            ja.add(0, 2, 1, "ShiftJIS")
            ja.add(0, 3, 2, "ISO-2022-JP")
            menu.add(1, 0, 1, "Bookmark")
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val a = getApplication() as TextViewApp
        if (item != null) {
            when (item.getGroupId()) {
                0 -> when (item.getItemId()) {
                    0 -> a.encoding = "UTF-8"
                    1 -> a.encoding = "EUC_JP"
                    2 -> a.encoding = "SJIS"
                    3 -> a.encoding = "ISO2022JP"
                }
                1 -> FavDialog().show(getFragmentManager(), "Fav")
            }
        }
        return true
    }
}


