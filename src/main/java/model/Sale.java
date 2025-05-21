package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

public class Sale {
    private Long  id;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Timestamp saleDate;

    public Sale(Long id, Long productId, Integer quantity, BigDecimal totalPrice) {
        this(productId, quantity, totalPrice);
        this.id = id;
        this.saleDate = Timestamp.from(Instant.now());
    }

    public Sale(Long productId, Integer quantity, BigDecimal totalPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.saleDate = Timestamp.from(Instant.now());
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Timestamp saleDate) {
        this.saleDate = saleDate;
    }
}
