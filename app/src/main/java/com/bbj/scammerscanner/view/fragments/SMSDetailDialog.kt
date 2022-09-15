package com.bbj.scammerscanner.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.view.MainView

class SMSDetailDialog : DialogFragment(){

    companion object{
        val BODY_KEY = "body"
        val ADDRESS_KEY = "address"
    }

    private var body = ""
    private var address = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        body = arguments?.getString(BODY_KEY,"")!!
        address = arguments?.getString(ADDRESS_KEY,"")!!
        return inflater.inflate(R.layout.dialog_sms_detail,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (requireActivity()as MainView).setActionBarTitle(address)
        val bodyTextView = view.findViewById<TextView>(R.id.sms_detail_item_body)
        bodyTextView.text = body
    }

}