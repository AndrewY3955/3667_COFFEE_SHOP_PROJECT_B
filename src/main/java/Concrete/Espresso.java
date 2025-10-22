package main.java.Concrete;

import main.java.Base.Coffee;

public class Espresso extends Coffee {

    public Espresso() {
        description = "Espresso";
    }

    @Override
    public double cost(String size) {
        double base = 1.80;
        double coffeeSizeMultiplier = getSizeMultiplier(size);
        double sumOfCondiments = 0.0;
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
