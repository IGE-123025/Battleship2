package battleship;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária para gerenciar conexões com o banco de dados SQLite
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:battleship.db";

    static {
        try {
            // Carregar explicitamente o driver SQLite
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver SQLite não encontrado: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}