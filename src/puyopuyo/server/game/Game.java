package puyopuyo.server.game;

import puyopuyo.server.game.round.RoundThread;

public class Game {
    private RoundThread roundThread1P;
    private RoundThread roundThread2P;

    public RoundThread getRoundThread(int player) {
        if(player == 1) return roundThread1P;
        else return roundThread2P;
    }

    public RoundThread setRoundThread1P(RoundThread roundThread1P) {
        this.roundThread1P = roundThread1P;
        return roundThread1P;
    }
    public RoundThread setRoundThread2P(RoundThread roundThread2P) {
        this.roundThread2P = roundThread2P;
        return roundThread2P;
    }

    private int win1P = 0;
    private int win2P = 0;

    private final int[] puyoLogic = new int[25];

    private boolean playing = false;
    public void roundStart() {
        playing = true;
    }
    public void roundEnd() {
        playing = false;
    }
    public boolean isPlaying() {
        return playing;
    }

    /**
     * 승리 횟수 카운트
     */
    public int plusWinCount(int player) {
        if(player == 1) return ++win1P;
        else return ++win2P;
    }

    public int[] getPuyoLogic() { return puyoLogic; }

    /** winCount getter */
    public int totalWin(int player) {
        if(player == 1) return win1P;
        else return win2P;
    }

    public static int otherPlayer(int player) {
        if(player == 1) return 2;
        else return 1;
    }
}
