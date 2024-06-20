package com.isa.employeemanagerapp

import android.app.ListActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.isa.employeemanagerapp.Database.EmployeeOperations
import com.isa.employeemanagerapp.Model.Employee

class ViewAllEmployeesActivity : ListActivity() {
    private var employeeOps: EmployeeOperations? = null
    private var employees: List<Employee>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_employees)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        employeeOps = EmployeeOperations(this)
        employeeOps!!.open()
        employees = employeeOps!!.allEmployees
        employeeOps!!.close()

        val adapter = EmployeeAdapter(this, employees!!)
        listAdapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
        actionCancel()
    }

    private fun actionCancel() {
        finish()
    }
}
