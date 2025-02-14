package se.yitingchang.moo;


import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class MooController {

    private final TextIO io;
    private final DatabaseManager dbManager;
    private Moo moo;

    public MooController(TextIO io,DatabaseManager dbManager) {
        this.io = io;
        this.dbManager = dbManager;
    }
    public void start() throws SQLException, InterruptedException {
        boolean answer = true;

        io.addString("Enter your user name:\n");
        String name = io.getString();
        int playerId = dbManager.getPlayerId(name);
        if (playerId == -1) {
            boolean addNewPlayer = io.yesNo("User not in database. Would you like to register as a new player?");
            if (addNewPlayer) {
                dbManager.addPlayer(name);
                playerId = dbManager.getPlayerId(name);
                io.addString("User registered successfully.\n");
            } else {
                io.addString("Please register with admin.");
                Thread.sleep(5000);
                io.exit();
            }
        }

        while (answer) {
            moo = new Moo();
            io.clear();
            io.addString("New game:\nGuess 4 digits\n " +
                    "B-Right digit and right position\n " +
                    "C-Right digit but wrong position\n"+
                    " Push 'A' to see the answer:\n");

            String guess = io.getString();
            if (guess.equalsIgnoreCase("A")) {
                io.addString("The answer is:"+moo.getGoal()+"\n");
                continue;
            }
            io.addString(guess + "\n");
            int nGuess = 1;
            String bbcc = moo.generateFeedback(guess);
            io.addString(bbcc + "\n");
            while (!bbcc.equals("BBBB,")) {
                nGuess++;
                guess = io.getString();
                if(guess.equalsIgnoreCase("A")){
                    io.addString("The answer is:"+moo.getGoal()+"\n");
                    continue;
                }
                io.addString(guess + ":\n");
                bbcc = moo.generateFeedback(guess);
                io.addString(bbcc + "\n");
            }
            dbManager.insertResult(playerId, nGuess);
            showTopTen();
            answer = io.yesNo("Correct, it took " + nGuess + " guesses\nContinue?");
        }
        dbManager.close();
        io.exit();
    }
        void showTopTen() throws SQLException {
            ArrayList<PlayerAverage> topList = new ArrayList<>();
            ResultSet rs = null;
            ResultSet rs2 = null;
            Statement stmt2 = null;

            try {
                rs = dbManager.getPlayers(); // Get all players

                while (rs.next()) {
                    int playerId = rs.getInt("playerId");
                    String name = rs.getString("name");

                    stmt2 = dbManager.getConnection().createStatement();
                    rs2 = stmt2.executeQuery("SELECT * FROM results WHERE playerId = " + playerId);

                    int nGames = 0;
                    int totalGuesses = 0;

                    while (rs2.next()) {
                        nGames++;
                        totalGuesses += rs2.getInt("result");
                    }

                    if (nGames > 0) {
                        topList.add(new PlayerAverage(name, (double) totalGuesses / nGames));
                    }
                }

                io.addString("Top Ten List\n    Player     Average\n");
                int pos = 1;
                topList.sort((p1, p2) -> Double.compare(p1.average, p2.average));

                for (PlayerAverage p : topList) {
                    io.addString(String.format(Locale.US,"%3d %-10s%5.2f%n", pos, p.name, p.average));
                    if (pos++ == 10) break;
                }

            } finally {
                if (rs != null) rs.close();
                if (rs2 != null) rs2.close();
                if (stmt2 != null) stmt2.close();
            }
        }

}


