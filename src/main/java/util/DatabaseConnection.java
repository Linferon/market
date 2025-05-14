package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static util.LoggerUtil.error;

public class DatabaseConnection {
    private DatabaseConnection() {}
    private static final String URL = "jdbc:h2:./db/market;AUTO_SERVER=TRUE";
    private static final String USER = "Dim";
    private static final String PASSWORD = "comse24";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null ||  connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            error("Ошибка при закрытии базы данных: " + e.getMessage());
        }
    }
}
