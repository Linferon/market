package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

public class Purchase {
    private Long id;
    private Product product;
    private Integer quantity;
    private BigDecimal totalCost;
    private Timestamp purchaseDate;

    public Purchase(Long id, Product product, Integer quantity, BigDecimal totalCost) {
        this(product,quantity,totalCost);
        this.id = id;
        this.purchaseDate = Timestamp.from(Instant.now());
    }

    public Purchase(Product product, Integer quantity, BigDecimal totalCost) {
        this.product = product;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.purchaseDate = Timestamp.from(Instant.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
