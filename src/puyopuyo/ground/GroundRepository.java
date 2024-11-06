package puyopuyo.ground;

import puyopuyo.puyo.Puyo;

public class GroundRepository {
    private final Puyo leftPuyo = new Puyo(0);
    private final Puyo rightPuyo = new Puyo(0);

    private Puyo[][] puyoMap = new Puyo[6][12];

    /**
     * setter
     *
     */
    public Puyo setLeftPuyo(int x, int y) {
        leftPuyo.setLocation(x, y);
        return leftPuyo;
    }

    public Puyo setRightPuyo(int x, int y) {
        rightPuyo.setLocation(x, y);
        return rightPuyo;
    }

    public Puyo[][] setPuyo(int indexX, int indexY, Puyo puyo) {
        puyoMap[indexX][indexY] = puyo;
        return puyoMap;
    }

    public Puyo[][] setPuyoMap(Puyo[][] puyoMap) {
        this.puyoMap = puyoMap;
        return puyoMap;
    }

    /**
     * getter
     *
     */
    public Puyo getLeftPuyo() {
        return leftPuyo;
    }

    public Puyo getRightPuyo() {
        return rightPuyo;
    }

    public Puyo[][] getPuyoMap() {
        return puyoMap;
    }
}
