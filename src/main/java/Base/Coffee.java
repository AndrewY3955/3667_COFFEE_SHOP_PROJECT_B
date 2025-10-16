/**
 * Coffee interface: describes coffee beverage behavior
 */
public interface Coffee {

	/**
	 * Description of the beverage
	 */
	String getDescription();

	/**
	 * Base cost of the beverage (without decorators)
	 */
	double cost();

	/**
	 * Size code: 1 = SMALL, 2 = MEDIUM, 3 = LARGE
	 */
	int size();

	/**
	 * Extra topping cost contributed by this component. Base coffees return 0.
	 * Decorators should override to return the topping's price.
	 */
	double extra();

	/**
	 * Base price of this beverage (without decorator extras). For base coffee
	 * implementations this is the same as cost(); decorators should delegate
	 * to the wrapped component.
	 */
	double basePrice();

}
