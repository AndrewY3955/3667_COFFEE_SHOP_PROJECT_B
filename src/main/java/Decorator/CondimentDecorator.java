package main.java.Decorator;

import main.java.Base.Coffee;

/** Abstract decorator that wraps Coffee */
public abstract class CondimentDecorator extends Coffee {
    protected Coffee coffee;

    public CondimentDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    public abstract String getDescription();

    /** Recursively sum all condiments */
    public double getCondimentSum() {
        return 0.0; // overridden in concrete decorators
    }

    /** Size multiplier utility for decorator */
    protected double getSizeMultiplier(String size) {
        switch(size) {
            case "Medium": return 1.2;
            case "Large": return 1.4;
            default: return 1.0;
        }
    }
}
