package com.example.fitme.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitme.R
import com.example.fitme.database.HisEntity

//adapter class for the history recycler view
class HistoryAdapter(val context: Context, val itemList: List<HisEntity>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        variables for the layout elements in the single row
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvArea: TextView = view.findViewById(R.id.tvArea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
//        inflating and returning the view
        val view = LayoutInflater.from(context)
            .inflate(R.layout.recycler_history_single_row, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
//        setting the contents of the text and image for each row
        val history = itemList[position]
        holder.tvDate.text = history.hisDate
        holder.tvArea.text = history.hisArea
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}