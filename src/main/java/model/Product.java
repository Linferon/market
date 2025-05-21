package model;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;

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

