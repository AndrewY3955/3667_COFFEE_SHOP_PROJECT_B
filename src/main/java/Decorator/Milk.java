package main.java.Decorator;

import main.java.Base.Coffee;

/** Adds Milk to a coffee */
public class Milk extends CondimentDecorator 
{
    private Coffee coffee;

    public Milk(Coffee coffee) 
	{
        this.coffee = coffee;
    }

    @Override
    public String getDescription() 
    {
        return coffee.getDescription() + ", Milk";
    }

    @Override
    public double cost(String size) 
    {
        double add = 0.20;
        return coffee.cost(size) + (add * getSizeMultiplier(size));
    }

    private double getSizeMultiplier(String size) 
    {
        switch (size) {
            case "Medium": return 1.2;
            case "Large": return 1.4;
            default: return 1.0;
        }
    }
}
