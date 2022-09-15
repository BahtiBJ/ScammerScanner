package com.bbj.scammerscanner.view

import android.Manifest
import android.app.role.RoleManager
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.view.adapters.PagerStateAdapter
import com.bbj.scammerscanner.view.fragments.SMSDetailDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),MainView {

    private val viewModel: MainViewModel by viewModels()

    private val toolbarTitle : TextView by lazy { findViewById(R.id.toolbar_title) }

    val requestCode = 1

    private val pager : ViewPager2 by lazy {findViewById(R.id.main_viewpager)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestRole()
        requestAllPermissions()

    }

    fun updateUI(){
        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val pager = findViewById<ViewPager2>(R.id.main_viewpager)
        pager.adapter = PagerStateAdapter(this)
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                toolbarTitle.text = when(position){
                    0 -> "Вызовы"
                    1 -> "СМС"
                    else -> ""
                }
                super.onPageSelected(position)
            }
        })

        val toolbarSettingButton = findViewById<ImageButton>(R.id.toolbar_setting_button)
        toolbarSettingButton.setOnClickListener {
            navigateToSettings()
        }

        val tabLayout = findViewById<TabLayout>(R.id.main_tablayout)
        TabLayoutMediator(tabLayout,pager){tab,position ->
            when (position){
                0 -> {
                    tab.setText(resources.getString(R.string.calls_title))
                }
                1 -> {
                    tab.setText(resources.getString(R.string.sms_title))
                }
            }
        }.attach()

    }

    @AfterPermissionGranted(permissionsID)
    fun requestAllPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_STATE
        )
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            updateUI()
        } else {
            EasyPermissions.requestPermissions(this, "", permissionsID, *permissions)
        }
    }

    val REQUEST_ID = 1

    fun requestRole() {
        val roleManager = getSystemService(ROLE_SERVICE) as RoleManager
        if (!roleManager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING)) {
            val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
            startActivityForResult(intent, REQUEST_ID)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun setActionBarTitle(text : String){
        toolbarTitle.text = text
    }

    override fun navigateToSettings(){
        startActivity(Intent(this,PreferencesActivity::class.java))
        overridePendingTransition(R.anim.slide_up_enter,R.anim.zoom_out_exitr)
    }

    override fun navigateToSmsDetail(bundle: Bundle){
        SMSDetailDialog().apply { arguments = bundle }.show(supportFragmentManager,"dialog")
    }

    companion object {
        private const val permissionsID = 24
    }
}