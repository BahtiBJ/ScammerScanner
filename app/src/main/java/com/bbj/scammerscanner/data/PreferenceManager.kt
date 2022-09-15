package com.bbj.scammerscanner.data

import android.content.Context

class PreferenceManager(context : Context) {

    companion object{
        val PREF_NAME = "settings"
        val DISABLE_SCAMMERKEY = "disableScammers"
        val NOTIFICATION_KEY = "notification"
    }

    val sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)

    fun getScammerEnabledState() : Boolean{
        return sharedPref.getBoolean(DISABLE_SCAMMERKEY,false)
    }

    fun getNotificationEnabledState() : Boolean{
        return sharedPref.getBoolean(NOTIFICATION_KEY,false)
    }

    fun setScammerEnabledState(state : Boolean){
        sharedPref.edit().putBoolean(DISABLE_SCAMMERKEY,state).apply()
    }

    fun setNotificationEnabledState(state : Boolean){
        sharedPref.edit().putBoolean(NOTIFICATION_KEY,state).apply()
    }

}