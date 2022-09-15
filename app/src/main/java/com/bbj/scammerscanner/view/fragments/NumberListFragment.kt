package com.bbj.scammerscanner.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.data.models.NumberType
import com.bbj.scammerscanner.view.MainViewModel
import com.bbj.scammerscanner.view.PreferenceViewModel
import com.bbj.scammerscanner.view.adapters.NumberListAdapter

class NumberListFragment : Fragment(){

    companion object{
        val TYPE_KEY = "type"
    }

    private val viewModel by viewModels<PreferenceViewModel>()
    private var numbersType : NumberType = NumberType.DEFAULT

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        numbersType = NumberType.valueOf(arguments?.getString(TYPE_KEY,NumberType.DEFAULT.toString())!!)
        return inflater.inflate(R.layout.fragment_numbers,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numbersList = view.findViewById<RecyclerView>(R.id.number_number_list)
        val adapter = NumberListAdapter(requireContext())
        numbersList.adapter = adapter

        when(numbersType){
            NumberType.DEFAULT -> {}
            NumberType.SCAMMER -> {
                viewModel.liveScammerNumbers.observe(viewLifecycleOwner){
                    adapter.addData(it.map { it.number })
                }
            }
            NumberType.MAYBE_SCAMMER -> {
                viewModel.liveMaybeScammerNumbers.observe(viewLifecycleOwner) {
                    adapter.addData(it.map { it.number })
                }
            }
            NumberType.SUSPICIOUS -> {
                viewModel.liveSuspiciousNumbers.observe(viewLifecycleOwner) {
                    adapter.addData(it.map { it.number })
                }
            }
        }
    }



}