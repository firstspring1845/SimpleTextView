package net.firsp.textview.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import java.io.File
import java.util.*

class FileListAdapter(val activity: Activity) : BaseAdapter() {

    val empty = arrayOf<File>()

    var currentDir = File("")
    var currentFiles = empty

    init {
        setDirectory(File("/"))
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val f = getItem(position)
        val v = activity.getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null) as TextView
        v.setText((if (f.isDirectory()) "D:" else "") + f.getName())
        return v
    }

    fun getDirectory() = currentDir

    fun setDirectory(dir: File) {
        try {
            currentDir = dir
            currentFiles = dir.listFiles() ?: empty
            Arrays.sort(currentFiles, {(f1, f2) ->
                when {
                    f1.isDirectory() && !f2.isDirectory() -> -1
                    !f1.isDirectory() && f2.isDirectory() -> 1
                    else -> f1.getName().compareTo(f2.getName())
                }
            })
            notifyDataSetChanged()
        } catch(e: Exception) {

        }
    }


    override fun getItem(position: Int) = currentFiles[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = currentFiles.size()

}