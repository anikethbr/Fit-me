package com.example.fitme.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitme.Adapter.DashboardAdapter
import com.example.fitme.R
import com.example.fitme.model.DashboardModel

class DashboardFragment : Fragment() {

//    variables for the layout elements
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var dashboardAdapter: DashboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

//        list for the focus areas with name and image
        val itemList: List<DashboardModel> = listOf(
            DashboardModel("Full Body", R.drawable.ic_full_body),
            DashboardModel("Abs", R.drawable.ic_abs),
            DashboardModel("Arms", R.drawable.ic_arms),
            DashboardModel("Chest", R.drawable.ic_chest),
            DashboardModel("Legs", R.drawable.ic_legs),
        )

//        initializing the recycler view and layout manager
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        layoutManager = LinearLayoutManager(activity)
        dashboardAdapter = DashboardAdapter(activity as Context, itemList)

//        setting the adapter and layout manager for the recycler
        recyclerDashboard.adapter = dashboardAdapter
        recyclerDashboard.layoutManager = layoutManager

        return view
    }

}