package dao.mapper;

import exception.DatabaseMapException;
import model.Purchase;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static util.LoggerUtil.error;

public class PurchaseMapper {
    private PurchaseMapper() {}

    public static Purchase mapRow(ResultSet rs) {
        try {
            Long id = rs.getLong("ID");
            Long productId = rs.getLong("PRODUCT_ID");
            Integer quantity = rs.getInt("QUANTITY");
            BigDecimal totalCost = rs.getBigDecimal("TOTAL_COST");
            Timestamp purchaseDate = rs.getTimestamp("PURCHASE_DATE");

            return new Purchase(id, productId, quantity, totalCost, purchaseDate);
        } catch (SQLException e) {
            error("Error mapping purchase from ResultSet", e);
            throw new DatabaseMapException("Error mapping purchase");
        }
    }
}
