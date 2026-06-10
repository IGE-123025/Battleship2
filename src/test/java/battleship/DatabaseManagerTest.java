package battleship;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {

    @Test
    void shouldReturnValidConnection() throws Exception {
        Connection conn = DatabaseManager.getConnection();

        assertNotNull(conn);
        assertFalse(conn.isClosed());

        conn.close();
    }

    @Test
    void shouldCreateMultipleConnections() throws Exception {
        Connection conn1 = DatabaseManager.getConnection();
        Connection conn2 = DatabaseManager.getConnection();

        assertNotNull(conn1);
        assertNotNull(conn2);
        assertNotSame(conn1, conn2);

        conn1.close();
        conn2.close();
    }

    @Test
    void shouldNotThrowWhenGettingConnection() {
        assertDoesNotThrow(() -> {
            Connection conn = DatabaseManager.getConnection();
            conn.close();
        });
    }

    @Test
    void shouldLoadSQLiteDriver() {
        assertDoesNotThrow(() -> Class.forName("org.sqlite.JDBC"));
    }
}