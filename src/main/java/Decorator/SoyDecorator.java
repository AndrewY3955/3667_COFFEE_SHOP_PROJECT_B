/**
 * Concrete Decorator class
 */
public class SoyDecorator extends CoffeeDecorator
{

	public SoyDecorator(Coffee coffee) 
    {
		super(coffee);
	}


	@Override
	public String getDescription() 
    {
		return super.getDescription()+ " with added Soy ";
	}

	@Override
	public double extra() {
		return 1.0;
	}
	
}
