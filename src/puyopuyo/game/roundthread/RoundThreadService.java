package puyopuyo.game.roundthread;

import puyopuyo.ScorePanel;
import puyopuyo.game.GameService;
import puyopuyo.gameground.GameGroundService;
import puyopuyo.gameground.playerthread.PlayerThread;

import static java.lang.Thread.sleep;

/**
 * 굳이 Repository까지 만들기에는 메모리 낭비가 심할 것 같아 Service에 엔티티 생성
 */
public class RoundThreadService {
    private final RoundThread roundThread;

    private final PlayerThread playerThread1P;
    private final PlayerThread playerThread2P;

    private int winCount1P = 0;
    private int winCount2P = 0;

    private int[] puyoLogic = new int[25];

    private boolean roundChangeToggle = false;

    public RoundThreadService(
            RoundThread roundThread,
            GameService gameService,
            GameGroundService gameGround1PService,
            GameGroundService gameGround2PService,
            ScorePanel scorePanel
    ) {
        this.roundThread = roundThread;
        playerThread1P = new PlayerThread(gameService, gameGround1PService, scorePanel,1);
        playerThread2P = new PlayerThread(gameService, gameGround2PService, scorePanel, 2);
    }

    public void start() {
        roundThread.start();
    }

    public void changeRoundChangeToggle() {
        if(roundChangeToggle)
            roundChangeToggle = false;
        else
            roundChangeToggle = true;
    }

    public PlayerThread getPlayerThread1P() {
        return playerThread1P;
    }

    public PlayerThread getPlayerThread2P() {
        return playerThread2P;
    }

    /** 1P 승리 횟수 카운트 */
    public void plusWinCount1P() {++winCount1P;}

    /** 2P 승리 횟수 카운트 */
    public void plusWinCount2P() {++winCount2P;}

    public int[] getPuyoLogic() { return puyoLogic; }

    /** winCount getter */
    public int getWinCount1P() {return winCount1P;}
    public int getWinCount2P() {return winCount2P;}

    /** roundChangeToggle getter */
    public boolean getRoundChangeToggle() {return roundChangeToggle;}

    /**
     * puyoLogic을 재설정하는 함수이다.
     */
    public void makePuyoLogic() {
        int[][] puyoCase = new int[5][5];
        int firstPuyo;
        int secondPuyo;
        for (int i = 0; i < 25; i++)
        {
            while (true) {
                firstPuyo = (int)(Math.random()*5);
                secondPuyo = (int)(Math.random()*5);
                if (puyoCase[firstPuyo][secondPuyo] == 0) {
                    puyoCase[firstPuyo][secondPuyo] = 1;
                    puyoLogic[i] = firstPuyo * 10 + secondPuyo;
                    break;
                }
            }
        }
    }

    public void countThreeSecond() {
        try {
            for(int i=3; i>0; i--) {
                sleep(1000);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
