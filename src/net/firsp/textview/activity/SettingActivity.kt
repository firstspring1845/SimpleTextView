package net.firsp.textview.activity

import android.os.Bundle
import android.preference.PreferenceActivity
import net.firsp.textview.R
import net.firsp.textview.TextViewApp

class SettingActivity() : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme((getApplication() as TextViewApp).getThemeResource())
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }
}
