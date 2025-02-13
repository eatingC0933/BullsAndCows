package se.yitingchang.moo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseManagerTest {
    private DatabaseManager dbManager;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost/moo", "root", "Et631224!");
        dbManager = new DatabaseManager("jdbc:mysql://localhost/moo", "root", "Et631224!");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.close();

    }

    @Test
    public void testGetPlayerId() throws SQLException {
        dbManager.addPlayer("testUser");
        int playerId = dbManager.getPlayerId("testUser");
        assertTrue(playerId > 0, "Player ID should be greater than 0");
    }

    @Test
    public void testInsertResult() throws SQLException {
        dbManager.addPlayer("testUser");
        int playerId = dbManager.getPlayerId("testUser");
        dbManager.insertResult(playerId, 5);

        ResultSet rs = dbManager.getResults(playerId);
        assertTrue(rs.next(), "ResultSet should have at least one result");
        assertEquals(5, rs.getInt("result"), "Result should be 5");
    }

}
