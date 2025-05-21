package dao.impl;

import dao.Dao;
import dao.mapper.PurchaseMapper;
import model.Purchase;

import java.sql.ResultSet;
import java.util.function.Function;

public class PurchaseDao extends Dao<Purchase> {
    @Override
    protected String getTableName() {
        return "PURCHASES";
    }

    @Override
    protected Function<ResultSet, Purchase> getMapper() {
        return PurchaseMapper::mapRow;
    }

    public Long save(Purchase purchase) {
        if (purchase.getId() == null) {
            String sql = """
                    INSERT INTO PURCHASES 
                    (PRODUCT_ID, QUANTITY, TOTAL_COST, PURCHASE_DATE) 
                    VALUES (?, ?, ?, ?)""";
            Long id = insert(sql,
                    purchase.getProductId(),
                    purchase.getQuantity(),
                    purchase.getTotalCost(),
                    purchase.getPurchaseDate());
            if (id != null) {
                purchase.setId(id);
            }
            return id;
        } else {
            boolean updated = update(purchase);
            return updated ? purchase.getId() : null;
        }
    }

    public boolean update(Purchase purchase) {
        String sql = """
                UPDATE PURCHASES 
                SET PRODUCT_ID = ?, QUANTITY = ?,  TOTAL_COST = ?, 
                PURCHASE_DATE = ? 
                WHERE ID = ?""";
        return update(sql,
                purchase.getProductId(),
                purchase.getQuantity(),
                purchase.getTotalCost(),
                purchase.getPurchaseDate(),
                purchase.getId());
    }
}
