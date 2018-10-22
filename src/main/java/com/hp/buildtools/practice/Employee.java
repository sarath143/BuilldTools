package com.hp.buildtools.practice;

import java.util.Date;
import java.util.List;

public class Employee implements IEmployee{

    private String EmployeeName;
    private List<String> hobbies;
    private Date DateOfJoining;
    private Department department;

    public Employee() {

    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public Employee(final Employee instance){
        this.EmployeeName = instance.EmployeeName;
        this.DateOfJoining = instance.DateOfJoining;
        this.department = instance.department;
        this.hobbies = instance.hobbies;
    }

    public Employee(String employeeName, List<String> hobbies, Date dateOfJoining, Department department) {
        EmployeeName = employeeName;
        this.hobbies = hobbies;
        DateOfJoining = dateOfJoining;
        this.department = department;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Date getDateOfJoining() {
        return DateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        DateOfJoining = dateOfJoining;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public Employee makeCopy() {
        return new Employee(this);
    }

    @Deprecated
    @Override
    public Employee clone(){
        Employee employee = null;
        try {
            employee = (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "EmployeeName='" + EmployeeName + '\'' +
                ", hobbies=" + hobbies +
                ", DateOfJoining=" + DateOfJoining +
                ", department=" + department.toString() +
                '}';
    }
}
