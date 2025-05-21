package model;

import util.TableFormatter;

public class Stock implements FormattableEntity {
    private Long productId;
    private Integer quantity;

    private static final int PRODUCT_ID_WIDTH = 5;
    private static final int QUANTITY_WIDTH = 10;


    @Override
    public String toString() {
        return "Остаток на складе" +
                "\nпродукт: " + productId +
                "\nколичество: " + quantity;
    }

    @Override
    public String getTableHeader() {
        return TableFormatter.formatCell(" ID продукта", PRODUCT_ID_WIDTH) +
                TableFormatter.formatCell("Количество", QUANTITY_WIDTH);
    }

    @Override
    public String toTableRow() {
        return TableFormatter.formatCell(productId, PRODUCT_ID_WIDTH) +
                TableFormatter.formatCell(quantity, QUANTITY_WIDTH);
    }

    @Override
    public String getTableDivider() {
        return TableFormatter.createDivider(PRODUCT_ID_WIDTH, QUANTITY_WIDTH);
    }

    public Stock(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
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
}
