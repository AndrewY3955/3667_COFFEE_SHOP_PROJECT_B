/**
 * This class is a type of drink made from Coffee. 
 * Its a derived class called HouseBlendCoffee
 * 
 */
	public class HouseBlendCoffee implements Coffee {

		@Override
		public String getDescription() {
			return "House Blend Coffee";
		}

		@Override
		public double cost() {
			return 2.5;
		}

		@Override
		public int size() {
			// default to MEDIUM
			return 2;
		}

		@Override
		public double extra() {
			return 0.0;
		}

		@Override
		public double basePrice() {
			return cost();
		}

	}
