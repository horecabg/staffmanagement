package com.sirmaacademy.staffmanagement;


import java.time.LocalDate;

public class Employee {
    private int Id;
    private String Name;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private String Department;
    private String Role;
    private double Salary;

    // this constructor is used when input data from PC console - Add Employee
    public Employee(int id, String name, String department, String role, double salary) {
        Id = id;
        Name = name;
        Department = department;
        Role = role;
        Salary = salary;
    }

    // this constructor is used when loading data from file - When starting program
    public Employee(int id, String name, LocalDate StartDate,  LocalDate EndDate, String department, String role, double salary) {
        Id = id;
        Name = name;
        Department = department;
        Role = role;
        Salary = salary;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double salary) {
        Salary = salary;
    }

    //override toString method
    @Override
    public String toString() {
        return this.getId() + ", " + this.getName() + ", "  + this.getStartDate() + ", "  + this.getEndDate() + ", " + this.getDepartment() + ", " + this.getRole() + ", " + this.getSalary();
    }
}
