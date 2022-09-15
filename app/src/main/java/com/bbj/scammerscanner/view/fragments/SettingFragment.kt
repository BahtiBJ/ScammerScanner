package com.bbj.scammerscanner.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.data.PreferenceManager
import com.bbj.scammerscanner.data.models.NumberType
import com.bbj.scammerscanner.view.activities.PreferencesActivity


class SettingFragment : Fragment() {

    private var notificationEnabled = false
    private var handleCalls = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("SettingFragment", "View created")

        notificationEnabled = PreferenceManager(requireContext()).getNotificationEnabledState()
        handleCalls = PreferenceManager(requireContext()).getScammerEnabledState()

        val disableScammerCheckBox =
            view.findViewById<CheckBox>(R.id.settings_scammer_disable_checkbox)
        disableScammerCheckBox.isChecked = handleCalls
        disableScammerCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            handleCalls = b
        }
        val showNotificationCheckBox =
            view.findViewById<CheckBox>(R.id.settings_notification_checkbox)
        showNotificationCheckBox.isChecked = notificationEnabled
        showNotificationCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            notificationEnabled = b
        }

        val scammerNumbersArea = view.findViewById<LinearLayout>(R.id.settings_scammer_numbers_area)
        scammerNumbersArea.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                (requireActivity() as PreferencesActivity).navigateToNumberActivity(Bundle().apply {
                    putString(
                        NumberListFragment.TYPE_KEY,
                        NumberType.SCAMMER.toString()
                    )
                })
                true
            } else
                false
        }
        val maybeScammerNumbersArea =
            view.findViewById<LinearLayout>(R.id.settings_maybe_scammer_numbers_area)
        maybeScammerNumbersArea.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                (requireActivity() as PreferencesActivity).navigateToNumberActivity(Bundle().apply {
                    putString(
                        NumberListFragment.TYPE_KEY,
                        NumberType.MAYBE_SCAMMER.toString()
                    )
                })
                true
            } else
                view.performClick()
        }
        val suspiciousNumbersArea =
            view.findViewById<LinearLayout>(R.id.settings_suspicious_numbers_area)
        suspiciousNumbersArea.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                (requireActivity() as PreferencesActivity).navigateToNumberActivity(Bundle().apply {
                    putString(
                        NumberListFragment.TYPE_KEY,
                        NumberType.SUSPICIOUS.toString()
                    )
                })
                true
            } else
                view.performClick()
        }

    }

    override fun onStop() {
        PreferenceManager(requireContext()).setScammerEnabledState(handleCalls)
        PreferenceManager(requireContext()).setNotificationEnabledState(notificationEnabled)
        super.onStop()
    }

}