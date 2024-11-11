package puyopuyo.Panel.map.game;

import puyopuyo.Panel.map.MapPanel;
import puyopuyo.Panel.map.subpanel.ground.round.RoundThread;
import puyopuyo.Panel.start.StartPanel;
import puyopuyo.frame.Frame;

import static java.lang.Thread.sleep;

public class GameService {
    private static GameService instance;

    public synchronized static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    /**
     * 게임 한판에 대한 정보를 가지는 엔티티이다.
     */
    private Game game;

    /**
     * 라운드마다 puyoLogic을 재설정하는 함수이다.
     */
    private void makePuyoLogic() {
        var puyoLogic = game.getPuyoLogic();

        int[][] puyoCase = new int[5][5];
        int firstPuyo;
        int secondPuyo;

        for (int i = 0; i < 25; i++) {
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

    /**
     * 게임 시작 전 3초 지연시키는 함수이다.
     */
    public void countThreeSecond() {
        try {
            for(int i=3; i>0; i--) sleep(1000);
        } catch (InterruptedException _) {}
    }

    /**
     * 새로운 게임 엔티티를 만들고 진행하는 함수이다.
     */
    public void newGame() {
        game = new Game();
        var totalRound = 3;

        while(totalRound-- != 0) {
            round();
            if(isEnd()) Frame.getInstance().changePanel(StartPanel.getInstance());
        }
    }

    /**
     * 게임 한 라운드에 대한 로직을 관리하는 함수
     */
    private void round() {
        var mapPanel = MapPanel.getInstance();

        makePuyoLogic();

        countThreeSecond(); // 3초 뒤 시작

        game.setRoundThread1P(new RoundThread(1)).start();
        game.setRoundThread2P(new RoundThread(2)).start();

        mapPanel.requestFocus();

        roundStart(); // 게임 진행중에는 true여야 함
        
        while(game.isPlaying()) {
            try {
                sleep(100);
            } catch (InterruptedException _) {}

            //TODO: 시간 측정 및 출력
        }
    }

    /**
     * 한 라운드가 종료되었는지 확인하는 함수
     */
    private boolean isEnd() {
        if(game.totalWin(1) == 2) {
            // TODO: 최종 승리 문구 출력
            return true;
        } else if(game.totalWin(2) == 2) {
            // TODO: 최종 승리 문구 출력
            return true;
        }
        return false;
    }

    /**
     * RoundThread에서 상대방의 Game.win 속성에 접근 가능하도록 지원하는 함수
     * ※ 전달하는 주체의 player 번호를 주입할 것
     */
    public void playerWin(int player) {
        game.plusWinCount(player);
    }

    /**
     * RoundThread에서 상대방의 Game.garbagePuyo 속성에 접근 가능하도록 지원하는 함수
     * ※ 전달하는 주체의 player 번호를 주입할 것
     */
    public void tossGarbagePuyo(int player, int plusScore) {
        game.getRoundThread(Game.otherPlayer(player)).getRoundService().setGarbagePuyo(plusScore/70);
    }

    /**
     * 라운드 시작
     */
    public void roundStart() {
        game.roundStart();
    }
    
    /**
     * 라운드 종료
     */
    public void roundEnd() {
        game.roundEnd();
    }

    // getter
    public RoundThread getRoundThread(int player) {
        return game.getRoundThread(player);
    }

    public int[] getPuyoLogic() {
        return game.getPuyoLogic();
    }
}
