package main.java.Concrete;

import main.java.Base.Coffee;

/** Concrete Coffee: Dark Roast */
public class DarkRoast extends Coffee 
{

    public DarkRoast() 
    {
        description = "Dark Roast";
    }

    @Override
    public double cost(String size) 
    {
        double base = 1.60;
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
