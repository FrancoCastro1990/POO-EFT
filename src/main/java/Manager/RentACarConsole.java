package Manager;

import Vehicle.CargoVehicle;
import Vehicle.Manager.VehicleManager;
import Vehicle.PassengerVehicle;
import Vehicle.Vehicle;

import java.util.List;
import java.util.Scanner;

public class RentACarConsole {
    private VehicleManager manager;
    private Scanner scanner;

    public RentACarConsole() {
        this.manager = new VehicleManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    listVehicles();
                    break;
                case 3:
                    showBills();
                    break;
                case 4:
                    showAllVehicles();
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for using Rent-a-Car BriefDrive. Goodbye!");
                    manager.shutdown();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n--- Rent-a-Car BriefDrive Menu ---");
        System.out.println("1. Add Vehicle");
        System.out.println("2. List Vehicles");
        System.out.println("3. Show Bills");
        System.out.println("4. show all vehicles");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }

    private void addVehicle() {
        System.out.println("\n--- Add Vehicle ---");
        System.out.print("Enter vehicle type (1 for Cargo, 2 for Passenger): ");
        int type =  getValidVehicleType();

        String licensePlate = getValidLicensePlateInput("Enter license plate (format XXXX99): ");
        String brand = getValidInput("Enter brand: ");
        String model =  getValidInput("Enter model: ");
        int year = getValidIntInput("Enter year: ", 1900, 2100);
        double dailyRate = getValidDoubleInput("Enter daily rate: ", 0, Double.MAX_VALUE);

        Vehicle vehicle;
        if (type == 1) {
            double loadCapacity = getValidDoubleInput("Enter load capacity: ", 0, Double.MAX_VALUE);
            vehicle = new CargoVehicle(licensePlate, brand, model, year, dailyRate, loadCapacity);
        } else {
            int passengerCapacity = getValidIntInput("Enter passenger capacity: ", 1, 100);
            vehicle = new PassengerVehicle(licensePlate, brand, model, year, dailyRate, passengerCapacity);
        }

        manager.addVehicleAsync(vehicle);

    }

    private int getValidVehicleType() {
        while (true) {
            System.out.print("Enter vehicle type (1 for Cargo, 2 for Passenger): ");
            try {
                int type = Integer.parseInt(scanner.nextLine());
                if (type == 1 || type == 2) {
                    return type;
                }
                System.out.println("Invalid type. Please enter 1 for Cargo or 2 for Passenger.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private String getValidInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if(!input.isEmpty()) {
                return input;
            }
            System.out.println("Invalid input. Please try again.");
        }
    }

    private String getValidLicensePlateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if(!input.isEmpty() && isValidLicensePlate(input)) {
                return input;
            }
            System.out.println("Invalid license plate. Please try again.");
        }
    }

    private boolean isValidLicensePlate(String licensePlate) {
        // Validación para el formato chileno: cuatro letras seguidas de dos números
        return licensePlate != null && licensePlate.matches("[A-Z]{4}\\d{2}");
    }

    private int getValidIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }

        }
    }

    private double getValidDoubleInput(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double input = Double.parseDouble(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    private void listVehicles() {
        System.out.println("\n--- List of Vehicles ---");
        manager.listVehicles();
    }

    private void showBills() {
        System.out.println("\n--- Show Bills ---");
        System.out.print("Enter license plate: ");
        String licensePlate = scanner.nextLine();
        System.out.print("Enter rental days: ");
        int rentalDays = Integer.parseInt(scanner.nextLine());

        Vehicle vehicle = manager.findVehicleByLicensePlate(licensePlate);
        if (vehicle != null) {
            vehicle.calculateBill(rentalDays);
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    private void showAllVehicles() {
        System.out.println("\n--- Show All Vehicles ---");

       manager.listVehicles();
    }
}
