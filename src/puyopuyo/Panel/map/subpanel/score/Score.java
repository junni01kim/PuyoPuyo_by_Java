package puyopuyo.Panel.map.subpanel.score;

public class Score {
    private final int player;

    public Score(int player) {
        this.player = player;
    }

    private int score = 0;

    public void plusScore(int plusScore) {
        score += plusScore;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
