package com.example.fitme.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.fitme.Activity.MainActivity
import com.example.fitme.Adapter.HistoryAdapter
import com.example.fitme.R
import com.example.fitme.database.HisDatabase
import com.example.fitme.database.HisEntity

class HistoryFragment : Fragment() {

//    variables for the layout elements
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var historyAdapter: HistoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

//        initializing the recycler view and layout manager
        recyclerView = view.findViewById(R.id.recyclerHistory)
        layoutManager = LinearLayoutManager(activity)

//        retrieving all entries from database
        val itemList = DBAsyncTask(activity as Context).execute().get()
        if (itemList.isEmpty()) {
//            alert for empty history
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Your history is empty!")
            dialog.setPositiveButton("OK") { text, listener ->
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
            dialog.setOnDismissListener {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
            dialog.create()
            dialog.show()
        }
        historyAdapter = HistoryAdapter(activity as Context, itemList)

//        setting the adapter and layout manager for the recycler
        recyclerView.adapter = historyAdapter
        recyclerView.layoutManager = layoutManager

//        for the divider decoration
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                layoutManager.orientation
            )
        )

        return view
    }

    class DBAsyncTask(val context: Context) : AsyncTask<Void, Void, List<HisEntity>>() {

        private val db = Room.databaseBuilder(context, HisDatabase::class.java, "his_db").build()
        override fun doInBackground(vararg p0: Void?): List<HisEntity> {
            return db.hisDao().getAll()
        }

    }

}