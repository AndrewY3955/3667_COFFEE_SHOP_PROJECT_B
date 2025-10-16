public class ExampleMain {
    public static void main(String[] args) {
        // create a DarkRoast base (default size MEDIUM = 2)
        DarkRoast base = new DarkRoast();

        // For this demo we will create a LARGE coffee by creating a small helper
        // anonymous subclass that overrides size(). This keeps changes minimal.
        DarkRoast largeBase = new DarkRoast() {
            @Override
            public int size() {
                return 3; // LARGE
            }
        };

        // Add Milk and Mocha decorators
        Coffee withMilk = new MilkDecorator(largeBase);
        Coffee withMilkAndMocha = new MochaDecorator(withMilk);

        double total = withMilkAndMocha.cost();
        System.out.printf("Total price = %.2f\n", total);
    }
}
