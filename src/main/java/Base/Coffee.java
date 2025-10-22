package main.java.Base;

/**
 * Abstract Beverage class representing a coffee drink.
 * Each coffee must return a description and cost.
 */
public abstract class Coffee {
    protected String description = "Unknown Coffee";

    public String getDescription() {
        return description;
    }

    public abstract double cost(String size);
}
