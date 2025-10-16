/**
 * Espresso concrete base class
 */
public class Espresso implements Coffee {

    @Override
    public String getDescription() {
        return "Espresso";
    }

    @Override
    public double cost() {
        return 2.0;
    }

    @Override
    public int size() {
        return 2; // MEDIUM default
    }

    @Override
    public double extra() {
        return 0.0;
    }

    @Override
    public double basePrice() {
        return cost();
    }
}
