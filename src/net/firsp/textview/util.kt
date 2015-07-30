package net.firsp.textview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View

object Util{

    var lastImageName = ""
    var lastImage = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565)

    fun loadBackgroundBitmap(path:String, view: View?) : Bitmap {
        if(path.equals(lastImageName)) return lastImage
        val o = BitmapFactory.Options()
        o.inPreferredConfig = Bitmap.Config.RGB_565

        if(view != null){
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

}