package com.isa.employeemanagerapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class AdminLoginActivity : AppCompatActivity()
{
    var usernameEditText: EditText? = null
    var passwordEditText: EditText? = null

    var loginAdmin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        usernameEditText = findViewById<View>(R.id.et_username) as EditText
        passwordEditText = findViewById<View>(R.id.et_password) as EditText

        loginAdmin = findViewById<View>(R.id.btn_login) as Button

        loginAdmin!!.setOnClickListener {
            if (usernameEditText!!.text.toString() == "Admin" && passwordEditText!!.text.toString() == "Admin123") {
                val i = Intent(this@AdminLoginActivity, AdminActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this@AdminLoginActivity, "Incorrect Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}