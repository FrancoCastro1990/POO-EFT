package Vehicle;

import Vehicle.Manager.VehicleManager;

public class VehicleLoader implements Runnable {
    private VehicleManager manager;
    private Vehicle vehicle;

    public VehicleLoader(VehicleManager manager, Vehicle vehicle) {
        this.manager = manager;
        this.vehicle = vehicle;
    }

    @Override
    public void run() {
        try {
            //agregamos de manera async un libro
            manager.addVehicle(vehicle);
        } catch (Exception e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
        }
    }
}
