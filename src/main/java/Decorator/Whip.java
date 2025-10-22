package main.java.Decorator;

import main.java.Base.Coffee;

public class Whip extends CondimentDecorator {
    private double price = 0.15;

    public Whip(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Whip";
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
