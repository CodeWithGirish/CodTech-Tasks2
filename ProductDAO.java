import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;

    public ProductDAO() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryDB", "patil", "girishpatil123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, cost, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product.getId());
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getCost());
            statement.setInt(3, product.getQuantity());
            statement.executeUpdate();
        }
    }

    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    BigDecimal cost = resultSet.getBigDecimal("cost");
                    int quantity = resultSet.getInt("quantity");
                    return new Product(id, name, cost, quantity);
                }
            }
        }
        return null;
    }

    public List<Product> listAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal cost = resultSet.getBigDecimal("cost");
                int quantity = resultSet.getInt("quantity");
                products.add(new Product(id, name, cost, quantity));
            }
        }
        return products;
    }

    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, cost = ?, quantity = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getCost());
            statement.setInt(3, product.getQuantity());
            statement.setInt(4, product.getId());
            statement.executeUpdate();
        }
    }

    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
