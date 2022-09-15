package com.bbj.scammerscanner.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.view.fragments.NumberListFragment
import com.bbj.scammerscanner.view.fragments.SettingFragment

class PreferencesActivity : AppCompatActivity() {

    private val viewModel by viewModels<PreferenceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

//        supportFragmentManager.beginTransaction()
//            .replace(R.id.setting_activity_fragment_Container, SettingFragment()).commit()
    }

    fun navigateToNumberActivity(bundle : Bundle){
        Toast.makeText(this,"CLICK",Toast.LENGTH_LONG).show()
        supportFragmentManager.beginTransaction()
            .add(R.id.setting_activity_fragment_Container, NumberListFragment().apply {
                arguments = bundle
            }).commit()
    }


}