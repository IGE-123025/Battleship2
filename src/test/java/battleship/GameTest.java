package battleship;

import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Game.
 * Author: Tiago Reinolds, 123011
 * Date: 2026-01-05
 * Time: 15:20
 * Cyclomatic Complexity for each method:
 * - Game (constructor): 1
 * - fireShots:3
 * - fireSingleShot: 5
 * - getShots: 1
 * - getRepeatedShots: 1
 * - getInvalidShots: 1
 * - getHits: 1
 * - getSunkShips: 1
 * - getRemainingShips: 1
 * - validShot: 3
 * - repeatedShot: 2
 * - printBoard: 1
 * - printValidShots: 1
 * - printFleet: 1
 */
public class GameTest {

	private Game game;

	@BeforeEach
	void setUp() {
		game = new Game(new Fleet());
	}

	@AfterEach
	void tearDown() {
		game = null;
	}

	// ---------------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------------

	@Test
	@DisplayName("Constructor initializes game state correctly")
	void constructor() {
		assertNotNull(game);

		assertAll(
				() -> assertTrue(game.getAlienMoves().isEmpty()),
				() -> assertEquals(0, game.getInvalidShots()),
				() -> assertEquals(0, game.getRepeatedShots()),
				() -> assertEquals(0, game.getHits()),
				() -> assertEquals(0, game.getSunkShips())
		);
	}

	// ---------------------------------------------------
	// fireSingleShot (CRITICAL BRANCHES)
	// ---------------------------------------------------

	@Test
	@DisplayName("fireSingleShot - invalid position branch")
	void fireSingleShot_invalid() {
		game.fireSingleShot(new Position(-1, 5), false);

		assertEquals(1, game.getInvalidShots(),
				"Invalid shot branch not executed correctly");
	}

	@Test
	@DisplayName("fireSingleShot - miss branch (no ship)")
	void fireSingleShot_miss() {
		IGame.ShotResult result = game.fireSingleShot(new Position(9, 9), false);

		assertNull(result.ship());
		assertFalse(result.sunk());
	}

	@Test
	@DisplayName("fireSingleShot - hit branch")
	void fireSingleShot_hit() {
		Position pos = new Position(1, 1);

		Ship ship = new Barge(Compass.NORTH, pos);
		game.getMyFleet().addShip(ship);

		IGame.ShotResult result = game.fireSingleShot(pos, false);

		assertNotNull(result.ship());
	}

	@Test
	@DisplayName("fireSingleShot - sink branch (critical)")
	void fireSingleShot_sinkBranch() {
		Position pos = new Position(2, 2);

		Ship ship = new Barge(Compass.NORTH, pos);
		game.getMyFleet().addShip(ship);

		game.fireSingleShot(pos, false);

		assertFalse(ship.stillFloating(),
				"Sink branch not fully executed");
	}

	@Test
	@DisplayName("fireSingleShot - repeated via parameter")
	void fireSingleShot_repeatedFlag() {
		IGame.ShotResult result = game.fireSingleShot(new Position(3, 3), true);

		assertTrue(result.repeated());
	}

	@Test
	@DisplayName("fireSingleShot - repeated via history branch")
	void fireSingleShot_repeatedHistory() {
		Position pos = new Position(2, 3);

		game.fireShots(List.of(pos, new Position(2, 4), new Position(2, 5)));

		IGame.ShotResult result = game.fireSingleShot(pos, false);

		assertTrue(result.repeated());
	}

	// ---------------------------------------------------
	// fireShots (STATE + LOOP BRANCHES)
	// ---------------------------------------------------

	@Test
	@DisplayName("fireShots - valid execution")
	void fireShots_valid() {
		game.fireShots(List.of(
				new Position(1,1),
				new Position(2,2),
				new Position(3,3)
		));

		assertEquals(1, game.getAlienMoves().size());
	}

	@Test
	@DisplayName("fireShots - invalid size exception")
	void fireShots_invalidSize() {
		assertThrows(IllegalArgumentException.class,
				() -> game.fireShots(List.of(new Position(1,1))));
	}

	@Test
	@DisplayName("fireShots - duplicate positions branch")
	void fireShots_duplicates() {
		game.fireShots(List.of(
				new Position(1,1),
				new Position(1,1),
				new Position(2,2)
		));

		assertEquals(1, game.getAlienMoves().size());
	}

	@Test
	@DisplayName("fireShots - alreadyShot.contains branch trigger")
	void fireShots_alreadyShotBranch() {
		Position p = new Position(2, 2);

		game.fireShots(List.of(p, new Position(3,3), new Position(4,4)));
		game.fireShots(List.of(p, new Position(5,5), new Position(6,6)));

		assertTrue(game.getAlienMoves().size() >= 2);
	}

	// ---------------------------------------------------
	// repeatedShot (EMPTY vs FILLED state)
	// ---------------------------------------------------

	@Test
	@DisplayName("repeatedShot - false initially")
	void repeatedShot_false() {
		assertFalse(game.repeatedShot(new Position(1,1)));
	}

	@Test
	@DisplayName("repeatedShot - true after firing")
	void repeatedShot_true() {
		game.fireShots(List.of(
				new Position(1,1),
				new Position(2,2),
				new Position(3,3)
		));

		assertTrue(game.repeatedShot(new Position(1,1)));
	}

	// ---------------------------------------------------
	// readEnemyFire (PARSING BRANCHES)
	// ---------------------------------------------------

	@Test
	@DisplayName("readEnemyFire - valid combined format")
	void readEnemyFire_combined() {
		assertNotNull(game.readEnemyFire(new Scanner("A1 B2 C3")));
	}

	@Test
	@DisplayName("readEnemyFire - split format")
	void readEnemyFire_split() {
		assertNotNull(game.readEnemyFire(new Scanner("A 1 B 2 C 3")));
	}

	@Test
	@DisplayName("readEnemyFire - invalid format exception")
	void readEnemyFire_invalid() {
		assertThrows(IllegalArgumentException.class,
				() -> game.readEnemyFire(new Scanner("A B C")));
	}

	@Test
	@DisplayName("readEnemyFire - missing integer branch")
	void readEnemyFire_missingInt() {
		assertThrows(IllegalArgumentException.class,
				() -> game.readEnemyFire(new Scanner("A B")));
	}

	@Test
	@DisplayName("readEnemyFire - missing shots branch")
	void readEnemyFire_missingShots() {
		assertThrows(IllegalArgumentException.class,
				() -> game.readEnemyFire(new Scanner("A1")));
	}

	// ---------------------------------------------------
	// randomEnemyFire (STATE ONLY)
	// ---------------------------------------------------

	@Test
	@DisplayName("randomEnemyFire - executes safely")
	void randomEnemyFire_runs() {
		assertNotNull(game.randomEnemyFire());
	}

	@Test
	@DisplayName("randomEnemyFire - accumulates moves")
	void randomEnemyFire_accumulate() {
		game.randomEnemyFire();
		game.randomEnemyFire();

		assertEquals(2, game.getAlienMoves().size());
	}

	// ---------------------------------------------------
	// FLEET
	// ---------------------------------------------------

	@Test
	@DisplayName("getMyFleet - not null")
	void getMyFleet() {
		assertNotNull(game.getMyFleet());
	}

	@Test
	@DisplayName("getRemainingShips - sink logic")
	void getRemainingShips() {
		IFleet fleet = game.getMyFleet();

		Ship s1 = new Barge(Compass.NORTH, new Position(1,1));
		Ship s2 = new Frigate(Compass.EAST, new Position(5,5));

		fleet.addShip(s1);
		fleet.addShip(s2);

		assertEquals(2, game.getRemainingShips());

		s2.sink();

		assertEquals(1, game.getRemainingShips());
	}
}