package com.example.fitme.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitme.R
import java.util.*


class ProfileFragment : Fragment() {
//    variables for the layout elements
    lateinit var tvName: TextView
    lateinit var tvAge: TextView
    lateinit var tvHeight: TextView
    lateinit var tvWeight: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//        initializing the layout elements
        tvName = view.findViewById(R.id.tvName)
        tvAge = view.findViewById(R.id.tvAge)
        tvHeight = view.findViewById(R.id.tvHeight)
        tvWeight = view.findViewById(R.id.tvWeight)

//        getting the shared preferences file
        val sharedPreferences = activity?.getSharedPreferences(
            "fitme_preferences",
            AppCompatActivity.MODE_PRIVATE
        )

//        setting the text for the textViews from the sharedpreferences
        tvName.text = "Name: ${sharedPreferences?.getString("name", null).toString()
            .uppercase(Locale.getDefault())}"
        tvAge.text = "Age: ${sharedPreferences?.getString("age", null)}"
        tvHeight.text = "Height: ${sharedPreferences?.getString("height", null)} Cm"
        tvWeight.text = "Weight: ${sharedPreferences?.getString("weight", null)} Kg"

        return view
    }

}