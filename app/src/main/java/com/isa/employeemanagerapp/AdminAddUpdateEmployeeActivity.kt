package com.isa.employeemanagerapp

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.isa.employeemanagerapp.Database.EmployeeOperations
import com.isa.employeemanagerapp.Model.Employee
import java.text.SimpleDateFormat
import java.util.*

class AdminAddUpdateEmployeeActivity : AppCompatActivity()
{
    var EXTRA_EMPLOYEE_ID: String = "com.isa.EId"
    var EXTRA_ADD_UPDATE: String = "com.isa.add_update"

    private var radioGroup: RadioGroup? = null
    private var trueRadioButton: RadioButton? = null
    private var falseRadioButton:RadioButton? = null

    private var firstNameEditText: EditText? = null
    private var lastNameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var hireDateEditText: EditText? = null

    private var jobTitleEditText: EditText? = null

    private var salaryEditText: EditText? = null

    private var calendarImageView: ImageView? = null

    private var addUpdateButton: Button? = null

    private var newEmpInfo: Employee? = null
    private var oldEmpInfo: Employee? = null

    private var mode: String? = null

    private var employeeID: Long = 0

    private var employeeData: EmployeeOperations? = null

    val hireCalendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_update_employee)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        newEmpInfo = Employee()
        oldEmpInfo = Employee()

        firstNameEditText = findViewById<View>(R.id.edit_text_first_name) as EditText
        lastNameEditText = findViewById<View>(R.id.edit_text_last_name) as EditText
        passwordEditText = findViewById<View>(R.id.edit_text_employeepassword) as EditText
        hireDateEditText = findViewById<View>(R.id.edit_text_hire_date) as EditText
        jobTitleEditText = findViewById<View>(R.id.edit_text_jobtitle) as EditText
        salaryEditText = findViewById<View>(R.id.edit_text_salary) as EditText

        radioGroup = findViewById<View>(R.id.radio_working) as RadioGroup

        calendarImageView = findViewById<View>(R.id.image_view_date) as ImageView

        trueRadioButton = findViewById<View>(R.id.radio_true) as RadioButton
        falseRadioButton = findViewById<View>(R.id.radio_false) as RadioButton

        addUpdateButton = findViewById<View>(R.id.btn_add_update_employee) as Button

        val adapterJobs = ArrayAdapter.createFromResource(this, R.array.JobTitle, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        adapterJobs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val jobTitles = findViewById<View>(R.id.spinner_jobs) as Spinner
        jobTitles.adapter = adapterJobs
        jobTitles.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val jobTitlesText = parent.getItemAtPosition(position).toString()
                jobTitleEditText!!.setText(jobTitlesText)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }


        val adapterSalaries = ArrayAdapter.createFromResource(this, R.array.BaseSalary, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        adapterSalaries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val baseSalaries = findViewById<View>(R.id.spinner_salary) as Spinner
        baseSalaries.adapter = adapterSalaries
        baseSalaries.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val baseSalariesText = parent.getItemAtPosition(position).toString()
                salaryEditText!!.setText(baseSalariesText)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }


        employeeData = EmployeeOperations(this)
        employeeData!!.open()
        newEmpInfo!!.isworking = "True"

        mode = intent.getStringExtra(com.isa.employeemanagerapp.AdminAddUpdateEmployeeActivity.EXTRA_ADD_UPDATE)

        if (mode == "Update") {
            addUpdateButton!!.setText("Update Employee")
            employeeID =
                intent.getLongExtra(com.isa.employeemanagerapp.AdminAddUpdateEmployeeActivity.EXTRA_EMPLOYEE_ID, 0)

            initializeEmployee(employeeID)
        }

        radioGroup!!.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio_true) {
                newEmpInfo!!.isworking = "True"
                if (mode == "Update") {
                    oldEmpInfo!!.isworking = "True"
                }
            } else if (checkedId == R.id.radio_false) {
                newEmpInfo!!.isworking = "False"
                if (mode == "Update") {
                    oldEmpInfo!!.isworking = "False"
                }
            }
        })

        addUpdateButton!!.setOnClickListener(View.OnClickListener {
            if (mode == "Add") {
                newEmpInfo!!.firstname = firstNameEditText!!.text.toString()
                newEmpInfo!!.lastname = lastNameEditText!!.text.toString()
                newEmpInfo!!.password = passwordEditText!!.text.toString()
                newEmpInfo!!.hiredate = hireDateEditText!!.text.toString()
                newEmpInfo!!.job = jobTitleEditText!!.text.toString()
                newEmpInfo!!.salary = salaryEditText!!.text.toString()

                if (firstNameEditText!!.text.toString().isEmpty() || lastNameEditText!!.text.toString()
                        .isEmpty() || passwordEditText!!.text.toString().isEmpty() || hireDateEditText!!.text.toString()
                        .isEmpty() || jobTitleEditText!!.text.toString().isEmpty() || salaryEditText!!.text.toString()
                        .isEmpty()
                ) {
                    Toast.makeText(
                        this@AdminAddUpdateEmployeeActivity,
                        "Field or Fields cannot be empty !!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    employeeData!!.addEmployee(newEmpInfo)
                    Toast.makeText(
                        this@AdminAddUpdateEmployeeActivity,
                        ("Employee " + newEmpInfo!!.firstname).toString() + " has been added successfully !",
                        Toast.LENGTH_LONG
                    ).show()
                    val i = Intent(
                        this@AdminAddUpdateEmployeeActivity,
                        AdminActivity::class.java
                    )
                    startActivity(i)
                }
            } else {
                oldEmpInfo!!.firstname = firstNameEditText!!.text.toString()
                oldEmpInfo!!.lastname = lastNameEditText!!.text.toString()
                oldEmpInfo!!.password = passwordEditText!!.text.toString()
                oldEmpInfo!!.hiredate = hireDateEditText!!.text.toString()
                oldEmpInfo!!.job = jobTitleEditText!!.text.toString()
                oldEmpInfo!!.salary = salaryEditText!!.text.toString()

                if (firstNameEditText!!.text.toString().isEmpty() || lastNameEditText!!.text.toString()
                        .isEmpty() || passwordEditText!!.text.toString().isEmpty() || hireDateEditText!!.text.toString()
                        .isEmpty() || jobTitleEditText!!.text.toString().isEmpty() || salaryEditText!!.text.toString()
                        .isEmpty()
                ) {
                    Toast.makeText(
                        this@AdminAddUpdateEmployeeActivity,
                        "Field or Fields cannot be empty !!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    employeeData!!.updateEmployee(oldEmpInfo)
                    Toast.makeText(
                        this@AdminAddUpdateEmployeeActivity,
                        ("Employee " + oldEmpInfo!!.firstname).toString() + " has been updated successfully !!",
                        Toast.LENGTH_LONG
                    ).show()
                    val i = Intent(
                        this@AdminAddUpdateEmployeeActivity,
                        AdminActivity::class.java
                    )
                    startActivity(i)
                }
            }
        })

        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                hireCalendar[Calendar.YEAR] = year
                hireCalendar[Calendar.MONTH] = monthOfYear
                hireCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel()
            }

        calendarImageView!!.setOnClickListener(View.OnClickListener {
            DatePickerDialog(
                this@AdminAddUpdateEmployeeActivity, date,
                hireCalendar[Calendar.YEAR], hireCalendar[Calendar.MONTH], hireCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        })

    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        hireDateEditText!!.setText(sdf.format(hireCalendar.time))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_return, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cancel -> actionCancel()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        actionCancel()
    }

    private fun actionCancel() {
        finish()
    }

    private fun initializeEmployee(employeeID: Long) {
        var oldEmpInfo = employeeData!!.getEmployee(employeeID)

        firstNameEditText!!.setText(oldEmpInfo.firstname)
        lastNameEditText!!.setText(oldEmpInfo.lastname)
        passwordEditText!!.setText(oldEmpInfo.password)
        hireDateEditText!!.setText(oldEmpInfo.hiredate)

        radioGroup!!.check(if (oldEmpInfo.isworking.equals("True")) R.id.radio_true else R.id.radio_false)
        jobTitleEditText!!.setText(oldEmpInfo.job)
        salaryEditText!!.setText(oldEmpInfo.salary)
    }

    companion object {
        val EXTRA_EMPLOYEE_ID:String = "com.isa.add_update"
        val EXTRA_ADD_UPDATE: String = "com.isa.EId"
    }
}
