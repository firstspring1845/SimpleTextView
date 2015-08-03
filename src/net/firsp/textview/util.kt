package net.firsp.textview

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.preference.PreferenceManager
import android.view.View
import java.util.*

object Util {

    var lastImageName = ""
    var lastImage = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565)

    fun loadBackgroundBitmap(path: String, view: View?): Bitmap {
        if (path.equals(lastImageName)) return lastImage
        val o = BitmapFactory.Options()
        o.inPreferredConfig = Bitmap.Config.RGB_565

        if (view != null) {
            o.inJustDecodeBounds = true

            //get image size
            BitmapFactory.decodeFile(path, o)

            val wscale = (o.outWidth / view.getWidth()).toInt()
            val hscale = (o.outHeight / view.getHeight()).toInt()

            o.inSampleSize = Math.max(wscale, hscale)
            o.inJustDecodeBounds = false
        }

        val img = BitmapFactory.decodeFile(path, o)
        lastImageName = path
        lastImage = img
        return img
    }

    fun getFavorites(pref: SharedPreferences): ArrayList<String> {
        val s = pref.getString("fav", "")
        if (s.length() == 0) return arrayListOf()
        return s.splitBy("|").toArrayList()
    }

    fun addFavorite(pref: SharedPreferences, path: String) {
        val fav = getFavorites(pref)
        fav.add(path)
        val e = pref.edit()
        e.putString("fav", fav.joinToString("|"))
        e.commit()
    }

    fun removeFavorite(pref: SharedPreferences, path: String) {
        val fav = getFavorites(pref)
        fav.remove(path)
        val e = pref.edit()
        e.putString("fav", fav.joinToString("|"))
        e.commit()
    }

}
