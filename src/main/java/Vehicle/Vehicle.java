package Vehicle;

import Vehicle.Calculable.Calculable;

public abstract class Vehicle implements Calculable {
    protected String licensePlate;
    protected String brand;
    protected String model;
    protected int year;
    protected double dailyRate;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }



    public Vehicle() {}

    public Vehicle(String licensePlate, String brand, String model, int year, double dailyRate) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.dailyRate = dailyRate;
    }

    @Override
    public void calculateBill(int rentalDays) {
        double basePrice = dailyRate * rentalDays;
        double vatAmount = basePrice * IVA;
        System.out.println("Base Price: $" + basePrice);
        System.out.println("IVA (" + (IVA * 100) + "%): $" + vatAmount);
        System.out.println("Total: $" + (basePrice + vatAmount));
    }


    public abstract void displayData();
}