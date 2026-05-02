package battleship;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Position.
 * Author: Tiago Reinolds, 123011
 * Date: 01/05/2026 11:30
 * Cyclomatic Complexity for each method:
 * - Constructor (char,int) : 1
 * - Constructor (int,int) :  1
 * - RandomPosition : 1
 * - getRow: 1
 * - getColumn: 1
 * - getClassicRow : 1
 * - getClassicColumn : 1
 * - isInside : 2
 * - isAdjacentTo: 2
 * - adjacentPositions : 3
 * - isOccupied: 1
 * - isHit: 1
 * - occupy: 1
 * - shoot: 1
 * - equals: 3
 * - hashCode: 1
 * - toString: 1
 */
public class PositionTest {
	private Position position;

	@BeforeEach
	void setUp() {
		position = new Position(2, 3);
	//	position = new Position('C', 4);
	}

	@AfterEach
	void tearDown() {
		position = null;
	}

	// ------------ CONSTRUCTOR --------------

	@Test
	@DisplayName("Constructor (char, int) should correctly convert to internal coordinates")
	void constructorCharInt() {
		Position pos = new Position('B', 4);
		assertAll(
				() ->assertEquals(1, pos.getRow(), "Failed to set row: expected 1 but got " + pos.getRow()),
				() ->assertEquals(3, pos.getColumn(), "Failed to set column: expected 3 but got " + pos.getColumn()),
				() -> assertFalse(pos.isOccupied(), "New position should not be occupied"),
				() ->assertFalse(pos.isHit(), "New position should not be hit")
		);
	}

	@Test
	@DisplayName("Constructor (int, int) should assing row and column directly")
	void constructorIntInt(){
		Position pos = new Position(5,6);
		assertAll(
				() -> assertEquals(5, pos.getRow(), "Expected row 5 but got " + pos.getRow()),
				() -> assertEquals(6, pos.getColumn(), "Expected column 6 but got" + pos.getColumn())
		);
	}

	// --------------- RANDOM -------------------

	@Test
	@DisplayName("RandomPosition should always generate coordinates within borad limits")
	void randomPosition() {
		for(int i = 0; i < 20; i++ ){
			Position p = Position.randomPosition();
			assertAll(
					() -> assertTrue(p.getRow() >= 0 && p.getRow() < Game.BOARD_SIZE, "Failed to set row: Row out of bonds " + p.getRow()),
					() -> assertTrue(p.getColumn() >= 0 && p.getColumn() < Game.BOARD_SIZE, "Failed to set column: Column out of bonds" + p.getColumn())
			);
		}
	}

	// -------------GETTERS---------------

	@Test
	@DisplayName("getRow should return the correct row")
	void getRow() {
		assertEquals(2, position.getRow(), "Failed to get row: expected 2 but got " + position.getRow());
	}

	@Test
	@DisplayName("getColumn should return the correct column")
	void getColumn() {
		assertEquals(3, position.getColumn(), "Failed to get column: expected 3 but got " + position.getColumn());
	}

	@Test
	@DisplayName("getClassicRow should convert row index to letter")
	void getClassicRow() {
		assertEquals('C', position.getClassicRow(), "Failed to get row: expected 2 but got " + position.getRow());
	}

	@Test
	@DisplayName("getClassicColumn should convert column index to letter")
	void getClassicColumn() {
		assertEquals(3, position.getColumn(), "Failed to get column: expected 3 but got " + position.getColumn());
	}

	// ------------------- isInside ------------------
	@Test
	@DisplayName("isInside should return true for valid position")
	void isInside_Valid() {
		position = new Position(0, 0);
		assertTrue(position.isInside(), "Position (0,0) should be valid");
	}

	@Test
	@DisplayName("isInside should return false for negative row")
	void isInside_NegativeRow() {
		position = new Position(-1, 0);
		assertFalse(position.isInside(), "Position with negative row should be invalid");
	}

	@Test
	@DisplayName("isInside should return false for negative column")
	void isInside_NegativeColumn() {
		position = new Position(0, -1);
		assertFalse(position.isInside(), "Position with negative column should be invalid");
	}

	@Test
	@DisplayName("isInside should return false when row exceeds board size")
	void isInside_RowExceedsBoardSize() {
		position = new Position(Game.BOARD_SIZE, 0);
		assertFalse(position.isInside(), "Position with row >= BOARD_SIZE should be invalid");
	}

	@Test
	@DisplayName("isInside should return false when column exceeds board size")
	void isInside_ColumnExceedsBoardSize() {
		position = new Position(0, Game.BOARD_SIZE);
		assertFalse(position.isInside(), "Position with column >= BOARD_SIZE should be invalid");
	}

	// ------------ isAdjacentTo -----------------

	@Test
	@DisplayName("isAdjacentTo should return true for adjacent positions")
	void isAdjacentTo_True() {
		Position other = new Position(3, 4);
		assertTrue(position.isAdjacentTo(other), "Failed to detect horizontally adjacent position");
	}

	@Test
	@DisplayName("isAdjacentTo should return false for non-adjacent positions")
	void isAdjacentTo_False() {
		Position other = new Position(5, 5);
		assertFalse(position.isAdjacentTo(other), "Failed to detect vertically adjacent position");
	}

	@Test
	void isAdjacentTo4() {
		Position other = new Position(4, 5);
		assertFalse(position.isAdjacentTo(other), "Non-adjacent position incorrectly identified as adjacent");
	}

	@Test
	void isAdjacentToWithNull() {
		assertThrows(NullPointerException.class, () -> position.isAdjacentTo(null),
				"isAdjacentTo should throw NullPointerException for null input");
	}

	// ----------------- adjacentPositions ------------

	@Test
	@DisplayName("adjacentPositions should return 8 positions for center")
	void adjacentPositions_center() {
		Position center = new Position(5, 5);
		List<IPosition> adj = center.adjacentPositions();

		assertEquals(8, adj.size(), "Center must have 8 neighbors");

		for (IPosition p : adj) {
			assertTrue(p.isInside(), "All adjacent positions must be inside");
		}
	}

	@Test
	@DisplayName("adjacentPositions should return fewer than 8 positions for corner")
	void adjacentPositions_corner() {
		Position corner = new Position(0, 0);
		List<IPosition> adj = corner.adjacentPositions();

		assertTrue(adj.size() < 8, "Corner must have fewer neighbors");
	}

	@Test
	@DisplayName("adjacentPositions should return between 3 and 5 positions for edge")
	void adjacentPositions_edge() {
		Position edge = new Position(0, 5);
		int size = edge.adjacentPositions().size();

		assertTrue(size >= 3 && size <= 5, "Edge must have 3–5 neighbors");
	}

	// ----------------- STATE ---------------------

	@Test
	@DisplayName("Occupy should mark position as occupied")
	void isOccupied() {
		assertFalse(position.isOccupied(), "New position should not be occupied");
		position.occupy();
		assertTrue(position.isOccupied(), "Position should be occupied after occupy()");
	}

	@Test
	@DisplayName("Shoot should mark position as hit")
	void isHit() {
		assertFalse(position.isHit(), "New position should not be hit");
		position.shoot();
		assertTrue(position.isHit(), "Position should be hit after shoot()");
	}

	// ------------ EQUALS ---------------

	@Test
	@DisplayName("Equals should return true when comparing the same instance")
	void equals_self() {
		assertTrue(position.equals(position), "Equal positions not identified as equal");
	}

	@Test
	@DisplayName("equals should return true for same coordinates ")
	void equals_sameCoordinates() {
		assertTrue(position.equals(new Position(2,3)), "Same coordinates must be equal");
	}

	@Test
	@DisplayName("equals should return false when comparing with null")
	void equals_null() {
		assertFalse(position.equals(null), "Null positions should not be equal");
	}

	@Test
	@DisplayName("equals should return false when comparing with non-Position object")
	void equals_differentObject() {
		Object other = new Object();
		assertFalse(position.equals(other), "Position should not equal non-Position object");
	}

	@Test
	@DisplayName("equals should return false for different coordinates")
	void equals_differentCoordinates() {
		Position other = new Position(2, 4);
		assertFalse(position.equals(other), "Positions with the same row but different column should not be equal");
	}

	// ------------ hashCode ----------------

	@Test
	@DisplayName(" hashCode should be equal for equal objects")
	void hashCodeConsistency() {
		Position same = new Position(2, 3);
		assertEquals(position.hashCode(), same.hashCode(),
				"Hash codes not consistent for equal positions");
	}

	// ------------ toString ------------------

	@Test
	@DisplayName("toString should return correct board notation")
	void toStringFormat() {
//		String expected = "Row = C, Column = 4";
		String expected = "C4";
		assertEquals(expected, position.toString(),
				"Incorrect string representation: expected '" + expected +
						"' but got '" + position.toString() + "'");
	}
}