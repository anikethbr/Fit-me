package com.example.fitme.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fitme.R
import kotlin.math.pow

//fragment for bmi calculation
class BmiFragment : Fragment() {

//    variabes for the layout elements
    private lateinit var etHeight: EditText
    private lateinit var etWeight: EditText
    private lateinit var btnCalculate: Button
    lateinit var tvBmi: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bmi, container, false)
//        initializing the layout element variables
        etHeight = view.findViewById(R.id.etHeight)
        etWeight = view.findViewById(R.id.etWeight)
        btnCalculate = view.findViewById(R.id.btnCalculate)
        tvBmi = view.findViewById(R.id.tvBmi)



        btnCalculate.setOnClickListener {
//            test for empty input fields
            if (etHeight.text.toString().isEmpty() || etWeight.text.toString().isEmpty()) {

                tvBmi.text = "Enter all fields!!"

            } else {
//                calling bmi calculation function with the user input
                tvBmi.text = calcBmi(
                    Integer.parseInt(etHeight.text.toString()).toDouble() / 100,
                    Integer.parseInt(etWeight.text.toString()).toDouble()
                )
            }

        }


        return view
    }

//    function to calculate bmi
    fun calcBmi(height: Double, weight: Double): String {
        val bmi = weight / (height.pow(2.0))
//        setting the message according to the calculated value of the bmi
        when {
            bmi <= 18.5 -> {
                return "Underweight \n\n $bmi"
            }
            bmi > 18.5 && bmi <= 24.9 -> {
                return "Normal \n\n $bmi"
            }
            bmi > 24.9 && bmi <= 29.9 -> {
                return "Overweight \n\n $bmi"
            }
            bmi > 29.9 && bmi <= 39.9 -> {
                return "Obese \n\n $bmi"
            }
        }
        return "Invalid Input"
    }

}