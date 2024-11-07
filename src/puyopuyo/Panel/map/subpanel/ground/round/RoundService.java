package puyopuyo.Panel.map.subpanel.ground.round;

import puyopuyo.Panel.map.MapService;
import puyopuyo.Panel.map.game.Game;
import puyopuyo.Panel.map.game.GameService;
import puyopuyo.Panel.map.subpanel.ground.GroundPanel;
import puyopuyo.Panel.map.subpanel.ground.GroundService;
import puyopuyo.Panel.map.subpanel.ground.Puyo;

import static java.lang.Thread.sleep;
import static puyopuyo.resource.Constants.*;

public class RoundService {
    private final int player;
    private Round round;

    private final MapService mapService = MapService.getInstance();
    private final GameService gameService = GameService.getInstance();
    private final GroundPanel groundPanel;
    private final GroundService groundService;

    private final Algorithm algorithm;

    public RoundService(int player) {
        this.player = player;
        groundPanel = mapService.getGroundPanel(player);
        groundService = groundPanel.getGroundService();
        algorithm = new Algorithm(player);
    }

    /**
     * 라운드 종료 후 모든 roundRepository를 정리한다.
     */
    public void clearRound() {
        var puyoMap = groundService.getPuyoMap();

        groundService.getLeftPuyo().setVisible(false);
        groundService.getRightPuyo().setVisible(false);

        for(int i=0;i<puyoMap.length;i++)
            for(int j=0;j<puyoMap[i].length;j++)
                if(puyoMap[i][j]!=null) {
                    groundPanel.remove(puyoMap[i][j]);
                    puyoMap[i][j] = null;
                }
    }

    /**
     * 새로운 라운드를 시작하는 함수
     */
    public void newRound() {
        round = new Round(player);
        nextPuyo();
        
        // 게임이 끝났는지 판단
        while (true) {

            // 뿌요 중 하나가 바닥에 닿은 경우
            if (round.isWin()) {
                // TODO: 승리 모션
                break;
            }
            else if (isLose()) {
                // TODO: 패배 모션
                break;
            }
            else if (algorithm.isFix()) {
                algorithm.detect();
                nextPuyo();
            }
            // 뿌요 중 하나가 바닥에 닿지 않은 경우
            else dropPuyo();

            try {
                sleep(500);
            } catch (InterruptedException _) {
            }
        }

        clearRound();
        groundPanel.repaint();
    }

    /**
     * 패배를 판단하는 함수
     * @return
     */
    public boolean isLose() {
        var puyoMap = groundService.getPuyoMap();

        // 1. 이전에 배치된 뿌요가 뿌요 생성 지점에 이미 존재하는지 확인
        if(puyoMap[LEFT_PUYO_SPAWN_X][LEFT_PUYO_SPAWN_Y] == null || puyoMap[RIGHT_PUYO_SPAWN_X][RIGHT_PUYO_SPAWN_Y] == null) return false;

        // 상대팀이 승리했음을 알림
        gameService.getRoundThread(Game.otherPlayer(player)).getRoundService().win();
        return true;
    }

    /**
     * 뿌요를 한칸 밑으로 내린다.
     */
    public void dropPuyo() {
        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        // 뿌요 중 하나가 바닥에 닿은 경우
        if(algorithm.isFix()) {
            algorithm.detect();
            nextPuyo();
        }

        // Puyo를 한 블록(+60 픽셀)만큼 내린다.
        leftPuyo.pos(leftPuyo.x(), leftPuyo.y()+MOVE);
        rightPuyo.pos(rightPuyo.x(), rightPuyo.y()+MOVE);
    }

    /**
     * 다음 뿌요로 전환시켜주는 함수이다.
     */
    void nextPuyo() {
        var scoreService = mapService.getScorePanel().getScoreService();
        var puyoLogic = gameService.getPuyoLogic();
        var puyoIndex = round.getPuyoIndex();
        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        int puyo1Color = (puyoLogic[puyoIndex%puyoLogic.length]) / 10;
        int puyo2Color = (puyoLogic[(puyoIndex++)%puyoLogic.length]) % 10;

        round.setPuyoIndex(puyoIndex);

        leftPuyo.setColor(puyo1Color);
        rightPuyo.setColor(puyo2Color);

        // type에 맞는 아이콘을 사용한다.
        leftPuyo.setIcon(Puyo.getPuyoIcon()[puyo1Color]);
        rightPuyo.setIcon(Puyo.getPuyoIcon()[puyo2Color]);

        leftPuyo.pos(2,0);
        rightPuyo.pos(3,0);

        leftPuyo.setVisible(true);
        rightPuyo.setVisible(true);

        scoreService.changeNextPuyo(round.getPlayer(), puyoLogic, puyoIndex);
    }

    /**
     * 뿌요 폭발 후 가산에 이용했던 점수 변수 초기화
     */
    void initializeScoreVariable() {
//        var colorChecker = round.getColorChecker();
//
//        for(int i=0;i<Puyo.getPuyoIcon().length;i++)
//            colorChecker[i]=false;
//
//        round.setPuyoRemovedSum(0);
//        round.setPuyoConnect(0);
//        round.setPuyoCombo(0);
//        round.setPuyoColor(0);
    }

    /**
     * 스코어 패널에 점수 표시
     */
    void printScore() {
        var player = round.getPlayer();

        var scoreService = mapService.getScorePanel().getScoreService();

        var score = round.getScore();

        if(player == 1)
            scoreService.getScoreLabel(1).setText(Integer.toString(score));
        else
            scoreService.getScoreLabel(2).setText(Integer.toString(score));
    }

    /**
     * 3판 2선승제 기준 승리 여부를 판단하는 함수
     */
    public void changeOneWin() {
        round.changeOneWin();
    }

    /**
     * 방해 뿌요 개수를 설정하는 함수
     */
    public void setGarbagePuyo(int numberOfGarbagePuyo) {
        round.setGarbagePuyo(numberOfGarbagePuyo);
    }

    public void win() {
        round.win(true);
    }
}
