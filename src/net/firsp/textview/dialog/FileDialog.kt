package net.firsp.textview.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import net.firsp.textview.activity.ListViewActivity
import net.firsp.textview.util.Command
import java.io.File

class FileDialog() : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog? {
        val a = getActivity() as ListViewActivity
        val p = getArguments().getString("path")

        val v = ListView(getActivity())
        val c = arrayOf(
                Command("Set Background Image", {
                    val e = a.getSharedPreferences("config", Context.MODE_PRIVATE).edit()
                    e.putString("background", p)
                    e.commit()
                    a.setBackgroundImage(p)
                }),
                Command("Send Intent", {

                }))
        v.setAdapter(ArrayAdapter(a, android.R.layout.simple_list_item_1, c))

        val d = AlertDialog.Builder(getActivity())
        .setView(v)
        .create()

        v.setOnItemClickListener { adapterView, view, i, l ->
            (adapterView.getItemAtPosition(i) as Command).run()
            dismiss()
        }

        return d
    }
}
