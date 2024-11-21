package com.mycompany.superman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;


public class wag {
    // Employee class to store employee data
    static class Employee {
        String name;
        String username;
        String password;
        double id;
        double salary;
        double hourlyRate;
        String role;
        double bonuses;
        private final double deductions;
        public List<String> customDeductions; // To store each custom deduction description
        
  
        
            public Employee(String name, String username, String password, double id, double salary, double hourlyRate, String role) {
                this.name = name;
                this.username = username;
                this.password = password;
                this.id = id;
                this.salary = salary;
                this.hourlyRate = hourlyRate;
                this.role = role;
                this.bonuses = 0.0;  // Initializing bonuses to 0
                this.deductions = 0; // Default deductions
                this.customDeductions = new ArrayList<>();
            }
        
        public void addCustomDeduction(String description, double amount) { //forda adjustment ang ferson fuunction
        this.customDeductions.add(description + ": PHP " + amount);
    }
        

        @Override
        public String toString() {
            return name;
        }
    }

    static class LeaveRequest {
        Employee employee;
        String reason;
        String startDate;  // Start date of the leave
        String endDate;    // End date of the leave
        boolean approved;   // Approval status
        boolean declined;   // Decline status

        public LeaveRequest(Employee employee, String reason, String startDate, String endDate) {
            this.employee = employee;
            this.reason = reason;
            this.startDate = startDate;
            this.endDate = endDate;
            this.approved = false; // Initially not approved
            this.declined = false; // Initially not declined
        }

        @Override
        public String toString() {
            String status = " [Pending]";
            if (approved) {
                status = " [Approved]";
            } else if (declined) {
                status = " [Declined]";
            }
            return employee.name + " requested leave for: " + reason + " from " + startDate + " to " + endDate + "" + status;
        }
    }

    // Advance Pay Request Class to include reason and amount
    static class AdvancePayRequest {
        Employee employee;
        double amount;
        String reason;  // Reason for advance pay
        boolean approved; // Approval status
        boolean declined; // Decline status

        public AdvancePayRequest(Employee employee, double amount, String reason) {
            this.employee = employee;
            this.amount = amount;
            this.reason = reason;
            this.approved = false; // Initially not approved
            this.declined = false; // Initially not declined
        }

        @Override
        public String toString() {
            String status = " [Pending]";
            if (approved) {
                status = " [Approved]";
            } else if (declined) {
                status = " [Declined]";
            }
            return employee.name + " requested an advance paycheck of: PHP " + amount + " for reason: " + reason + "" + status;
        }
    }

    
        
    static class Admin {
        String username;
        String password;
        
        public Admin(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    static ArrayList<LeaveRequest> leaveRequests = new ArrayList<>();
    static ArrayList<AdvancePayRequest> advancePayRequests = new ArrayList<>();
    static ArrayList<Employee> employeeList = new ArrayList<>();
    static Admin admin = new Admin("admin", "admin123");

    public static void main(String[] args) {
        mainMenu();
    }
        //MAIN MENU
        public static void mainMenu() {

        Scanner scanner = new Scanner(System.in);
        int choice = 0;


        while (true) {
            System.out.println();
            System.out.println("==== Main Menu ====");
            System.out.println("[1] Login");
            System.out.println("[2] Exit");

            boolean validInput = false;
            while (!validInput) {
                System.out.print("\nSelect an option: ");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); 

                    if (choice >= 1 && choice <= 2) {
                        validInput = true; 
                    } else {
                        System.out.println("Invalid option. Please select 1 or 2.");
                    }
                } else {
                    // If input is not an integer
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); 
                }
            }
            // Process the if tama na yung input ni user
            switch (choice) {
                case 1:
                    userLogin(scanner); // can go to login function
                    break;
                case 2:
                    System.out.println("\nExiting program. Goodbye!");
                    System.exit(0); // Exit the program
                    break;
            }
        }
    }

   
    // Login for Admin and Employee
    public static void userLogin(Scanner scanner) {
        String username, password;

        while (true) {
            try {
                System.out.println("\n==== Login ====");
                System.out.print("\nEnter username: ");
                username = scanner.nextLine();

                System.out.print("Enter password: ");
                password = scanner.nextLine();

                // Validate login as Admin
                if (validateAdmin(username, password)) {
                    System.out.println("\nLogin Successful as Admin!\n");
                    adminDashboard(scanner);  // Redirect to admin dashboard
                    break; // Exit after successful login

                // Validate login as Employee
                } else {
                    Employee employee = validateEmployee(username, password);
                    if (employee != null) {
                        System.out.println("\nLogin Successful as Employee!\n");
                        employeeDashboard(scanner, employee);  // Redirect to employee dashboard
                        break; // Exit after successful login
                    } else {
                        System.out.println("\nInvalid Username or Password. Please Try Again.\n");
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear the scanner buffer
            }
        }
    }

                                        // Admin login from file
                                        //so bali hindi kona nilagyan ng design yung sa storing ng file handling 
                                        // naka isang line nalang lahat ng info sa pag store ng credentials ni user
                                        public static boolean validateAdmin(String username, String password) {
                                        String filePath = "C:\\Users\\Rainier\\OneDrive\\Documents\\NetBeansProjects\\superman\\src\\main\\java\\com\\mycompany\\superman\\admin.txt";
                                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                                            String line;
                                            while ((line = reader.readLine()) != null) {
                                                String[] credentials = line.split(",");
                                                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                                                    return true;  // Admin login successful
                                                }
                                            }
                                        } catch (IOException e) {
                                            System.out.println("Error reading admin account file: " + e.getMessage());
                                        }
                                        return false;
                                    }


                                        // Employee login from file same with admin 
                                        public static Employee validateEmployee(String username, String password) {
                                        String filePath = "C:\\Users\\Rainier\\OneDrive\\Documents\\NetBeansProjects\\superman\\src\\main\\java\\com\\mycompany\\superman\\employee_accounts.txt";
                                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                                            String line;
                                            while ((line = reader.readLine()) != null) {
                                                String[] credentials = line.split(",");

                                                // Check if the line has enough data fields
                                                if (credentials.length < 6) {
                                                    System.out.println("Invalid data format for employee: " + Arrays.toString(credentials));
                                                    continue; // Skip this line and move to the next
                                                }

                                                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                                                    return new Employee(
                                                        credentials[2], // name
                                                        username, 
                                                        password, 
                                                        Double.parseDouble(credentials[3]), // id
                                                        Double.parseDouble(credentials[4]), // salary
                                                        Double.parseDouble(credentials[5]), // hourlyRate
                                                        "Employee"
                                                    );
                                                }
                                            }
                                        } catch (IOException e) {
                                            System.out.println("Error reading employee account file: " + e.getMessage());
                                        }
                                        return null;
                                    }




    // EMPLOYEE DASHBOARD/MENU
    public static void employeeDashboard(Scanner scanner, Employee employee) {
        while (true) {
        
            System.out.println("\n==== Employee Dashboard (Name: " + employee.name + ") ====");
            System.out.println("[1] View paycheck");
            System.out.println("[2] Apply for leave");
            System.out.println("[3] View bonuses");
            System.out.println("[4] View deductions");
            System.out.println("[5] Request advance paycheck");
            System.out.println("[6] View leave request status");
            System.out.println("[7] View advance pay request status");
            System.out.println("[8] Logout");

            System.out.print("\nEnter option: ");

            String input = scanner.nextLine(); 

            try {
                int option = Integer.parseInt(input); 

                switch (option) {
                    case 1:
                        viewPaycheck(scanner, employee);
                        break;
                    case 2:
                        applyForLeave(scanner, employee);
                        break;
                    case 3:
                        bonuses(scanner, employee);
                        break;
                    case 4:
                        viewDeduction(scanner, employee);
                        break;
                    case 5:
                        requestAdvancePay(scanner, employee);
                        break;
                    case 6:
                        viewLeaveRequestStatus(employee);
                        break;
                    case 7:
                        viewAdvancePayRequestStatus(employee);
                        break;
                    case 8:
                        System.out.println("Logging out...");
                        return; // Exit the method
                    default:
                        System.out.println("\nInvalid option. Please select a number between 1 and 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid number.");
            }
        }
    }

        public static void viewPaycheck(Scanner scanner, Employee employee) {
        boolean viewAgain;
        do {
            System.out.println("\n==== Paycheck for " + employee.name + " ====");

            // Enter working details
            System.out.print("Enter number of hours worked: ");
            double hoursWorked = scanner.nextDouble();
            scanner.nextLine(); // Clear the buffer

            // Overtime details
            System.out.print("Do you have overtime? (Y/N): ");
            String overtimeResponse = scanner.nextLine();
            boolean hasOvertime = overtimeResponse.equalsIgnoreCase("Y");

            double overtimeHours = 0;
            if (hasOvertime) {
                System.out.print("Enter number of overtime hours: ");
                overtimeHours = scanner.nextDouble();
                scanner.nextLine(); // Clear the buffer
            }

            // Calculations
            double baseSalary = hoursWorked * employee.hourlyRate;
            double overtimePay = overtimeHours * (employee.hourlyRate * 1.25); // Overtime rate: 25% more
            double grossSalary = baseSalary + overtimePay + employee.bonuses;

            // Deductions
            double sss = 0.045 * grossSalary;
            double philHealth = 0.035 * grossSalary;
            double pagIbig = Math.min(100, grossSalary * 0.02);
            double tax = calculateTax(grossSalary);

            // Custom deductions
            double customDeductionTotal = displayCustomDeductions(employee);

            // Total deductions and net salary
            double totalDeductions = sss + philHealth + pagIbig + tax + customDeductionTotal;
            double netSalary = grossSalary - totalDeductions;

            // Paycheck breakdown
            System.out.println("\n==== Paycheck Breakdown ====");
            System.out.printf("Employee Name  : %s\n", employee.name);
            System.out.printf("Role           : %s\n", employee.role);
            System.out.printf("Hourly Rate    : PHP %.2f\n", employee.hourlyRate);
            System.out.printf("Hours Worked   : %.0f\n", hoursWorked);
            System.out.printf("Base Salary    : PHP %.2f\n", baseSalary);
            if (hasOvertime) {
                System.out.printf("Overtime Pay   : PHP %.2f (%.0f Overtime Hours)\n", overtimePay, overtimeHours);
            }
            if (employee.bonuses > 0) {
                System.out.printf("Bonuses        : PHP %.2f\n", employee.bonuses);
            }
            System.out.printf("\nGross Salary   : PHP %.2f\n", grossSalary);

            // Deductions display
            System.out.println("\nDeductions:");
            System.out.printf("SSS            : PHP %.2f\n", sss);
            System.out.printf("PhilHealth     : PHP %.2f\n", philHealth);
            System.out.printf("Pag-IBIG       : PHP %.2f\n", pagIbig);
            System.out.printf("Tax            : PHP %.2f\n", tax);
            System.out.printf("Custom Deductions: PHP %.2f\n", customDeductionTotal);
            System.out.printf("\nTotal Deductions: PHP %.2f\n", totalDeductions);
            System.out.printf("Net Salary      : PHP %.2f\n", netSalary);

            // Save paycheck details to a file
            savePaycheckToFile(employee, hoursWorked, overtimeHours, grossSalary, netSalary, sss, philHealth, pagIbig, tax);

            // Prompt for viewing another paycheck
            System.out.print("\nWould you like to view another paycheck? (Y/N): ");
            String response = scanner.nextLine();
            viewAgain = response.equalsIgnoreCase("Y");

        } while (viewAgain);
    }


    public static double calculateTax(double grossSalary) {
        if (grossSalary <= 20833) { // No tax for salary <= 250k annually
            return 0; 
        } else if (grossSalary <= 33332) {
            return (grossSalary - 20833) * 0.20;
        } else if (grossSalary <= 66666) {
            return 2500 + (grossSalary - 33332) * 0.25;
        } else if (grossSalary <= 166666) {
            return 10833 + (grossSalary - 66666) * 0.30;
        } else if (grossSalary <= 666666) {
            return 40833 + (grossSalary - 166666) * 0.32;
        } else {
            return 200833 + (grossSalary - 666666) * 0.35;
        }
    }

    
                                // VIEW PAYCHECK FILE HANDLING
                                private static void savePaycheckToFile(Employee employee, double hoursWorked, double overtimeHours,
                                                               double grossSalary, double netSalary, double sss, double philHealth,
                                                                                                 double pagIbig, double tax) {
                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("paychecks.txt", true))) {
                                        writer.write("Paycheck for " + employee.name + " - Date: " + java.time.LocalDate.now());
                                        writer.newLine();
                                        writer.write(String.format("Hours Worked: %.0f, Overtime: %.0f, Gross Salary: PHP %.2f, Net Salary: PHP %.2f", 
                                            hoursWorked, overtimeHours, grossSalary, netSalary));
                                        writer.newLine();
                                        writer.write("Deductions: SSS PHP " + sss + ", PhilHealth PHP " + philHealth + 
                                                     ", Pag-IBIG PHP " + pagIbig + ", Tax PHP " + tax);
                                        writer.newLine();
                                        writer.write("==============================================");
                                        writer.newLine();
                                    } catch (IOException e) {
                                        System.out.println("Error saving paycheck details: " + e.getMessage());
                                    }
                                }
                                //////////////////////////////////////////////////////////////////////////////////////
                                // Function to load and display custom deductions from log file
                                private static double displayCustomDeductions(Employee employee) {
                                double total = 0.0;
                                try (BufferedReader reader = new BufferedReader(new FileReader("deduction_changes.log"))) {
                                    String line;
                                    System.out.println("\nCustom Deductions:");
                                    while ((line = reader.readLine()) != null) {
                                        if (line.contains("Employee ID: " + employee.id) && line.contains("added")) {
                                            String[] parts = line.split("Amount: PHP ");
                                            if (parts.length > 1) {
                                                String amountString = parts[1].split(" ")[0];
                                                try {
                                                    double deductionAmount = Double.parseDouble(amountString);
                                                    total += deductionAmount;
                                                    System.out.println(" - " + line); // Show deduction line
                                                } catch (NumberFormatException e) {
                                                    System.out.println("Error parsing deduction amount: " + amountString);
                                                }
                                            }
                                        }
                                    }
                                    if (total == 0) {
                                        System.out.println("No custom deductions.");
                                    }
                                } catch (IOException e) {
                                    System.out.println("Error reading deductions log: " + e.getMessage());
                                }
                                return total;
                            }



    
    
    // APPLY FOR LEAVE 
    public static void applyForLeave(Scanner scanner, Employee employee) {
        String choice;
        
        do {
            System.out.println("\n==== Apply for Leave ====");
            System.out.print("\nEnter reason for leave: ");
            String reason = scanner.nextLine();
            
            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();
    
            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDate = scanner.nextLine();
    
            LeaveRequest leaveRequest = new LeaveRequest(employee, reason, startDate, endDate);
            leaveRequests.add(leaveRequest);
            System.out.println("\nLeave request submitted successfully.");
            
            // Save leave request details to a file
            logLeaveRequest(employee, reason, startDate, endDate);
            
            // Prompt if the user wants to apply for another leave
            System.out.print("\nDo you want to apply for another leave? (yes/no): ");
            choice = scanner.nextLine().toLowerCase();
    
        } while (choice.equals("yes"));
    
        // Back to the employee dashboard
        employeeDashboard(scanner, employee);
    }
    
                                    ///////////////////////////////////////////////////////////////////////////
                                    // REQUEST LEAVE FILE HANDLING
                                    private static void logLeaveRequest(Employee employee, String reason, String startDate, String endDate) {
                                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("leave_requests.txt", true))) {
                                            writer.write(String.format("Leave Request for %s (ID: %s) - Date Submitted: %s; Reason: %s; Start Date: %s; End Date: %s%n", 
                                                employee.name, employee.id, java.time.LocalDate.now(), reason, startDate, endDate));
                                        } catch (IOException e) {
                                            System.out.println("Error saving leave request: " + e.getMessage());
                                        }
                                    }
                                    

    // VIEW BONUSES
    public static void bonuses(Scanner scanner, Employee employee) {
        while (true) {
            System.out.println("\n==== Bonus Details for " + employee.name + " ====");
            System.out.println("Bonuses: PHP " + employee.bonuses);  // Display the actual bonuses
            
            logBonusView(employee);

            // if they want to view the bonuses again or go back to the dashboard
            while (true) {
                System.out.print("\nReturn to Dashboard? (Y/N): ");
                String response = scanner.nextLine().trim().toUpperCase();

                if (response.equals("N")) {
                    break; 
                } else if (response.equals("Y")) {
                    System.out.println("Returning to dashboard...");
                    return; // Exit 
                } else {
                    System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
                }
            }
        }
    }

                                        //////////////////////////////////////////////////////////////////
                                       // BONUS FILE HANDLING
                                       private static void logBonusView(Employee employee) {
                                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bonus_views.txt", true))) {
                                            writer.write(String.format("Bonus details viewed for %s on %s; Bonuses: PHP %.2f%n", 
                                                employee.name, java.time.LocalDate.now(), employee.bonuses));
                                        } catch (IOException e) {
                                            System.out.println("Error logging bonus view: " + e.getMessage());
                                        }
                                    }                                    
                                       ////////////////////////////////////////////////////////////////////

    // VIEW DEDUCTION
    public static void viewDeduction(Scanner scanner, Employee employee) {
        while (true) {
            // Pakita yung header at basic info ng employee
            System.out.println("\n==== View Deductions ====");
            System.out.println("Employee ID: " + employee.id);
            System.out.println("Employee Name: " + employee.name);
            
            // Pakita yung current deduction
            System.out.printf("Current deduction: PHP %.2f%n", employee.deductions);
            
            
            logDeductionView(employee);
        
            
            while (true) {
                System.out.print("\nReturn to dashboard? (Y/N): ");
                String response = scanner.nextLine().trim().toUpperCase();

                if (response.equals("N")) {
                    break; // Ulitin lang yung loop para ipakita ulit deductions
                } else if (response.equals("Y")) {
                    System.out.println("\nReturn to dashboard...");
                    return; // Exit at balik sa dashboard
                } else {
                    // Pag mali yung input, sabihan lang na invalid
                    System.out.println("Invalid input. Please input 'Y' for Yes or 'N' for No.");
                }
            }
        }
    }

                                    ////////////////////////////////////////////////////////////////////////
                                    // DEDUCTION VIEW FILE HANDLING
                                    private static void logDeductionView(Employee employee) {
                                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("deduction_views.txt", true))) {
                                            writer.write(String.format("Deductions viewed for %s (ID: %s) on %s; Current Deduction: PHP %.2f%n", 
                                                employee.name, employee.id, java.time.LocalDate.now(), employee.deductions));
                                        } catch (IOException e) {
                                            System.out.println("Error logging deduction view: " + e.getMessage());
                                        }
                                    }                                    
                                    ///////////////////////////////////////////////////////////////////////


    // REQUEST PAYCHECK
    public static void requestAdvancePay(Scanner scanner, Employee employee) {
        while (true) {
            
            System.out.println("\n==== Request Advance Pay ====");
            
            double amount = 0;
            while (true) {
                // Input ng amount, dapat tama yung format
                System.out.print("\nEnter amount for advance pay request: ");
                try {
                    amount = scanner.nextDouble();
                    scanner.nextLine(); 
                    if (amount > 0) {
                        break; // Proceed na kung valid amount
                    } else {
                        System.out.println("Invalid amount. It should be a positive number.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); 
                }
            }
            
            // Input ng reason para sa advance pay
            System.out.print("Enter reason for advance pay request: ");
            String reason = scanner.nextLine();
            
            
            AdvancePayRequest advancePayRequest = new AdvancePayRequest(employee, amount, reason);
            advancePayRequests.add(advancePayRequest);
            System.out.println("\nAdvance pay request submitted successfully.");
            
            // file handling
            logAdvancePayRequest(employee, amount, reason);

            
            while (true) {
                System.out.print("\nWould you like to request another advance pay? (Y/N): ");
                String response = scanner.nextLine().trim().toUpperCase();

                if (response.equals("Y")) {
                    break; // Ulitin lang yung loop para makapag-request ulit
                } else if (response.equals("N")) {
                    System.out.println("Returning to dashboard...");
                    return; // Exit at balik sa dashboard
                } else {
                    System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
                }
            }
        }
    }

                                    ////////////////////////////////////////////////////////////////////////
                                    // ADVANCE PAY FILE HANDLING
                                    private static void logAdvancePayRequest(Employee employee, double amount, String reason) {
                                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("advance_pay_requests.txt", true))) {
                                            writer.write(String.format("Advance Pay Request by %s (ID: %s) on %s; Requested Amount: PHP %.2f; Reason: %s%n", 
                                                employee.name, employee.id, java.time.LocalDate.now(), amount, reason));
                                        } catch (IOException e) {
                                            System.out.println("Error logging advance pay request: " + e.getMessage());
                                        }
                                    }                                    
                                    /////////////////////////////////////////////////////////////////////////


    // VIEW LEAVE REQUEST STATUS
    @SuppressWarnings("resource")//para lang sa error na yellow haahahah
    public static void viewLeaveRequestStatus(Employee employee) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n==== Leave Request Status ====");
            
            // Loop through leave requests para mahanap yung sa employee
            boolean hasRequests = false;
            for (LeaveRequest request : leaveRequests) {
                if (request.employee.equals(employee)) {
                    System.out.println(request);
                    hasRequests = true;
                }
            }
            
            // Kung walang leave requests, sabihin lang na wala pa
            if (!hasRequests) {
                System.out.println("No leave requests found for this employee.");
            }
            
            // file handling
            logLeaveRequestStatusView(employee);
            
            // Tanongin kung gusto bumalik sa dashboard
            while (true) {
                System.out.print("\nWould you like to go back to the dashboard? (Y/N): ");
                String response = scanner.nextLine().trim().toUpperCase();

                if (response.equals("Y")) {
                    System.out.println("Returning to dashboard...");
                    return; // Exit at balik sa dashboard
                } else if (response.equals("N")) {
                    break; // Ulitin yung loop para ipakita ulit leave request status
                } else {
                    System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
                }
            }
        }
    }

                                    ///////////////////////////////////////////////////////////////////////
                                    // LEAVE REQUEST STATUS FILE HANDLING
                                    private static void logLeaveRequestStatusView(Employee employee) {
                                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("leave_request_views.txt", true))) {
                                            writer.write(String.format("Leave request status viewed by %s (ID: %s) on %s%n", 
                                                employee.name, employee.id, java.time.LocalDate.now()));
                                        } catch (IOException e) {
                                            System.out.println("Error logging leave request view: " + e.getMessage());
                                        }
                                    }                                    
                                    ///////////////////////////////////////////////////////////////////////


        
    // VIEW ADVANCE PAY REQUEST STATUS
    @SuppressWarnings("resource")// for yellow bulb waring 
    public static void viewAdvancePayRequestStatus(Employee employee) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n==== Advance Pay Request Status ====");
            
            // Loop through advance pay requests para mahanap yung sa employee
            boolean hasRequests = false;
            for (AdvancePayRequest request : advancePayRequests) {
                if (request.employee.equals(employee)) {
                    System.out.println(request);
                    hasRequests = true;
                }
            }
            
            // Kung walang advance pay requests, sabihin lang na wala pa
            if (!hasRequests) {
                System.out.println("No advance pay requests found for this employee.");
            }
            
            // file handling
            logAdvancePayRequestStatusView(employee);
            
            
            while (true) {
                System.out.print("\nWould you like to go back to the dashboard? (Y/N): ");
                String response = scanner.nextLine().trim().toUpperCase();

                if (response.equals("Y")) {
                    System.out.println("Returning to dashboard...");
                    return; // Exit at balik sa dashboard
                } else if (response.equals("N")) {
                    break; // Ulitin yung loop para ipakita ulit advance pay request status
                } else {
                    System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
                }
            }
        }
    }
                                ////////////////////////////////////////////////////////////////////////
                                // ADVANCE PAY STATUS FILE HANDLING
                                private static void logAdvancePayRequestStatusView(Employee employee) {
                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("advance_pay_request_views.txt", true))) {
                                        writer.write(String.format("Advance pay request status viewed by %s (ID: %s) on %s%n", 
                                            employee.name, employee.id, java.time.LocalDate.now()));
                                    } catch (IOException e) {
                                        System.out.println("Error logging advance pay request view: " + e.getMessage());
                                    }
                                }                                
                                //////////////////////////////////////////////////////////////////////



    public static void adminDashboard(Scanner scanner) {
        while (true) {
            System.out.println("\n==== Admin Dashboard ====");
            System.out.println("[1] Add/Remove Bonuses");
            System.out.println("[2] Make Adjustments");
            System.out.println("[3] View User Account");
            System.out.println("[4] Manage Payroll Data");
            System.out.println("[5] Approve Advance Pay Requests");
            System.out.println("[6] Approve Leave Requests");
            System.out.println("[7] Create Employee Account");
            System.out.println("[8] Logout");
            System.out.print("\nEnter option: ");
                                
                if (scanner.hasNextInt()) {
                    int option = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                                
                switch (option) {
                    case 1:
                        addingRemoving(scanner);
                        break;
                    case 2:
                        adjustments(scanner, employeeList);
                        break;
                    case 3:
                        viewUserAccounts();
                        break;
                    case 4:
                        managePayroll(scanner);
                        break;
                    case 5:
                        approveAdvancePayRequests(scanner);
                        break;
                    case 6:
                        approveLeaveRequests(scanner);
                        break;
                    case 7:
                        createEmployeeAccount(scanner);
                        break;
                    case 8:
                        logout();
                        return; // Lumabas na sa admin dashboard
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number."); // Invalid na input
                scanner.nextLine(); // Consume invalid input
                }
            }
 }


    // ADD AND REMOVE BONUSES
    public static void addingRemoving(Scanner scanner) {
        loadEmployeeAccounts(); 
        System.out.println("==== Add/Remove Bonuses ====");

        if (employeeList.isEmpty()) {
            System.out.println("No Employee Found.");
            return; // Wala na, exit na
        }

        // display ang employees
        for (int i = 0; i < employeeList.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + employeeList.get(i).name);
        }

        while (true) {
            // display ang option para maggexit
            System.out.println("\nType 'exit' to go back Admin Dashboard");

            System.out.print("\nSelect the employee number to apply bonus/deduction: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Return to dashboard...");
                return; // exit 
            }

            try {
                int employeeNumber = Integer.parseInt(input);

                if (employeeNumber > 0 && employeeNumber <= employeeList.size()) {
                    Employee employee = employeeList.get(employeeNumber - 1);

                    int action = getAction(scanner); 

                    if (action == 1) { // Add Bonus
                        double amount = getBonusAmount(scanner, true); // Get amount for addition
                        employee.bonuses += amount;  // Add bonus to employee

                        // Debugging output to confirm bonus was updated
                        System.out.println("Updated bonus for " + employee.name + ": " + employee.bonuses);

                        logBonusChange(employee, amount, true); // Log addition
                        System.out.println("\nBonus have been added to " + employee.name);
                    } else if (action == 2) { // Remove Bonus
                        double previousBonus = employee.bonuses; // Store previous bonus for logging
                        employee.bonuses = 0;  // Remove all bonuses

                        // Debugging output to confirm bonus was reset
                        System.out.println("Removed bonus for " + employee.name + ": " + employee.bonuses);

                        logBonusChange(employee, previousBonus, false); // Log removal
                        System.out.println("\nBonuses removed from " + employee.name);
                    }

                    System.out.print("\nDo you want to Add/Remove bonuses? (Y/N): ");
                    String continueInput = scanner.nextLine().trim().toUpperCase();
                    if (continueInput.equals("Y")) {
                        continue; // Ulitin ang loop
                    } else if (continueInput.equals("N")) {
                        System.out.println("Return to dashboard...");
                        return; // Exit if user does not want to continue
                    } else {
                        System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
                    }

                } else {
                    System.out.println("\nInvalid employee number. Please select a number from the list.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid employee number or 'exit' to return.");
            }
        }
    }


   
    private static int getAction(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Add Bonus (1) or Remove Bonuses (2): ");
                int action = Integer.parseInt(scanner.nextLine());
                if (action == 1 || action == 2) {
                    return action; // Valid action entered, return the action
                } else {
                    System.out.println("\nInvalid action. Please enter 1 to add or 2 to remove bonuses.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter 1 to add or 2 to remove bonuses.");
            }
        }
    }

    
    private static double getBonusAmount(Scanner scanner, boolean isAddition) {
        double amount = 0;
        while (true) {
            try {
                System.out.print("Enter " + (isAddition ? "Add" : "Remove") + " Bonus Amount: ");
                amount = Double.parseDouble(scanner.nextLine());
                if (amount < 0 && isAddition) {
                    System.out.println("Bonus amount cannot be negative. Please try again.");
                } else {
                    return amount; // Return valid amount
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for the bonus amount.");
            }
        }
    }
     
                                    //////////////////////////////////////////////////////////////////////
                                    // CHANGE BONUS FILE HANDLING
                                    private static void logBonusChange(Employee employee, double amount, boolean isAddition) {
                                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bonus_changes.log", true))) {
                                            String action = isAddition ? "added" : "removed";
                                            writer.write(String.format("%s: Bonus of PHP %.2f %s for employee %s (ID: %s) on %s", 
                                                action, amount, action, employee.name, employee.id, java.time.LocalDate.now()));
                                            writer.newLine();
                                            writer.write("==============================================");
                                            writer.newLine();
                                        } catch (IOException e) {
                                            System.out.println("Error logging bonus change: " + e.getMessage());
                                        }
                                    }
                                    /////////////////////////////////////////////////////////////////////////


    // ADJUSTMENTS FUNCTION
    public static void adjustments(Scanner scanner, List<Employee> employeeList) {
        loadEmployeeAccounts();  
        System.out.println("\n=== Employee Adjustments ===");

        System.out.print("Enter Employee ID for adjustment: ");
        int employeeID = -1;

        // Use scanner to capture an int instead of a double
        while (true) {
            if (scanner.hasNextInt()) { // Check for int
                employeeID = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                break; // Break if input is valid
            } else {
                System.out.println("Invalid input. Please enter a numeric Employee ID.");
                scanner.nextLine(); // Clear the invalid input
            }
        }


        // CHECK IF RECORDED BA YUNG ID NI EMPLOYEE
        Employee selectedEmployee = null;
        for (Employee emp : employeeList) {
            if (emp.id == employeeID) {
                selectedEmployee = emp;
                break;
            }
        }

        // IF TRUE THEN PROCEED HERE
        if (selectedEmployee != null) {
            int action = 0;
            while (true) {
                System.out.println("\nSelect an action:");
                System.out.println("1. Add Deduction");
                System.out.println("2. Remove Deduction");
                System.out.print("Enter your choice (1 or 2): ");

                if (scanner.hasNextInt()) {
                    action = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (action == 1 || action == 2) {
                        break; // Valid action selected
                    } else {
                        System.out.println("Invalid option. Please enter 1 to add or 2 to remove a deduction.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a numeric option (1 or 2).");
                    scanner.nextLine(); // Clear the invalid input
                }
            }

            // IF ADD
            if (action == 1) { 
                System.out.print("\nEnter type of deduction: ");
                String deductionType = scanner.nextLine().trim();

                System.out.print("Enter amount for " + deductionType + ": ");
                double deductionAmount = -1;
                while (true) {
                    if (scanner.hasNextDouble()) {
                        deductionAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        if (deductionAmount >= 0) {
                            break; // Valid amount
                        } else {
                            System.out.println("Amount cannot be negative. Please enter a valid amount.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a numeric amount.");
                        scanner.nextLine(); // Clear the invalid input
                    }
                }

                selectedEmployee.addCustomDeduction(deductionType, deductionAmount);
                System.out.println("\n" + deductionType + " deduction of PHP " + String.format("%.2f", deductionAmount) + " added for " + selectedEmployee.name + ".");
                
                // Log the deduction change
                logDeductionChange(selectedEmployee, deductionType, deductionAmount, true); // true for addition

                
            // IF REMOVE
            } else if (action == 2) { 
                if (selectedEmployee.customDeductions.isEmpty()) {
                    System.out.println("\nNo custom deductions found for " + selectedEmployee.name + ".");
                    return;
                }

                System.out.println("\nExisting Custom Deductions:");
                for (int i = 0; i < selectedEmployee.customDeductions.size(); i++) {
                    System.out.printf("[%d] %s\n", (i + 1), selectedEmployee.customDeductions.get(i));
                }

                int deductionIndex = -1;
                while (true) {
                    System.out.print("Select the number of the deduction to remove: ");
                    if (scanner.hasNextInt()) {
                        deductionIndex = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        if (deductionIndex > 0 && deductionIndex <= selectedEmployee.customDeductions.size()) {
                            break; // Valid selection
                        } else {
                            System.out.println("Invalid selection. Please select a valid deduction number.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a numeric deduction number.");
                        scanner.nextLine(); // Clear the invalid input
                    }
                }

                // Remove the selected deduction
                String deductionToRemove = selectedEmployee.customDeductions.get(deductionIndex - 1);
                selectedEmployee.customDeductions.remove(deductionIndex - 1);
                System.out.println("\n" + deductionToRemove + " deduction removed for " + selectedEmployee.name + ".");
                
                // Log the deduction change
                logDeductionChange(selectedEmployee, deductionToRemove, 0, false); // false for removal
            }
        } else {
            System.out.println("Employee not found. Please check the Employee ID and try again.");
        }

        // Prompt to adjust again
        System.out.print("\nDo you want to adjust another employee? (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();
        if (response.equals("Y")) {
            adjustments(scanner, employeeList); // Call the method again for another adjustment
        } else {
            System.out.println("Exiting adjustments.");
        }
    }
                            ///////////////////////////////////////////////////////////////////////
                            // DEDUCTION FILE HANDLING
                                private static void logDeductionChange(Employee employee, String deductionType, double amount, boolean isAddition) {
                                 try (BufferedWriter writer = new BufferedWriter(new FileWriter("deduction_changes.log", true))) {
                                     String action = isAddition ? "added" : "removed";
                                     writer.write(String.format("%s: Deduction Type: %s, Amount: PHP %.2f, Employee ID: %d, Date: %s", 
                                         action, deductionType, amount, (int) employee.id, java.time.LocalDate.now()));
                                     writer.newLine();
                                     writer.write("==============================================");
                                     writer.newLine();
                                 } catch (IOException e) {
                                     System.out.println("Error logging deduction change: " + e.getMessage());
                                 }
                             }

                            

                            ///////////////////////////////////////////////////////////////////////


     @SuppressWarnings("resource")
     public static void viewUserAccounts() {
     Scanner scanner = new Scanner(System.in);
                                
         // Load employee accounts before displaying
         loadEmployeeAccounts();  
                                
         // Display the accounts
         System.out.println("\n==== Employee Accounts ====");
         if (employeeList.isEmpty()) {
         System.out.println("No employee accounts found.");
         return;
        }
                            
        // Print header for better readability
        System.out.printf("%-15s | %-30s | %-15s%n", "Employee ID", "Name", "Total Bonuses");
        System.out.println("-------------------------------------------------------");
                                
        for (Employee employee : employeeList) {
        // Assuming Employee class has the 'id', 'name', and 'bonuses' fields directly accessible
        System.out.printf("%-15.0f | %-30s | PHP %.2f%n", employee.id, employee.name, employee.bonuses);
        }
                                
        System.out.println("-------------------------------------------------------");
                            
        // Ask if the user wants to go back to the dashboard
        while (true) {
        System.out.print("\nReturn to the dashboard? [Y/N]: ");
        String response = scanner.nextLine().trim().toUpperCase();  // Convert input to uppercase to handle 'y' and 'n'
                            
        if (response.equals("Y")) {
            System.out.println("Returning to Admin Dashboard...");
                break;  // Exit viewUserAccounts, returning control to the calling function (the dashboard)
        } else if (response.equals("N")) {
            System.out.println("Staying in View User Accounts...");
        // Simply display the accounts again if user chooses not to go back
            System.out.println("\n==== Employee Accounts ====");
        for (Employee employee : employeeList) {
            System.out.printf("%-15.0f | %-30s | PHP %.2f%n", employee.id, employee.name, employee.bonuses);
        }
            System.out.println("-------------------------------------------------------");
        } else {
        // Invalid input, ask again
        System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
            }
         }
    }
                            
                                ///FILE HANDLING LOADING DATA FORM TEXT FILES
                                private static void loadEmployeeAccounts() {
                                employeeList.clear();  // Clear the list before loading the data
                                String filePath = "C:\\Users\\Rainier\\OneDrive\\Documents\\NetBeansProjects\\superman\\src\\main\\java\\com\\mycompany\\superman\\employee_accounts.txt";
                                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        String[] details = line.split(",");
                                        // System.out.println("Loading employee: " + Arrays.toString(details)); // Debugging: Print details

                                        // Check if the file has 6 values (no role) or 7 (with role)
                                        if (details.length == 6) {
                                            String role = "Employee";
                                            Employee employee = new Employee(details[2], details[0], details[1],
                                                    Double.parseDouble(details[3]),
                                                    Double.parseDouble(details[4]),
                                                    Double.parseDouble(details[5]),
                                                    role);
                                            employeeList.add(employee);
                                        } else if (details.length == 7) {
                                            // If role is also stored in the file
                                            Employee employee = new Employee(details[2], details[0], details[1],
                                                    Double.parseDouble(details[3]),
                                                    Double.parseDouble(details[4]),
                                                    Double.parseDouble(details[5]),
                                                    details[6]); // Use role from file
                                            employeeList.add(employee);
                                        } else {
                                            System.out.println("Invalid data format for employee: " + Arrays.toString(details));
                                        }
                                    }
                                } catch (IOException e) {
                                    System.out.println("Error loading employee accounts: " + e.getMessage());
                                }
                            }

        

    
    
    public static void managePayroll(Scanner scanner) {
        System.out.println("\n\nManage Payroll Data");
        System.out.println("1. Add Payroll");
        System.out.println("2. Edit Payroll");
        System.out.println("3. Delete Payroll");
        System.out.println("0. Exit to return to Admin Dashboard");  // Exit option
        System.out.print("Choose Action (0-3): ");
    
        int action = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        switch (action) {
            case 0: // Exit option
                System.out.println("Returning to the admin dashboard...");
                return;  // Exit the method and return to the admin dashboard
    
            case 1: // Add Payroll
                System.out.print("Enter new payroll amount: ");
                double newPayrollAmount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                // Logic to add payroll (implement as needed)
                System.out.println("New payroll has been applied successfully: " + newPayrollAmount);
                
                
                // Logic to add payroll (implement as needed)
                logPayrollChange("Added", newPayrollAmount);
                System.out.println("New payroll has been applied successfully: " + newPayrollAmount);
                break;
    
            case 2: // Edit Payroll
                System.out.print("Enter existing payroll amount to edit: ");
                double existingPayrollAmount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
    
                // Logic to find and edit the existing payroll amount
                System.out.print("Enter new payroll amount: ");
                double editedPayrollAmount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
    
                // Implement the logic to update the payroll
                System.out.println("Payroll amount has been updated from " + existingPayrollAmount + " to " + editedPayrollAmount);
                
                
                // Implement the logic to update the payroll
                logPayrollChange("Edited", editedPayrollAmount);
                System.out.println("Payroll amount has been updated from " + existingPayrollAmount + " to " + editedPayrollAmount);
                break;
    
            case 3: // Delete Payroll
                System.out.print("Enter payroll amount to delete: ");
                double amountToDelete = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
    
                System.out.println("Payroll amount of " + amountToDelete + " has been deleted.");
                
                
                 // Logic to delete payroll (implement as needed)
                logPayrollChange("Deleted", amountToDelete);
                System.out.println("Payroll amount of " + amountToDelete + " has been deleted.");
                break;
    
            default:
                System.out.println("Invalid action. Please choose 0, 1, 2, or 3.");
                break;
        }
    }
                        /////////////////////////////////////////////////////////////////
                        // PAYROLL CHANGES FILE HANDLING
                        private static void logPayrollChange(String action, double amount) {
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter("payroll_changes.log", true))) {
                                writer.write(String.format("%s: Payroll amount of PHP %.2f on %s%n", action, amount, java.time.LocalDate.now()));
                                // Uncomment the following line if you want to keep the visual separator
                                // writer.write("==============================================\n");
                            } catch (IOException e) {
                                System.out.println("Error logging payroll change: " + e.getMessage());
                            }
                        }
                        
    
    
        // APPROVE ADVANCE PAYCHECK
        public static void approveAdvancePayRequests(Scanner scanner) {
            System.out.println("\n==== Approve Advance Pay Requests ====");
            if (advancePayRequests.isEmpty()) {
                System.out.println("\nNo advance pay requests to approve.");
                return;
            }

            for (int i = 0; i < advancePayRequests.size(); i++) {
                AdvancePayRequest request = advancePayRequests.get(i);
                System.out.println("[" + (i + 1) + "] " + request);
            }

            System.out.print("Select request number to approve or decline (1 to approve, 2 to decline, 0 to cancel): ");
            int requestNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (requestNumber > 0 && requestNumber <= advancePayRequests.size()) {
                System.out.print("Do you want to approve (1) or decline (2)?: ");
                int action = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                AdvancePayRequest request = advancePayRequests.get(requestNumber - 1);

                if (action == 1) {
                    request.approved = true;
                    logApprovalDecision(request, "Approved");
                    System.out.println("\nAdvance pay request approved.");
                } else if (action == 2) {
                    request.declined = true;
                    logApprovalDecision(request, "Declined");
                    System.out.println("\nAdvance pay request declined.");
                } else {
                    System.out.println("\nInvalid action.");
                }
            } else if (requestNumber == 0) {
                System.out.println("\nCancelled approval process.");
            } else {
                System.out.println("\nInvalid request number.");
            }
        }

                        // ADVANCE PAY APPROVAL FILE HANDLING 
                        private static void logApprovalDecision(AdvancePayRequest request, String status) {
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter("advance_pay_approvals.txt", true))) {
                                Employee employee = request.employee; // Direct access to employee field
                                writer.write(String.format("Advance Pay Request by %s (ID: %s) on %s; Requested Amount: PHP %.2f; Reason: %s; Decision: %s%n", 
                                    employee.name, employee.id, java.time.LocalDate.now(), request.amount, request.reason, status));
                                // Uncomment the following line if you want to keep the visual separator
                                // writer.write("==============================================\n");
                            } catch (IOException e) {
                                System.out.println("Error logging approval decision: " + e.getMessage());
                            }
                        }
             
             
       // CREATE EMPLOYEEE
       public static void createEmployeeAccount(Scanner scanner) {
           System.out.println("\n==== Create New Employee Account ====");
           System.out.print("Enter employee name: ");
           String name = scanner.nextLine();

           System.out.print("Enter username: ");
           String username = scanner.nextLine();

           System.out.print("Enter password: ");
           String password = scanner.nextLine();

           System.out.print("Enter employee ID: ");
           double id = scanner.nextDouble();

           System.out.print("Enter salary: ");
           double salary = scanner.nextDouble();

           System.out.print("Enter hourly rate: ");
           double hourlyRate = scanner.nextDouble();
           scanner.nextLine(); // Consume newline

           Employee newEmployee = new Employee(name, username, password, id, salary, hourlyRate, "Employee");
           employeeList.add(newEmployee);
           System.out.println("\nNew employee account created successfully.");

           // Log the new employee account to a file
           logNewEmployeeAccount(newEmployee);
       }

                            // CREATE ACCOUNT FILE HANDLING
                            private static void logNewEmployeeAccount(Employee employee) {
                                try (BufferedWriter writer = new BufferedWriter(new FileWriter("employee_accounts.txt", true))) {
                                    // Store all employee details in a single line, separated by commas
                                    writer.write(employee.username + "," + employee.password + "," + employee.name + "," + employee.id + "," + employee.salary + "," + employee.hourlyRate);
                                    writer.newLine(); // Move to the next line for the next employee
                                } catch (IOException e) {
                                    System.out.println("Error logging new employee account: " + e.getMessage());
                                }
                            }                                                            


        // APPROVAL OF LEAVE REQUEST
        public static void approveLeaveRequests(Scanner scanner) {
            System.out.println("\n==== Approve Leave Requests ====");
            if (leaveRequests.isEmpty()) {
                System.out.println("No leave requests to approve.");
                return;
            }
            for (int i = 0; i < leaveRequests.size(); i++) {
                LeaveRequest request = leaveRequests.get(i);
                System.out.println("[" + (i + 1) + "] " + request);
            }
            System.out.print("Select request number to approve or decline (1 to approve, 2 to decline, 0 to cancel): ");
            int requestNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (requestNumber > 0 && requestNumber <= leaveRequests.size()) {
                System.out.print("Do you want to approve (1) or decline (2)?: ");
                int action = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                LeaveRequest request = leaveRequests.get(requestNumber - 1);

                if (action == 1) {
                    request.approved = true;
                    logLeaveDecision(request, "Approved");
                    System.out.println("\nLeave request approved.");
                } else if (action == 2) {
                    request.declined = true;
                    logLeaveDecision(request, "Declined");
                    System.out.println("\nLeave request declined.");
                } else {
                    System.out.println("\nInvalid action.");
                }
            } else if (requestNumber == 0) {
                System.out.println("\nCancelled approval process.");
            } else {
                System.out.println("\nInvalid request number.");
            }
        }

                    // LEAVE APPROVAL FILE HANDLING
                    private static void logLeaveDecision(LeaveRequest request, String status) {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("leave_approvals.txt", true))) {
                            Employee employee = request.employee; // Direct access to employee
                            writer.write(String.format("Leave Request Decision for %s (ID: %s) on %s; Reason: %s; Start Date: %s; End Date: %s; Decision: %s%n", 
                                employee.name, employee.id, java.time.LocalDate.now(), request.reason, request.startDate, request.endDate, status));
                            // Uncomment the following line if you want to keep the visual separator
                            // writer.write("==============================================\n");
                        } catch (IOException e) {
                            System.out.println("Error logging leave request decision: " + e.getMessage());
                        }
                    }
                    

    
    public static void logout() {
    System.out.println("Logging out...");
    // Here you could add any cleanup code or save state if needed
    // For example: saveCurrentState();
    System.out.println("You have been logged out successfully.");

    }

}


