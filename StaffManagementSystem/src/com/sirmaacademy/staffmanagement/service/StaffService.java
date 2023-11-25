package com.sirmaacademy.staffmanagement.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.sirmaacademy.staffmanagement.Employee;

import java.io.FileWriter;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class StaffService implements Service {
    public List<Employee> employees;
    public CSVReader reader;
    public CSVWriter writer;

    public StaffService(CSVReader reader, CSVWriter writer) {
        this.employees = new ArrayList<>();
        this.reader = reader;
        this.writer = writer;
        loadData();
    }

    @Override
    public void addEmployee(Employee employee) {
        employee.setStartDate(LocalDate.now());
        employee.setEndDate(null);
        employees.add(employee);
    }
    @Override
    public  void addEmployeeFile(Employee employee){
        employees.add(employee);
    }

    @Override
    public void editEmployee(int id, Employee employee) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                e.setName(employee.getName());
                e.setDepartment(employee.getDepartment());
                e.setRole(employee.getRole());
                e.setSalary(employee.getSalary());
                System.out.printf("You have edit successfully the record with ID: %d%n", id);
                System.out.println(e.toString());
            }
        }
    }

    //   public void editEmployee(Employee employeeOld, Employee employeeNew) {

    // }

    @Override
    public void fireEmployee(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                // set the current day - when you nmake the employee inactive
                e.setEndDate(LocalDate.now());
            }
        }
    }

    public boolean isActive(Employee employee) {
        // check if an amployee is active or not - if active then the endDate is null
        if (employee.getEndDate() == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void listEmployees() {
        for (Employee e : this.employees) {
            if (e.getEndDate() == null) {
                System.out.println(e.toString());
            }
        }
    }

    @Override
    public Employee searchById(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void searchByName(String name) {
        // you can search using any part of the name  - not case sensitive
        // make a list with all matches
        List<Employee> employeeByName = new ArrayList<>();
        employeeByName = this.employees.stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        if (!employeeByName.isEmpty()) {
            for (Employee e : employeeByName) {
                System.out.println(e.toString());
            }
        } else {
            System.out.println("=== ERROR === There is no records found with this criteria");
        }
    }

    @Override
    public void searchByDepartment(String department) {
        // you can search using any part of the department  - not case sensitive
        // make a list with all matches
        List<Employee> employeeByDepartment = new ArrayList<>();
        employeeByDepartment = this.employees.stream()
                .filter(e -> e.getDepartment().toLowerCase().contains(department.toLowerCase()))
                .collect(Collectors.toList());
        if (!employeeByDepartment.isEmpty()) {
            for (Employee e : employeeByDepartment) {
                System.out.println(e.toString());
            }
        } else {
            System.out.println("=== ERROR === There is no records found with this criteria");
        }
    }

    @Override
    public void saveData() {
        // save the date in a file before exit and close the resource
        try {
            try {
                writer = new CSVWriter(new FileWriter("output.csv"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (Employee e : employees) {
                String[] stringLine = {String.valueOf(e.getId()), e.getName(), String.valueOf(e.getStartDate()), String.valueOf(e.getEndDate()), e.getDepartment(), e.getRole(), String.valueOf(e.getSalary())};
                this.writer.writeNext(stringLine);
            }
        } finally {
            ((StaffService) this).closeWriter();
        }
    }

    public void loadData() {
        // load date initially and during check up process only valid items save in the list it computer memory
        // check for unique id and have to be positive number also
        // the name is not possible to be empty string and only with white spaces also
        // the salary has to be positive number
        // also check the date format
        String[] entity;
        while (true) {
            try {
                if ((entity = reader.readNext()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Arrays.toString(entity));
            if (entity.length == 7) {
                try {
                    // we trim all of the fields and check if first filed is integer number and
                    // the last one is the doubled number. If not - Error Exception
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    int i = Integer.parseInt(entity[0].trim());
                    double d = Double.parseDouble(entity[entity.length - 1].trim());
                    LocalDate startDate = LocalDate.parse(entity[2], formatter);
                    // only with formatter pattern there is a problem with dates like 2023-11-31
                    // This date is wrong but there is no Exception
                    // So I use one more check to fix this problem
                    startDate = LocalDate.of(Integer.parseInt(entity[2].substring(0, 4)), Integer.parseInt(entity[2].substring(5, 7)), Integer.parseInt(entity[2].substring(8, 10)));
                    if (startDate.isAfter(LocalDate.now())) {
                        throw new DateTimeException("startDate cannot be in the future");
                    }
                    LocalDate endDate;
                    if (entity[3].equals("null")) {
                        endDate = null;
                    } else {
                        endDate = LocalDate.parse(entity[3], formatter);
                        // only with formatter pattern there is a problem with dates like 2023-11-31
                        // This date is wrong but there is no Exception
                        // So I use one more check to fix this problem
                        endDate = LocalDate.of(Integer.parseInt(entity[3].substring(0, 4)), Integer.parseInt(entity[3].substring(5, 7)), Integer.parseInt(entity[3].substring(8, 10)));
                        if (endDate.isBefore(startDate)) {
                            throw new DateTimeException("endDate cannot be before startDate");
                        }
                    }
                    // ID must have positive number, Name field is not possible to be empty and
                    // the Salary have to be initially 0.0 or positive double number - You can edit after that
                    if (i > 0 && !(entity[1].trim().isEmpty()) && d >= 0.00) {
                        Employee employee = new Employee(i, entity[1].trim(), startDate, endDate, entity[4].trim(), entity[5].trim(), d);
                        // check if such ID already exist - If not you can add this entity to the list
                          if (this.searchById(i) == null) {
                              this.addEmployeeFile(employee);
                              System.out.println("You have add successfully this record:");
                          } else {
                              System.out.println("=== ERROR === You have yet a record with this ID");
                          }
                    } else {
                        System.out.println("=== ERROR === Wrong ID, Name or Salary field");
                    }
                } catch (NumberFormatException | DateTimeException e) {
                    System.out.println("=== ERROR === Wrong format");
                }
            } else {
                System.out.println("=== ERROR === Wrong number of fields");
            }
        }
    }

    @Override
    public void closeWriter() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("=== ERROR === IOException");
        }
    }
}

