package main.java.Decorator;

import main.java.Base.Coffee;

/** Abstract decorator that wraps Coffee */
public abstract class CondimentDecorator extends Coffee 
{
    public abstract String getDescription();
}
