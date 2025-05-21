package dao.mapper;

import exception.DatabaseMapException;
import model.Product;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static util.LoggerUtil.error;

public class ProductMapper {
    private ProductMapper() {}

    public static Product mapRow(ResultSet rs) {
        try {
            Long id = rs.getLong("ID");
            String name = rs.getString("NAME");
            BigDecimal buyPrice = rs.getBigDecimal("BUY_PRICE");
            BigDecimal sellPrice = rs.getBigDecimal("SELL_PRICE");

            return new Product(id, name, buyPrice, sellPrice);
        } catch (SQLException e) {
            error("Error mapping product from ResultSet", e);
            throw new DatabaseMapException("Error mapping product");
        }
    }
}
