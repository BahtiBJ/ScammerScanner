package com.bbj.scammerscanner.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.data.PreferenceManager
import com.bbj.scammerscanner.view.fragments.NumberListFragment
import com.bbj.scammerscanner.view.fragments.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreferencesActivity : AppCompatActivity() {

    private val viewModel by viewModels<PreferenceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val toolbar : Toolbar = findViewById(R.id.preference_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        supportFragmentManager.beginTransaction()
            .replace(R.id.setting_activity_fragment_Container, SettingFragment())
            .commit()
    }

    fun navigateToNumberActivity(bundle : Bundle){
        supportFragmentManager.beginTransaction()
            .replace(R.id.setting_activity_fragment_Container, NumberListFragment().apply {
                arguments = bundle
            }).addToBackStack(null)
            .setCustomAnimations(R.anim.slide_up_enter,R.anim.slide_down_exit)
            .commit()
    }


}