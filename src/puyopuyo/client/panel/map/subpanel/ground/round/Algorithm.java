package puyopuyo.client.panel.map.subpanel.ground.round;

import puyopuyo.client.panel.map.MapService;
import puyopuyo.client.panel.map.subpanel.score.Score;
import puyopuyo.server.game.GameService;
import puyopuyo.client.panel.map.subpanel.ground.GroundPanel;
import puyopuyo.client.panel.map.subpanel.ground.GroundService;
import puyopuyo.client.panel.map.subpanel.ground.Puyo;

import static java.lang.Thread.sleep;
import static puyopuyo.resource.Constants.*;

public class Algorithm {
    private Round round;
    private final Score score;

    private final MapService mapService = MapService.getInstance();
    private final GameService gameService = GameService.getInstance();
    private final GroundPanel groundPanel;
    private final GroundService groundService;

    private final Puyo[][] puyoMap;

    // 폭발 계산 용 변수
    private final boolean[][] samePuyoChecker = new boolean[6][12];
    private final boolean[] colorBonusChecker = new boolean[Puyo.getPuyoIcon().length];
    private int numberOfSamePuyo = 0;

    public Algorithm(int player) {
        score = new Score(player);

        groundPanel = mapService.getGroundPanel(player);
        groundService = groundPanel.getGroundService();
        puyoMap = groundService.getPuyoMap();
    }

    /**
     * 뿌요가 바닥까지 왔는지 판단하는 함수
     *
     */
    public boolean isFix() {
        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        // 2. left 아래에 뿌요가 존재하는지 확인하는 로직
        if(leftPuyo.y() >= Y_MAX || puyoMap[leftPuyo.x()][leftPuyo.y()+MOVE] != null) {
            fixPuyo(leftPuyo); // 닿은 뿌요 배치
            dropPuyo(rightPuyo); // 남은 뿌요 하강
            return true;
        } // 3. right 아래에 뿌요가 존재하는지 확인하는 로직
        else if(rightPuyo.y() >= Y_MAX || puyoMap[rightPuyo.x()][rightPuyo.y()+MOVE] != null) {
            fixPuyo(rightPuyo); // 닿은 뿌요 배치
            dropPuyo(leftPuyo); // 남은 뿌요 하강
            return true;
        }

        return false;
    }

    private void fixPuyo(Puyo puyo) {
        // 1. 현재 뿌요를 가리는 함수 setVisible()
        puyo.setVisible(false);

        // 2. 현재 뿌요맵 위치에 새로운 객체를 생성
        puyoMap[(puyo.x())][(puyo.y())] = new Puyo(puyo.getColor(),puyo.x(),puyo.y());
        groundPanel.add(puyoMap[puyo.x()][puyo.y()]);
    }

    /**
     * 한 뿌요의 위치가 정해지고 다음 뿌요의 위치를 보여주는 함수
     */
    private void dropPuyo(Puyo puyo) {
        for(int y = Y_MAX; y >= Y_MIN; y--)
            if(puyoMap[puyo.x()][y]==null) {
                puyo.pos(puyo.x(),y);
                fixPuyo(puyo);
                break;
            }

        try {
            sleep(500);
        } catch (InterruptedException _) {}
    }

    /**
     * 뿌요 조작 후 삭제할 뿌요가 존재하는지 확인하는 함수
     *
     * 존재한다면 삭제될 색상을 detector에 모두 표시하고 true를 반환한다
     * 존재하지 않다면 false를 반환한다.
     */
    public void detect() {
        var player = round.getPlayer();
        var scoreService = mapService.getScorePanel().getScoreService();
        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        var puyoColor = 0;
        var puyoConnect = 0;
        var puyoCombo = 0;
        var puyoRemovedSum = 0;
        var plusScore = 0;

        leftPuyo.setVisible(false);
        rightPuyo.setVisible(false);

        while(true){
            boolean check = false;

            // 1. 동일한 뿌요가 있는지 탐색하는 함수
            for(int COLOR = 0; COLOR < COLOR_BONUS.length; COLOR++)
                for (int x = X_MIN; x <= X_MAX; x++) for (int y = Y_MIN; y <= Y_MAX; y++)
                    if (!samePuyoChecker[x][y] && puyoMap[x][y] != null && puyoMap[x][y].getColor() == COLOR) {
                        initVar(); // 탐색 전 초기화

                        // 2. 4가지 이상 존재하는 영역은 마킹을 한다.
                        checkPuyo(x, y); // 여기서 numberOfSamePuyo의 값이 확정된다.

                        if (numberOfSamePuyo >= 4) {
                            // [색수 보너스] 뿌요 폭발 조건에서 폭발된 새로운 색상을 기록
                            if(!colorBonusChecker[COLOR]) {
                                colorBonusChecker[COLOR]=true;
                                puyoColor++;
                            }
                            // [연결 보너스] 폭발되는 뿌요에 한번에 연결된 뿌요의 수를 기록
                            puyoConnect = numberOfSamePuyo;

                            // [없어진 뿌요 수] 총 제거된 뿌요의 수를 기록
                            puyoRemovedSum += numberOfSamePuyo;

                            // [연쇄 보너스] 연속적으로 폭발된 횟수를 기록
                            // check가 이미 true인 경우, 이미 puyoCombo가 증가한 상황이다.
                            if(!check) puyoCombo++;

                            // [득점 계산] 없어진 뿌요 수 x (연쇄 보너스 + 연결 보너스 + 색수 보너스) x 10
                            plusScore = puyoRemovedSum * (COMBO_BONUS[puyoCombo] + COLOR_BONUS[puyoColor] + CONNECT_BONUS[puyoConnect]) * 10;

                            // 득점 점수 합산 (전체 점수 Label에 작성)
                            score.setScore(plusScore);

                            scoreService.setScore(player, plusScore);

                            // TODO: 전달될 방해뿌요 수를 그림으로 출력
                            scoreService.setGarbagePuyoCount(player, plusScore/70);

                            deletePuyos(x, y);
                            check = true;
                        }
                    }
                    
            // 폭발되어 값이 수정되었다면 모든 뿌요를 드롭시킨다.
            if (check) dropPuyos();
            else break;
        }

        // 득점 점수로 바뀌었던 점수 기존 점수로 전환
        scoreService.setScore(player, score.getScore());

        // 추가된 총 점수의 /70 만큼 방해 뿌요 상대방에게 전달
        gameService.tossGarbagePuyo(round.getPlayer(), plusScore);
    }

    private void checkPuyo(int x, int y) {
        // 1. 터질 뿌요 체크 후 다음 드롭 뿌요 체크 진행

        // 예외처리: 탐색하려 했으나, 다른 뿌요의 detect() 등으로 이미 제거된 경우
        if(samePuyoChecker[x][y]) return;

        // 현재 탐색 중인 뿌요의 색상을 확인하기 위함
        var color = puyoMap[x][y].getColor();

        numberOfSamePuyo++;
        samePuyoChecker[x][y] = true;

        // 예외처리: puyoMap[][] 범위밖에서 Puyo를 호출한다.
        if (x <= X_MAX && y <= Y_MAX - 1 && puyoMap[x][y + 1] != null && puyoMap[x][y + 1].getColor() == color && !samePuyoChecker[x][y + 1]) checkPuyo(x, y + 1);
        if (x <= X_MAX && y >= Y_MIN + 1 && y <= Y_MAX && puyoMap[x][y - 1] != null && puyoMap[x][y - 1].getColor() == color && !samePuyoChecker[x][y - 1]) checkPuyo(x, y - 1);
        if (x <= X_MAX - 1 && y <= Y_MAX && puyoMap[x + 1][y] != null && puyoMap[x + 1][y].getColor() == color && !samePuyoChecker[x + 1][y]) checkPuyo(x + 1, y);
        if (x >= X_MIN + 1 && x <= X_MAX && y <= Y_MAX && puyoMap[x - 1][y] != null && puyoMap[x - 1][y].getColor() == color && !samePuyoChecker[x - 1][y]) checkPuyo(x - 1, y);
    }

    private void deletePuyos(int x, int y) {
        // 해당 뿌요 삭제
        samePuyoChecker[x][y] = false;

        groundPanel.remove(puyoMap[x][y]);
        groundPanel.repaint();
        puyoMap[x][y]=null;

        splashGarbagePuyo(x,y);

        // checkNumberOfSamePuyo()에서 포착된 뿌요들을 제거
        // 예외처리: puyoMap[][] 범위밖에서 Puyo를 호출한다.
        if (x <= X_MAX && y <= Y_MAX - 1 && puyoMap[x][y + 1] != null && samePuyoChecker[x][y + 1]) deletePuyos(x, y+1);
        if (x <= X_MAX && y >= Y_MIN + 1 && y <= Y_MAX && puyoMap[x][y - 1] != null && samePuyoChecker[x][y - 1]) deletePuyos(x, y-1);
        if (x <= X_MAX - 1 && y <= Y_MAX && puyoMap[x + 1][y] != null && samePuyoChecker[x + 1][y]) deletePuyos(x+1, y);
        if (x >= X_MIN + 1 && x <= X_MAX && y <= Y_MAX && puyoMap[x - 1][y] != null && samePuyoChecker[x - 1][y]) deletePuyos(x-1, y);
    }

    /**
     * deletePuyo() 이후 공중에 떠있는 블록들을 아래로 정렬한다.
     */
    private void dropPuyos() {
        for (int x = X_MIN; x <= X_MAX; x++) for (int y = Y_MAX; y >= Y_MIN; y--)
                if (puyoMap[x][y] == null)
                    // 배치되어야할 뿌요 위치
                    for (int q = y-1; q >= Y_MIN; q--)
                        if (puyoMap[x][q] != null) {
                            puyoMap[x][y] = puyoMap[x][q];
                            puyoMap[x][q] = null;
                            puyoMap[x][y].pos(x,y);
                            break;
                        }

        /*
         * 뿌요 폭발을 시각적으로 보여주기 위해서이다.
         */
        try {
            sleep(500);
        } catch (InterruptedException _) {}
    }

    void dropGarbagePuyo() {
        // 1. 상대 유저에게 방해뿌요 전달
        var garbagePuyo = round.getGarbagePuyo();

        // 방해뿌요 위치 분배
        int seperateGarbagePuyo = garbagePuyo / 6;
        for (int x = X_MIN; x <= X_MAX; x++)
            for (int y = Y_MAX; y >= Y_MIN; y--)
                if (puyoMap[x][y] == null) {
                    for (int q = 0; q < seperateGarbagePuyo; q++) {
                        if (y - q <= 2) continue;
                        puyoMap[x][y - q] = new Puyo(GARBAGE, x, y - q);

                        groundPanel.add(puyoMap[x][y - q]);
                    }
                    break;
                }

        int moduloGarbagePuyo = garbagePuyo % 6;

        while (moduloGarbagePuyo-- > 0) {
            int randomVariable = (int)(Math.random()*6);

            for (int j = 11; j >= 0; j--)
                if (puyoMap[randomVariable][j] == null) {
                    if(j<=1) continue;
                    puyoMap[randomVariable][j] = new Puyo(5, randomVariable, j);

                    groundPanel.add(puyoMap[randomVariable][j]);
                    break;
                }
        }
        groundPanel.repaint();

        round.setGarbagePuyo(0);
        var scoreService = MapService.getInstance().getScorePanel().getScoreService();
        scoreService.setGarbagePuyoCount(round.getPlayer(), round.getGarbagePuyo());
    }

    private void splashGarbagePuyo(int x, int y) {
        // 1. 주변에 방해뿌요를 탐색하고 삭제한다.
        if (x+1 <= X_MAX && puyoMap[x+1][y] != null && puyoMap[x+1][y].getColor() == GARBAGE) {
            groundPanel.remove(puyoMap[x+1][y]);
            groundPanel.repaint();
            puyoMap[x+1][y] = null;
        }
        if (x-1 >= X_MIN && puyoMap[x-1][y] != null && puyoMap[x-1][y].getColor() == GARBAGE) {
            groundPanel.remove(puyoMap[x-1][y]);
            groundPanel.repaint();
            puyoMap[x-1][y] = null;
        }
        if (y+1 <= Y_MAX && puyoMap[x][y+1] != null && puyoMap[x][y+1].getColor() == GARBAGE) {
            groundPanel.remove(puyoMap[x][y+1]);
            groundPanel.repaint();
            puyoMap[x][y+1] = null;
        }
        if (y-1 >= Y_MIN && puyoMap[x][y-1] != null && puyoMap[x][y-1].getColor() == GARBAGE) {
            groundPanel.remove(puyoMap[x][y-1]);
            groundPanel.repaint();
            puyoMap[x][y-1] = null;
        }
    }

    // CheckNumberOfSamePuyo와 deletePuyos에 필요한 변수들을 초기화 하는 함수
    private void initVar() {
        numberOfSamePuyo = 0;

        for(int x=0; x<samePuyoChecker.length; x++)
            for(int y=0; y<samePuyoChecker[x].length; y++)
                samePuyoChecker[x][y] = false;
    }

    public void setRound(Round round) {
        this.round = round;
    }
}
