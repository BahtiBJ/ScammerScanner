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
    val numberList : ArrayList<String> = arrayListOf<String>().apply { add("Список пуст") }

    fun addData(list : List<String>){
        numberList.clear()
        numberList.addAll(list)
        notifyDataSetChanged()
    }

    fun addElement(element : String){
        numberList.add(element)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_number_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.number.text = numberList[position]
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number = view.findViewById<TextView>(R.id.number_item_number)
    }

}