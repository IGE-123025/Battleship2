package battleship.messages;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Messages
 * Author: ${user.name}
 * Date: 2026-04-30 13:59
 * Cyclomatic Complexity:
 * - goodbyeMessage: 4
 * - invalidCommand: 4
 * - boardCaptions: 4
 * - status: 4
 */
public class MessagesTest {

    @BeforeEach
    void resetLocale() {
        Messages.setLocale("pt");
    }

    @AfterEach
    void resetLocale2() {
        Messages.setLocale("pt");
    }

    @Test
    @DisplayName("LOCALE returns current locale after setLocale")
    void localeTest() {
        Messages.setLocale("en");
        assertEquals("en", Messages.LOCALE().getLanguage());
    }

    @Test
    @DisplayName("goodbyeMessage returns English message")
    void goodbyeMessage1() {
        Messages.setLocale("en");
        String result = Messages.goodbyeMessage();
        assertEquals("Good Sailing!", result);
    }

    @Test
    @DisplayName("goodbyeMessage returns Portuguese message")
    void goodbyeMessage2() {
        Messages.setLocale("pt");
        String result = Messages.goodbyeMessage();
        assertEquals("Bons ventos!", result);
    }

    @Test
    @DisplayName("goodbyeMessage returns default for unknown locale")
    void goodbyeMessage3() {
        Messages.setLocale("fr");
        String result = Messages.goodbyeMessage();
        assertEquals("Bons ventos!", result);
    }

    @Test
    @DisplayName("goodbyeMessage returns default for empty locale")
    void goodbyeMessage4() {
        Messages.setLocale("");
        String result = Messages.goodbyeMessage();
        assertEquals("Bons ventos!", result);
    }


    @Test
    @DisplayName("invalidCommand returns English message")
    void invalidCommand1() {
        Messages.setLocale("en");
        String result = Messages.invalidCommand();
        assertEquals("Invalid command!", result);
    }

    @Test
    @DisplayName("invalidCommand returns Portuguese message")
    void invalidCommand2() {
        Messages.setLocale("pt");
        String result = Messages.invalidCommand();
        assertEquals("Que comando é esse??? Repete ...", result);
    }

    @Test
    @DisplayName("invalidCommand returns default for unknown locale")
    void invalidCommand3() {
        Messages.setLocale("fr");
        String result = Messages.invalidCommand();
        assertEquals("Que comando é esse??? Repete ...", result);
    }

    @Test
    @DisplayName("invalidCommand duplicate default test")
    void invalidCommand4() {
        Messages.setLocale("fr");
        String result = Messages.invalidCommand();
        assertEquals("Que comando é esse??? Repete ...", result);
    }


    @Test
    @DisplayName("boardCaptions returns English captions")
    void boardCaptions1() {
        Messages.setLocale("en");
        String[] result = Messages.boardCaptions('#','-', '.',
                '*', 'o');
        assertEquals("          CAPTION", result[0]);
        assertTrue(result[1].contains("'#'->ship"));
        assertTrue(result[1].contains("'-'->ship adjacent"));
        assertTrue(result[1].contains("'.'->water"));
        assertTrue(result[2].contains("'*'->Hit"));
        assertTrue(result[2].contains("'o'->Missed shot"));
    }

    @Test
    @DisplayName("boardCaptions returns Portuguese captions")
    void boardCaptions2() {
        Messages.setLocale("pt");
        String[] result = Messages.boardCaptions('#', '-', '.',
                '*', 'o');
        assertEquals("          LEGENDA", result[0]);
        assertTrue(result[1].contains("'#'->navio"));
        assertTrue(result[1].contains("'-'->adjacente a navio"));
        assertTrue(result[1].contains("'.'->água"));
        assertTrue(result[2].contains("'*'->Tiro certeiro"));
        assertTrue(result[2].contains("'o'->Tiro na água"));
    }

    @Test
    @DisplayName("boardCaptions returns default captions for unknown locale")
    void boardCaptions3() {
        Messages.setLocale("fr");
        String[] result = Messages.boardCaptions('#', '-', '.',
                '*', 'o');
        assertEquals("          LEGENDA", result[0]);
        assertTrue(result[1].contains("'#'->navio"));
        assertTrue(result[1].contains("'-'->adjacente a navio"));
        assertTrue(result[1].contains("'.'->água"));
        assertTrue(result[2].contains("'*'->Tiro certeiro"));
        assertTrue(result[2].contains("'o'->Tiro na água"));
    }

    @Test
    @DisplayName("boardCaptions returns default captions for empty locale")
    void boardCaptions4() {
        Messages.setLocale("");
        String[] result = Messages.boardCaptions('#', '-', '.',
                '*', 'o');
        assertEquals("          LEGENDA", result[0]);
        assertTrue(result[1].contains("'#'->navio"));
        assertTrue(result[1].contains("'-'->adjacente a navio"));
        assertTrue(result[1].contains("'.'->água"));
        assertTrue(result[2].contains("'*'->Tiro certeiro"));
        assertTrue(result[2].contains("'o'->Tiro na água"));
    }

    @Test
    @DisplayName("status formats message in English")
    void status1() {
        int floatingShips = 7;
        int sunkenShips = 4;
        Messages.setLocale("en");
        String result = Messages.status(floatingShips, sunkenShips);
        assertEquals("Fleet State: 7 floating, 4 sunken!", result);
    }

    @Test
    @DisplayName("status formats message in Portuguese")
    void status2() {
        int floatingShips = 7;
        int sunkenShips = 4;
        Messages.setLocale("pt");
        String result = Messages.status(floatingShips, sunkenShips);
        assertEquals("Estado da Frota: 7 a flutuar, 4 afundados!", result);
    }

    @Test
    @DisplayName("status formats default message for unknown locale")
    void status3() {
        int floatingShips = 7;
        int sunkenShips = 4;
        Messages.setLocale("fr");
        String result = Messages.status(floatingShips, sunkenShips);
        assertEquals("Estado da Frota: 7 a flutuar, 4 afundados!", result);
    }

    @Test
    @DisplayName("status uses default locale when none is set explicitly")
    void status4() {
        int floatingShips = 7;
        int sunkenShips = 4;
        String result = Messages.status(floatingShips, sunkenShips);
        assertEquals("Estado da Frota: 7 a flutuar, 4 afundados!", result);
    }
}
