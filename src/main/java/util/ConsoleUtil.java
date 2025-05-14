package util;

import java.time.LocalDate;
import java.util.List;

public class ConsoleUtil {
    private ConsoleUtil() {
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void print(Object obj) {
        System.out.print(obj);
    }

    public static void printDivider() { System.out.println("----------------------------"); }

    public static void printHeader(String title) {
        printDivider();
        println(title);
        printDivider();
    }

    public static void printError(String s) { System.out.println(s); }

    public static void showEntityDetails(Object entity, String title) {
        printHeader(title);
        println(entity.toString());
    }

    @FunctionalInterface
    public interface DateRangeSupplier<T> {
        List<T> getDateRange(LocalDate startDate, LocalDate endDate);
    }

    public static <T extends FormattableEntity> void showEntitiesTable(List<T> entities, String title) {
        printHeader(title);
        println(TableFormatter.formatTable(entities));
    }
}
