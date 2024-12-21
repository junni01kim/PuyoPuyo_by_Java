package puyopuyo.server.game.round;

public class PuyoS {
    private int color;
    private int x;
    private int y;

    public PuyoS(int color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public void pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
