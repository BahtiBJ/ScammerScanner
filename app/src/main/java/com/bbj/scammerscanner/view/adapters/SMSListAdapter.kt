package com.bbj.scammerscanner.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.data.models.SMSModel
import com.bbj.scammerscanner.util.decreaseToSize

class SMSListAdapter(
    context: Context,
    private val smsList: ArrayList<SMSModel>,
    private val smsClick: onSmsClick
) :
    RecyclerView.Adapter<SMSListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from((context))

    interface onSmsClick {
        fun click(sms: SMSModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_sms_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.address.text = smsList[position].address
        holder.bodyPreview.text = smsList[position].body.decreaseToSize(50)
        holder.itemView.setOnClickListener {
            smsClick.click(smsList[position])
        }
    }

    override fun getItemCount(): Int {
        return smsList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val address = view.findViewById<TextView>(R.id.sms_list_item_address)
        val bodyPreview = view.findViewById<TextView>(R.id.sms_list_item_body_preview)
    }

}