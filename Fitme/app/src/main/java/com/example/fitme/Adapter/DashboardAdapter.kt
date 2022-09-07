package com.example.fitme.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitme.Activity.ExerciseActivity
import com.example.fitme.R
import com.example.fitme.model.DashboardModel

//adapter class for the dashboard recycler view
class DashboardAdapter(val context: Context, private val itemList: List<DashboardModel>) :
    RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

//    viewHolder for the dashboard recycler
    class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//    variables for the layout elements in the single row
        val imgExercise: ImageView = view.findViewById(R.id.imgExercise)
        val tvExerciseName: TextView = view.findViewById(R.id.tvExerciseName)
        val liContent: CardView = view.findViewById(R.id.liContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
//        inflating and returning the view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_dashboard_single_row, parent, false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
//        setting the contents of the text and image for each row
        val exercise = itemList[position]
        holder.tvExerciseName.text = exercise.exerciseName
        holder.imgExercise.setImageResource(exercise.exerciseImage)
//        click listener for each recycler view item
        holder.liContent.setOnClickListener {
//            intent to the exercise activity along with passing the focus area with the intent
            val intent = Intent(context, ExerciseActivity::class.java)
            intent.putExtra("area", holder.tvExerciseName.text)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
//        returns the size of the list
        return itemList.size
    }
}