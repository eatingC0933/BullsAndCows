package se.yitingchang.moo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MooTest {

    @Test
    public void testMakeGoal() {
        Moo game = new Moo();
        String goal = game.getGoal();
        assertEquals(4, goal.length(), "Goal should be 4 digits long");
        for (char c : goal.toCharArray()) {
            assertTrue(Character.isDigit(c), "Goal should only contain digits");
        }
    }

    @Test
    public void testGenerateFeedback() {
        Moo game = new Moo("1234");
        String feedback = game.generateFeedback("1234");
        assertEquals("BBBB,", feedback, "Feedback should be 'BBBB,' for correct guess");

        feedback = game.generateFeedback("4321");
        assertEquals(",CCCC", feedback, "Feedback should be ',CCCC' for all correct digits in wrong positions");

        feedback = game.generateFeedback("5678");
        assertEquals(",", feedback, "Feedback should be ',,,,' for no correct digits");

        feedback = game.generateFeedback("1243");
        assertEquals("BB,CC", feedback, "Feedback should be 'BB,CC' for two correct digits in correct positions and two in wrong positions");
    }
}
