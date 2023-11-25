package com.sirmaacademy.staffmanagement.service;

import com.sirmaacademy.staffmanagement.Employee;

public interface Service {
    void addEmployee(Employee employee); // from console
    void addEmployeeFile(Employee employee); // from file
    void editEmployee(int id, Employee employee);
    void fireEmployee(int id);
    void listEmployees();
    Employee searchById(int id);
    void searchByName(String name);
    void searchByDepartment(String department);
    boolean isActive(Employee employee);
    public void closeWriter();
    public void loadData();
    void saveData();
}
