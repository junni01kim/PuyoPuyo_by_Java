package puyopuyo.client.panel.map.subpanel.ground.round;

public class Round {
    public Round(int player) {
        this.player = player;
    }

    private final int player;
    public int getPlayer() { return player; }

    private boolean win = false;
    public void win(boolean state) {
        win = state;
    }
    public boolean isWin() {
        return win;
    }

    // PuyoLogic 번호 사용한다.
    private int puyoIndex = 0;
    public void setPuyoIndex(int i) {
        puyoIndex = i;
    }
    public int getPuyoIndex() {
        return puyoIndex;
    }

    private int garbagePuyo = 0;
    public void plusGarbagePuyo(int garbagePuyo) {
        this.garbagePuyo += garbagePuyo;
    }
    public void setGarbagePuyo(int garbagePuyo) {
        this.garbagePuyo = garbagePuyo;
    }
    public int getGarbagePuyo() {
        return garbagePuyo;
    }
}
