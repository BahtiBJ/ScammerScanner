package com.bbj.scammerscanner.view

import android.os.Bundle

interface MainView {

    fun setActionBarTitle(text : String)

    fun navigateToSettings()

    fun navigateToSmsDetail(bundle: Bundle)



}