package main.java;

import javax.swing.*;
import java.awt.*;
import main.java.Base.Coffee;
import main.java.Concrete.*;
import main.java.Decorator.*;

/**
 * Coffee Shop GUI demonstrating Decorator Pattern.
 */
public class CoffeeShopGUI extends JFrame 
{
    private JComboBox<String> baseBox, sizeBox;
    private JCheckBox[] condiments;
    private JTextArea summaryArea;
    private JButton clearBtn, checkoutBtn;
    private Coffee currentCoffee;

    public CoffeeShopGUI() {
        setTitle("Coffee Shop Decorator GUI");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Left panel: Base & Size
        JPanel leftPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Base & Size"));
        baseBox = new JComboBox<>(new String[]{"Select Base", "Espresso", "House Blend", "Dark Roast", "Decaf"});
        baseBox.addActionListener(e -> updateOrder());
        sizeBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        sizeBox.setSelectedIndex(0); // Small by default
        sizeBox.addActionListener(e -> updateOrder());
        leftPanel.add(new JLabel("Base:"));
        leftPanel.add(baseBox);
        leftPanel.add(new JLabel("Size:"));
        leftPanel.add(sizeBox);

        // Middle panel: Condiments
        JPanel middlePanel = new JPanel(new GridLayout(6, 1, 5, 5));
        middlePanel.setBorder(BorderFactory.createTitledBorder("Condiments"));
        String[] condNames = {"Milk", "Mocha", "Soy", "Whip", "Caramel", "Vanilla"};
        condiments = new JCheckBox[condNames.length];
        for (int i = 0; i < condNames.length; i++) 
        {
            condiments[i] = new JCheckBox(condNames[i]);
            condiments[i].addActionListener(e -> updateOrder());
            middlePanel.add(condiments[i]);
        }

        // Right panel: Summary
        JPanel rightPanel = new JPanel(new BorderLayout(5,5));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Your Coffee"));
        summaryArea = new JTextArea(10, 25);
        summaryArea.setEditable(false);
        rightPanel.add(new JScrollPane(summaryArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> clearOrder());
        checkoutBtn = new JButton("Checkout");
        checkoutBtn.addActionListener(e -> checkout());
        buttonPanel.add(clearBtn);
        buttonPanel.add(checkoutBtn);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(middlePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Initialize default coffee order: HouseBlend, Small
        currentCoffee = new HouseBlend();
        baseBox.setSelectedIndex(2); // "House Blend"
        updateOrder();
    }

    private void updateOrder() 
    {
        String base = (String) baseBox.getSelectedItem();
        String size = (String) sizeBox.getSelectedItem();
        if (base == null || base.equals("Select Base")) 
        {
            summaryArea.setText("Please select a base coffee.");
            return;
        }

        switch(base) 
        {
            case "Espresso": currentCoffee = new Espresso(); break;
            case "House Blend": currentCoffee = new HouseBlend(); break;
            case "Dark Roast": currentCoffee = new DarkRoast(); break;
            case "Decaf": currentCoffee = new Decaf(); break;
        }

        for (JCheckBox box : condiments) 
        {
            if (box.isSelected()) 
            {
                switch (box.getText()) 
                {
                    case "Milk": currentCoffee = new Milk(currentCoffee); break;
                    case "Mocha": currentCoffee = new Mocha(currentCoffee); break;
                    case "Soy": currentCoffee = new Soy(currentCoffee); break;
                    case "Whip": currentCoffee = new Whip(currentCoffee); break;
                    case "Caramel": currentCoffee = new Caramel(currentCoffee); break;
                    case "Vanilla": currentCoffee = new Vanilla(currentCoffee); break;
                }
            }
        }

        summaryArea.setText(currentCoffee.getDescription() + " (" + size + ")\n");
        summaryArea.append(String.format("\nTotal: $%.2f", currentCoffee.cost(size)));
    }

    private void clearOrder() 
    {
        baseBox.setSelectedIndex(0);
        sizeBox.setSelectedIndex(0);
        for (JCheckBox box : condiments) box.setSelected(false);
        summaryArea.setText("");
    }

    private void checkout() 
    {
        if (summaryArea.getText().isEmpty() || baseBox.getSelectedIndex() == 0) 
        {
            JOptionPane.showMessageDialog(this, "Please select a base coffee before checkout.");
            return;
        }
        JOptionPane.showMessageDialog(this, "Order placed successfully!");
        clearOrder();
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> new CoffeeShopGUI().setVisible(true));
    }
}
