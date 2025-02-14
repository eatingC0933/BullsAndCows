

public class Moo {

    private String goal;

    public Moo() {
        this.goal = makeGoal();
    }

    public Moo(String goal) {this.goal = goal;}

    public String getGoal() {
        return goal;
    }


    public String generateFeedback(String guess) {
        guess += "    ";
        int cows = 0, bulls = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (goal.charAt(i) == guess.charAt(j)) {
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }
        String result = "";
        for (int i = 0; i < bulls; i++) {
            result = result + "B";
        }
        result = result + ",";
        for (int i = 0; i < cows; i++) {
            result = result + "C";
        }
        return result;
    }

    private String makeGoal() {
        String goal = "";
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 10);
            String randomDigit = "" + random;
            while (goal.contains(randomDigit)) {
                random = (int) (Math.random() * 10);
                randomDigit = "" + random;
            }
            goal = goal + randomDigit;
        }
        return goal;
    }
}
