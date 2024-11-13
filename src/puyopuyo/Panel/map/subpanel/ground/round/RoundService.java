package puyopuyo.Panel.map.subpanel.ground.round;

import puyopuyo.Panel.map.MapService;
import puyopuyo.Panel.map.game.Game;
import puyopuyo.Panel.map.game.GameService;
import puyopuyo.Panel.map.subpanel.ground.GroundPanel;
import puyopuyo.Panel.map.subpanel.ground.GroundService;
import puyopuyo.Panel.map.subpanel.ground.Puyo;
import puyopuyo.Panel.map.subpanel.score.ScorePanel;

import static java.lang.Thread.sleep;
import static puyopuyo.resource.Constants.*;

public class RoundService {
    private ScorePanel scorePanel = new ScorePanel();
    private final int player;
    private Round round;

    private final MapService mapService = MapService.getInstance();
    private final GameService gameService = GameService.getInstance();
    private final GroundPanel groundPanel;
    private final GroundService groundService;

    private final Algorithm algorithm;
    private final Puyo[][] puyoMap;

    public RoundService(int player) {
        this.player = player;
        groundPanel = mapService.getGroundPanel(player);
        groundService = groundPanel.getGroundService();
        puyoMap = groundService.getPuyoMap();
        algorithm = new Algorithm(player);
        this.scorePanel = scorePanel;
    }

    /**
     * 라운드 종료 후 모든 roundRepository를 정리한다.
     */
    public void clearRound() {
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
        algorithm.setRound(round);

        scorePanel.advanceToNextRound();
        nextPuyo();
        
        // 게임이 끝났는지 판단
        while (true) {

            // 뿌요 중 하나가 바닥에 닿은 경우
            if (isWin()) {
                // TODO: 승리 모션
                break;
            }
            else if (isLose()) {
                // TODO: 패배 모션
                break;
            }
            else if (algorithm.isFix()) {
                algorithm.detect();
                algorithm.dropGarbagePuyo();
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

    public boolean isWin() {
        if(!round.isWin()) return false;

        // 자신의 승리 카운트를 올림
        gameService.playerWin(player);
        return true;
    }

    /**
     * 패배를 판단하는 함수
     * @return
     */
    public boolean isLose() {
        // 1. 이전에 배치된 뿌요가 뿌요 생성 지점에 이미 존재하는지 확인
        if(puyoMap[LEFT_PUYO_SPAWN_X][LEFT_PUYO_SPAWN_Y] == null && puyoMap[RIGHT_PUYO_SPAWN_X][RIGHT_PUYO_SPAWN_Y] == null) return false;

        // 상대팀이 승리했음을 알림
        gameService.getRoundThread(Game.otherPlayer(player)).getRoundService().win();

        // GameThread에게 게임 종료를 알림
        gameService.roundEnd();
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

    public void downPuyo() {
        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        // 뿌요 중 하나가 바닥에 닿은 경우
        if(leftPuyo.y() >= Y_MAX || puyoMap[leftPuyo.x()][leftPuyo.y()+MOVE] != null
                || rightPuyo.y() >= Y_MAX || puyoMap[rightPuyo.x()][rightPuyo.y()+MOVE] != null) {
            return;
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

    }

    /**
     * 스코어 패널에 점수 표시
     */
    void printScore() {

    }

    /**
     * 방해 뿌요 개수를 설정하는 함수
     */
    public void plusGarbagePuyo(int numberOfGarbagePuyo) {
        round.plusGarbagePuyo(numberOfGarbagePuyo);
    }

    public void win() {
        round.win(true);
    }
}
