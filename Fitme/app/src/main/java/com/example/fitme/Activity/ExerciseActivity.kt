package com.example.fitme.Activity

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.fitme.R
import com.example.fitme.database.HisDatabase
import com.example.fitme.database.HisEntity
import com.example.fitme.model.DashboardModel
import java.text.SimpleDateFormat
import java.util.*


class ExerciseActivity : AppCompatActivity() {

    //    variables for layout elements
    private lateinit var imgExercise: ImageView
    private lateinit var tvExerciseName: TextView
    private lateinit var btnNext: Button
    private lateinit var tvCounter: TextView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    //    counter for the number of exercises completed
    private var counter = 0

    //    index of the exercises list
    var index = 0

    //    timer for the counter to be displayed
    var timer: CountDownTimer? = null

    //    date to be added to the history database
    private val sdf = SimpleDateFormat("dd/M/yyyy  hh:mm:ss")
    private val currentDate: String = sdf.format(Date())


    //    list of exercises for each focus area
    private var exercises: ArrayList<List<DashboardModel>> = arrayListOf(
        listOf(
            DashboardModel("Jumping Jacks", R.drawable.ic_jumping_jacks),
            DashboardModel("Push Ups", R.drawable.ic_push_up),
            DashboardModel("Wall Sit", R.drawable.ic_wall_sit),
            DashboardModel("Abdominal Crunches", R.drawable.ic_abdominal_crunches),
            DashboardModel("Planks", R.drawable.ic_plank),
        ),
        listOf(
            DashboardModel("Jumping Jacks", R.drawable.ic_jumping_jacks),
            DashboardModel("Abdominal Crunches", R.drawable.ic_abdominal_crunches),
            DashboardModel("Russian Twist", R.drawable.ic_russian_twist),
            DashboardModel("Mountain Climber", R.drawable.ic_mountain_climber),
            DashboardModel("Plank", R.drawable.ic_plank),
        ),
        listOf(
            DashboardModel("Arm Raises", R.drawable.ic_arm_raises),
            DashboardModel("Side Arm Raises", R.drawable.ic_side_arm_raises),
            DashboardModel("Triceps Dips", R.drawable.ic_triceps_dip_on_chair),
            DashboardModel("Push Ups", R.drawable.ic_push_up),
            DashboardModel("Jumping Jacks", R.drawable.ic_jumping_jacks),
        ),
        listOf(
            DashboardModel("Jumping Jacks", R.drawable.ic_jumping_jacks),
            DashboardModel("Push Ups", R.drawable.ic_push_up),
            DashboardModel("Triceps Dips", R.drawable.ic_triceps_dip_on_chair),
            DashboardModel("Wide Arm Push-ups", R.drawable.ic_wide_arm_push_ups),
            DashboardModel("Cobra Stretches", R.drawable.ic_cobra_streches),
        ),
        listOf(
            DashboardModel("Side Lying Leg Lift Left", R.drawable.ic_side_leg_lift_left),
            DashboardModel("Squats", R.drawable.ic_squat),
            DashboardModel("Side Hop", R.drawable.ic_side_hop),
            DashboardModel("Side Lying Leg Lift Right", R.drawable.ic_side_leg_lift_right),
            DashboardModel("Side Lying Leg Lift Left", R.drawable.ic_side_leg_lift_left),
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

//        initializing the layout element variables
        imgExercise = findViewById(R.id.imgExercise)
        tvExerciseName = findViewById(R.id.tvExerciseName)
        btnNext = findViewById(R.id.btnNext)
        toolbar = findViewById(R.id.toolbar)
        tvCounter = findViewById(R.id.tvCounter)
        tvCounter.visibility = View.GONE

//        to display the toolbar
        setUpToolbar()

        if (intent == null) {
            finish()
        }

//        setting the toolbar title as the focuns area name
        supportActionBar?.title = intent.getStringExtra("area")

//        setting the value of index according to the focus area from the intent
        when (intent.getStringExtra("area")) {
            "Full Body" -> index = 0
            "Abs" -> index = 1
            "Arms" -> index = 2
            "Chest" -> index = 3
            "Legs" -> index = 4
        }

        btnNext.setOnClickListener {

//            disabling the next button
            tvCounter.visibility = View.VISIBLE
            btnNext.visibility = View.GONE

            if (counter == 5) {
//              adding history to the database once all 5 exercises are finished
                val hisEntity = HisEntity(currentDate, intent.getStringExtra("area").toString())
                DBAsyncTask(this@ExerciseActivity, hisEntity).execute().get()
                finish()
                Toast.makeText(this@ExerciseActivity, "Done", Toast.LENGTH_SHORT)
                    .show()
            } else {
                timer = object : CountDownTimer(26000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        tvCounter.text = (millisUntilFinished / 1000).toString()
                    }

                    override fun onFinish() {
//                        make a beep sound at the end of each exercise
                        makeABeep()
                        tvExerciseName.text = "Rest"
                        imgExercise.setImageResource(R.drawable.ic_rest)

//                        timer for rest between exercises
                        val restTimer = object : CountDownTimer(11000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                tvCounter.text = (millisUntilFinished / 1000).toString()
                            }

                            override fun onFinish() {
//                                to go to the next exercise
                                btnNext.callOnClick()
                                makeABeep()
                            }
                        }
                        restTimer.start()
                    }
                }
                (timer as CountDownTimer).start()
//                setting the exercise image and name from the list
                tvExerciseName.text = exercises[index][counter].exerciseName
                imgExercise.setImageResource(exercises[index][counter].exerciseImage)
                counter++
            }
        }

    }

    //    function to set up the toolbar and the home button
    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
//            confirmation for exit when user clicks on the home button
            val dialog = AlertDialog.Builder(this@ExerciseActivity)
            dialog.setMessage("Are you sure to exit?")
            dialog.setPositiveButton("Exit") { text, listener ->
                if (timer != null) {
//                    disabling the timer
                    timer?.cancel()
                    counter = 0
                }
                finish()
            }
            dialog.setNegativeButton("Cancel") { text, listener ->

            }
            dialog.setOnDismissListener {

            }
            dialog.create()
            dialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
//        confirm exit when user presses the back navigation button
        val dialog = AlertDialog.Builder(this@ExerciseActivity)
        dialog.setMessage("Are you sure to exit?")
        dialog.setPositiveButton("Exit") { text, listener ->
            if (timer != null) {
                timer?.cancel()
                counter = 0
            }
            finish()
        }
        dialog.setNegativeButton("Cancel") { text, listener ->

        }
        dialog.setOnDismissListener {

        }
        dialog.create()
        dialog.show()
    }


    //    AsyncTask class for performing database operations
    class DBAsyncTask(val context: Context, private val hisEntity: HisEntity) :
        AsyncTask<Void, Void, Boolean>() {

        private val db = Room.databaseBuilder(context, HisDatabase::class.java, "his_db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {
            db.hisDao().insert(hisEntity)
            db.close()
            return true
        }

    }

    //    function to make a beep sound
    fun makeABeep() {
        val toneG = ToneGenerator(
            AudioManager.STREAM_ALARM,
            100
        )
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
    }

}