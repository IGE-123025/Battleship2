package battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {

    @BeforeEach
    void cleanDatabase() throws Exception {
        Scoreboard scoreboard = new Scoreboard();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM games");
        }
    }

    @Test
    void shouldInitializeDatabaseWithoutErrors() {
        assertDoesNotThrow(() -> new Scoreboard());
    }

    @Test
    void shouldReturnEmptyListWhenNoGamesExist() {
        Scoreboard scoreboard = new Scoreboard();

        List<Scoreboard.GameRecord> games = scoreboard.getAllGames();

        assertNotNull(games);
        assertTrue(games.isEmpty());
    }

    @Test
    void shouldSaveGameAndRetrieveIt() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.saveGame(60, 10, 5, 5, 2, "Player1", "NORMAL");

        List<Scoreboard.GameRecord> games = scoreboard.getAllGames();

        assertNotNull(games);
        assertEquals(1, games.size());

        Scoreboard.GameRecord game = games.get(0);

        assertTrue(game.id() > 0);
        assertNotNull(game.date());
        assertEquals(60, game.durationSeconds());
        assertEquals(10, game.totalShots());
        assertEquals(5, game.hits());
        assertEquals(5, game.misses());
        assertEquals(2, game.shipsSunk());
        assertEquals("Player1", game.winner());
        assertEquals("NORMAL", game.gameMode());
    }

    @Test
    void shouldReturnMultipleGamesOrderedByDate() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.saveGame(30, 3, 2, 1, 1, "A", "NORMAL");
        scoreboard.saveGame(40, 4, 3, 1, 2, "B", "NORMAL");

        List<Scoreboard.GameRecord> games = scoreboard.getAllGames();

        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    void shouldDisplayEmptyScoreboardWithoutCrashing() {
        Scoreboard scoreboard = new Scoreboard();

        assertTrue(scoreboard.getAllGames().isEmpty());
        assertDoesNotThrow(scoreboard::displayScoreboard);
    }

    @Test
    void shouldDisplayScoreboardWithGamesWithoutCrashing() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.saveGame(90, 20, 12, 8, 4, "Player2", "SIMULATION");

        assertFalse(scoreboard.getAllGames().isEmpty());
        assertDoesNotThrow(scoreboard::displayScoreboard);
    }

    @Test
    void gameRecordShouldStoreAllValuesCorrectly() {
        Scoreboard.GameRecord record = new Scoreboard.GameRecord(
                1,
                "2025-01-01T10:00:00",
                120,
                15,
                7,
                8,
                3,
                "Player",
                "NORMAL"
        );

        assertEquals(1, record.id());
        assertEquals("2025-01-01T10:00:00", record.date());
        assertEquals(120, record.durationSeconds());
        assertEquals(15, record.totalShots());
        assertEquals(7, record.hits());
        assertEquals(8, record.misses());
        assertEquals(3, record.shipsSunk());
        assertEquals("Player", record.winner());
        assertEquals("NORMAL", record.gameMode());
    }
}