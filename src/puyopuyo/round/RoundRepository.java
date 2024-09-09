package puyopuyo.round;

public class RoundRepository {
    private final RoundThread roundThread;

    public RoundRepository(RoundThread roundThread) {
        this.roundThread = roundThread;
    }

    public RoundThread getRoundThread() {return roundThread;}

    private int winCount1P = 0;
    private int winCount2P = 0;

    private int[] puyoLogic = new int[25];

    private boolean roundChangeToggle = false;

    /** 1P 승리 횟수 카운트 */
    public void plusWinCount1P() {++winCount1P;}

    /** 2P 승리 횟수 카운트 */
    public void plusWinCount2P() {++winCount2P;}

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
