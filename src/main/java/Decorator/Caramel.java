package main.java.Decorator;

import main.java.Base.Coffee;

public class Caramel extends CondimentDecorator {
    private Coffee coffee;

    public Caramel(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Caramel";
    }

    @Override
    public double cost(String size) {
        double add = 0.30;
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
