package battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Test class for Ship.
 * Author: ${user.name}
 * Date: ${current_date}
 * Time: ${current_time}
 * Cyclomatic Complexity for each method:
 * - Constructor: 1
 * - getCategory: 1
 * - getSize: 1
 * - getBearing: 1
 * - getPositions: 1
 * - stillFloating: 2
 * - shoot: 2
 * - occupies: 2
 * - tooCloseTo (IShip): 2
 * - tooCloseTo (IPosition): 2
 * - getTopMostPos: 2
 * - getBottomMostPos: 2
 * - getLeftMostPos: 2
 * - getRightMostPos: 2
 * - buildShip: 7
 * - getAdjacentPositions: 5
 * - getPosition: 1
 * - sink: 2
 * - toString: 1
 */
public class ShipTest {

    private Ship ship;

    @BeforeEach
    void setUp() {
        // Since Ship is abstract, instantiate it with a concrete subclass (e.g., Barge)
        ship = new Barge(Compass.NORTH, new Position(5, 5));
    }

    @AfterEach
    void tearDown() {
        ship = null;
    }

    /**
     * Test for the constructor.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testConstructor() {
        assertNotNull(ship, "Error: Ship instance should not be null.");
        assertEquals("Barca", ship.getCategory(), "Error: Ship category is incorrect.");
        assertEquals(Compass.NORTH, ship.getBearing(), "Error: Ship bearing is incorrect.");
        assertEquals(1, ship.getSize(), "Error: Ship size is incorrect.");
        assertFalse(ship.getPositions().isEmpty(), "Error: Ship positions should not be empty.");
    }

    /**
     * Test for the getCategory method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetCategory() {
        assertEquals("Barca", ship.getCategory(), "Error: Ship category should be 'Barca'.");
    }

    /**
     * Test for the getSize method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetSize() {
        assertEquals(1, ship.getSize(), "Error: Ship size should be 1.");
    }

    /**
     * Test for the getBearing method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetBearing() {
        assertEquals(Compass.NORTH, ship.getBearing(), "Error: Ship bearing should be NORTH.");
    }

    /**
     * Test for the getPositions method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetPositions() {
        List<IPosition> positions = ship.getPositions();
        assertNotNull(positions, "Error: Ship positions should not be null.");
        assertEquals(1, positions.size(), "Error: Ship should have exactly one position.");
        assertEquals(5, positions.get(0).getRow(), "Error: Position's row should be 5.");
        assertEquals(5, positions.get(0).getColumn(), "Error: Position's column should be 5.");
    }

    /**
     * Test for the stillFloating method (all positions intact).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testStillFloating1() {
        assertTrue(ship.stillFloating(), "Error: Ship should still be floating.");
    }

    /**
     * Test for the stillFloating method (all positions hit).
     */
    @Test
    void testStillFloating2() {
        ship.getPositions().get(0).shoot();
        assertFalse(ship.stillFloating(), "Error: Ship should no longer be floating after being hit.");
    }

    /**
     * Test for the shoot method (valid position).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testShoot1() {
        Position target = new Position(5, 5);
        ship.shoot(target);
        assertTrue(ship.getPositions().get(0).isHit(), "Error: Position should be marked as hit.");
    }

    /**
     * Test for the shoot method (invalid position).
     */
    @Test
    void testShoot2() {
        Position target = new Position(0, 0);
        ship.shoot(target); // No exception expected
        assertFalse(ship.getPositions().get(0).isHit(), "Error: Position should not be marked as hit for an invalid target.");
    }

    /**
     * Test for the occupies method (position occupied).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testOccupies1() {
        Position pos = new Position(5, 5);
        assertTrue(ship.occupies(pos), "Error: Ship should occupy position (5, 5).");
    }

    /**
     * Test for the occupies method (position not occupied).
     */
    @Test
    void testOccupies2() {
        Position pos = new Position(1, 1);
        assertFalse(ship.occupies(pos), "Error: Ship should not occupy position (1, 1).");
    }

    /**
     * Test for the tooCloseTo method with another IShip (ships too close).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testTooCloseToShip1() {
        Ship nearbyShip = new Barge(Compass.NORTH, new Position(5, 6));
        assertTrue(ship.tooCloseTo(nearbyShip), "Error: Ships should be too close.");
    }

    /**
     * Test for the tooCloseTo method with another IShip (ships not close).
     */
    @Test
    void testTooCloseToShip2() {
        Ship farShip = new Barge(Compass.NORTH, new Position(10, 10));
        assertFalse(ship.tooCloseTo(farShip), "Error: Ships should not be too close.");
    }

    /**
     * Test for the tooCloseTo method with an IPosition (positions adjacent).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testTooCloseToPosition1() {
        Position pos = new Position(5, 6); // Adjacent position
        assertTrue(ship.tooCloseTo(pos), "Error: Ship should be too close to the given position.");
    }

    /**
     * Test for the tooCloseTo method with an IPosition (positions not adjacent).
     */
    @Test
    void testTooCloseToPosition2() {
        Position pos = new Position(7, 7); // Non-adjacent position
        assertFalse(ship.tooCloseTo(pos), "Error: Ship should not be too close to the given position.");
    }

    /**
     * Test for the getTopMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetTopMostPos1() {
        assertEquals(5, ship.getTopMostPos(), "Error: The topmost position should be 5.");
    }

    @Test
    @DisplayName("getTopMostPos works with multi-position ship")
    void testGetTopMost2() {
        Ship s = new Caravel(Compass.NORTH, new Position(5,5));

        assertTrue(s.getTopMostPos() <= s.getBottomMostPos());
    }

    @Test
    @DisplayName("getTopMostPos is consistent with positions list")
    void getTopMostPos3() {
        Ship s = new Frigate(Compass.NORTH, new Position(5,5));

        int expected = s.getPositions()
                .stream()
                .mapToInt(IPosition::getRow)
                .min()
                .orElseThrow();

        assertEquals(expected, s.getTopMostPos());
    }

    /**
     * Test for the getBottomMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetBottomMostPos() {
        assertEquals(5, ship.getBottomMostPos(), "Error: The bottommost position should be 5.");
    }

    /**
     * Test for the getLeftMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetLeftMostPos() {
        assertEquals(5, ship.getLeftMostPos(), "Error: The leftmost position should be 5.");
    }

    /**
     * Test for the getRightMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetRightMostPos() {
        assertEquals(5, ship.getRightMostPos(), "Error: The rightmost position should be 5.");
    }

    @Test @DisplayName("buildShip creates Barge")
    void buildShip1() {
        assertTrue(Ship.buildShip("barca", Compass.NORTH, new Position(1,1)) instanceof Barge);
    }

    @Test @DisplayName("buildShip creates Caravel")
    void buildShip2() {
        assertTrue(Ship.buildShip("caravela", Compass.NORTH, new Position(1,1)) instanceof Caravel);
    }

    @Test @DisplayName("buildShip creates Carrack")
    void buildShip3() {
        assertTrue(Ship.buildShip("nau", Compass.NORTH, new Position(1,1)) instanceof Carrack);
    }

    @Test @DisplayName("buildShip creates Frigate")
    void buildShip4() {
        assertTrue(Ship.buildShip("fragata", Compass.NORTH, new Position(1,1)) instanceof Frigate);
    }

    @Test @DisplayName("buildShip creates Galleon")
    void buildShip5() {
        assertTrue(Ship.buildShip("galeao", Compass.NORTH, new Position(1,1)) instanceof Galleon);
    }

    @Test @DisplayName("buildShip returns null for invalid type")
    void buildShip6() {
        assertNull(Ship.buildShip("invalid", Compass.NORTH, new Position(1,1)));
    }

    @Test
    @DisplayName("getAdjacentPositions returns positions")
    void getAdjacentPositions1() {
        List<IPosition> adj = ship.getAdjacentPositions();
        assertFalse(adj.isEmpty());
    }

    @Test
    @DisplayName("adjacent does not include ship positions")
    void getAdjacentPositions2() {
        List<IPosition> adj = ship.getAdjacentPositions();

        for (IPosition p : adj) {
            assertFalse(ship.getPositions().contains(p));
        }
    }

    @Test
    @DisplayName("adjacent handles multiple ship positions")
    void getAdjacentPositions3() {
        Ship s = new Caravel(Compass.NORTH, new Position(5,5));

        List<IPosition> adj = s.getAdjacentPositions();

        assertNotNull(adj);
        assertFalse(adj.isEmpty());
    }

    @Test
    @DisplayName("adjacent positions contain no duplicates")
    void getAdjacentPositions4() {
        Ship s = new Caravel(Compass.NORTH, new Position(5,5));

        List<IPosition> adj = s.getAdjacentPositions();

        long distinct = adj.stream().distinct().count();

        assertEquals(distinct, adj.size());
    }

    @Test
    @DisplayName("getPosition returns initial position")
    void testGetPosition() {
        assertEquals(5, ship.getPosition().getRow());
        assertEquals(5, ship.getPosition().getColumn());
    }

    @Test
    @DisplayName("sink hits all positions")
    void sink1() {
        Ship s = new Caravel(Compass.NORTH, new Position(5,5));

        s.sink();

        for (IPosition p : s.getPositions()) {
            assertTrue(p.isHit());
        }
    }

    @Test
    @DisplayName("sink on ship with positions does not throw")
    void sink2() {
        assertDoesNotThrow(() -> ship.sink());
    }

    @Test
    @DisplayName("toString returns correct format")
    void testToString() {
        String str = ship.toString();

        assertTrue(str.contains("Barca"));
        assertTrue(str.contains("n"));
    }

    @Test
    @DisplayName("occupies throws AssertionError when null")
    void occupiesNull() {
        assertThrows(AssertionError.class, () -> ship.occupies(null));
    }

    @Test
    @DisplayName("tooCloseTo(IShip) throws AssertionError when null")
    void tooCloseToShipNull() {
        assertThrows(AssertionError.class, () -> ship.tooCloseTo((IShip) null));
    }

    @Test
    @DisplayName("tooCloseTo(IPosition) throws AssertionError when null")
    void tooCloseToPositionNull() {
        assertThrows(AssertionError.class, () -> ship.tooCloseTo((IPosition) null));
    }

    @Test
    @DisplayName("shoot throws AssertionError when null")
    void shootNull() {
        assertThrows(AssertionError.class, () -> ship.shoot(null));
    }

    @Test
    @DisplayName("shoot throws AssertionError when position is outside")
    void shootOutside() {
        Position outside = new Position(-1, -1); // assume isInside() = false
        assertThrows(AssertionError.class, () -> ship.shoot(outside));
    }
}