/**
 * This class is a type of drink made from Coffee. 
 * Its a derived class called Decaf
 * 
 */
public class Decaf implements Coffee {

    @Override
    public String getDescription() {
        return "Decaf Coffee";
    }

    @Override
    public double cost() {
        return 2.5;
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
