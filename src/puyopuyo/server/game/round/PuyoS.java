package puyopuyo.server.game.round;

/**
 * 서버에서 사용하는 Puyo 클래스
 * 
 * 클라이언트의 Puyo 클래스는 JLabel로써, Gson으로 직렬화할 수 없기 때문에, 단순화 한 클래스
 * 
 * TODO: Puyo가 이걸 상속받게 만들어도 괜찮을듯 
 */
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
