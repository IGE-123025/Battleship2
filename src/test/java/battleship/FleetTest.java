package battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Test class for Fleet.
 * Author: ${user.name}
 * Date: ${current_date}
 * Time: ${current_time}
 * Cyclomatic Complexity for each method:
 * - Constructor: 1
 * - addShip: 3
 * - getShips: 1
 * - getShipsLike: 2
 * - getFloatingShips: 2
 * - shipAt: 2
 * - isInsideBoard: 3
 * - colisionRisk: 2
 * - createRandom: 4
 * - getSunkShips: 3
 * - printAllShips: 1
 * - printShips: 2
 * - printShipsByCategory: 1
 * - printStatus: 1
 * - printFloatingShips: 1
 */
public class FleetTest {

    private Fleet fleet;

    @BeforeEach
    void setUp() {
        fleet = new Fleet();
    }

    @AfterEach
    void tearDown() {
        fleet = null;
    }

    /**
     * Test for the Fleet constructor.
     * Cyclomatic Complexity: 1
     */
    @Test
    @DisplayName("Constructor initializes empty fleet")
    void testConstructor() {
        assertNotNull(fleet, "Error: Instance of Fleet should not be null.");
        assertTrue(fleet.getShips().isEmpty(), "Error: Fleet should be initialized with empty ships list.");
    }

    /**
     * Test for the addShip method (all conditions true).
     * Cyclomatic Complexity: 4
     */
    @Test
    void testAddShip1() {
        IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
        assertTrue(fleet.addShip(ship), "Error: Valid ship should be added successfully.");
        assertEquals(1, fleet.getShips().size(), "Error: Fleet should contain one ship after addition.");
    }

    /**
     * Test for the addShip method (fleet size limit reached).
     */
    @Test
    void testAddShip2() {
        for (int i = 0; i < Fleet.FLEET_SIZE; i++) {
            fleet.addShip(new Barge(Compass.NORTH, new Position(i, 0)));
        }
        IShip anotherShip = new Barge(Compass.NORTH, new Position(10, 10));
        assertFalse(fleet.addShip(anotherShip), "Error: Should not add ship when fleet size limit is reached.");
    }

    /**
     * Test for the addShip method (ship outside the board).
     */
    @Test
    void testAddShip3() {
        IShip shipOutside = new Barge(Compass.NORTH, new Position(99, 99));
        assertFalse(fleet.addShip(shipOutside), "Error: Should not add ship outside the board.");
    }

    /**
     * Test for the addShip method (collision risk).
     */
    @Test
    void testAddShip4() {
        IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
        IShip ship2 = new Barge(Compass.NORTH, new Position(1, 1));  // Overlapping position
        fleet.addShip(ship1);
        assertFalse(fleet.addShip(ship2), "Error: Should not add ship with a collision risk.");
    }

    @Test
    @DisplayName("addShip fails when fleet size is greater than limit (strict overflow)")
    void testAddShip5() {
        for (int i = 0; i <= Fleet.FLEET_SIZE; i++) {
            fleet.addShip(new Barge(Compass.NORTH, new Position(i, 0)));
        }

        // Agora size > FLEET_SIZE
        IShip extra = new Barge(Compass.NORTH, new Position(10, 10));

        assertFalse(fleet.addShip(extra));
    }

    @Test
    @DisplayName("addShip fails when ships are adjacent (collision branch coverage)")
    void testAddShip6() {
        IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
        IShip ship2 = new Barge(Compass.NORTH, new Position(1, 2)); // adjacente

        fleet.addShip(ship1);

        assertFalse(fleet.addShip(ship2));
    }

    /**
     * Test for the getShips method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetShips() {
        assertTrue(fleet.getShips().isEmpty(), "Error: Fleet's ships list should initially be empty.");
        IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
        fleet.addShip(ship);
        assertEquals(1, fleet.getShips().size(), "Error: Fleet should have size 1 after adding a ship.");
        assertEquals(ship, fleet.getShips().get(0), "Error: Fleet's first ship should match the added ship.");
    }

    /**
     * Test for the getShipsLike method (ships of specific category).
     * Cyclomatic Complexity: 2
     */
    @Test
    void getShipsLike1() {
        IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
        IShip ship2 = new Caravel(Compass.NORTH, new Position(2, 1));
        fleet.addShip(ship1);
        fleet.addShip(ship2);

        List<IShip> barges = fleet.getShipsLike("Barca");
        assertEquals(1, barges.size(), "Error: There should be exactly one ship of category 'Barca'.");
        assertEquals(ship1, barges.get(0), "Error: The ship of category 'Barca' does not match.");
    }

    @Test
    @DisplayName("getShipsLike returns ships of given category")
    void getShipsLike2() {
        IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
        IShip ship2 = new Caravel(Compass.NORTH, new Position(2, 1));

        fleet.addShip(ship1);
        fleet.addShip(ship2);

        List<IShip> barges = fleet.getShipsLike("Barca");

        assertEquals(1, barges.size());
        assertEquals(ship1, barges.get(0));
    }


    /**
     * Test for the getFloatingShips method.
     * Cyclomatic Complexity: 2
     */
    @Test
    @DisplayName("getFloatingShips returns all ships initially")
    void testGetFloatingShips() {
        IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
        IShip ship2 = new Caravel(Compass.NORTH, new Position(4, 4));
        fleet.addShip(ship1);
        fleet.addShip(ship2);

        List<IShip> floatingShips = fleet.getFloatingShips();
        assertEquals(2, floatingShips.size(), "Error: All ships should be floating initially.");

        ship1.getPositions().get(0).shoot();  // Sink ship1
        floatingShips = fleet.getFloatingShips();
        assertEquals(1, floatingShips.size(), "Error: Only one ship should be floating after sinking one.");
        assertEquals(ship2, floatingShips.get(0), "Error: The floating ship should match the expected result.");
    }

    /**
     * Test for the shipAt method.
     * Cyclomatic Complexity: 2
     */
    @Test
    @DisplayName("shipAt returns ship at given position")
    void shipAt1() {
        IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
        fleet.addShip(ship);

        assertEquals(ship, fleet.shipAt(new Position(1, 1)), "Error: Should return the correct ship at the position.");
        assertNull(fleet.shipAt(new Position(5, 5)), "Error: Should return null for empty positions in the fleet.");
    }

    @Test
    @DisplayName("shipAt returns null when no ship exists at position")
    void shipAt2() {
        assertNull(fleet.shipAt(new Position(5, 5)));
    }

    /**
     * Test for private method isInsideBoard.
     * Cyclomatic Complexity: 3
     */
    @Test
    @DisplayName("isInsideBoard returns correct result")
    void isInsideBoard1() throws Exception {
        // Use reflection to access private methods
        var method = Fleet.class.getDeclaredMethod("isInsideBoard", IShip.class);
        method.setAccessible(true);

        IShip insideShip = new Barge(Compass.NORTH, new Position(1, 1));
        IShip outsideShip = new Barge(Compass.NORTH, new Position(99, 99));

        assertTrue((Boolean) method.invoke(fleet, insideShip), "Error: Ship inside the board should return true.");
        assertFalse((Boolean) method.invoke(fleet, outsideShip), "Error: Ship outside the board should return false.");
    }

    @Test
    @DisplayName("isInsideBoard fails when leftMostPos < 0")
    void isInsideBoard2() throws Exception {
        var method = Fleet.class.getDeclaredMethod("isInsideBoard", IShip.class);
        method.setAccessible(true);

        IShip ship = new Barge(Compass.NORTH, new Position(-1, 1));

        assertFalse((Boolean) method.invoke(fleet, ship));
    }

    @Test
    @DisplayName("isInsideBoard fails when rightMostPos exceeds board")
    void isInsideBoard3() throws Exception {
        var method = Fleet.class.getDeclaredMethod("isInsideBoard", IShip.class);
        method.setAccessible(true);

        IShip ship = new Barge(Compass.NORTH, new Position(Game.BOARD_SIZE, 1));

        assertFalse((Boolean) method.invoke(fleet, ship));
    }

    @Test
    @DisplayName("isInsideBoard fails when topMostPos < 0")
    void isInsideBoard4() throws Exception {
        var method = Fleet.class.getDeclaredMethod("isInsideBoard", IShip.class);
        method.setAccessible(true);

        IShip ship = new Barge(Compass.NORTH, new Position(1, -1));

        assertFalse((Boolean) method.invoke(fleet, ship));
    }

    @Test
    @DisplayName("isInsideBoard fails when bottomMostPos exceeds board")
    void isInsideBoard5() throws Exception {
        var method = Fleet.class.getDeclaredMethod("isInsideBoard", IShip.class);
        method.setAccessible(true);

        IShip ship = new Barge(Compass.NORTH, new Position(1, Game.BOARD_SIZE));

        assertFalse((Boolean) method.invoke(fleet, ship));
    }

    /**
     * Test for private method colisionRisk.
     * Cyclomatic Complexity: 2
     */
    @Test
    @DisplayName("colisionRisk detects overlapping ships")
    void testColisionRisk() throws Exception {
        var method = Fleet.class.getDeclaredMethod("colisionRisk", IShip.class);
        method.setAccessible(true);

        IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
        IShip ship2 = new Barge(Compass.NORTH, new Position(1, 1));  // Overlapping position
        fleet.addShip(ship1);

        assertTrue((Boolean) method.invoke(fleet, ship2), "Error: Overlapping ships should be at collision risk.");
        assertFalse((Boolean) method.invoke(fleet, new Barge(Compass.NORTH, new Position(5, 5))),
                "Error: Ships at non-overlapping positions should not have a collision risk.");
    }

    /**
     * Test for the printStatus method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testPrintStatus() {
        IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
        fleet.addShip(ship);
        assertDoesNotThrow(fleet::printStatus, "Error: printStatus should not throw any exceptions.");
    }

    /**
     * Test for the createRandom method
     * CyclomaticComplexity: 4
     */
    @Test
    @DisplayName("createRandom returns a non-null fleet")
    void createRandom1() {
        IFleet randomFleet = Fleet.createRandom();

        assertNotNull(randomFleet);
    }

    /**
     * Test for the createRandom method
     * CyclomaticComplexity: 4
     */
    @Test
    @DisplayName("createRandom generates fleet with correct size")
    void createRandom2() {
        IFleet randomFleet = Fleet.createRandom();

        assertEquals(Fleet.FLEET_SIZE, randomFleet.getShips().size());
    }

    /**
     * Test for the createRandom method
     * CyclomaticComplexity: 4
     */
    @Test
    @DisplayName("createRandom generates correct ship categories")
    void createRandom3() {
        IFleet fleet = Fleet.createRandom();

        assertEquals(1, fleet.getShipsLike("galeao").size());
        assertEquals(1, fleet.getShipsLike("fragata").size());
        assertEquals(2, fleet.getShipsLike("nau").size());
        assertEquals(3, fleet.getShipsLike("caravela").size());
        assertEquals(4, fleet.getShipsLike("barca").size());
    }

    /**
     * Test for the createRandom method
     * CyclomaticComplexity: 4
     */
    @Test
    @DisplayName("createRandom generates ships inside board boundaries")
    void createRandom4() {
        IFleet fleet = Fleet.createRandom();

        for (IShip ship : fleet.getShips()) {
            assertTrue(
                    ship.getLeftMostPos() >= 0 &&
                            ship.getRightMostPos() < Game.BOARD_SIZE &&
                            ship.getTopMostPos() >= 0 &&
                            ship.getBottomMostPos() < Game.BOARD_SIZE
            );
        }
    }

    @Test
    @DisplayName("createRandom eventually fills fleet despite failed insertions")
    void createRandom5() {
        IFleet fleet = Fleet.createRandom();

        assertEquals(Fleet.FLEET_SIZE, fleet.getShips().size());
    }

    /**
     * Test for the printShips method
     * Cyclomatic Complexity: 2
     */
    @Test
    @DisplayName("printShips does not throw exception")
    void printShips1() {
        assertDoesNotThrow(() -> fleet.printShips(new ArrayList<>()));
    }

    @Test
    @DisplayName("printShips prints ships when list is not empty")
    void printShips2() {
        List<IShip> ships = new ArrayList<>();
        ships.add(new Barge(Compass.NORTH, new Position(1, 1)));

        assertDoesNotThrow(() -> fleet.printShips(ships));
    }

    /**
     * Test for the printShipsByCategory method
     * Cyclomatic Complexity: 1
     */
    @Test
    @DisplayName("printShipsByCategory does not throw exception")
    void testPrintShipsByCategory() {
        assertDoesNotThrow(() -> fleet.printShipsByCategory("Barca"));
    }

    /**
     * Test for the printFloatingShips method
     * Cyclomatic Complexity: 1
     */
    @Test
    @DisplayName("printFloatingShips does not throw exception")
    void testPrintFloatingShips() {
        assertDoesNotThrow(fleet::printFloatingShips);
    }

    /**
     * Test for the printAllShips method
     * Cyclomatic Complexity: 1
     */
    @Test
    @DisplayName("printAllShips does not throw exception")
    void testPrintAllShips() {
        assertDoesNotThrow(fleet::printAllShips);
    }

    @Test
    @DisplayName("getSunkShips returns empty list when no ships are sunk")
    void getSunkShips1() {
        IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
        fleet.addShip(ship);

        List<IShip> sunkShips = fleet.getSunkShips();

        assertTrue(sunkShips.isEmpty());
    }

    @Test
    @DisplayName("getSunkShips returns all ships when all are sunk")
    void getSunkShips2() {
        IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
        IShip ship2 = new Caravel(Compass.NORTH, new Position(3, 3));

        fleet.addShip(ship1);
        fleet.addShip(ship2);

        // Afundar todos (assumindo 1 posição por simplicidade)
        ship1.getPositions().forEach(p -> p.shoot());
        ship2.getPositions().forEach(p -> p.shoot());

        List<IShip> sunkShips = fleet.getSunkShips();

        assertEquals(2, sunkShips.size());
    }

    @Test
    @DisplayName("getSunkShips returns only sunk ships when some are sunk")
    void getSunkShips3() {
        IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
        IShip ship2 = new Caravel(Compass.NORTH, new Position(4, 4));

        fleet.addShip(ship1);
        fleet.addShip(ship2);

        // Afundar apenas um
        ship1.getPositions().forEach(p -> p.shoot());

        List<IShip> sunkShips = fleet.getSunkShips();

        assertEquals(1, sunkShips.size());
        assertEquals(ship1, sunkShips.get(0));
    }

}