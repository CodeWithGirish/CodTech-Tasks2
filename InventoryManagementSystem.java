import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class InventoryManagementSystem extends JFrame {
    private final JTextField productIdField;
    private final DefaultTableModel tableModel;
    private final ProductDAO productDAO;

    public InventoryManagementSystem() {
        productDAO = new ProductDAO();

        if (productDAO.isConnected()) {
            JOptionPane.showMessageDialog(this, "Connection established successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to establish connection.");
            System.exit(1); // Exit if the connection fails
        }

        setTitle("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Top panel with product ID input and View Product button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JLabel("Enter Product Id :"));
        productIdField = new JTextField(10);
        topPanel.add(productIdField);
        JButton viewProductButton = new JButton("View Product");
        topPanel.add(viewProductButton);
        add(topPanel, BorderLayout.NORTH);

        // Button panel with other actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton listAllProductsButton = new JButton("List All Products");
        buttonPanel.add(listAllProductsButton);

        JButton addProductButton = new JButton("Add Product");
        buttonPanel.add(addProductButton);

        JButton updateQuantityButton = new JButton("Update Quantity");
        buttonPanel.add(updateQuantityButton);

        JButton updateCostButton = new JButton("Update Cost");
        buttonPanel.add(updateCostButton);

        JButton deleteProductButton = new JButton("Delete Product");
        buttonPanel.add(deleteProductButton);

        JButton exitButton = new JButton("Exit");
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Table to display products
        String[] columnNames = {"ID", "Name", "Cost", "Quantity"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        add(tableScrollPane, BorderLayout.SOUTH);

        // Action listeners for buttons
        viewProductButton.addActionListener(e -> viewProduct());

        listAllProductsButton.addActionListener(e -> listAllProducts());

        addProductButton.addActionListener(e -> addProduct());

        updateQuantityButton.addActionListener(e -> updateQuantity());

        updateCostButton.addActionListener(e -> updateCost());

        deleteProductButton.addActionListener(e -> deleteProduct());

        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void viewProduct() {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            Product product = productDAO.getProductById(productId);
            if (product != null) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{product.getId(), product.getName(), product.getCost(), product.getQuantity()});
            } else {
                JOptionPane.showMessageDialog(this, "Product not found!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void listAllProducts() {
        try {
            List<Product> productList = productDAO.listAllProducts();
            tableModel.setRowCount(0);
            for (Product product : productList) {
                tableModel.addRow(new Object[]{product.getId(), product.getName(), product.getCost(), product.getQuantity()});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addProduct() {
        //JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField costField = new JTextField(10);
        JTextField quantityField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        //panel.add(new JLabel("Id:"));
        //panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Cost:"));
        panel.add(costField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                BigDecimal cost = new BigDecimal(costField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                Product product = new Product(0, name, cost, quantity);
                productDAO.addProduct(product);
                listAllProducts();  // Refresh the table

                JOptionPane.showMessageDialog(this, "Product added successfully!");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding product: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void updateQuantity() {
        JTextField idField = new JTextField(10);
        JTextField quantityField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.add(new JLabel("Product ID:"));
        panel.add(idField);
        panel.add(new JLabel("New Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Quantity", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                Product product = productDAO.getProductById(id);
                if (product != null) {
                    product.setQuantity(quantity);
                    productDAO.updateProduct(product);
                    listAllProducts();  // Refresh the table

                    JOptionPane.showMessageDialog(this, "Quantity updated successfully!");

                } else {
                    JOptionPane.showMessageDialog(this, "Product not found!");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error updating quantity: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void updateCost() {

        JTextField idField = new JTextField(10);
        JTextField costField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.add(new JLabel("Product ID:"));
        panel.add(idField);
        panel.add(new JLabel("New Cost:"));
        panel.add(costField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Cost", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                BigDecimal cost = new BigDecimal(costField.getText());

                Product product = productDAO.getProductById(id);
                if (product != null) {
                    product.setCost(cost);
                    productDAO.updateProduct(product);
                    listAllProducts();  // Refresh the table

                    JOptionPane.showMessageDialog(this, "Cost updated successfully!");

                } else {
                    JOptionPane.showMessageDialog(this, "Product not found!");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error updating cost: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void deleteProduct() {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            productDAO.deleteProduct(productId);
            JOptionPane.showMessageDialog(this, "Product deleted successfully!");
            listAllProducts();  // Refresh the table
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new InventoryManagementSystem();
    }
}
