package battleship;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia o placar de jogos passados do Battleship.
 * Armazena informações como data, duração, tiros disparados e navios afundados.
 */
public class Scoreboard {

    private static final String DB_URL = "jdbc:sqlite:battleship.db";

    public Scoreboard() {
        // Apenas verifica/cria a tabela, não mantém conexão aberta
        initDatabase();
    }

    /**
     * Inicializa o banco de dados e cria a tabela se não existir
     */
    private void initDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS games (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                date TEXT NOT NULL,
                duration_seconds INTEGER,
                total_shots INTEGER,
                hits INTEGER,
                misses INTEGER,
                ships_sunk INTEGER,
                winner TEXT,
                game_mode TEXT
            );
        """;

        // Usar try-with-resources para garantir que a conexão é fechada
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar banco de dados: " + e.getMessage());
        }
    }

    /**
     * Salva um novo jogo no scoreboard
     */
    public void saveGame(int durationSeconds, int totalShots, int hits,
                         int misses, int shipsSunk, String winner, String gameMode) {
        String sql = """
            INSERT INTO games(date, duration_seconds, total_shots, hits, misses, ships_sunk, winner, game_mode)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?)
        """;

        // Criar nova conexão para cada operação
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            pstmt.setInt(2, durationSeconds);
            pstmt.setInt(3, totalShots);
            pstmt.setInt(4, hits);
            pstmt.setInt(5, misses);
            pstmt.setInt(6, shipsSunk);
            pstmt.setString(7, winner);
            pstmt.setString(8, gameMode);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar jogo: " + e.getMessage());
        }
    }

    /**
     * Retorna todos os jogos salvos
     */
    public List<GameRecord> getAllGames() {
        List<GameRecord> games = new ArrayList<>();
        String sql = "SELECT * FROM games ORDER BY date DESC";

        // Criar nova conexão para cada operação
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                games.add(new GameRecord(
                        rs.getInt("id"),
                        rs.getString("date"),
                        rs.getInt("duration_seconds"),
                        rs.getInt("total_shots"),
                        rs.getInt("hits"),
                        rs.getInt("misses"),
                        rs.getInt("ships_sunk"),
                        rs.getString("winner"),
                        rs.getString("game_mode")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar jogos: " + e.getMessage());
        }
        return games;
    }

    /**
     * Mostra o scoreboard formatado no console
     */
    public void displayScoreboard() {
        List<GameRecord> games = getAllGames();

        if (games.isEmpty()) {
            System.out.println("\n📊 Nenhum jogo registrado ainda!\n");
            return;
        }

        System.out.println("\n==================== SCOREBOARD ====================");
        System.out.printf("%-4s %-10s %-8s %-6s %-4s %-6s %-10s %-8s%n",
                "ID", "Data", "Duração", "Tiros", "Acertos", "Erros", "Navios", "Vencedor");
        System.out.println("------------------------------------------------------");

        for (GameRecord game : games) {
            System.out.printf("%-4d %-10s %-8d %-6d %-4d %-6d %-10d %-8s%n",
                    game.id(),
                    game.date().substring(0, 10),
                    game.durationSeconds(),
                    game.totalShots(),
                    game.hits(),
                    game.misses(),
                    game.shipsSunk(),
                    game.winner()
            );
        }
        System.out.println("====================================================\n");
    }

    /**
     * Record para representar um jogo salvo
     */
    public record GameRecord(int id, String date, int durationSeconds,
                             int totalShots, int hits, int misses,
                             int shipsSunk, String winner, String gameMode) {}
}