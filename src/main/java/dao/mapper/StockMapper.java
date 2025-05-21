package dao.mapper;

import exception.DatabaseMapException;
import model.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;

import static util.LoggerUtil.error;

public class StockMapper {
    private StockMapper() {}

    public static Stock mapRow(ResultSet rs) {
        try {
            Long productId = rs.getLong("PRODUCT_ID");
            Integer quantity = rs.getInt("QUANTITY");

            return new Stock(productId, quantity);
        } catch (SQLException e) {
            error("Error mapping stock from ResultSet", e);
            throw new DatabaseMapException("Error mapping stock");
        }
    }
}
