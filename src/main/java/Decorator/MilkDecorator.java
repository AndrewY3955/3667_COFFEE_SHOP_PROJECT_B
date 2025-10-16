/**
 * Concrete Decorator class
 */
public class MilkDecorator extends CoffeeDecorator
{

	public MilkDecorator(Coffee coffee) 
    {
		super(coffee);
	}


	@Override
	public String getDescription() 
    {
		return super.getDescription()+ " with added Milk ";
	}

	@Override
	public double extra() {
		return 0.20;
	}
	
}
