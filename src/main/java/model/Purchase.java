package model;

import util.TableFormatter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class Purchase implements FormattableEntity {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalCost;
    private Timestamp purchaseDate;

    private static final int ID_WIDTH = 5;
    private static final int PRODUCT_WIDTH = 30;
    private static final int QUANTITY_WIDTH = 10;
    private static final int COST_WIDTH = 15;
    private static final int DATE_WIDTH = 20;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Override
    public String toString() {
        return "Закупка: " +
                "\nid: " + id +
                "\nпродукт: " + productId +
                "\nколичество: " + quantity +
                "\nдата: " + (purchaseDate != null ? purchaseDate.toLocalDateTime().format(DATE_FORMATTER) : "не указана") +
                "\nстоимость: " + totalCost ;
    }

    @Override
    public String getTableHeader() {
        return TableFormatter.formatCell("ID", ID_WIDTH) +
                TableFormatter.formatCell("Продукт", PRODUCT_WIDTH) +
                TableFormatter.formatCell("Кол-во", QUANTITY_WIDTH) +
                TableFormatter.formatCell("Стоимость", COST_WIDTH) +
                TableFormatter.formatCell("Дата", DATE_WIDTH);
    }

    @Override
    public String toTableRow() {
        return TableFormatter.formatCell(id, ID_WIDTH) +
                TableFormatter.formatCell(productId, PRODUCT_WIDTH) +
                TableFormatter.formatCell(quantity, QUANTITY_WIDTH) +
                TableFormatter.formatCell(totalCost, COST_WIDTH) +
                TableFormatter.formatCell(purchaseDate, DATE_WIDTH);
    }

    @Override
    public String getTableDivider() {
        return TableFormatter.createDivider(ID_WIDTH, PRODUCT_WIDTH, QUANTITY_WIDTH, COST_WIDTH, DATE_WIDTH);
    }

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
