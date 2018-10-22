package com.hp.buildtools.practice;

import java.util.Date;

public class Department {

    private Long departmentID;
    private String departmentName;
    private Date dateOfDepartment;

    public Department() {
    }

    public Department(Long departmentID, String departmentName, Date dateOfDepartment) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.dateOfDepartment = dateOfDepartment;
    }

    public Long getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Long departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getDateOfDepartment() {
        return dateOfDepartment;
    }

    public void setDateOfDepartment(Date dateOfDepartment) {
        this.dateOfDepartment = dateOfDepartment;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentID=" + departmentID +
                ", departmentName='" + departmentName + '\'' +
                ", dateOfDepartment=" + dateOfDepartment +
                '}';
    }
}
