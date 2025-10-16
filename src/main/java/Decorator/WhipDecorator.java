/**
 * Concrete Decorator class
 */
public class WhipDecorator extends CoffeeDecorator
{

	public WhipDecorator(Coffee coffee) 
    {
		super(coffee);
	}


	@Override
	public String getDescription() 
    {
		return super.getDescription()+ " with added Whip ";
	}

	@Override
	public double extra() {
		return 1.0;
	}
	
}
