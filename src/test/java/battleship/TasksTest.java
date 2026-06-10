package battleship;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TasksTest {

    @Test
    void shouldPrintMenuHelpWithoutErrors() {
        assertDoesNotThrow(Tasks::menuHelp);
    }

    @Test
    void shouldExitMenuImmediately() {
        String input = "desisto\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(Tasks::menu);
    }

    @Test
    void shouldHandleAjudaCommand() {
        String input = "ajuda\ndesisto\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(Tasks::menu);
    }

    @Test
    void shouldHandleInvalidCommand() {
        String input = "xyz\ndesisto\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(Tasks::menu);
    }

    @Test
    void shouldReadPositionCorrectly() {
        Scanner scanner = new Scanner("3 4");

        Position pos = Tasks.readPosition(scanner);

        assertNotNull(pos);
    }

    @Test
    void shouldReadClassicPositionCompactFormat() {
        Scanner scanner = new Scanner("A3");

        IPosition pos = Tasks.readClassicPosition(scanner);

        assertNotNull(pos);
    }

    @Test
    void shouldReadClassicPositionWithSpace() {
        Scanner scanner = new Scanner("A 3");

        IPosition pos = Tasks.readClassicPosition(scanner);

        assertNotNull(pos);
    }

    @Test
    void shouldThrowExceptionForInvalidClassicPosition() {
        Scanner scanner = new Scanner("invalid");

        assertThrows(IllegalArgumentException.class, () -> {
            Tasks.readClassicPosition(scanner);
        });
    }

    @Test
    void shouldThrowExceptionWhenNoInputClassicPosition() {
        Scanner scanner = new Scanner("");

        assertThrows(IllegalArgumentException.class, () -> {
            Tasks.readClassicPosition(scanner);
        });
    }

    @Test
    void shouldReadShip() {
        String input = "B 1 1 N";
        Scanner scanner = new Scanner(input);

        Ship ship = Tasks.readShip(scanner);

        assertNotNull(ship);
    }

    @Test
    void shouldBuildFleetWithoutCrashing() {
        String input = "B 1 1 N B 2 2 N B 3 3 N B 4 4 N B 5 5 N";
        Scanner scanner = new Scanner(input);

        assertDoesNotThrow(() -> Tasks.buildFleet(scanner));
    }
}