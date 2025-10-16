import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Swing GUI for Coffee Shop builder that follows the pricing schema:
 * total = (base + toppings) * sizeMultiplier
 */
public class CoffeeShopGUI extends JFrame {

    // lightweight menu entry for UI; price will be read from concrete classes
    record MenuBase(String id, String name) {}
    record Condiment(String id, String name, double price) {}

    private final List<MenuBase> bases = List.of(
            new MenuBase("espresso", "Espresso"),
        new MenuBase("darkroast", "Dark Roast"),
        new MenuBase("decaf", "Decaf"),
        new MenuBase("houseblend", "House Blend")
    );

    private final List<Condiment> condiments = List.of(
            new Condiment("milk", "Milk", 0.20),
            new Condiment("mocha", "Mocha", 0.35),
            new Condiment("soy", "Soy", 0.25),
            new Condiment("whip", "Whip", 0.30),
            new Condiment("caramel", "Caramel", 0.40),
            new Condiment("vanilla", "Vanilla", 0.30)
    );

    // UI components
    private final JPanel leftPanel = new JPanel();
    private final JPanel middlePanel = new JPanel();
    private final JPanel rightPanel = new JPanel();

    private final ButtonGroup baseGroup = new ButtonGroup();
    private final ButtonGroup sizeGroup = new ButtonGroup();

    // track selected condiment checkboxes
    private final List<JCheckBox> condimentChecks = new ArrayList<>();

    private final JLabel descriptionLabel = new JLabel("No base selected");
    private final JTextArea breakdownArea = new JTextArea(12, 48);
    private final JLabel totalLabel = new JLabel("Total: $0.00");
    private final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    private final DefaultListModel<String> cartModel = new DefaultListModel<>();
    private JList<String> cartList; // referenced in multiple methods

    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    

    public CoffeeShopGUI() {
        setTitle("Coffee Shop Builder");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    // increase window size by ~25% for more room
    setSize(1250, 750);
        setLayout(new BorderLayout(12, 12));

        var header = new JLabel("Coffee Shop Builder");
        header.setFont(header.getFont().deriveFont(Font.BOLD, 20f));
        header.setBorder(new EmptyBorder(12, 12, 0, 12));
        add(header, BorderLayout.NORTH);

        var center = new JPanel(new GridLayout(1, 3, 12, 12));
        center.setBorder(new EmptyBorder(12, 12, 12, 12));
        add(center, BorderLayout.CENTER);

        buildLeftPanel();
        buildMiddlePanel();
        buildRightPanel();

        center.add(leftPanel);
        center.add(middlePanel);
        center.add(rightPanel);

        restoreSession();

        setVisible(true);
    }

    private void buildLeftPanel() {
    leftPanel.setLayout(new BorderLayout(8, 8));
    leftPanel.setBorder(BorderFactory.createTitledBorder("Base & Size"));

    // short instruction to make the section clearer
    JLabel leftInstr = new JLabel("Choose one base and one size multiplier");
    leftInstr.setFont(leftInstr.getFont().deriveFont(12f));
    leftInstr.setBorder(new EmptyBorder(6,6,6,6));
    leftPanel.add(leftInstr, BorderLayout.NORTH);

        JPanel baseList = new JPanel();
        baseList.setLayout(new BoxLayout(baseList, BoxLayout.Y_AXIS));
        baseList.setBorder(new EmptyBorder(8, 8, 8, 8));
        for (MenuBase b : bases) {
            Coffee baseInst = createBaseById(b.id());
            double price = baseInst.basePrice();
            JRadioButton rb = new JRadioButton(b.name() + " - " + currency.format(price));
            rb.setActionCommand(b.id());
            rb.addActionListener(e -> onSelectionChanged());
            rb.setFont(rb.getFont().deriveFont(14f));
            rb.setToolTipText(b.name() + " - " + currency.format(price) + ". Select this as the drink base.");
            baseGroup.add(rb);
            baseList.add(rb);
            // restore selection if matches pref
            if (b.id().equals(prefs.get("lastBase", ""))) rb.setSelected(true);
        }

    JPanel sizePanel = new JPanel();
    sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.Y_AXIS));
    sizePanel.setBorder(new EmptyBorder(8, 8, 8, 8));
    JRadioButton small = new JRadioButton("Small - x1.0");
    small.setActionCommand("1");
    JRadioButton medium = new JRadioButton("Medium - x1.2");
    medium.setActionCommand("2");
    JRadioButton large = new JRadioButton("Large - x1.4");
    large.setActionCommand("3");
    sizeGroup.add(small); sizeGroup.add(medium); sizeGroup.add(large);
    small.setFont(small.getFont().deriveFont(13f));
    medium.setFont(medium.getFont().deriveFont(13f));
    large.setFont(large.getFont().deriveFont(13f));
    sizePanel.add(new JLabel("Size (select one):"));
    sizePanel.add(small); sizePanel.add(medium); sizePanel.add(large);
    small.addActionListener(e -> onSelectionChanged());
    medium.addActionListener(e -> onSelectionChanged());
    large.addActionListener(e -> onSelectionChanged());

        // restore size pref
        String lastSize = prefs.get("lastSize", "2");
        for (AbstractButton b : java.util.Collections.list(sizeGroup.getElements())) {
            if (b.getActionCommand().equals(lastSize)) b.setSelected(true);
        }

        leftPanel.add(baseList, BorderLayout.CENTER);
        leftPanel.add(sizePanel, BorderLayout.SOUTH);
    }

    private void buildMiddlePanel() {
    middlePanel.setLayout(new BorderLayout(8, 8));
    middlePanel.setBorder(BorderFactory.createTitledBorder("Condiments"));

    JLabel midInstr = new JLabel("Optional - pick any condiments to add flavor and cost");
    midInstr.setFont(midInstr.getFont().deriveFont(12f));
    midInstr.setBorder(new EmptyBorder(6,6,6,6));
    middlePanel.add(midInstr, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 2, 8, 8));
        for (Condiment c : condiments) {
            JCheckBox cb = new JCheckBox(c.name() + " - " + currency.format(c.price()));
            cb.setActionCommand(c.id());
            cb.addActionListener(e -> onSelectionChanged());
            cb.setFont(cb.getFont().deriveFont(13f));
            cb.setToolTipText(c.name() + " adds " + currency.format(c.price()) + " to the subtotal.");
            condimentChecks.add(cb);
            grid.add(cb);
            // restore checked
            if (prefs.getBoolean("condiment." + c.id(), false)) cb.setSelected(true);
        }

        middlePanel.add(grid, BorderLayout.CENTER);
    }

    private void buildRightPanel() {
        rightPanel.setLayout(new BorderLayout(8, 8));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Your Drink"));

    descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(Font.BOLD, 16f));
    // description area with helper text to guide the user
    JPanel descArea = new JPanel(new BorderLayout());
    descArea.add(descriptionLabel, BorderLayout.NORTH);
    JLabel descHelp = new JLabel("Price breakdown below updates live. Click 'Add to Cart' to save this item.");
    descHelp.setFont(descHelp.getFont().deriveFont(12f));
    descHelp.setBorder(new EmptyBorder(4,4,4,4));
    descArea.add(descHelp, BorderLayout.SOUTH);
    rightPanel.add(descArea, BorderLayout.NORTH);

    breakdownArea.setEditable(false);
    breakdownArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
    JScrollPane breakdownScroll = new JScrollPane(breakdownArea);
    breakdownScroll.setPreferredSize(new Dimension(600, 420));
    rightPanel.add(breakdownScroll, BorderLayout.CENTER);

    // bottom area: total label above action buttons to avoid overlap
    JPanel bottom = new JPanel();
    bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

    JPanel totalRow = new JPanel(new BorderLayout());
    totalLabel.setFont(totalLabel.getFont().deriveFont(Font.BOLD, 18f));
    totalRow.add(totalLabel, BorderLayout.WEST);
    bottom.add(totalRow);

    JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton clearBtn = new JButton("Reset");
    JButton addBtn = new JButton("Add to Cart");
    JButton checkoutBtn = new JButton("Checkout");

    clearBtn.addActionListener(e -> resetBuilder(false));
    addBtn.addActionListener(e -> addToCart());
    checkoutBtn.addActionListener(e -> checkout());

    actions.add(clearBtn); actions.add(addBtn); actions.add(checkoutBtn);
    bottom.add(actions);

    rightPanel.add(bottom, BorderLayout.SOUTH);

        // cart list on the right as a separate small panel
    JPanel cartPanel = new JPanel(new BorderLayout());
    cartPanel.setBorder(BorderFactory.createTitledBorder("Cart"));
    cartList = new JList<>(cartModel);
    cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);
    cartPanel.setPreferredSize(new Dimension(220, 220));

    JPanel cartActions = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton editBtn = new JButton("Edit");
    JButton removeBtn = new JButton("Remove");
    cartActions.add(editBtn); cartActions.add(removeBtn);
    cartPanel.add(cartActions, BorderLayout.SOUTH);

    editBtn.addActionListener(e -> {
        int idx = cartList.getSelectedIndex();
        if (idx < 0) return;
        // cartModel stores readable strings; parse description part before the ' - '
        String entry = cartModel.get(idx);
        String[] parts = entry.split(" - ", 2);
            if (parts.length > 0) {
            String desc = parts[0];
            // try to set the builder by matching base name in description
            for (MenuBase b : bases) {
                if (desc.startsWith(b.name())) {
                    for (AbstractButton rb : java.util.Collections.list(baseGroup.getElements())) {
                        rb.setSelected(rb.getActionCommand().equals(b.id()));
                    }
                    break;
                }
            }
            // set condiments by name
            for (JCheckBox cb : condimentChecks) cb.setSelected(false);
            for (Condiment c : condiments) if (desc.contains(c.name())) {
                for (JCheckBox cb : condimentChecks) if (cb.getActionCommand().equals(c.id())) cb.setSelected(true);
            }
            // sizes: look for small/medium/large
            if (desc.contains("small")) selectSizeByAction("1");
            else if (desc.contains("large")) selectSizeByAction("3");
            else selectSizeByAction("2");
        }
        updateSummary();
    });

    removeBtn.addActionListener(e -> {
        int idx = cartList.getSelectedIndex();
        if (idx < 0) return;
        cartModel.remove(idx);
        saveCart();
    });

    rightPanel.add(cartPanel, BorderLayout.EAST);

        // Help menu
        JMenuBar mb = new JMenuBar();
        JMenu help = new JMenu("Help");
        JMenuItem info = new JMenuItem("How add-ons wrap the drink");
        info.addActionListener(e -> showHelp());
        help.add(info);
        mb.add(help);
        setJMenuBar(mb);
    }

    private void showHelp() {
        String text = "Decorator concept:\n" +
                "Base beverages provide a core price. Each condiment 'wraps' the drink and adds its own price.\n" +
                "Total = (base price + sum(condiment prices)) × size multiplier (1.0/1.2/1.4).";
        JOptionPane.showMessageDialog(this, text, "How add-ons wrap the drink", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onSelectionChanged() {
        saveSession();
        updateSummary();
    }

    private String getSelectedBaseId() {
        for (AbstractButton b : java.util.Collections.list(baseGroup.getElements())) {
            if (b.isSelected()) return b.getActionCommand();
        }
        return null;
    }

    /**
     * Create a concrete base Coffee by id. This returns a Coffee instance
     * representing only the base (no toppings) so we can read basePrice().
     */
    private Coffee createBaseById(String id) {
        if (id == null) return new HouseBlendCoffee();
        return switch (id) {
            case "darkroast" -> new DarkRoast();
            case "decaf" -> new Decaf();
            case "houseblend" -> new HouseBlendCoffee();
            case "espresso" -> {
                // Espresso class may not exist; fall back to HouseBlend if missing
                try {
                    yield (Coffee) Class.forName("Concrete.Espresso").getDeclaredConstructor().newInstance();
                } catch (Exception ex) {
                    yield new HouseBlendCoffee();
                }
            }
            default -> new HouseBlendCoffee();
        };
    }

    /**
     * Build a decorated Coffee instance for the current builder selection.
     * Base is wrapped by condiment decorators; size is applied by keeping
     * the base/decorator size method (we rely on CoffeeDecorator.cost()).
     */
    private Coffee buildDecoratedCoffee(String baseId, int sizeCode, List<Condiment> selected) {
        Coffee base = createBaseById(baseId);
        // set size on base if possible by reflection (many base classes use size()),
        // but our decorators use the size() of the wrapped component. We assume
        // base implementations honor size() default; to apply size, we create
        // an anonymous wrapper that overrides size().
        Coffee sizedBase = new Coffee() {
            @Override public String getDescription() { return base.getDescription(); }
            @Override public double cost() { return base.cost(); }
            @Override public int size() { return sizeCode; }
            @Override public double extra() { return base.extra(); }
            @Override public double basePrice() { return base.basePrice(); }
        };

        Coffee current = sizedBase;
        for (Condiment c : selected) {
            switch (c.id()) {
                case "milk" -> current = new MilkDecorator(current);
                case "mocha" -> current = new MochaDecorator(current);
                case "soy" -> current = new SoyDecorator(current);
                case "whip" -> current = new WhipDecorator(current);
                case "caramel" -> current = new CaramelDecorator(current);
                case "vanilla" -> current = new VanillaDecorator(current);
                default -> {
                    // unknown condiment - ignore
                }
            }
        }
        return current;
    }

    /** Compute total by building decorated coffee and asking it for cost(). */
    private double computeTotalUsingDecorators(String baseId, int sizeCode, List<Condiment> selected) {
        Coffee decorated = buildDecoratedCoffee(baseId, sizeCode, selected);
        return decorated.cost();
    }

    private void updateSummary() {
        // find selected base
        String baseId = getSelectedBaseId();

        int sizeCode = 2; // default MEDIUM
        for (AbstractButton b : java.util.Collections.list(sizeGroup.getElements())) {
            if (b.isSelected()) sizeCode = Integer.parseInt(b.getActionCommand());
        }

    NumberFormat nf = currency;

        if (baseId == null) {
            descriptionLabel.setText("No base selected");
            breakdownArea.setText("Select a base to see pricing details.");
            totalLabel.setText("Total: " + nf.format(0));
            return;
        }

    MenuBase base = bases.stream().filter(b -> b.id().equals(baseId)).findFirst().orElse(bases.get(0));

        List<Condiment> selected = new ArrayList<>();
        for (JCheckBox cb : condimentChecks) {
            if (cb.isSelected()) {
                selected.add(condiments.stream().filter(c -> c.id().equals(cb.getActionCommand())).findFirst().get());
            }
        }

        // Compute total using Decorator classes to keep single source of truth
        double total = computeTotalUsingDecorators(baseId, sizeCode, selected);
        double basePrice = createBaseById(baseId).basePrice();
        double subtotal = basePrice + selected.stream().mapToDouble(Condiment::price).sum();
        double multiplier = switch (sizeCode) {
            case 1 -> 1.0;
            case 2 -> 1.2;
            case 3 -> 1.4;
            default -> 1.2;
        };

        // description
        StringBuilder desc = new StringBuilder();
        desc.append(base.name()).append(" (").append(switch (sizeCode) {case 1 -> "small"; case 2 -> "medium"; case 3 -> "large"; default -> "medium";}).append(")");
        if (!selected.isEmpty()) {
            desc.append(", ");
            desc.append(String.join(", ", selected.stream().map(Condiment::name).toList()));
        }

        descriptionLabel.setText(desc.toString());

        // breakdown
        StringBuilder out = new StringBuilder();
        out.append(String.format("Base: %s %s\n", base.name(), nf.format(basePrice)));
        if (!selected.isEmpty()) {
            out.append("Condiments:\n");
            for (Condiment c : selected) {
                out.append(String.format(" - %s: %s\n", c.name(), nf.format(c.price())));
            }
        }
    out.append("----------------------------------------\n");
    out.append(String.format("Subtotal (base + condiments): %s\n", nf.format(subtotal)));
    // use ASCII 'x' instead of the unicode multiplication sign to avoid encoding issues
    out.append(String.format("Multiplier: x%.2f\n", multiplier));
    out.append(String.format("Total: %s\n", nf.format(total)));

        breakdownArea.setText(out.toString());
        totalLabel.setText("Total: " + nf.format(total));
    }

    private void selectSizeByAction(String action) {
        for (AbstractButton b : java.util.Collections.list(sizeGroup.getElements())) {
            b.setSelected(b.getActionCommand().equals(action));
        }
    }

    

    private void addToCart() {
        // validate base
        String selectedBase = getSelectedBaseId();
        if (selectedBase == null) {
            JOptionPane.showMessageDialog(this, "Please select a base beverage before adding to cart.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // create structured cart item and persist
        List<String> sel = new ArrayList<>();
        for (JCheckBox cb : condimentChecks) if (cb.isSelected()) sel.add(cb.getActionCommand());
        String summary = descriptionLabel.getText() + " - " + totalLabel.getText();
        cartModel.addElement(summary);
        saveCart();
        JOptionPane.showMessageDialog(this, "Added to cart.", "Cart", JOptionPane.INFORMATION_MESSAGE);
    }

    private void checkout() {
        if (cartModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty. Add at least one drink to checkout.", "Checkout", JOptionPane.WARNING_MESSAGE);
            return;
        }
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order Summary:\n\n");
        for (int i = 0; i < cartModel.size(); i++) receipt.append((i+1)+") "+cartModel.get(i)+"\n");
        receipt.append("\nThank you for your order!");
        JOptionPane.showMessageDialog(this, receipt.toString(), "Checkout", JOptionPane.INFORMATION_MESSAGE);
        cartModel.clear();
        saveCart();
    }

    private void resetBuilder(boolean fullReset) {
        baseGroup.clearSelection();
        for (AbstractButton b : java.util.Collections.list(sizeGroup.getElements())) {
            if (b.getActionCommand().equals("2")) b.setSelected(true);
        }
        for (JCheckBox cb : condimentChecks) cb.setSelected(false);
        updateSummary();
        if (fullReset) cartModel.clear();
        if (fullReset) saveCart();
    }

    private void saveSession() {
        // persist last base and size and condiments
        for (AbstractButton b : java.util.Collections.list(baseGroup.getElements())) {
            if (b.isSelected()) prefs.put("lastBase", b.getActionCommand());
        }
        for (AbstractButton b : java.util.Collections.list(sizeGroup.getElements())) {
            if (b.isSelected()) prefs.put("lastSize", b.getActionCommand());
        }
        for (int i = 0; i < condiments.size(); i++) {
            prefs.putBoolean("condiment." + condiments.get(i).id(), condimentChecks.get(i).isSelected());
        }
        // also persist cart
        saveCart();
    }

    private void restoreSession() {
        // restore last selections
        // cart items are restored before summary to reflect totals
        restoreCart();
        updateSummary();
    }

    private void saveCart() {
        try {
            prefs.putInt("cart.size", cartModel.size());
            for (int i = 0; i < cartModel.size(); i++) prefs.put("cart.item." + i, cartModel.get(i));
        } catch (Exception ex) {
            // ignore
        }
    }

    private void restoreCart() {
        cartModel.clear();
        int n = prefs.getInt("cart.size", 0);
        for (int i = 0; i < n; i++) {
            String v = prefs.get("cart.item." + i, null);
            if (v != null) cartModel.addElement(v);
        }
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CoffeeShopGUI::new);
    }
}
