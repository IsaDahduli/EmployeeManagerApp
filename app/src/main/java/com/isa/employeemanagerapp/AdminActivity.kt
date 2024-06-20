package com.isa.employeemanagerapp

import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.isa.employeemanagerapp.Database.EmployeeDatabaseHandler
import com.isa.employeemanagerapp.Database.EmployeeOperations

class AdminActivity : AppCompatActivity()
{
    private var viewAllEmployeesButton: Button? = null
    private var addEmployeeButton: Button? = null
    private var editEmployeeButton: Button? = null
    private var deleteEmployeeButton: Button? = null

    private var employeeOps: EmployeeOperations? = null

    val EXTRA_EMPLOYEE_ID: String = "com.isa.EId"
    val EXTRA_ADD_UPDATE: String = "com.isa.add_update"
    val TAG: String = "Employee Exits"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        viewAllEmployeesButton = findViewById<View>(R.id.btn_admin_viewAll) as Button
        addEmployeeButton = findViewById<View>(R.id.btn_admin_add_employee) as Button
        editEmployeeButton = findViewById<View>(R.id.btn_admin_edit_employee) as Button
        deleteEmployeeButton = findViewById<View>(R.id.btn_admin_remove_employee) as Button

        addEmployeeButton!!.setOnClickListener {
            val i = Intent(
                this@AdminActivity,
                AdminAddUpdateEmployeeActivity::class.java
            )
            i.putExtra(EXTRA_ADD_UPDATE, "Add")
            startActivity(i)
        }

        editEmployeeButton!!.setOnClickListener { getEmployeeIDAndUpdateEmployee() }

        deleteEmployeeButton!!.setOnClickListener { getEmployeeIDAndRemoveEmployee() }

        viewAllEmployeesButton!!.setOnClickListener {
            val i = Intent(this@AdminActivity, ViewAllEmployeesActivity::class.java)
            startActivity(i)
        }
    }


    fun check_existence(emp_id: String): Boolean {
        val db: SQLiteOpenHelper = EmployeeDatabaseHandler(this)
        val database = db.writableDatabase

        val select = "SELECT * FROM employees WHERE employeeID =$emp_id"

        val c = database.rawQuery(select, null)

        if (c!!.moveToFirst()) {
            Log.d(TAG, "Employee Exists")
            return true
        }

        if (c != null) {
            c.close()
        }

        database.close()
        return false
    }

    fun getEmployeeIDAndUpdateEmployee() {
        val li = LayoutInflater.from(this)
        val getEmployeeIdView = li.inflate(R.layout.dialog_get_employee_id, null)

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(getEmployeeIdView)

        val userInput = getEmployeeIdView.findViewById<View>(R.id.editTextDialogUserInput) as EditText

        alertDialogBuilder.setCancelable(false).setPositiveButton(
            "OK"
        ) { dialog, id ->
            if (userInput.text.toString().isEmpty()) {
                Toast.makeText(this@AdminActivity, "User Input is Invalid", Toast.LENGTH_LONG).show()
            } else {
                // get user input and set it to result
                // edit text
                if (check_existence(userInput.text.toString()) == true) {
                    val i = Intent(
                        this@AdminActivity,
                        AdminAddUpdateEmployeeActivity::class.java
                    )
                    i.putExtra(EXTRA_ADD_UPDATE, "Update")
                    i.putExtra(EXTRA_EMPLOYEE_ID, userInput.text.toString().toLong())
                    startActivity(i)
                } else {
                    Toast.makeText(this@AdminActivity, "Input is invalid", Toast.LENGTH_SHORT).show()
                }
            }
        }.create().show()
    }

    fun getEmployeeIDAndRemoveEmployee() {
        val li = LayoutInflater.from(this)
        val getEmployeeIdView = li.inflate(R.layout.dialog_get_employee_id, null)

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(getEmployeeIdView)

        val userInput = getEmployeeIdView.findViewById<View>(R.id.editTextDialogUserInput) as EditText

        alertDialogBuilder.setCancelable(false).setPositiveButton(
            "OK"
        ) { dialog, id ->
            if (userInput.text.toString().isEmpty()) {
                Toast.makeText(this@AdminActivity, "Invalid Input", Toast.LENGTH_SHORT).show()
            } else {
                if (check_existence(userInput.text.toString()) == true) {
                    employeeOps!!.removeEmployee(employeeOps!!.getEmployee(userInput.text.toString().toLong()))
                    Toast.makeText(
                        this@AdminActivity,
                        "Employee has been removed successfully",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this@AdminActivity, "Invalid Input", Toast.LENGTH_LONG).show()
                }
            }
        }.create().show()
    }

    override fun onResume() {
        super.onResume()
        employeeOps = EmployeeOperations(this@AdminActivity)
        employeeOps!!.open()
    }

    override fun onPause() {
        super.onPause()
        employeeOps!!.close()
    }

}