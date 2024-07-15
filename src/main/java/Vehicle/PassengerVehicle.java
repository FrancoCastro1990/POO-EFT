package Vehicle;

import Vehicle.Calculable.Calculable;

public class PassengerVehicle extends Vehicle implements Calculable {
    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    private int passengerCapacity;

    public PassengerVehicle(String licensePlate, String brand, String model, int year, double dailyRate, int passengerCapacity) {
        super(licensePlate, brand, model, year, dailyRate);
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public void calculateBill(int rentalDays) {
        super.calculateBill(rentalDays);
        double discount = dailyRate * rentalDays * PASSENGER_DISCOUNT;
        System.out.println("Passenger Discount (" + (PASSENGER_DISCOUNT * 100) + "%): $" + discount);
        System.out.println("Final Total: $" + (dailyRate * rentalDays * (1 + IVA - PASSENGER_DISCOUNT)));
    }

    @Override
    public void displayData() {
        System.out.println("Passenger Vehicle - License Plate: " + licensePlate + ", Passenger Capacity: " + passengerCapacity + ",Brand: " + brand + ", Model: " + model + ", Year: " + year);
    }
}
