package model;

import util.TableFormatter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

public class Sale implements FormattableEntity {
    private Long  id;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Timestamp saleDate;


    private static final int ID_WIDTH = 5;
    private static final int PRODUCT_WIDTH = 30;
    private static final int QUANTITY_WIDTH = 10;
    private static final int AMOUNT_WIDTH = 15;

    @Override
    public String toString() {
        return "Продажа" +
                "\nid: " + id +
                "\nпродукт: " + productId +
                "\nколичество:" + quantity +
                "\nсумма: " + totalPrice;
    }

    @Override
    public String getTableHeader() {
        return TableFormatter.formatCell("ID", ID_WIDTH) +
                TableFormatter.formatCell("Продукт", PRODUCT_WIDTH) +
                TableFormatter.formatCell("Кол-во", QUANTITY_WIDTH) +
                TableFormatter.formatCell("Сумма", AMOUNT_WIDTH);
    }

    @Override
    public String toTableRow() {
        return TableFormatter.formatCell(id, ID_WIDTH) +
                TableFormatter.formatCell(productId, PRODUCT_WIDTH) +
                TableFormatter.formatCell(quantity, QUANTITY_WIDTH) +
                TableFormatter.formatCell(totalPrice, AMOUNT_WIDTH);
    }

    @Override
    public String getTableDivider() {
        return TableFormatter.createDivider(ID_WIDTH, PRODUCT_WIDTH, QUANTITY_WIDTH, AMOUNT_WIDTH);
    }

    public Sale(Long id, Long productId, Integer quantity, BigDecimal totalPrice, Timestamp saleDate) {
        this(id, productId, quantity, totalPrice);
        this.saleDate = saleDate;
    }

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
