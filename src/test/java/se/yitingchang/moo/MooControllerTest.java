package se.yitingchang.moo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.mockito.Mockito.*;
import java.util.Locale;


public class MooControllerTest {
    private TextIO mockIO;
    private DatabaseManager mockDbManager;
    private MooController controller;

    @BeforeEach
    public void setUp() throws SQLException {
        mockIO = Mockito.mock(TextIO.class);
        mockDbManager = Mockito.mock(DatabaseManager.class);
        controller = new MooController(mockIO, mockDbManager);
    }

    @Test
    public void testStart_NewUser_RegistersAndPlays() throws SQLException, InterruptedException {
        when(mockIO.getString()).thenReturn("testUser", "1234", "5678", "1234");
        when(mockDbManager.getPlayerId("testUser")).thenReturn(-1, 1);
        when(mockIO.yesNo(anyString())).thenReturn(true, false);

        controller.start();

        verify(mockDbManager).addPlayer("testUser");
        verify(mockDbManager, times(2)).getPlayerId("testUser");
        verify(mockIO).addString("User registered successfully.\n");
        verify(mockDbManager).insertResult(1, 1);
        verify(mockDbManager).close();
        verify(mockIO).exit();
    }

    @Test
    public void testStart_ExistingUser_PlaysGame() throws SQLException, InterruptedException {
        when(mockIO.getString()).thenReturn("testUser", "1234", "5678", "1234");
        when(mockDbManager.getPlayerId("testUser")).thenReturn(1);
        when(mockIO.yesNo(anyString())).thenReturn(false);

        controller.start();

        verify(mockDbManager).getPlayerId("testUser");
        verify(mockDbManager).insertResult(1, 1);
        verify(mockDbManager).close();
        verify(mockIO).exit();
    }
    @Test
    public void testShowTopTen() throws SQLException {
        // Mocking player results
        ResultSet mockPlayers = Mockito.mock(ResultSet.class);
        ResultSet mockResults = Mockito.mock(ResultSet.class);

        when(mockDbManager.getPlayers()).thenReturn(mockPlayers);
        when(mockPlayers.next()).thenReturn(true, false);
        when(mockPlayers.getInt("playerId")).thenReturn(1);
        when(mockPlayers.getString("name")).thenReturn("testUser");
        when(mockDbManager.getConnection()).thenReturn(Mockito.mock(java.sql.Connection.class));
        when(mockDbManager.getConnection().createStatement()).thenReturn(Mockito.mock(java.sql.Statement.class));
        when(mockDbManager.getConnection().createStatement().executeQuery(anyString())).thenReturn(mockResults);
        when(mockResults.next()).thenReturn(true, false);
        when(mockResults.getInt("result")).thenReturn(5);

        controller.showTopTen();

        verify(mockIO).addString("Top Ten List\n    Player     Average\n");
        verify(mockIO).addString(String.format(Locale.US, "%3d %-10s%5.2f%n", 1, "testUser", 5.00));
    }

}
