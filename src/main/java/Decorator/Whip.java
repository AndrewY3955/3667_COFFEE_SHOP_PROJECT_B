package main.java.Decorator;

import main.java.Base.Coffee;

public class Whip extends CondimentDecorator {
    private Coffee coffee;

    public Whip(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Whip";
    }

    @Override
    public double cost(String size) {
        double add = 0.15;
        return coffee.cost(size) + (add * getSizeMultiplier(size));
    }

    private double getSizeMultiplier(String size) {
        switch (size) {
            case "Medium": return 1.2;
            case "Large": return 1.4;
            default: return 1.0;
        }
    }
}
