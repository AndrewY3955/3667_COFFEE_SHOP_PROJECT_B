package main.java.Concrete;

import main.java.Base.Coffee;

/** Concrete Coffee: House Blend */
public class HouseBlend extends Coffee 
{

    public HouseBlend() 
    {
        description = "House Blend";
    }

    @Override
    public double cost(String size) 
    {
        double base = 1.50;
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
