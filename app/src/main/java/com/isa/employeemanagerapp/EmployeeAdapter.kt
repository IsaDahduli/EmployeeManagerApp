package com.isa.employeemanagerapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.isa.employeemanagerapp.Model.Employee

class EmployeeAdapter(context: Context, employees: List<Employee>) : ArrayAdapter<Employee>(context, 0, employees) {
    @NonNull
    override fun getView(position: Int, @Nullable convertView: View?, @NonNull parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.simple_list_item_1, parent, false)
        }

        val currentEmployee = getItem(position)

        val textView = listItemView!!.findViewById<TextView>(android.R.id.text1)
        textView.text = currentEmployee!!.firstname + " " + currentEmployee.lastname

        return listItemView
    }
}
