package net.firsp.textview.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import net.firsp.textview.TextViewApp

class TextViewAdapter(val activity: Activity) : BaseAdapter() {

    var data = Array<String>(0, { "" })

    fun setText(arr: Array<String>) {
        data = arr
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val v = TextView(activity)
        v.setTextColor((activity.getApplication() as TextViewApp).getTextColor())
        v.setText(getItem(position).toString())
        return v
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size()
}