import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Stock {
    int itemno;
    String description;
    int stocklvl, minlvl;

    void read(int itemno, String description, int stocklvl, int minlvl) {
        this.itemno = itemno;
        this.description = description;
        this.stocklvl = stocklvl;
        this.minlvl = minlvl;
    }

    void receipt(int qty) {
        stocklvl += qty;
    }

    void issue(int qty) {
        stocklvl -= qty;
    }

    String print() {
        return "Item number: " + itemno + "\nItem description: " + description + 
               "\nStock level: " + stocklvl + "\nMinimum level: " + minlvl + "\n";
    }
}

public class StockCountGUI extends JFrame {
    private ArrayList<Stock> items = new ArrayList<>();
    private JTextArea textArea;
    private JTextField itemNoField, descriptionField, stockLevelField, minLevelField, qtyField;

    public StockCountGUI() {
        setTitle("Stock Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton createButton = new JButton("Create New Stock Item");
        JButton printButton = new JButton("Print Stock Details");
        JButton receiveButton = new JButton("Receive Stock");
        JButton issueButton = new JButton("Issue Stock");
        JButton exitButton = new JButton("Exit");
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        
        itemNoField = new JTextField(10);
        descriptionField = new JTextField(10);
        stockLevelField = new JTextField(10);
        minLevelField = new JTextField(10);
        qtyField = new JTextField(10);

        add(new JLabel("Item No:"));
        add(itemNoField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel("Stock Level:"));
        add(stockLevelField);
        add(new JLabel("Minimum Level:"));
        add(minLevelField);
        add(createButton);
        add(printButton);
        add(new JLabel("Quantity:"));
        add(qtyField);
        add(receiveButton);
        add(issueButton);
        add(exitButton);
        add(new JScrollPane(textArea));

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int itemno = Integer.parseInt(itemNoField.getText());
                String description = descriptionField.getText();
                int stocklvl = Integer.parseInt(stockLevelField.getText());
                int minlvl = Integer.parseInt(minLevelField.getText());
                
                Stock newItem = new Stock();
                newItem.read(itemno, description, stocklvl, minlvl);
                items.add(newItem);
                
                textArea.append("Item created: " + newItem.print());
                clearFields();
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                for (Stock item : items) {
                    textArea.append(item.print() + "\n");
                }
            }
        });

        receiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int qty = Integer.parseInt(qtyField.getText());
                int itemno = Integer.parseInt(itemNoField.getText());
                boolean found = false;

                for (Stock item : items) {
                    if (item.itemno == itemno) {
                        item.receipt(qty);
                        textArea.append("Received " + qty + " for item " + item.itemno + "\n");
                        found = true;
                        break; // Exit the loop once the item is found
                    }
                }

                if (!found) {
                    textArea.append("Item with item number " + itemno + " not found.\n");
                }

                clearFields();
            }
        });

        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int qty = Integer.parseInt(qtyField.getText());
                int itemno = Integer.parseInt(itemNoField.getText());
                boolean found = false;

                for (Stock item : items) {
                    if (item.itemno == itemno) {
                        item.issue(qty);
                        textArea.append("Issued " + qty + " for item " + item.itemno + "\n");
                        found = true;
                        break; // Exit the loop once the item is found
                    }
                }

                if (!found) {
                    textArea.append("Item with item number " + itemno + " not found.\n");
                }

                clearFields();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void clearFields() {
        itemNoField.setText("");
        descriptionField.setText("");
        stockLevelField.setText("");
        minLevelField.setText("");
        qtyField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StockCountGUI gui = new StockCountGUI();
                gui.setVisible(true);
            }
        });
    }
}