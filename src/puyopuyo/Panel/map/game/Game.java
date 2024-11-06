package puyopuyo.Panel.map.game;

import puyopuyo.Panel.map.subpanel.ground.round.RoundThread;

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

    private int winCount1P = 0;
    private int winCount2P = 0;

    private final int[] puyoLogic = new int[25];

    // TODO: 이름 바꿀 것 start
    private boolean roundChangeToggle = false;

    /**
     * 승리 횟수 카운트
     */
    public int plusWinCount(int player) {
        if(player == 1) return ++winCount1P;
        else return ++winCount2P;
    }

    // TODO: 이름 바꿀 것 setStart()
    public boolean changeRoundChangeToggle() {
        roundChangeToggle = !roundChangeToggle;
        return roundChangeToggle;
    }

    public int[] getPuyoLogic() { return puyoLogic; }

    /** winCount getter */
    public int getWinCount(int player) {
        if(player == 1) return winCount1P;
        else return winCount2P;
    }

    /** roundChangeToggle getter */
    public boolean getRoundChangeToggle() {return roundChangeToggle;}

    public static int otherPlayer(int player) {
        if(player == 1) return 2;
        else return 1;
    }
}
