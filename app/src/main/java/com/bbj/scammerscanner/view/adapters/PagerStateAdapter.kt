package com.bbj.scammerscanner.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bbj.scammerscanner.view.fragments.CallLogFragment
import com.bbj.scammerscanner.view.fragments.SmsListFragment

class PagerStateAdapter(fragmentActivity : FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position){
            0 -> return CallLogFragment()
            1 -> return SmsListFragment()
            else -> error("UNKNOWN STATE")
        }
    }
}