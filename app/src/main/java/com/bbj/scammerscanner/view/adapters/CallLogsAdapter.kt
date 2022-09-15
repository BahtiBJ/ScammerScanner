package com.bbj.scammerscanner.view.adapters

import android.content.Context
import android.provider.CallLog
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.data.models.CallInfo

class CallLogsAdapter(context: Context, private val callsList: ArrayList<CallInfo>,private val onDotsClick : OnDotsClick) :
    RecyclerView.Adapter<CallLogsAdapter.ViewHolder>() {

    interface OnDotsClick{
        fun click(view: View,number : String)
    }

    private val inflater = LayoutInflater.from((context))
    private val res = context.resources

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_call_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.number.text = callsList[position].number
        holder.date.text = callsList[position].date
        when (callsList[position].type){
            CallLog.Calls.BLOCKED_TYPE -> {
                holder.type.run {
                    setText(R.string.blocked)
                    setTextColor(res.getColor(R.color.red))
                }
            }
            CallLog.Calls.INCOMING_TYPE -> holder.type.setText(R.string.incoming)
            CallLog.Calls.OUTGOING_TYPE -> holder.type.setText(R.string.outgoing)
        }

        holder.itemView.setOnClickListener {
            onDotsClick.click(it,callsList[position].number)
        }
    }

    override fun getItemCount(): Int {
        return callsList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number = view.findViewById<TextView>(R.id.calls_item_number)
        val date = view.findViewById<TextView>(R.id.calls_item_date)
        val type = view.findViewById<TextView>(R.id.calls_item_type)

    }

}