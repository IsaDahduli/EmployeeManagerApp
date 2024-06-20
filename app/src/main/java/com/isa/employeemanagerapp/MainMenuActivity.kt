package com.isa.employeemanagerapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainMenuActivity : AppCompatActivity() {
    private var AdminButton: Button? = null
    private var EmployeeButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        AdminButton = findViewById<Button>(R.id.btn_admin)
        EmployeeButton = findViewById<Button>(R.id.btn_employee)

        AdminButton?.setOnClickListener {
            val i = Intent(this@MainMenuActivity, AdminLoginActivity::class.java)
            startActivity(i)
        }

        EmployeeButton?.setOnClickListener {
            val i = Intent(this@MainMenuActivity, EmployeeLoginActivity::class.java)
            startActivity(i)
        }
    }
}
