package puyopuyo.Panel.map.subpanel.score;

public class Score {
    private final int player;

    public Score(int player) {
        this.player = player;
    }

    private int score = 0;

    public int plusScore(int i) {
        score += i;
        return score;
    }

    public void setScore() {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
