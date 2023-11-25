package com.sirmaacademy.staffmanagement.manager;


import com.sirmaacademy.staffmanagement.Employee;
import com.sirmaacademy.staffmanagement.service.Service;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class StaffManager extends Manager {
    private Service service;

    public StaffManager(Service service) {
        this.service = service;
    }

    @Override
    public void execute(String command) {
        switch (command) {
            case "1": {
                Scanner scanner = new Scanner(System.in);
                String entity;
                System.out.println("Add a new employee");
                System.out.println("Use this format to add a new employee: (ID, name, department, role, salary");
                entity = scanner.nextLine();
                // split input line by comma
                String[] tokens = entity.split(",");
                // check if the number of separated fields are 5. If not - means the wrong number of fields have entered
                if (tokens.length == 5) {
                    try {
                        // we trim all of the fields and check if first filed is integer number and
                        // the last one is the doubled number. If not - Error Exception
                        int i = Integer.parseInt(tokens[0].trim());
                        double d = Double.parseDouble(tokens[tokens.length - 1].trim());
                        // ID must have positive number, Name field is not possible to be empty and
                        // the Salary have to be initially 0.0 or positive double number - You can edit after that
                        if (i > 0 && !(tokens[1].trim().isEmpty()) && d >= 0.00) {
                            Employee employee = new Employee(Integer.parseInt(tokens[0].trim()), tokens[1].trim(), tokens[2].trim(), tokens[3].trim(), Double.parseDouble(tokens[4].trim()));
                            // check if such ID already exist - If not you can add this entity to the list
                            if (this.service.searchById(i) == null) {
                                this.service.addEmployee(employee);
                                System.out.println("You have add successfully this record:");
                                System.out.println(employee.toString());
                            } else {
                                System.out.println("=== ERROR === You have yet a record with this ID");
                            }
                        } else {
                            System.out.println("=== ERROR === Wrong ID, Name or Salary field");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("=== ERROR === Wrong number format");
                    }
                } else {
                    System.out.println("=== ERROR === You entered wrong number of fields");
                }
                break;
            }
            case "2": {
                Scanner scanner = new Scanner(System.in);
                String entity;
                System.out.println("Edit Employee");
                System.out.println("Use this format to add a new employee: (ID, name, department, role, salary");
                entity = scanner.nextLine();
                // split input line by comma
                String[] tokens = entity.split(",");
                // check if the number of separated fields are 5. If not - means the wrong number of fields have entered
                if (tokens.length == 5) {
                    try {
                        // we trim all of the fields and check if first filed is integer number and
                        // the last one is the doubled number. If not - Error Exception
                        int i = Integer.parseInt(tokens[0].trim());
                        double d = Double.parseDouble(tokens[tokens.length - 1].trim());
                        // ID must have positive number, Name field is not possible to be empty and
                        // the Salary have to be initially 0.0 or positive double number - You can edit after that
                        if (i > 0 && !(tokens[1].trim().isEmpty()) && d >= 0.00) {
                            Employee employee;
                            employee = this.service.searchById(i);
                            // check if such ID already exist - If yes you can edit this record
                            if (employee != null) {
                                if (employee.getEndDate() == null) {
                                    System.out.println("You will edit this record:");
                                    System.out.println(this.service.searchById(i).toString());
                                    employee = new Employee(Integer.parseInt(tokens[0].trim()), tokens[1].trim(), tokens[2].trim(), tokens[3].trim(), Double.parseDouble(tokens[4].trim()));
                                    this.service.editEmployee(i, employee);
                                } else {
                                    System.out.println("=== ERROR === This employee has fired. You are not able to edit it");
                                }
                            } else {
                                System.out.println("=== ERROR === You have not a record with this ID");
                            }
                        } else {
                            System.out.println("=== ERROR === Wrong ID, Name or Salary field");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("=== ERROR === Wrong number format");
                    }
                } else {
                    System.out.println("=== ERROR === You entered wrong number of fields");
                }
                break;
            }
            case "3": {
                Scanner scanner = new Scanner(System.in);
                String entity;
                System.out.println("Fire Employee");
                System.out.println("Enter an ID: ");
                entity = scanner.nextLine();
                try {
                    // we trim this entity and check if it is an integer number.
                    // If not - Error Exception
                    int i = Integer.parseInt(entity.trim());
                    // ID must be positive number
                    if (i > 0) {
                        // check if such ID already exist - If exist we will check if this employee is active
                        Employee employee;
                        employee = this.service.searchById(i);
                        if (employee != null) {
                            // check if this employee is active
                            if (this.service.isActive(employee)) {
                                System.out.println("You will fire this employee:");
                                System.out.println(employee.toString());
                                this.service.fireEmployee(i);
                                System.out.printf("You have fired successfully record with ID: %d%n", i);
                                System.out.println(employee.toString());
                            } else {
                                System.out.println("=== ERROR === This employee has already fired");
                            }
                        } else {
                            System.out.println("=== ERROR === You have not a record with this ID");
                        }
                    } else {
                        System.out.println("=== ERROR === Wrong ID");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("=== ERROR === Wrong number format");
                }
                break;
            }
            case "4": {
                System.out.printf("List employees%n%n");
                // list active employees
                this.service.listEmployees();
                break;
            }
            case "5": {
                try {
                    Scanner scanner = new Scanner(System.in);
                    String entity;
                    Employee employee;
                    System.out.println("Search");
                    System.out.println("You can search by Id, Name or Department.");
                    System.out.println("Use these formats: Search Id (id) | Search Name (name) | Search Department (department):");
                    entity = scanner.nextLine();
                    if (entity.trim().toLowerCase().startsWith("search ")) {
                        entity = entity.trim().substring(7);
                        if (entity.trim().toLowerCase().startsWith("id ")) {
                            entity = entity.trim().substring(3);
                            entity = entity.trim();
                            try {
                                int i = Integer.parseInt(entity);
                                employee = this.service.searchById(i);
                                if (employee != null) {
                                    System.out.println(employee.toString());
                                } else {
                                    System.out.println("=== ERROR === You have not a record with this ID");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("=== ERROR === Wrong number format");
                            }
                        } else if (entity.trim().toLowerCase().startsWith("name ")) {
                            entity = entity.trim().substring(5);
                            entity = entity.trim();
                            this.service.searchByName(entity);
                        } else if (entity.trim().toLowerCase().startsWith("department ")) {
                            entity = entity.trim().substring(11);
                            entity = entity.trim();
                            this.service.searchByDepartment(entity);
                        } else {
                            System.out.println("=== ERROR === Wrong format");
                        }
                    } else {
                        System.out.println("=== ERROR === Wrong format");
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("=== ERROR === Problem with scanner");
                }
                ;
                break;
            }
            case "6": {
                System.out.println("Save & Exit");
                this.service.saveData();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + command);
        }
    }
}

