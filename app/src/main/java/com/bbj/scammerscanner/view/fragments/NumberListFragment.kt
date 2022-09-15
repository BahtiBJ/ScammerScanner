package com.bbj.scammerscanner.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.data.models.NumberType
import com.bbj.scammerscanner.view.viewmodels.PreferenceViewModel
import com.bbj.scammerscanner.view.adapters.NumberListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NumberListFragment : Fragment() {

    companion object {
        val TYPE_KEY = "type"
    }

    private val viewModel: PreferenceViewModel by viewModels()
    private var numbersType: NumberType = NumberType.DEFAULT

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        numbersType = NumberType.valueOf(
            arguments?.getString(TYPE_KEY, NumberType.DEFAULT.toString())
                ?: NumberType.DEFAULT.toString()
        )
        return inflater.inflate(R.layout.fragment_numbers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("NUMBERLIST", "Craate number list")

        val numbersList = view.findViewById<RecyclerView>(R.id.number_number_list)
        numbersList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        val adapter = NumberListAdapter(requireContext())
        numbersList.adapter = adapter

        when (numbersType) {
            NumberType.DEFAULT -> {}
            NumberType.SCAMMER -> {
                viewModel.liveScammerNumbers.observe(viewLifecycleOwner) { it ->
                    adapter.addData(it.map { it.number })
                    if (adapter.itemCount == 0)
                        adapter.addElement("Список пуст")
                }
                viewModel.claimScammerNumbers()
            }
            NumberType.MAYBE_SCAMMER -> {
                viewModel.liveMaybeScammerNumbers.observe(viewLifecycleOwner) {
                    adapter.addData(it.map { it.number })
                    if (adapter.itemCount == 0)
                        adapter.addElement("Список пуст")
                }
                viewModel.claimMaybeScammerNumbers()
            }
            NumberType.SUSPICIOUS -> {
                viewModel.liveSuspiciousNumbers.observe(viewLifecycleOwner) {
                    adapter.addData(it.map { it.number })
                    if (adapter.itemCount == 0)
                        adapter.addElement("Список пуст")
                }
                viewModel.claimSuspiciousNumbersNumbers()
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem =
                    adapter.numberList.get(viewHolder.adapterPosition)
                val position = viewHolder.adapterPosition
                viewModel.deleteFromDB(deletedItem, numbersType)
                adapter.numberList.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }).attachToRecyclerView(numbersList)
    }


}