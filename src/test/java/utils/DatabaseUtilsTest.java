package utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the DatabaseUtils class using H2 in-memory database.
 */
class DatabaseUtilsTest {
    // Using H2 in-memory database for testing
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private static DatabaseUtils dbUtils;

    @BeforeAll
    static void setUp() {
        // Initialize the database utility
        dbUtils = new DatabaseUtils(JDBC_URL, USERNAME, PASSWORD);

        // Create test tables
        dbUtils.execute(
                "CREATE TABLE venue (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "location VARCHAR(255) NOT NULL, " +
                        "capacity INT NOT NULL)");

        dbUtils.execute(
                "CREATE TABLE event (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "status VARCHAR(50) NOT NULL, " +
                        "venue_id INT, " +
                        "FOREIGN KEY (venue_id) REFERENCES venue(id))");
    }

    @BeforeEach
    void clearTables() {
        // Clear test data before each test
        dbUtils.execute("DELETE FROM event");
        dbUtils.execute("DELETE FROM venue");
    }

    @Test
    void testExecuteWithVarargs() {
        // Test execute method with varargs
        dbUtils.execute(
                "INSERT INTO venue (name, location, capacity) VALUES (?, ?, ?)",
                "Test Stadium", "Test City", 1000);

        // Verify the insertion
        Map<String, Object> venue = dbUtils.findOne(
                "SELECT * FROM venue WHERE name = ?",
                this::mapResultSetToMap,
                "Test Stadium");

        assertNotNull(venue);
        assertEquals("Test Stadium", venue.get("name"));
        assertEquals("Test City", venue.get("location"));
        assertEquals(1000, venue.get("capacity"));
    }

    @Test
    void testExecuteWithConsumer() {
        // Test execute method with consumer
        dbUtils.execute(
                "INSERT INTO venue (name, location, capacity) VALUES (?, ?, ?)",
                stmt -> {
                    try {
                        stmt.setString(1, "Consumer Stadium");
                        stmt.setString(2, "Consumer City");
                        stmt.setInt(3, 2000);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

        // Verify the insertion
        Map<String, Object> venue = dbUtils.findOne(
                "SELECT * FROM venue WHERE name = ?",
                this::mapResultSetToMap,
                "Consumer Stadium");

        assertNotNull(venue);
        assertEquals("Consumer Stadium", venue.get("name"));
        assertEquals("Consumer City", venue.get("location"));
        assertEquals(2000, venue.get("capacity"));
    }

    @Test
    void testFindOne() {
        // Insert test data
        dbUtils.execute(
                "INSERT INTO venue (name, location, capacity) VALUES (?, ?, ?)",
                "FindOne Stadium", "FindOne City", 3000);

        // Test findOne method
        Map<String, Object> venue = dbUtils.findOne(
                "SELECT * FROM venue WHERE name = ?",
                this::mapResultSetToMap,
                "FindOne Stadium");

        assertNotNull(venue);
        assertEquals("FindOne Stadium", venue.get("name"));
    }

    @Test
    void testFindOneNoResults() {
        // Test findOne with no results
        Map<String, Object> venue = dbUtils.findOne(
                "SELECT * FROM venue WHERE name = ?",
                this::mapResultSetToMap,
                "NonExistent Stadium");

        assertNull(venue);
    }

    @Test
    void testFindOneMultipleResultsException() {
        // Insert duplicate data
        dbUtils.execute(
                "INSERT INTO venue (name, location, capacity) VALUES (?, ?, ?)",
                "Duplicate Stadium", "City 1", 1000);
        dbUtils.execute(
                "INSERT INTO venue (name, location, capacity) VALUES (?, ?, ?)",
                "Duplicate Stadium", "City 2", 2000);

        // Test findOne with multiple results (should throw exception)
        assertThrows(RuntimeException.class, () -> {
            dbUtils.findOne(
                    "SELECT * FROM venue WHERE name = ?",
                    this::mapResultSetToMap,
                    "Duplicate Stadium");
        });
    }

    @Test
    void testFindMany() {
        // Insert test data
        dbUtils.execute(
                "INSERT INTO venue (name, location, capacity) VALUES (?, ?, ?)",
                "Stadium 1", "City 1", 1000);
        dbUtils.execute(
                "INSERT INTO venue (name, location, capacity) VALUES (?, ?, ?)",
                "Stadium 2", "City 2", 2000);

        // Test findMany method
        List<Map<String, Object>> venues = dbUtils.findMany(
                "SELECT * FROM venue ORDER BY name",
                this::mapResultSetToMap);

        assertEquals(2, venues.size());
        assertEquals("Stadium 1", venues.get(0).get("name"));
        assertEquals("Stadium 2", venues.get(1).get("name"));
    }

    @Test
    void testFindManyNoResults() {
        // Test findMany with no results
        List<Map<String, Object>> venues = dbUtils.findMany(
                "SELECT * FROM venue WHERE name = ?",
                this::mapResultSetToMap,
                "NonExistent Stadium");

        assertNotNull(venues);
        assertTrue(venues.isEmpty());
    }

    @Test
    void testParentChildRelationship() {
        // Insert venue (parent)
        dbUtils.execute(
                "INSERT INTO venue (name, location, capacity) VALUES (?, ?, ?)",
                "Parent Venue", "Parent City", 5000);

        Map<String, Object> venue = dbUtils.findOne(
                "SELECT * FROM venue WHERE name = ?",
                this::mapResultSetToMap,
                "Parent Venue");

        int venueId = ((Number) venue.get("id")).intValue();

        // Insert events (children)
        dbUtils.execute(
                "INSERT INTO event (name, status, venue_id) VALUES (?, ?, ?)",
                "Event 1", "REGISTRATION_OPEN", venueId);
        dbUtils.execute(
                "INSERT INTO event (name, status, venue_id) VALUES (?, ?, ?)",
                "Event 2", "PLANNING", venueId);

        // Fetch venue with its events
        List<Map<String, Object>> events = dbUtils.findMany(
                "SELECT e.* FROM event e WHERE e.venue_id = ?",
                this::mapResultSetToMap,
                venueId);

        assertEquals(2, events.size());

        // Add venue details to the result
        Map<String, Object> venueWithEvents = Map.of(
                "venue", venue,
                "events", events
        );

        assertNotNull(venueWithEvents.get("venue"));
        assertEquals(2, ((List<?>) venueWithEvents.get("events")).size());
        assertEquals("Parent Venue", ((Map<?, ?>) venueWithEvents.get("venue")).get("name"));
    }

    /**
     * Helper method to map ResultSet to Map
     */
    private Map<String, Object> mapResultSetToMap(ResultSet rs) {
        try {
            Map<String, Object> map = new java.util.HashMap<>();
            java.sql.ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = meta.getColumnName(i).toLowerCase();
                Object value = rs.getObject(i);
                map.put(columnName, value);
            }

            return map;
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping ResultSet to Map", e);
        }
    }
}