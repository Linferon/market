package model;

import util.TableFormatter;

import java.math.BigDecimal;

public class Product implements FormattableEntity{
    private Long id;
    private String name;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;

    private static final int ID_WIDTH = 5;
    private static final int NAME_WIDTH = 30;
    private static final int PRICE_WIDTH = 15;

    @Override
    public String toString() {
        return "Продукт" +
                "\nid:" + id +
                "\nназвание: " + name +
                "\nцена закупки: " + buyPrice +
                "\nцена продажи: " + sellPrice;
    }

    @Override
    public String getTableHeader() {
        return TableFormatter.formatCell("ID", ID_WIDTH) +
                TableFormatter.formatCell("Название", NAME_WIDTH) +
                TableFormatter.formatCell("Цена закупки", PRICE_WIDTH) +
                TableFormatter.formatCell("Цена продажи", PRICE_WIDTH);
    }

    @Override
    public String toTableRow() {
        return TableFormatter.formatCell(id, ID_WIDTH) +
                TableFormatter.formatCell(name, NAME_WIDTH) +
                TableFormatter.formatCell(buyPrice, PRICE_WIDTH) +
                TableFormatter.formatCell(sellPrice, PRICE_WIDTH);
    }

    @Override
    public String getTableDivider() {
        return TableFormatter.createDivider(ID_WIDTH, NAME_WIDTH, PRICE_WIDTH, PRICE_WIDTH);
    }


    public Product(String name, BigDecimal buyPrice, BigDecimal sellPrice) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public Product(Long id, String name, BigDecimal buyPrice, BigDecimal sellPrice) {
        this(name, buyPrice, sellPrice);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }
}

