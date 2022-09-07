package com.example.fitme.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.fitme.R

class RegisterActivity : AppCompatActivity() {

//   variables for the layout elements
    lateinit var etAge: EditText
    lateinit var etName: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnRegister: Button
    lateinit var tvLogin: TextView
    lateinit var etHeight: EditText
    lateinit var etWeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        initializing layout elements
        etAge = findViewById(R.id.etAge)
        etPassword = findViewById(R.id.etPassword)
        etName = findViewById(R.id.etName)
        etHeight = findViewById(R.id.etHeight)
        etWeight = findViewById(R.id.etWeight)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)
//        accessing the shared preferences folder
        val sharedPreferences = getSharedPreferences("fitme_preferences", MODE_PRIVATE)

//        click listener for the register button
        btnRegister.setOnClickListener {
//            checking for empty fields
            if (etAge.text.toString().isEmpty() || etName.text.toString()
                    .isEmpty() || etPassword.text.toString()
                    .isEmpty() || etConfirmPassword.text.toString()
                    .isEmpty() || etHeight.text.toString()
                    .isEmpty() || etWeight.text.toString().isEmpty()
            ) {
                Toast.makeText(this@RegisterActivity, "Enter all the details!!", Toast.LENGTH_SHORT)
                    .show()
//                validating password to be at least of length 8
            } else if (etPassword.text.toString().length < 8) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password must be at least 8 characters!!",
                    Toast.LENGTH_SHORT
                ).show()
//                checking if the passwords match
            } else if (etPassword.text.toString()
                    .compareTo(etConfirmPassword.text.toString()) != 0
            ) {
                Toast.makeText(this@RegisterActivity, "Passwords must match!!", Toast.LENGTH_SHORT)
                    .show()
            } else {
//                inserting data from input fields to the shared preferences file
                sharedPreferences.edit().putString("name", etName.text.toString()).apply()
                sharedPreferences.edit().putString("age", etAge.text.toString()).apply()
                sharedPreferences.edit().putString("height", etHeight.text.toString()).apply()
                sharedPreferences.edit().putString("weight", etWeight.text.toString()).apply()
                sharedPreferences.edit().putString("password", etPassword.text.toString()).apply()

//                displays an alert box to prompt the user to login
                val dialog = AlertDialog.Builder(this@RegisterActivity)
                dialog.setTitle("Registered")
                dialog.setMessage("Log in with your details")
                dialog.setPositiveButton("Login") { text, listener ->
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                dialog.setNegativeButton("Exit") { text, listener ->
                    ActivityCompat.finishAffinity(this@RegisterActivity)
                }
                dialog.setOnDismissListener { }
                dialog.create()
                dialog.show()

            }
        }

//        to navigate back to the login activity
        tvLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
//        redirecting to login activity on pressing the back button
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

    override fun onPause() {
        finish()
        super.onPause()
    }
}