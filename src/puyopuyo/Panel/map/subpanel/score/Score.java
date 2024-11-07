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

    private int puyoRemovedSum;
    public void setPuyoRemovedSum(int i) {
        puyoRemovedSum = i;
    }
    public int getPuyoRemovedSum() {
        return puyoRemovedSum;
    }

    private int puyoConnect;
    public int setPuyoConnect(int i) {
        puyoConnect = i;
        return puyoConnect;
    }
    public int getPuyoConnect() {
        return puyoConnect;
    }

    private int puyoCombo;
    public void setPuyoCombo(int i) {
        puyoCombo = i;
    }
    public int getPuyoCombo() {
        return puyoCombo;
    }

    private int puyoColor;
    public void setPuyoColor(int i) {
        puyoColor = i;
    }
    public int getPuyoColor() {
        return puyoColor;
    }

    public int plusPuyoRemovedSum(int i) {
        puyoRemovedSum += i;
        return puyoRemovedSum;
    }
}
