package puyopuyo.server.game;

import puyopuyo.client.panel.map.MapPanel;
import puyopuyo.server.ServerProcess;
import puyopuyo.server.game.round.PuyoS;
import puyopuyo.server.game.round.RoundThread;
import puyopuyo.client.panel.start.StartPanel;
import puyopuyo.client.frame.Frame;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class GameService {
    private static GameService instance;
    private final ArrayList<PuyoS[][]> puyoMaps = new ArrayList<>();
    private final ArrayList<PuyoS[]> lrPuyo = new ArrayList<>();

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
        System.out.println("[Server] makePuyoLogic");
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
        System.out.println("[Server] countThreeSecond");
        try {
            for(int i=3; i>0; i--) sleep(1000);
        } catch (InterruptedException _) {}
    }

    /**
     * 새로운 게임 엔티티를 만들고 진행하는 함수이다.
     */
    public void newGame() {
        System.out.println("[Server] newGame");
        //var roundCountLabel = MapService.getInstance().getScorePanel().getScoreService().getRoundCountLabel();
        game = new Game();
        var totalRound = 0;

        while(totalRound++ <= 3) {
            //roundCountLabel.setText(Integer.toString(totalRound));
            round();
            if(isEnd()) Frame.getInstance().changePanel(StartPanel.getInstance());
        }
    }

    /**
     * 게임 한 라운드에 대한 로직을 관리하는 함수
     */
    private void round() {
        System.out.println("[Server] round");
        var mapPanel = MapPanel.getInstance();

        makePuyoLogic();

        countThreeSecond(); // 3초 뒤 시작

        game.setRoundThread1P(new RoundThread(0)).start();
        game.setRoundThread2P(new RoundThread(1)).start();

        mapPanel.requestFocus();

        roundStart(); // 게임 진행중에는 true여야 함

        LocalTime start = LocalTime.now();

        while(game.isPlaying()) {
            try {
                sleep(100);
                
                // ScorePanel Timer에 시간초 출력
                ServerProcess.getInstance().toAllClient(5, String.valueOf(Duration.between(start, LocalTime.now()).toSeconds()));
            } catch (InterruptedException _) {}
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
        game.getRoundThread(player).getRoundService().plusGarbagePuyo(plusScore/70);
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

    public ArrayList<PuyoS[][]> getPuyoMaps() {
        if(puyoMaps.isEmpty()) {
            puyoMaps.add(getRoundThread(0).getRoundService().getPuyoMap());
            puyoMaps.add(getRoundThread(1).getRoundService().getPuyoMap());
        }
        return puyoMaps;
    }

    public ArrayList<PuyoS[]> getLRPuyo() {
        if(lrPuyo.isEmpty()) {
            PuyoS[] leftPuyo = {getRoundThread(0).getRoundService().getLeftPuyo(), getRoundThread(0).getRoundService().getRightPuyo()};
            PuyoS[] rightPuyo = {getRoundThread(1).getRoundService().getLeftPuyo(), getRoundThread(1).getRoundService().getRightPuyo()};
            lrPuyo.add(leftPuyo);
            lrPuyo.add(rightPuyo);
        }
        return lrPuyo;
    }
}
