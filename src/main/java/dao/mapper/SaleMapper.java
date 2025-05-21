package dao.mapper;

import exception.DatabaseMapException;
import liquibase.exception.DatabaseException;
import model.Sale;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static util.LoggerUtil.error;

public class SaleMapper {
    private SaleMapper() {}

    public static Sale mapRow(ResultSet rs) {
        try {
            Long id = rs.getLong("ID");
            Long productId = rs.getLong("PRODUCT_ID");
            Integer quantity = rs.getInt("QUANTITY");
            BigDecimal totalPrice = rs.getBigDecimal("TOTAL_PRICE");
            Timestamp saleDate = rs.getTimestamp("SALE_DATE");

            return new Sale(id, productId, quantity, totalPrice, saleDate);
        } catch (SQLException e) {
            error("Error mapping row to Sale", e);
            throw new DatabaseMapException("Error mapping row to Sale");
        }
    }
}
