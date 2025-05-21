package util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import static java.math.BigDecimal.ZERO;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static void validateInteger(Integer quantity, String message) {
        if (quantity == null) {
            throw new IllegalArgumentException(message);
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateInteger(Integer quantity) {
        validateInteger(quantity, "Количество товара не может быть отрицательным");
    }

    public static void validatePositiveAmount(BigDecimal amount, String message) {
        if (amount == null) return;
        if (amount.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validatePositiveAmount(BigDecimal amount) {
        validatePositiveAmount(amount, "Значение должно быть положительным числом");
    }

    public static void validateId(Long id, String message) {
        if (id == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateId(Long id) {
        validateId(id, "ID не может быть null");
    }

    public static void validateDate(LocalDate date, String message) {
        if (date == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateDateRange(Timestamp startDate, Timestamp endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Даты начала и окончания периода должны быть указаны");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Дата начала не может быть позже даты окончания");
        }
    }

    public static void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Даты начала и окончания периода должны быть указаны");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Дата начала не может быть позже даты окончания");
        }
    }

    public static void validateString(String str, String message) {
        if (str.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
