package com.bbj.scammerscanner.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.data.models.SMSModel
import com.bbj.scammerscanner.view.MainView
import com.bbj.scammerscanner.view.viewmodels.MainViewModel
import com.bbj.scammerscanner.view.adapters.SMSListAdapter

class SmsListFragment : Fragment(){

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sms_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainView).setActionBarTitle(resources.getString(R.string.sms_title))

        val smsList = view.findViewById<RecyclerView>(R.id.sms_sms_list)

        viewModel.liveSmsModels.observe(viewLifecycleOwner){
            smsList.adapter = SMSListAdapter(requireContext(),it, object : SMSListAdapter.onSmsClick {
                override fun click(sms: SMSModel) {
                    (requireActivity() as MainView).navigateToSmsDetail(Bundle().apply {
                        putString(SMSDetailDialog.BODY_KEY,sms.body)
                        putString(SMSDetailDialog.ADDRESS_KEY,sms.address)
                    })
                }
            })
        }

        viewModel.claimSms()
    }



}