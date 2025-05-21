package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

public class Purchase {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalCost;
    private Timestamp purchaseDate;

    public Purchase(Long id, Long productId, Integer quantity, BigDecimal totalCost, Timestamp purchaseDate) {
        this(id, productId, quantity, totalCost);
        this.purchaseDate = purchaseDate;
    }

    public Purchase(Long id, Long productId, Integer quantity, BigDecimal totalCost) {
        this(productId,quantity,totalCost);
        this.id = id;
        this.purchaseDate = Timestamp.from(Instant.now());
    }

    public Purchase(Long productId, Integer quantity, BigDecimal totalCost) {
        this.productId = productId;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
