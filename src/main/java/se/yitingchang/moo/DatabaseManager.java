ppackage se.yitingchang.moo;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;
    private Statement stmt;


    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost/moo", "root", "Et631224!");
        stmt = connection.createStatement();
    }
    //@Table= players
    public int getPlayerId(String name) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT playerId FROM players WHERE name = '" + name + "'");
        if (rs.next()) {
            return rs.getInt("playerId");
        } else {
            return -1;
        }
    }

    public void addPlayer(String name) throws SQLException {
        stmt.executeUpdate("INSERT INTO players (name) VALUES ('" + name + "')");
    }

    //@Table= results
    public void insertResult(int playerId, int result) throws SQLException {
        String query="INSERT INTO results (result, playerId) VALUES (?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, result);
            pstmt.setInt(2, playerId);
            pstmt.executeUpdate();
        }
    }

    public ResultSet getPlayers() throws SQLException {
        return stmt.executeQuery("SELECT * FROM players");
    }

    public ResultSet getResults(int playerId) throws SQLException {
        return stmt.executeQuery("SELECT * FROM results WHERE playerId = " + playerId);
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        stmt.close();
        connection.close();
    }


}
