package puyopuyo.game;

import puyopuyo.round.RoundThread;

public class GameRepository {
    private final GameThread gameThread;
    private RoundThread roundThread1P;
    private RoundThread roundThread2P;

    public GameRepository(GameThread gameThread) {
        this.gameThread = gameThread;
    }

    public GameThread getGameThread() {return gameThread;}
    public RoundThread getRoundThread1P() {return roundThread1P;}
    public RoundThread getRoundThread2P() {return roundThread2P;}

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

    /** 1P 승리 횟수 카운트 */
    public void plusWinCount1P() {++winCount1P;}

    /** 2P 승리 횟수 카운트 */
    public void plusWinCount2P() {++winCount2P;}

    // TODO: 이름 바꿀 것 setStart()
    public boolean changeRoundChangeToggle() {
        roundChangeToggle = !roundChangeToggle;
        return roundChangeToggle;
    }

    public int[] getPuyoLogic() { return puyoLogic; }

    /** winCount getter */
    public int getWinCount1P() {return winCount1P;}
    public int getWinCount2P() {return winCount2P;}

    /** roundChangeToggle getter */
    public boolean getRoundChangeToggle() {return roundChangeToggle;}
}
