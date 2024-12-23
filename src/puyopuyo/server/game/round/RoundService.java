package puyopuyo.server.game.round;

import puyopuyo.server.ServerProcess;
import puyopuyo.server.game.Game;
import puyopuyo.server.game.GameService;

import static java.lang.Thread.sleep;
import static puyopuyo.resource.Constants.*;

public class RoundService {
    /** @property 해당 객체가 관리하고 있는 플레이어 번호 */
    private final int player;
    /** @property RoundService가 관리하는 라운드 정보 */
    private Round round;

    /** @property 클래스가 소속된 GameService 객체 */
    private final GameService gameService = GameService.getInstance();

    /** @property 라운드 진행에 필요한 알고리즘이 들어있는 클래스 */
    private final Algorithm algorithm;
    /** @property 뿌요뿌요가 진행될 게임 보드 */
    private final PuyoS[][] puyoMap = new PuyoS[6][12];

    /** @property 클라이언트가 조작하는 왼쪽 뿌요 */
    private final PuyoS leftPuyo = new PuyoS(GARBAGE, 2 ,0);
    /** @property 클라이언트가 조작하는 오른쪽 뿌요 */
    private final PuyoS rightPuyo = new PuyoS(GARBAGE, 3, 0);

    /** @property 다음 뿌요 색상(타입) [좌, 우] */
    private final int[] nextPuyo = {5, 5};

    public RoundService(int player) {
        this.player = player;
        algorithm = new Algorithm(this);
    }

    /**
     * 라운드 종료 후 모든 roundRepository를 정리한다.
     */
    public void initRound() {

        for(int i=0;i<puyoMap.length;i++)
            for(int j=0;j<puyoMap[i].length;j++)
                if(puyoMap[i][j] != null) {
                    puyoMap[i][j] = null;
                }
    }

    /**
     * 새로운 라운드를 시작하는 함수
     */
    public void newRound() {
        System.out.println("New Round");
        round = new Round(player);
        algorithm.setRound(round);

        initRound();

        nextPuyo();
        
        // 게임이 끝났는지 판단
        while (true) {
            // 뿌요 중 하나가 바닥에 닿은 경우
            if (isWin()) {
                // TODO: 승리 모션
                System.out.println("Game Over You Won!");
                ServerProcess.getInstance().toAllClient(10, "The End");
                // TODO: 라운드 로직 추가할 것
//                ServerProcess.getInstance().toAllClient(8+ round.getPlayer(), String.valueOf(round.getPlayer()));
                break;
            }
            else if (isLose()) {
                // TODO: 패배 모션
                System.out.println("Game Over You Lose!");
                try {
                    sleep(3);
                    ServerProcess.getInstance().toAllClient(10, "The End");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // TODO: 라운드 로직 추가할 것
                break;
            }
            else if (algorithm.isFix()) {
                ServerProcess.getInstance().toAllClient(3+ round.getPlayer(), ServerProcess.getInstance().getGson().toJson(GameService.getInstance().getPuyoMaps()));
                algorithm.detect();
                algorithm.dropGarbagePuyo();
                nextPuyo();
            }
            // 뿌요 중 하나가 바닥에 닿지 않은 경우
            else dropPuyo();

            ServerProcess.getInstance().toAllClient(player, ServerProcess.getInstance().getGson().toJson(gameService.getPuyoMaps()));

            try {
                sleep(500);
            } catch (InterruptedException _) {}
        }
    }

    /**
     * 승리를 판단하는 함수
     * 
     * @return
     */
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
        // 뿌요 중 하나가 바닥에 닿은 경우
        if(algorithm.isFix()) {
            algorithm.detect();
            nextPuyo();
        }

        // Puyo를 한 블록(+60 픽셀)만큼 내린다.
        leftPuyo.pos(leftPuyo.x(), leftPuyo.y()+MOVE);
        rightPuyo.pos(rightPuyo.x(), rightPuyo.y()+MOVE);

        ServerProcess.getInstance().toAllClient(2, ServerProcess.getInstance().getGson().toJson(gameService.getLRPuyo()));
    }

    public void downPuyo() {
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
        var puyoLogic = gameService.getPuyoLogic();
        var puyoIndex = round.getPuyoIndex();

        int puyo1Color = (puyoLogic[puyoIndex%puyoLogic.length]) / 10;
        int puyo2Color = (puyoLogic[(puyoIndex++)%puyoLogic.length]) % 10;

        round.setPuyoIndex(puyoIndex);

        leftPuyo.setColor(puyo1Color);
        rightPuyo.setColor(puyo2Color);

        leftPuyo.pos(2,0);
        rightPuyo.pos(3,0);

        changeNextPuyo();
    }

    /**
     * 다음 뿌요를 계산하여 클라이언트에게 해당 정보를 전달하는 함수
     */
    private void changeNextPuyo() {
        var puyoLogic = gameService.getPuyoLogic();
        var puyoIndex = round.getPuyoIndex();

        nextPuyo[0] = (puyoLogic[(puyoIndex)%puyoLogic.length])/10;
        nextPuyo[1] = (puyoLogic[(puyoIndex)%puyoLogic.length])%10;

        ServerProcess.getInstance().toAllClient(6+player, ServerProcess.getInstance().getGson().toJson(nextPuyo));
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

    public PuyoS[][] getPuyoMap() {
        return puyoMap;
    }

    public PuyoS getLeftPuyo() {
        return leftPuyo;
    }

    public PuyoS getRightPuyo() {
        return rightPuyo;
    }
}
