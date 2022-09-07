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

class LoginActivity : AppCompatActivity() {

    //    variables for the layout elements
    lateinit var tvRegister: TextView
    lateinit var etUserName: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        initializing the variables for the layout elememts
        tvRegister = findViewById(R.id.tvRegister)
        etUserName = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
//        accessing the shared preferences file
        val sharedPreferences = getSharedPreferences("fitme_preferences", MODE_PRIVATE)

        btnLogin.setOnClickListener {
//           test for empty fields
            if (etPassword.text.toString().isEmpty() || etUserName.text.toString().isEmpty()) {
                Toast.makeText(this@LoginActivity, "Enter all the details!!", Toast.LENGTH_SHORT)
                    .show()
            } else {

//              check if the username and password matches the one in the shared preferences
                if (etUserName.text.toString() == sharedPreferences.getString(
                        "name",
                        null
                    ) && etPassword.text.toString() == sharedPreferences.getString("password", null)
                ) {
//                    setting the isLogin to true and redirecting to the Main Activity
                    sharedPreferences.edit().putBoolean("isLogin", true).apply()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
//                    alert box for invalid credentials
                    val dialog = AlertDialog.Builder(this@LoginActivity)
                    dialog.setTitle("Error")
                    dialog.setMessage("Invalid credentials!!")
                    dialog.setPositiveButton("Ok") { text, listener ->

                    }
                    dialog.setNegativeButton("EXIT") { text, listener ->
                        ActivityCompat.finishAffinity(this@LoginActivity)
                    }
                    dialog.setOnDismissListener { }
                    dialog.create()
                    dialog.show()
                }
            }
        }
        tvRegister.setOnClickListener {
//            to navigate to register activity
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
//        to redirect to the Main page if the user is logged in
        val sharedPreferences = getSharedPreferences("fitme_preferences", MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isLogin", false)) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
//        setting the text as empty when the user returns to the Login activity from another activity
        etUserName.text = null
        etPassword.text = null
        finish()
        super.onPause()
    }
}