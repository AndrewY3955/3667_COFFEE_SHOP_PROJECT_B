/**
 * Concrete Decorator class
 */
public class MochaDecorator extends CoffeeDecorator
{

	public MochaDecorator(Coffee coffee) 
    {
		super(coffee);
	}


	@Override
	public String getDescription() 
    {
		return super.getDescription()+ " with added Mocha ";
	}
	@Override
	public double extra() {
		return 0.35;
	}
	
}
