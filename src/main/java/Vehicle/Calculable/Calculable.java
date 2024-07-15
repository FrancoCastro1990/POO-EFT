package Vehicle.Calculable;

public interface Calculable {
    double IVA = 0.19;
    double CARGO_DISCOUNT = 0.03;
    double PASSENGER_DISCOUNT = 0.07;

    void calculateBill(int rentalDays);
}
