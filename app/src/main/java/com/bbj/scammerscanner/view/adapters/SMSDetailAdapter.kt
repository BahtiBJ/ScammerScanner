package com.bbj.scammerscanner.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.data.models.SMSModel

class SMSDetailAdapter(context: Context, private val numberList: ArrayList<SMSModel>) :
    RecyclerView.Adapter<SMSDetailAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from((context))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  inflater.inflate(R.layout.item_sms_detail,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.smsBody.text = numberList[position].body
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val smsBody = view.findViewById<TextView>(R.id.sms_detail_item_body)
    }

}