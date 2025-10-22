package main.java.Decorator;

import main.java.Base.Coffee;

public class Soy extends CondimentDecorator 
{
    private Coffee coffee;

    public Soy(Coffee coffee) 
	{
        this.coffee = coffee;
    }

    @Override
    public String getDescription() 
	{
        return coffee.getDescription() + ", Soy";
    }

    @Override
    public double cost(String size) 
	{
        double add = 0.25;
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
