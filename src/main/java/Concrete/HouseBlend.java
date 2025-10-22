package main.java.Concrete;

import main.java.Base.Coffee;

public class HouseBlend extends Coffee {

    public HouseBlend() {
        description = "House Coffee";
    }

    @Override
    public double cost(String size) {
        double base = 1.50; // coffeeType
        double coffeeSizeMultiplier = getSizeMultiplier(size);
        double sumOfCondiments = 0.0; // base coffee has no condiments
        return ((base * coffeeSizeMultiplier) + ((sumOfCondiments) * coffeeSizeMultiplier));
    }

    protected double getSizeMultiplier(String size) {
        switch (size) {
            case "Medium": return 1.2;
            case "Large": return 1.4;
            default: return 1.0;
        }
    }
}
