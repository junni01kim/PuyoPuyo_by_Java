package puyopuyo.Panel.map.subpanel.ground.round;

import puyopuyo.Panel.map.MapService;
import puyopuyo.Panel.map.subpanel.ground.GroundPanel;
import puyopuyo.Panel.map.subpanel.ground.GroundService;
import puyopuyo.Panel.map.subpanel.ground.Puyo;

import static java.lang.Thread.sleep;
import static puyopuyo.resource.Constants.*;

public class Algorithm {
    private final Round2 round;

    private final MapService mapService = MapService.getInstance();
    private final GroundPanel groundPanel;
    private final GroundService groundService;

    private final Puyo[][] puyoMap;

    // 폭발 계산 용 변수
    private final boolean[] colorChecker = new boolean[Puyo.getPuyoIcon().length];
    private final boolean[][] samePuyoChecker = new boolean[6][12];
    private int numberOfSamePuyo = 0;

    public Algorithm(int player) {
        round = new Round2(player);
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
            dropGarbagePuyo();
            return true;
        } // 3. right 아래에 뿌요가 존재하는지 확인하는 로직
        else if(rightPuyo.y() >= Y_MAX || puyoMap[rightPuyo.x()][rightPuyo.y()+MOVE] != null) {
            fixPuyo(rightPuyo); // 닿은 뿌요 배치
            dropPuyo(leftPuyo); // 남은 뿌요 하강
            dropGarbagePuyo();
            return true;
        }

        return false;
    }

    private void fixPuyo(Puyo puyo) {
        // 1. 현재 뿌요를 가리는 함수 setVisible()
        puyo.setVisible(false);

        // TODO: 같이 생성할 방해 뿌요가 존재하는지 확인한다.

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

        puyo.setVisible(false);

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
        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        boolean check;

        leftPuyo.setVisible(false);
        rightPuyo.setVisible(false);

        while(true){
            check = false;

            // 1. 동일한 뿌요가 있는지 탐색하는 함수
            for(int COLOR = 0; COLOR < COLOR_BONUS.length; COLOR++)
                for (int x = X_MIN; x <= X_MAX; x++) for (int y = Y_MIN; y <= Y_MAX; y++)
                    if (!samePuyoChecker[x][y] && puyoMap[x][y] != null && puyoMap[x][y].getColor() == COLOR) {
                        initVar(); // 탐색 전 초기화

                        // 2. 4가지 이상 존재하는 영역은 마킹을 한다.
                        checkPuyo(x, y); // 여기서 numberOfSamePuyo의 값이 확정된다.
                        //System.out.println(numberOfSamePuyo);

                        if (numberOfSamePuyo >= 4) {
                            deletePuyos(x, y);
                            check = true;
                        }
                    }


            // 폭발되어 값이 수정되었다면 모든 뿌요를 드롭시킨다.
            if (check) dropPuyos();
            else break;
        }
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
        samePuyoChecker[x][y] = false;

        // 현재 삭제 중인 뿌요의 색상을 확인하기 위함
        var color = puyoMap[x][y].getColor();

        samePuyoChecker[x][y] = false;

        // 해당 뿌요 삭제
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
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void dropGarbagePuyo() {
        // 1. 상대 유저에게 방해뿌요 전달
        var garbagePuyo = round.getGarbagePuyo();

        // 방해뿌요 위치 분배
        int seperateGarbagePuyo = garbagePuyo / 6;
        for (int x = 0; x < 6; x++) for (int y = 11; y >= 0; y--)
            if (puyoMap[x][y] == null)
                for (int q = 0; q < seperateGarbagePuyo; q++) {
                    if (y - q <= 2) continue;
                    puyoMap[x][y - q] = new Puyo(GARBAGE, x, y-q);
                    groundPanel.add(puyoMap[x][y - q]);
                }

        int moduloGarbagePuyo = garbagePuyo % 6;

        while (moduloGarbagePuyo-- > 0) {
            int randomVariable = (int)(Math.random()*6);

            for (int j = 11; j >= 0; j--)
                if (puyoMap[randomVariable][j] == null) {
                    if(j<=2) continue;
                    puyoMap[randomVariable][j] = new Puyo(5, randomVariable, j);
                    groundPanel.add(puyoMap[randomVariable][j]);
                    break;
                }
        }
        round.setGarbagePuyo(0);

        // TODO: 방해 뿌요 표시를 0으로 변경
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
}
