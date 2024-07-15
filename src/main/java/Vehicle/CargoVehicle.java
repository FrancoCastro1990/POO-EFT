package Vehicle;

import Vehicle.Calculable.Calculable;

public class CargoVehicle extends Vehicle implements Calculable {
    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    private double loadCapacity;

    public CargoVehicle(String licensePlate, String brand, String model, int year, double dailyRate, double loadCapacity) {
        super(licensePlate, brand, model, year, dailyRate);
        this.loadCapacity = loadCapacity;
    }

    @Override
    public void calculateBill(int rentalDays) {
        super.calculateBill(rentalDays);
        double discount = dailyRate * rentalDays * CARGO_DISCOUNT;
        System.out.println("Cargo Discount (" + (CARGO_DISCOUNT * 100) + "%): $" + discount);
        System.out.println("Final Total: $" + (dailyRate * rentalDays * (1 + IVA - CARGO_DISCOUNT)));
    }

    @Override
    public void displayData() {
        System.out.println("Cargo Vehicle - License Plate: " + licensePlate + ", Load Capacity: " + loadCapacity + " kg" + ", Brand: " + brand + ", Model: " + model + ", Year: " + year);
    }
}
