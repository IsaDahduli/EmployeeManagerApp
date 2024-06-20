package com.isa.employeemanagerapp

import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.isa.employeemanagerapp.Database.EmployeeDatabaseHandler

class EmployeeLoginActivity : AppCompatActivity()
{
    val TAG: String = "Employee Exits"

    var idEditText: EditText? = null
    var passEditText: EditText? = null

    var EmpFN_EditTextShow: EditText? = null
    var EmpLN_EditTextShow: EditText? = null
    var EmpHireDateEditTextShow: EditText? = null
    var EmpWorking_EditTextShow: EditText? = null
    var EmpJob_EditTextShow: EditText? = null
    var EmpSalary_EditTextShow: EditText? = null


    var LoginEmp: Button? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_login)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        idEditText = findViewById<View>(R.id.et_id) as EditText
        passEditText = findViewById<View>(R.id.et_password) as EditText

        EmpFN_EditTextShow = findViewById<View>(R.id.et_firstname) as EditText
        EmpLN_EditTextShow = findViewById<View>(R.id.et_lastname) as EditText
        EmpHireDateEditTextShow = findViewById<View>(R.id.et_hiredate) as EditText
        EmpWorking_EditTextShow = findViewById<View>(R.id.et_workingStatus) as EditText
        EmpJob_EditTextShow = findViewById<View>(R.id.et_Job) as EditText
        EmpSalary_EditTextShow = findViewById<View>(R.id.et_salary) as EditText

        LoginEmp = findViewById<View>(R.id.btn_getInformation) as Button

        LoginEmp!!.setOnClickListener {
            if (idEditText!!.text.toString().isEmpty() || passEditText!!.text.toString().isEmpty()) {
                Toast.makeText(this@EmployeeLoginActivity, "Empty fields", Toast.LENGTH_SHORT).show()
            } else {
                if (check_login(idEditText!!.text.toString(), passEditText!!.text.toString()) == true) {
                    Toast.makeText(this@EmployeeLoginActivity, "Successful login", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@EmployeeLoginActivity, "Wrong Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun check_login(emp_id: String, emp_pass: String): Boolean {
        val database: SQLiteOpenHelper = EmployeeDatabaseHandler(this)
        val db = database.writableDatabase

        val select = "SELECT * FROM employees WHERE employeeID ='$emp_id' AND password='$emp_pass'"

        val c = db.rawQuery(select, null)

        
        
        if (c!!.moveToFirst()) 
        {
            Log.d(com.isa.employeemanagerapp.EmployeeLoginActivity.TAG, "Employee exists")

            EmpFN_EditTextShow!!.setText(c!!.getString(1))
            EmpLN_EditTextShow!!.setText(c!!.getString(2))
            EmpHireDateEditTextShow!!.setText(c!!.getString(4))
            EmpWorking_EditTextShow!!.setText(c!!.getString(5))
            EmpJob_EditTextShow!!.setText(c!!.getString(6))
            EmpSalary_EditTextShow!!.setText(c!!.getString(7))

            return true
        }
        if (c != null) {
            c.close()
        }
        db.close()
        return false
    }

    companion object {
        val TAG: String = "Employee Exits"
    }
}
