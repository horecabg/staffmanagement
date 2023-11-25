package com.sirmaacademy.staffmanagement;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.sirmaacademy.staffmanagement.manager.Manager;
import com.sirmaacademy.staffmanagement.manager.StaffManager;
import com.sirmaacademy.staffmanagement.service.Service;
import com.sirmaacademy.staffmanagement.service.StaffService;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      //  CSVReader reader = null;
        CSVReader reader;
        CSVWriter writer = null;
        try {
            Path outputPath = Paths.get("output.csv");
                   if (!Files.exists(outputPath)) {
                Files.createFile(outputPath);
            }
            reader = new CSVReader(new FileReader("output.csv"));
            String command;
            Scanner scanner = new Scanner(System.in);
            Service service = new StaffService(reader, writer);
            Manager manager = new StaffManager(service);
            System.out.println("Welcome to Staff Management System");
            displayCommands();
            boolean isRunning = true;
            while (isRunning) {
                try {command = scanner.nextLine();
                    int choice = Integer.parseInt(command);
                    if (1 <= choice && choice <= 6) {
                        if (choice == 6) isRunning = false;
                        manager.execute(command);
                    } else System.out.printf("=== ERROR === Invalid choice. Please try again.%n%n");
                } catch (NumberFormatException ee) {
                    System.out.printf("=== ERROR === Invalid input. Please enter a number.%n%n");
                }
                if (isRunning) displayCommands();
            }
        } catch (IOException e) {
            System.out.println("=== ERROR === IOException");
        }
    }

    private static void displayCommands() {
        System.out.println();
        System.out.println("1. Add Employee");
        System.out.println("2. Edit Employee");
        System.out.println("3. Fire Employee");
        System.out.println("4. List employees");
        System.out.println("5. Search");
        System.out.println("6. Save & Exit");   
        System.out.println();
        System.out.println("Enter your choice (from 1 to 6): ");
    }
}