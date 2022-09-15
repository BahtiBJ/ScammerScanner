package com.bbj.scammerscanner.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.data.room.MaybeScammerNumbers
import com.bbj.scammerscanner.data.room.ScammerNumbers
import com.bbj.scammerscanner.data.room.SuspiciousNumbers
import com.bbj.scammerscanner.view.MainViewModel
import com.bbj.scammerscanner.view.adapters.CallLogsAdapter
import java.time.LocalDate

class CallLogFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    private var lastChosenNumber = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callList = view.findViewById<RecyclerView>(R.id.calls_calls_list)

        viewModel.liveCallLogs.observe(viewLifecycleOwner) {
            val adapter = CallLogsAdapter(requireContext(), it,object : CallLogsAdapter.OnDotsClick {
                override fun click(view: View, number: String) {
                    lastChosenNumber = number
                    registerForContextMenu(view)
                    view.performLongClick()
                }
            })
            callList.adapter = adapter
        }
        viewModel.claimCallLogs()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        MenuInflater(requireContext()).inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val today = LocalDate.now().toString()
        when (item.itemId) {
            R.id.to_scammer -> {
                viewModel.insertIntoScammers(
                    ScammerNumbers(lastChosenNumber, today))
                    return true
            }
            R.id.to_maybe_scammer -> {
                viewModel.insertIntoMaybeScammers(
                    MaybeScammerNumbers(lastChosenNumber, today)
                )
                return true
            }
            R.id.to_suspicious -> {
                viewModel.insertIntoSuspicious(
                    SuspiciousNumbers(lastChosenNumber, today))
                return true
            }
            else -> super.onContextItemSelected(item)
        }
        return super.onContextItemSelected(item)
    }

}