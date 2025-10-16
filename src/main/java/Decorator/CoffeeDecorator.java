// Default package (matches other source files in this repo)

/**
 * Decorator base class for Coffee decorators
 */
public class CoffeeDecorator implements Coffee {

    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription();
    }

    @Override
    public int size() {
        // delegate to wrapped coffee
        return coffee.size();
    }

    /**
     * Return a human-readable size label using a switch over the size code.
     * 1 = SMALL, 2 = MEDIUM, 3 = LARGE
     */
    public String sizeLabel() {
        switch (coffee.size()) {
            case 1:
                return "SMALL";
            case 2:
                return "MEDIUM";
            case 3:
                return "LARGE";
            default:
                return "MEDIUM"; // default to MEDIUM
        }
    }

    /**
     * Extra cost contributed by THIS decorator (not cumulative). Subclasses
     * should override to return their topping price.
     */
    @Override
    public double extra() {
        return 0.0;
    }

    /**
     * Base price delegates to the wrapped component (ultimately a base coffee).
     */
    @Override
    public double basePrice() {
        return coffee.basePrice();
    }

    /**
     * Walk decorator chain and sum extras (including this one).
     */
    public double totalExtra() {
        double sum = extra();
        if (coffee instanceof CoffeeDecorator) {
            sum += ((CoffeeDecorator) coffee).totalExtra();
        } else {
            // wrapped coffee is a base coffee; add its extra (should be 0)
            sum += coffee.extra();
        }
        return sum;
    }

    @Override
    public double cost() {
        // size multipliers: SMALL=1.0, MEDIUM=1.2, LARGE=1.4
        double multiplier;
        switch (size()) {
            case 1:
                multiplier = 1.0;
                break;
            case 2:
                multiplier = 1.2;
                break;
            case 3:
                multiplier = 1.4;
                break;
            default:
                multiplier = 1.2;
        }

        double base = basePrice();
        double extras = totalExtra();
        // apply multiplier to both base and toppings
        return (base + extras) * multiplier;
    }

}
