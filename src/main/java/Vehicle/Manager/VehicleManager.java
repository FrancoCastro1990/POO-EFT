package Vehicle.Manager;


import Repository.VehicleRepository;
import Vehicle.Vehicle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Vehicle.VehicleLoader;


public class VehicleManager {
    private List<Vehicle> vehicles;
    private final ConcurrentHashMap<String, Vehicle> vehicleMap;
    private static final String FILE_NAME = "vehicles.txt";
    private final VehicleRepository repository;
    //Preferi el uso de Executor para poder optimizar el uso de hilos
    private final ExecutorService executor;


    public VehicleManager() {
        this.vehicles = Collections.synchronizedList(new ArrayList<>());
        this.vehicleMap = new ConcurrentHashMap<>();
        this.repository = new VehicleRepository(FILE_NAME);
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        loadVehiclesFromFile();
    }

    public void addVehicleAsync(Vehicle vehicle){
//        Thread thread = new Thread(new VehicleLoader(this,vehicle));
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        executor.submit(new VehicleLoader(this, vehicle));
    }

    public synchronized void addVehicle(Vehicle vehicle) {
        try {
            if (findVehicleByLicensePlate(vehicle.getLicensePlate()) == null) {
                vehicles.add(vehicle);
                this.vehicleMap.put(vehicle.getLicensePlate(), vehicle);
                saveVehicleToFile(vehicle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void loadVehiclesFromFile() {

        try {
            this.vehicles = repository.getAll();
            for (Vehicle vehicle : this.vehicles) {
                this.vehicleMap.put(vehicle.getLicensePlate(), vehicle);
            }
        } catch (Exception e) {
            System.err.println("Error loading vehicles: " + e.getMessage());
        }
    }

    private synchronized void saveVehicleToFile(Vehicle vehicle) {
        try  {
            repository.add(vehicle);
        } catch (Exception e) {
            System.err.println("Error saving vehicle: " + e.getMessage());
        }
    }

    private void saveVehiclesToFile() {
        try  {
            repository.updateAll(this.vehicles);

        } catch (Exception e) {
            System.err.println("Error saving vehicles: " + e.getMessage());
        }
    }

    public Vehicle findVehicleByLicensePlate(String licensePlate) {
        return this.vehicleMap.get(licensePlate);
    }

    public void listVehicles() {
        for (Vehicle v : vehicles) {
            v.displayData();
        }
    }


    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
