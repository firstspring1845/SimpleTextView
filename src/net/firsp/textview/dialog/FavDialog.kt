package net.firsp.textview.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import net.firsp.textview.R
import net.firsp.textview.Util
import net.firsp.textview.activity.FileListActivity
import net.firsp.textview.activity.ListViewActivity
import net.firsp.textview.util.Command
import java.io.File

class FavDialog() : DialogFragment() {

    var adapter: ArrayAdapter<String>? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog? {
        val a = getActivity() as FileListActivity

        val v = ListView(getActivity())
        val l = Util.getFavorites(PreferenceManager.getDefaultSharedPreferences(a)).toArrayList()
        adapter = ArrayAdapter<String>(a, android.R.layout.simple_list_item_1, l)
        v.setAdapter(adapter)

        val img = ImageView(a)
        img.setImageResource(android.R.drawable.ic_menu_add)
        v.addFooterView(img)

        val d = AlertDialog.Builder(getActivity())
                .setTitle("Favorites")
                .setView(v)
                .create()

        v.setOnItemClickListener { adapterView, view, i, l ->
            //addButton
            if (adapter!!.getCount() == i) {
                val path = a.adapter.getDirectory().getAbsolutePath()
                Util.addFavorite(PreferenceManager.getDefaultSharedPreferences(a), path)
                adapter!!.add(path)
            } else {
                var path = adapterView.getItemAtPosition(i).toString()
                a.adapter.setDirectory(File(path))
                (a.findViewById(R.id.listView) as ListView).setSelection(0)
                dismiss()
            }
        }

        v.setOnItemLongClickListener { adapterView, view, i, l ->
            //disable addButton LongClick
            if (adapter!!.getCount() == i) false
            else {
                val path = adapterView.getItemAtPosition(i).toString()
                Util.removeFavorite(PreferenceManager.getDefaultSharedPreferences(a), path)
                adapter!!.remove(path)
                true
            }
        }

        return d
    }
}