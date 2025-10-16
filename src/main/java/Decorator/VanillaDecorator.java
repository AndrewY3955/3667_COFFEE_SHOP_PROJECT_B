/**
 * Concrete Decorator class
 */
public class VanillaDecorator extends CoffeeDecorator
{

	public VanillaDecorator(Coffee coffee) 
    {
		super(coffee);
	}


	@Override
	public String getDescription() 
    {
		return super.getDescription()+ " with added Vanilla ";
	}

	@Override
	public double extra() {
		return 1.0;
	}
	
}
