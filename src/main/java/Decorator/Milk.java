package main.java.Decorator;

import main.java.Base.Coffee;

public class Milk extends CondimentDecorator {
    private double price = 0.20;

    public Milk(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Milk";
    }

    @Override
    public double cost(String size) {
        double coffeeSizeMultiplier = getSizeMultiplier(size);

        double sumOfAllCondiments = price;
        if (coffee instanceof CondimentDecorator) {
            sumOfAllCondiments += ((CondimentDecorator) coffee).getCondimentSum();
        }

        double baseCoffeeCost = coffee.cost(size) - (sumOfAllCondiments * coffeeSizeMultiplier);
        return ((baseCoffeeCost * coffeeSizeMultiplier) + (sumOfAllCondiments * coffeeSizeMultiplier));
    }

    @Override
    public double getCondimentSum() {
        double sum = price;
        if (coffee instanceof CondimentDecorator) {
            sum += ((CondimentDecorator) coffee).getCondimentSum();
        }
        return sum;
    }
}
