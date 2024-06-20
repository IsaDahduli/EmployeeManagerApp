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

class AdminAddUpdateEmployeeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_EMPLOYEE_ID = "com.isa.EId"
        const val EXTRA_ADD_UPDATE = "com.isa.add_update"
    }

    private var radioGroup: RadioGroup? = null
    private var trueRadioButton: RadioButton? = null
    private var falseRadioButton: RadioButton? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_update_employee)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        newEmpInfo = Employee()
        oldEmpInfo = Employee()

        firstNameEditText = findViewById(R.id.edit_text_first_name)
        lastNameEditText = findViewById(R.id.edit_text_last_name)
        passwordEditText = findViewById(R.id.edit_text_employeepassword)
        hireDateEditText = findViewById(R.id.edit_text_hire_date)
        jobTitleEditText = findViewById(R.id.edit_text_jobtitle)
        salaryEditText = findViewById(R.id.edit_text_salary)

        radioGroup = findViewById(R.id.radio_working)
        calendarImageView = findViewById(R.id.image_view_date)
        trueRadioButton = findViewById(R.id.radio_true)
        falseRadioButton = findViewById(R.id.radio_false)
        addUpdateButton = findViewById(R.id.btn_add_update_employee)

        val adapterJobs = ArrayAdapter.createFromResource(
            this,
            R.array.JobTitle,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        )
        adapterJobs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val jobTitles = findViewById<Spinner>(R.id.spinner_jobs)
        jobTitles.adapter = adapterJobs
        jobTitles.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val jobTitlesText = parent.getItemAtPosition(position).toString()
                jobTitleEditText?.setText(jobTitlesText)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        val adapterSalaries = ArrayAdapter.createFromResource(
            this,
            R.array.BaseSalary,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        )
        adapterSalaries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val baseSalaries = findViewById<Spinner>(R.id.spinner_salary)
        baseSalaries.adapter = adapterSalaries
        baseSalaries.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val baseSalariesText = parent.getItemAtPosition(position).toString()
                salaryEditText?.setText(baseSalariesText)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        employeeData = EmployeeOperations(this)
        employeeData?.open()

        mode = intent.getStringExtra(EXTRA_ADD_UPDATE)
        if (mode == "Update") {
            addUpdateButton?.text = "Update Employee"
            employeeID = intent.getLongExtra(EXTRA_EMPLOYEE_ID, 0)
            initializeEmployee(employeeID)
        }

        radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_true -> {
                    newEmpInfo?.isworking = "True"
                    if (mode == "Update") {
                        oldEmpInfo?.isworking = "True"
                    }
                }
                R.id.radio_false -> {
                    newEmpInfo?.isworking = "False"
                    if (mode == "Update") {
                        oldEmpInfo?.isworking = "False"
                    }
                }
            }
        }

        addUpdateButton?.setOnClickListener {
            if (mode == "Add") {
                newEmpInfo?.apply {
                    firstname = firstNameEditText?.text.toString()
                    lastname = lastNameEditText?.text.toString()
                    password = passwordEditText?.text.toString()
                    hiredate = hireDateEditText?.text.toString()
                    job = jobTitleEditText?.text.toString()
                    salary = salaryEditText?.text.toString()
                }

                if (isAnyFieldEmpty(newEmpInfo)) {
                    Toast.makeText(
                        this,
                        "Field or Fields cannot be empty !!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    employeeData?.addEmployee(newEmpInfo)
                    Toast.makeText(
                        this,
                        "Employee ${newEmpInfo?.firstname} has been added successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this, AdminActivity::class.java))
                }
            } else {
                oldEmpInfo?.apply {
                    firstname = firstNameEditText?.text.toString()
                    lastname = lastNameEditText?.text.toString()
                    password = passwordEditText?.text.toString()
                    hiredate = hireDateEditText?.text.toString()
                    job = jobTitleEditText?.text.toString()
                    salary = salaryEditText?.text.toString()
                }

                if (isAnyFieldEmpty(oldEmpInfo)) {
                    Toast.makeText(
                        this,
                        "Field or Fields cannot be empty !!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    employeeData?.updateEmployee(oldEmpInfo)
                    Toast.makeText(
                        this,
                        "Employee ${oldEmpInfo?.firstname} has been updated successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this, AdminActivity::class.java))
                }
            }
        }

        val date = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            hireCalendar.set(year, monthOfYear, dayOfMonth)
            updateLabel()
        }

        calendarImageView?.setOnClickListener {
            DatePickerDialog(
                this,
                date,
                hireCalendar.get(Calendar.YEAR),
                hireCalendar.get(Calendar.MONTH),
                hireCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        hireDateEditText?.setText(sdf.format(hireCalendar.time))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_return, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cancel) {
            actionCancel()
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
        oldEmpInfo = employeeData?.getEmployee(employeeID)
        oldEmpInfo?.apply {
            firstNameEditText?.setText(firstname)
            lastNameEditText?.setText(lastname)
            passwordEditText?.setText(password)
            hireDateEditText?.setText(hiredate)
            jobTitleEditText?.setText(job)
            salaryEditText?.setText(salary)
            radioGroup?.check(if (isworking == "True") R.id.radio_true else R.id.radio_false)
        }
    }

    private fun isAnyFieldEmpty(employee: Employee?): Boolean {
        return employee?.firstname.isNullOrEmpty() || employee?.lastname.isNullOrEmpty() ||
                employee?.password.isNullOrEmpty() || employee?.hiredate.isNullOrEmpty() ||
                employee?.job.isNullOrEmpty() || employee?.salary.isNullOrEmpty()
    }
}
