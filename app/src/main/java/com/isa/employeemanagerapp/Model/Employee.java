package com.isa.employeemanagerapp.Model;

public class Employee
{
    public long employeeID;

    private String firstname;
    private String lastname;
    private String password;

    private String hiredate;
    private String isworking;
    private String job;
    private String salary;

    public Employee(long employeeID,String firstname, String lastname, String password, String hiredate ,String isworking, String job, String salary)
    {
        this.employeeID = employeeID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.hiredate = hiredate;
        this.isworking = isworking;
        this.job = job;
        this.salary = salary;
    }

    public Employee()
    {

    }

    public long getEmployeeID()
    {
        return employeeID;
    }

    public void setEmployeeID(long employeeID)
    {
        this.employeeID = employeeID;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getHiredate()
    {
        return hiredate;
    }

    public void setHiredate(String hiredate)
    {
        this.hiredate = hiredate;
    }

    public String getIsworking()
    {
        return isworking;
    }

    public void setIsworking(String isworking)
    {
        this.isworking = isworking;
    }

    public String getJob()
    {
        return job;
    }

    public void setJob(String job)
    {
        this.job = job;
    }

    public String getSalary()
    {
        return salary;
    }

    public void setSalary(String salary)
    {
        this.salary = salary;
    }

    public String toString()
    {
        return  "Employee ID: "+getEmployeeID()+ "\n" +
                "Name: "+getFirstname() + " " + getLastname() + "\n" +
                "Password: "+getPassword() + "\n" +
                "Hire Date: "+getHiredate() + "\n" +
                "Working Status: " +getIsworking() + "\n" +
                "Job Title: " +getJob() + "\n" +
                "Salary: " +getSalary();
    }

}