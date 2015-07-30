package net.firsp.textview.util

import android.util.Log

class Command(val name:String, val f:() -> Unit):Runnable{
    override fun run(){
        try{
            f()
        }catch(e:Exception){
            Log.e("SimpleTextView", "Command Error", e)
        }
    }

    override fun toString() = name

}
