package main.java.Concrete;

import main.java.Base.Coffee;

/** Concrete Beverage: Decaf */
public class Decaf extends Coffee 
{

    public Decaf() 
	{
        description = "Decaf";
    }

    @Override
    public double cost(String size) 
	{
        double base = 1.40;
        return base * getSizeMultiplier(size);
    }

    private double getSizeMultiplier(String size) 
	{
        switch (size) 
		{
            case "Medium": return 1.2;
            case "Large": return 1.4;
            default: return 1.0;
        }
    }
}
