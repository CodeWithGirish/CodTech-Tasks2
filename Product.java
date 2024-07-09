import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private BigDecimal cost;
    private int quantity;

    public Product(int id, String name, BigDecimal cost, int quantity) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
