package com.isa.employeemanagerapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.isa.employeemanagerapp.Model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeOperations
{
    public static final String LOGTAG = "EMP_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns =
            {
                    EmployeeDatabaseHandler.COLUMN_EID,
                    EmployeeDatabaseHandler.COLUMN_FIRST_NAME,
                    EmployeeDatabaseHandler.COLUMN_LAST_NAME,
                    EmployeeDatabaseHandler.COLUMN_PASSWORD,
                    EmployeeDatabaseHandler.COLUMN_HIRE_DATE,
                    EmployeeDatabaseHandler.COLUMN_ISWORKING,
                    EmployeeDatabaseHandler.COLUMN_JOB,
                    EmployeeDatabaseHandler.COLUMN_SALARY
            };

    public EmployeeOperations(Context context)
    {
        dbhandler = new EmployeeDatabaseHandler(context);
    }

    public void open()
    {
        Log.i(LOGTAG, "Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close()
    {
        Log.i(LOGTAG,"Database Closed");
        dbhandler.close();
    }

    public Employee addEmployee(Employee Employee)
    {
        ContentValues values = new ContentValues();
        values.put(EmployeeDatabaseHandler.COLUMN_FIRST_NAME, Employee.getFirstname());
        values.put(EmployeeDatabaseHandler.COLUMN_LAST_NAME, Employee.getLastname());
        values.put(EmployeeDatabaseHandler.COLUMN_PASSWORD, Employee.getPassword());
        values.put(EmployeeDatabaseHandler.COLUMN_HIRE_DATE, Employee.getHiredate());
        values.put(EmployeeDatabaseHandler.COLUMN_ISWORKING, Employee.getIsworking());
        values.put(EmployeeDatabaseHandler.COLUMN_JOB, Employee.getJob());
        values.put(EmployeeDatabaseHandler.COLUMN_SALARY, Employee.getSalary());
        long insertEID = database.insert(EmployeeDatabaseHandler.TABLE_EMPLOYEES,null,values);
        Employee.setEmployeeID(insertEID);
        return Employee;
    }

    public Employee getEmployee(long id)
    {
        Cursor cursor = database.query(EmployeeDatabaseHandler.TABLE_EMPLOYEES,allColumns,EmployeeDatabaseHandler.COLUMN_EID + "=?", new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!= null && cursor.moveToFirst())
            cursor.moveToFirst();

        Employee e = new Employee(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
        return e; //return single employee
    }

    public List<Employee> getAllEmployees()
    {
        Cursor cursor = database.query(EmployeeDatabaseHandler.TABLE_EMPLOYEES,allColumns,null,null,null,null,null);

        List<Employee> employees = new ArrayList<>();
        if(cursor.getCount() > 0 && cursor !=null)
        {
            while (cursor.moveToNext())
            {
                Employee employee = new Employee();
                employee.setEmployeeID(cursor.getLong(cursor.getColumnIndex(EmployeeDatabaseHandler.COLUMN_EID)));
                employee.setFirstname(cursor.getString(cursor.getColumnIndex(EmployeeDatabaseHandler.COLUMN_FIRST_NAME)));
                employee.setLastname(cursor.getString(cursor.getColumnIndex(EmployeeDatabaseHandler.COLUMN_LAST_NAME)));
                employee.setPassword(cursor.getString(cursor.getColumnIndex(EmployeeDatabaseHandler.COLUMN_PASSWORD)));
                employee.setHiredate(cursor.getString(cursor.getColumnIndex(EmployeeDatabaseHandler.COLUMN_HIRE_DATE)));
                employee.setIsworking(cursor.getString(cursor.getColumnIndex(EmployeeDatabaseHandler.COLUMN_ISWORKING)));
                employee.setJob(cursor.getString(cursor.getColumnIndex(EmployeeDatabaseHandler.COLUMN_JOB)));
                employee.setSalary(cursor.getString(cursor.getColumnIndex(EmployeeDatabaseHandler.COLUMN_SALARY)));

                employees.add(employee);
            }
        }
        //return all employees
        return employees;
    }

    public int updateEmployee(Employee employee)
    {
        ContentValues values = new ContentValues();

        values.put(EmployeeDatabaseHandler.COLUMN_FIRST_NAME, employee.getFirstname());
        values.put(EmployeeDatabaseHandler.COLUMN_LAST_NAME, employee.getLastname());
        values.put(EmployeeDatabaseHandler.COLUMN_PASSWORD, employee.getPassword());
        values.put(EmployeeDatabaseHandler.COLUMN_HIRE_DATE, employee.getHiredate());
        values.put(EmployeeDatabaseHandler.COLUMN_ISWORKING, employee.getIsworking());
        values.put(EmployeeDatabaseHandler.COLUMN_JOB, employee.getJob());
        values.put(EmployeeDatabaseHandler.COLUMN_SALARY, employee.getSalary());

        return database.update(EmployeeDatabaseHandler.TABLE_EMPLOYEES, values, EmployeeDatabaseHandler.COLUMN_EID + "=?", new String[] {String.valueOf(employee.getEmployeeID())});

    }

    public void removeEmployee(Employee employee)
    {
        database.delete(EmployeeDatabaseHandler.TABLE_EMPLOYEES, EmployeeDatabaseHandler.COLUMN_EID + "=" + employee.getEmployeeID(), null);
    }
}
