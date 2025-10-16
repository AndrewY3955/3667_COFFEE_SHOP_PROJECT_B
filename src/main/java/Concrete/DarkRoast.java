/**
 * This class is a type of drink made from Coffee. 
 * Its a derived class called DarkRoast
 * 
 */
public class DarkRoast implements Coffee {

    @Override
    public String getDescription() {
        return "Dark Roast Coffee";
    }

    @Override
    public double cost() {
        return 1.5;
    }

    @Override
    public int size() {
        return 2; // MEDIUM by default
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
