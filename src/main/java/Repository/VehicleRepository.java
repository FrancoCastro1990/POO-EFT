package Repository;

import Vehicle.Vehicle;
import Vehicle.CargoVehicle;
import Vehicle.PassengerVehicle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class VehicleRepository implements Repository<Vehicle> {
    private String fileName;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public VehicleRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Vehicle> getAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        lock.readLock().lock();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 7) {
                    System.err.println("Invalid line format: " + line);
                    continue;
                }
                if (parts[0].equals("Cargo")) {
                    vehicles.add(new CargoVehicle(
                            parts[1], // licensePlate
                            parts[2], // brand
                            parts[3], // model
                            Integer.parseInt(parts[4]), // year
                            Double.parseDouble(parts[5].replace(",", "")), // dailyRate
                            Double.parseDouble(parts[6].replace(",", ""))  // loadCapacity
                    ));
                } else if (parts[0].equals("Passenger")) {
                    vehicles.add(new PassengerVehicle(
                            parts[1], // licensePlate
                            parts[2], // brand
                            parts[3], // model
                            Integer.parseInt(parts[4]), // year
                            Double.parseDouble(parts[5].replace(",", "")), // dailyRate
                            Integer.parseInt(parts[6].replace(",", ""))  // passengerCapacity
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error reading vehicles from file: " + e.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
        return vehicles;
    }

    @Override
    public void add(Vehicle value) {
        lock.writeLock().lock();
        try (FileWriter writer = new FileWriter(this.fileName, true)) {
            if (value instanceof CargoVehicle) {
                CargoVehicle cargo = (CargoVehicle) value;
                writer.write(String.format(Locale.US, "Cargo,%s,%s,%s,%d,%.2f,%.2f\n",
                        cargo.getLicensePlate(), cargo.getBrand(), cargo.getModel(),
                        cargo.getYear(), cargo.getDailyRate(), cargo.getLoadCapacity()));
            } else if (value instanceof PassengerVehicle) {
                PassengerVehicle passenger = (PassengerVehicle) value;
                writer.write(String.format(Locale.US, "Passenger,%s,%s,%s,%d,%.2f,%d\n",
                        passenger.getLicensePlate(), passenger.getBrand(), passenger.getModel(),
                        passenger.getYear(), passenger.getDailyRate(), passenger.getPassengerCapacity()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error adding vehicle to file: " + e.getMessage(), e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void updateAll(List<Vehicle> values) {
        lock.writeLock().lock();
        try (FileWriter writer = new FileWriter(this.fileName)) {
            for (Vehicle vehicle : values) {
                if (vehicle instanceof CargoVehicle) {
                    CargoVehicle cargo = (CargoVehicle) vehicle;
                    writer.write(String.format(Locale.US, "Cargo,%s,%s,%s,%d,%.2f,%.2f\n",
                            cargo.getLicensePlate(), cargo.getBrand(), cargo.getModel(),
                            cargo.getYear(), cargo.getDailyRate(), cargo.getLoadCapacity()));
                } else if (vehicle instanceof PassengerVehicle) {
                    PassengerVehicle passenger = (PassengerVehicle) vehicle;
                    writer.write(String.format(Locale.US, "Passenger,%s,%s,%s,%d,%.2f,%d\n",
                            passenger.getLicensePlate(), passenger.getBrand(), passenger.getModel(),
                            passenger.getYear(), passenger.getDailyRate(), passenger.getPassengerCapacity()));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating vehicles in file: " + e.getMessage(), e);
        } finally {
            lock.writeLock().unlock();
        }
    }
}