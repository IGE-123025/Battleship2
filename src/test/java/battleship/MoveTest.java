package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Move.
 * Author: ${Tiago Reinolds, 123011}
 * Date: 30-04-2026, 14:25
 *
 * Cyclomatic Complexity:
 * constructor
 */
class MoveTest {
    private Move move;

    @BeforeEach
    void setUp() {
        move = new Move(1, new ArrayList<>(), new ArrayList<>());
    }

    @AfterEach
    void tearDown() {
        move = null;
    }
    // =====================
    // toString()
    // =====================

    @Test
    @DisplayName("toString deve conter número e contagens")
    void testToString() {
        String result = move.toString();
        assertAll(
                () -> assertNotNull(result, "Erro: toString retornou null!"),
                () -> assertTrue(result.contains("number=1"), "Erro número não presente"),
                () -> assertTrue(result.contains("shots=0"), "Erro: shots incorreto"),
                () -> assertTrue(result.contains("results=0"), "Erro: results incorreto")
        );
    }

    // =========================
    // getNumber()
    // =========================

    @Test
    @DisplayName("getNumber deve devolver o número correto")
    void getNumber() {
        assertEquals(1, move.getNumber(), "Erro: número esperado 1");
    }

    // =========================
    // getShots()
    // =========================

    @Test
    @DisplayName("getShots deve devolver lista vazia inicial")
    void getShots() {
        assertTrue(move.getShots().isEmpty(), "Erro: lista devia estar vazia");
    }

    // =========================
    // getShotResults()
    // =========================

    @Test
    @DisplayName("getShotResults deve devolver lista vazia inicial")
    void getShotResults() {
        assertTrue(move.getShotResults().isEmpty(), "Erro: lista devia estar vazia");
    }

    // =========================
    // processEnemyFire()
    // =========================

    // Caminho 1: Lista Vazia (Caso Base)
    @Test
    @DisplayName("processEnemyFire sem tiros deve gerar JSON com zeros")
    void processEnemyFire() {
        String json = move.processEnemyFire(false);

        assertAll(
                () -> assertTrue(json.contains("\"validShots\" : 0"), "Erro: validShots != 0"),
                () -> assertTrue(json.contains("\"repeatedShots\" : 0"), "Erro: repeatedShots != 0"),
                () -> assertTrue(json.contains("\"missedShots\" : 0"), "Erro: missedShots != 0")
        );
    }

}
