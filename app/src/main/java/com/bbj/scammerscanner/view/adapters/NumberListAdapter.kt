package com.bbj.scammerscanner.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbj.scammerscanner.R

class NumberListAdapter(context: Context) :
    RecyclerView.Adapter<NumberListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from((context))
    private val numberList : ArrayList<String> = arrayListOf()

    fun addData(list : List<String>){
        numberList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_number_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.number.text = numberList[position].toString()
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number = view.findViewById<TextView>(R.id.number_item_number)
    }

}