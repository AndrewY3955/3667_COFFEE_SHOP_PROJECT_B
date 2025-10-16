/**
 * Concrete Decorator class
 */
public class CaramelDecorator extends CoffeeDecorator
{

	public CaramelDecorator(Coffee coffee) 
    {
		super(coffee);
	}


	@Override
	public String getDescription() 
    {
		return super.getDescription()+ " with added Caramel ";
	}

	@Override
	public double extra() {
		return 1.0;
	}
	
}
