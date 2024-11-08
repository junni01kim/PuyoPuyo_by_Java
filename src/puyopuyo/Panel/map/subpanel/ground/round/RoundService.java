package puyopuyo.Panel.map.subpanel.ground.round;

import puyopuyo.Panel.map.game.Game;
import puyopuyo.Panel.map.game.GameService;
import puyopuyo.Panel.map.MapService;
import puyopuyo.Panel.map.subpanel.ground.GroundPanel;
import puyopuyo.Panel.map.subpanel.ground.GroundService;
import puyopuyo.Panel.map.subpanel.ground.Puyo;

import static java.lang.Thread.sleep;
import static puyopuyo.resource.Constants.*;

public class RoundService {
    private final Round round;

    private final MapService mapService = MapService.getInstance();
    private final GameService gameService = GameService.getInstance();
    private final GroundPanel groundPanel;
    private final GroundService groundService;

    public RoundService(int player) {
        round = new Round(player);
        groundPanel = mapService.getGroundPanel(player);
        groundService = groundPanel.getGroundService();
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
     * 뿌요를 한칸 밑으로 내린다.
     */
    public void dropPuyo() {
        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        checkPuyo();

        // JLabel을 +60 픽셀만큼 내린다.
        leftPuyo.pos(leftPuyo.x(), leftPuyo.y()+MOVE);
        rightPuyo.pos(rightPuyo.x(), rightPuyo.y()+MOVE);
    }

    /**
     * 내 주변에 뿌요가 존재하는지 판단한다.
     */
    public void checkPuyo() {
        var player = round.getPlayer();

        var puyoMap = groundService.getPuyoMap();

        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        var garbagePuyo = round.getGarbagePuyo();

        //게임 종료 조건
        if(puyoMap[2][1]!=null||puyoMap[3][1]!=null) {
            round.setEnd(true); // 종료됨을 알린다.

            gameService.playerWin(Game.otherPlayer(player));

            gameService.getRoundThread(Game.otherPlayer(player)).changeOneWin();
            gameService.getRoundThread(Game.otherPlayer(player)).setEnd(true);

            gameService.changeRoundChangeToggle();
            return;
        }

        // 뿌요1이 가장 아래로 내려운 경우
        if(leftPuyo.y() >= Y_MAX || puyoMap[leftPuyo.x()][leftPuyo.y()+MOVE] != null) {
            putPuyo(leftPuyo, rightPuyo);
        }
        // 뿌요2가 가장 아래로 내려운 경우
        else if(rightPuyo.y() >= Y_MAX || puyoMap[rightPuyo.x()][rightPuyo.y()+MOVE] != null) {
            putPuyo(rightPuyo, leftPuyo);
        }
    }

    /**
     * 뿌요가 최하단까지 도달하였을 시 뿌요 위치를 확정하는 함수
     * 
     * @param puyo
     * @param anotherPuyo
     */
    public void putPuyo(Puyo puyo, Puyo anotherPuyo) {
        var puyoMap = groundService.getPuyoMap();

        var garbagePuyo = round.getGarbagePuyo();

        puyo.setVisible(false);

        // puyoMap은 reference이기 때문에 따로 setter를 사용할 필요가 없다.
        puyoMap[(puyo.x())][(puyo.y())] = new Puyo(puyo.getType(),puyo.x(),puyo.y());
        groundPanel.add(puyoMap[puyo.x()][puyo.y()]);

        dropAnotherPuyo(anotherPuyo);

        scanNumberOfSamePuyo();
        checkSamePuyo();

        // 방해뿌요 드롭
        if(garbagePuyo != 0)
            dropGarbagePuyo();

        groundPanel.repaint();

        nextPuyo();
    }

    /**
     * 게임 종료 여부를 판단하는 함수
     */
    public Boolean getEndFlag() {
        return round.isEnd();
    }

    /**
     * 새로운 라운드를 시작하는 함수
     */
    public void newRound() {
        while(true) {
            try {
                if(getEndFlag())
                    break;
                dropPuyo();
                sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        clearRound();
        groundPanel.repaint();
    }

    /**
     * 방해 뿌요 개수를 설정하는 함수
     */
    public void setGarbagePuyo(int numberOfGarbagePuyo) {
        round.setGarbagePuyo(numberOfGarbagePuyo);
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

        int puyo1Type = (puyoLogic[puyoIndex%puyoLogic.length]) / 10;
        int puyo2Type = (puyoLogic[(puyoIndex++)%puyoLogic.length]) % 10;
        
        round.setPuyoIndex(puyoIndex);

        leftPuyo.setType(puyo1Type);
        rightPuyo.setType(puyo2Type);

        // type에 맞는 아이콘을 사용한다.
        leftPuyo.setIcon(Puyo.getPuyoIcon()[puyo1Type]);
        rightPuyo.setIcon(Puyo.getPuyoIcon()[puyo2Type]);

        leftPuyo.pos(2,0);
        rightPuyo.pos(3,0);

        leftPuyo.setVisible(true);
        rightPuyo.setVisible(true);

        scoreService.changeNextPuyo(round.getPlayer(), puyoLogic, puyoIndex);
    }

    /**
     * 한 뿌요의 위치가 정해지고 다음 뿌요의 위치를 보여주는 함수
     * 
     * @param anotherPuyo
     */
    void dropAnotherPuyo(Puyo anotherPuyo) {
        var puyoMap = groundService.getPuyoMap();

        int indexY = Y_MAX;
        while(true) {
            if(puyoMap[anotherPuyo.x()][indexY]==null) {
                anotherPuyo.pos(anotherPuyo.x(),indexY);
                puyoMap[anotherPuyo.x()][indexY] = new Puyo(anotherPuyo.getType(), anotherPuyo.x(), indexY);
                groundPanel.add(puyoMap[anotherPuyo.x()][indexY]);
                break;
            }
            indexY--;
        }
        anotherPuyo.setVisible(false);
    }

    void checkSamePuyo() {
        var player = round.getPlayer();

        GroundService groundService = mapService.getGroundPanel(player).getGroundService();

        var puyoMap = groundService.getPuyoMap();
        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();
        int numberOfSamePuyo;

        initializeCheckNumberOfSamePuyoVariable();
        checkNumberOfSamePuyo(puyoMap[leftPuyo.x()][leftPuyo.y()], leftPuyo.x(), leftPuyo.y());

        numberOfSamePuyo  = round.getNumberOfSamePuyo();
        if(numberOfSamePuyo>=4) {
            deletePuyos(puyoMap[leftPuyo.x()][leftPuyo.y()], leftPuyo.x(), leftPuyo.y());
            try {
                sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            dropPuyos();
        }

        initializeCheckNumberOfSamePuyoVariable();
        checkNumberOfSamePuyo(puyoMap[rightPuyo.x()][rightPuyo.y()], rightPuyo.x(), rightPuyo.y());

        numberOfSamePuyo  = round.getNumberOfSamePuyo();
        if(numberOfSamePuyo>=4) {
            deletePuyos(puyoMap[rightPuyo.x()][rightPuyo.y()], rightPuyo.x(), rightPuyo.y());
            try {
                sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            dropPuyos();
        }
    }

    void dropGarbagePuyo() {
        var scorePanel = mapService.getScorePanel();

        var puyoMap = groundService.getPuyoMap();
        var garbagePuyo = round.getGarbagePuyo();

        int seperateGarbagePuyo = garbagePuyo / 6;
        int moduloGarbagePuyo = garbagePuyo % 6;
        int randomVariable;

        //puyopuyo.Puyo.type = 5;

        for (int i = 0; i < 6; i++)
            for (int j = 11; j >= 0; j--)
                if (puyoMap[i][j] == null)
                {
                    for (int p = 0; p < seperateGarbagePuyo; p++)
                    {
                        if (j - p <= 2) continue;
                        puyoMap[i][j - p] = new Puyo(5, i, j-p);
                        groundPanel.add(puyoMap[i][j - p]);
                    }
                    break;
                }

        while (moduloGarbagePuyo-- > 0)
        {
            randomVariable = (int)(Math.random()*6);
            for (int j = 11; j >= 0; j--)
                if (puyoMap[randomVariable][j] == null)
                {
                    if(j<=2) continue;
                    puyoMap[randomVariable][j] = new Puyo(5, randomVariable, j);
                    groundPanel.add(puyoMap[randomVariable][j]);
                    break;
                }
        }
        round.setGarbagePuyo(0);

        if(round.getPlayer() == 1) scorePanel.getScoreService().getNumberOfGarbagePuyoLabel(1).setText("0");
        else scorePanel.getScoreService().getNumberOfGarbagePuyoLabel(2).setText("0");
    }

    // CheckNumberOfSamePuyo와 deletePuyos에 필요한 변수들을 초기화 하는 함수
    void initializeCheckNumberOfSamePuyoVariable() {
        var player = round.getPlayer();

        GroundService groundService = mapService.getGroundPanel(player).getGroundService();

        var puyoMap = groundService.getPuyoMap();

        round.setNumberOfSamePuyo(0);

        for(int i=0; i<puyoMap.length; i++)
            for(int j=0; j<puyoMap[i].length; j++)
                round.setSamePuyoChecker(false, i, j);
    }

    /**
     * 뿌요 폭발 후 가산에 이용했던 점수 변수 초기화
     */
    void initializeScoreVariable() {
        var colorChecker = round.getColorChecker();

        for(int i=0;i<Puyo.getPuyoIcon().length;i++)
            colorChecker[i]=false;

        round.setPuyoRemovedSum(0);
        round.setPuyoConnect(0);
        round.setPuyoCombo(0);
        round.setPuyoColor(0);
    }

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
     * 바닥에 도달한 뿌요 주변에 상호작용이 가능한 뿌요가 있는지 검사하는 함수
     * <p>
     * 해당 함수가 발동되는 조건은 다음과 같다.
     * 1. ControlledPuyo 중 하나가 바닥에 닿았을 경우
     * 2. 폭발로직이 작동하여 뿌요가 재배치 된 경우
     */
    void scanNumberOfSamePuyo() {
        var player = round.getPlayer();
        var gameService = GameService.getInstance();

        GroundService groundService = mapService.getGroundPanel(player).getGroundService();

        var scoreService = mapService.getScorePanel().getScoreService();

        var puyoMap = groundService.getPuyoMap();

        var samePuyoChecker = round.getSamePuyoChecker();
        var colorChecker = round.getColorChecker();
        var puyoColor = round.getPuyoColor();
        var puyoConnect = round.getPuyoConnect();
        var puyoCombo = round.getPuyoCombo();
        var score = round.getScore();

        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        Puyo puyo = new Puyo(0, 0, 0);
        boolean check = false;

        leftPuyo.setVisible(false);
        rightPuyo.setVisible(false);

        for(int T = 0; T < Puyo.getPuyoIcon().length-1; T++) {
            // 0색 체크
            for (int i = 0; i < 6; i++)
                for (int j = 0; j < 12; j++)
                    if (!samePuyoChecker[i][j] && puyoMap[i][j] != null && puyoMap[i][j].getType() == T) {
                        puyo.pos(i, j);
                        puyo.setType(T);
                        initializeCheckNumberOfSamePuyoVariable(); // 탐색 전 초기화

                        checkNumberOfSamePuyo(puyo, i, j); // 여기서 numberOfSamePuyo의 값이 확정된다.
                        var numberOfSamePuyo = round.getNumberOfSamePuyo();

                        // 점수 계산할 때 사용한다.
                        if (numberOfSamePuyo >= 4) {
                            if(!colorChecker[T]) {
                                colorChecker[T]=true;
                                round.setPuyoColor(puyoColor++);
                            }
                            puyoConnect = round.setPuyoConnect(numberOfSamePuyo);

                            var puyoRemovedSum = round.plusPuyoRemovedSum(puyoConnect);

                            round.setPuyoCombo(++puyoCombo);

                            int plusScore = puyoRemovedSum * (COMBO_BONUS[++puyoCombo] + COLOR_BONUS[puyoColor] + CONNECT_BONUS[puyoConnect]) * 10;
                            //int plusScore = puyoRemovedSum * (puyoCombo + puyoColor + puyoConnect) * 10;

                            round.plusScore(score);

                            printScore();

                            deletePuyos(puyo, i, j);

                            if(player == 1) {
                                gameService.tossGarbagePuyo(1, plusScore);
                                scoreService.getNumberOfGarbagePuyoLabel(2).setText(Integer.toString(plusScore/70));
                            }
                            else {
                                gameService.tossGarbagePuyo(2, plusScore);
                                scoreService.getNumberOfGarbagePuyoLabel(1).setText(Integer.toString(plusScore/70));
                            }
                            check = true;
                        }
                        round.setPuyoRemovedSum(0);
                    }
                }

        // 폭발되어 값이 수정되었다면 모든 뿌요를 드롭시킨다.
        if (check) dropPuyos();
    }

    /**
     * puyo1과 puyo2가 4개 이상 같은 색으로 연결되었는지 체크
     * 탐색에 Region Fill algorithm(재귀)을 사용한다.
     *
     * @param puyo
     * @param indexX
     * @param indexY
     */
    void checkNumberOfSamePuyo(Puyo puyo, int indexX, int indexY) {
        if(puyo == null) return;

        var player = round.getPlayer();

        GroundService groundService = mapService.getGroundPanel(player).getGroundService();

        var puyoMap = groundService.getPuyoMap();

        var samePuyoChecker = round.getSamePuyoChecker();
        var numberOfSamePuyo = round.getNumberOfSamePuyo();

        // 예외처리: 뿌요1과 2가 동시에 속해서 사라지는 경우 anotherPuyo는 존재하지 않음(null)
        if(samePuyoChecker[indexX][indexY]) return;

        round.setNumberOfSamePuyo(++numberOfSamePuyo);

        samePuyoChecker[indexX][indexY] = true;

        if(numberOfSamePuyo>=4)
            // 예외처리: puyoMap[][] 범위밖에서 Puyo를 호출한다.
            if (indexX <= 5 && indexY < 11 && puyoMap[indexX][indexY + 1] != null && puyoMap[indexX][indexY + 1].getType() == puyo.getType() && !samePuyoChecker[indexX][indexY + 1]) {
                checkNumberOfSamePuyo(puyo, indexX, indexY + 1);
            }

        if (indexX <= 5 && indexY > 0 && indexY < 12 && puyoMap[indexX][indexY - 1] != null && puyoMap[indexX][indexY - 1].getType() == puyo.getType() && !samePuyoChecker[indexX][indexY - 1]) {
            checkNumberOfSamePuyo(puyo, indexX, indexY - 1);
        }
        if (indexX <= 4 && indexY < 12 && puyoMap[indexX + 1][indexY] != null && puyoMap[indexX + 1][indexY].getType() == puyo.getType() && !samePuyoChecker[indexX + 1][indexY]) {
            checkNumberOfSamePuyo(puyo, indexX + 1, indexY);
        }
        if (indexX >= 1 && indexX <= 5 && indexY < 12 && puyoMap[indexX - 1][indexY] != null && puyoMap[indexX - 1][indexY].getType() == puyo.getType() && !samePuyoChecker[indexX - 1][indexY]) {
            checkNumberOfSamePuyo(puyo, indexX - 1, indexY);
        }

    }

    /**
     * 4개가 중첩되어 없애야 할 뿌요를 삭제한다.
     * TODO: 재귀말고 SamePuyoChecker가 true인 부분을 삭제하는 것 검토할 것
     *
     * @param puyo
     * @param indexX
     * @param indexY
     */
    void deletePuyos(Puyo puyo, int indexX, int indexY) {
        var player = round.getPlayer();

        GroundService groundService = mapService.getGroundPanel(player).getGroundService();

        var puyoMap = groundService.getPuyoMap();

        round.setSamePuyoChecker(false, indexX, indexY);
        var samePuyoChecker = round.getSamePuyoChecker();

        puyoMap[indexX][indexY].setVisible(false); // TODO: 삭제는 아니라 중첩 상태임 조금 비효율적
        puyoMap[indexX][indexY]=null;

        splashObstructPuyo(indexX, indexY);

        /*
          checkNumberOfSamePuyo()에서 포착된 뿌요들을 제거
          예외처리: puyoMap[][] 범위밖에서 Puyo를 호출한다.
         */
        if (indexX <= 5 && indexY < 11 && puyoMap[indexX][indexY + 1] != null && puyoMap[indexX][indexY + 1].getType() == puyo.getType() && samePuyoChecker[indexX][indexY + 1]) {
            deletePuyos(puyo, indexX, indexY+1);
        }
        if (indexX <= 5 && indexY > 0 && indexY < 12 && puyoMap[indexX][indexY - 1] != null && puyoMap[indexX][indexY - 1].getType() == puyo.getType() && samePuyoChecker[indexX][indexY - 1]) {
            deletePuyos(puyo, indexX, indexY-1);
        }
        if (indexX <= 4 && indexY < 12 && puyoMap[indexX + 1][indexY] != null && puyoMap[indexX + 1][indexY].getType() == puyo.getType() && samePuyoChecker[indexX + 1][indexY]) {
            deletePuyos(puyo, indexX+1, indexY);
        }
        if (indexX >= 1 && indexX <= 5 && indexY < 12 && puyoMap[indexX-1][indexY]!=null && puyoMap[indexX-1][indexY].getType() == puyo.getType() && samePuyoChecker[indexX-1][indexY]) {
            deletePuyos(puyo, indexX-1, indexY);
        }
    }

    /**
     * 주변에 방해뿌요 존재 시 함께 사라진다.
     *
     * @param indexX
     * @param indexY
     */
    void splashObstructPuyo(int indexX, int indexY) {
        var player = round.getPlayer();

        GroundService groundService = mapService.getGroundPanel(player).getGroundService();

        var puyoMap = groundService.getPuyoMap();

        if (indexX+1 < 6 && puyoMap[indexX+1][indexY] != null && puyoMap[indexX+1][indexY].getType() == 5) {
            puyoMap[indexX+1][indexY].setVisible(false);
            puyoMap[indexX+1][indexY] = null;
        }
        if (indexX-1 >= 0 && puyoMap[indexX-1][indexY] != null && puyoMap[indexX-1][indexY].getType() == 5) {
            puyoMap[indexX-1][indexY].setVisible(false);
            puyoMap[indexX-1][indexY] = null;
        }
        if (indexY+1 < 12 && puyoMap[indexX][indexY+1] != null && puyoMap[indexX][indexY+1].getType() == 5) {
            puyoMap[indexX][indexY+1].setVisible(false);
            puyoMap[indexX][indexY+1] = null;
        }
        if (indexY-1 >= 0 && puyoMap[indexX][indexY-1] != null && puyoMap[indexX][indexY-1].getType() == 5) {
            puyoMap[indexX][indexY-1].setVisible(false);
            puyoMap[indexX][indexY-1] = null;
        }
    }


    /**
     * deletePuyo() 이후 공중에 떠있는 블록들을 아래로 정렬한다.
     */
    void dropPuyos() {
        var player = round.getPlayer();

        GroundService groundService = mapService.getGroundPanel(player).getGroundService();

        var puyoMap = groundService.getPuyoMap();


        int i, j, q;

        for (i = 0; i < 6; i++)
        {
            for (j = 11; j >= 0; j--)
            {
                if (puyoMap[i][j] == null) {
                    for (q = j-1; q >= 0; q--)
                    {
                        if (puyoMap[i][q] != null) {// ���࿡ ����� ���� �ִٸ�,
                            puyoMap[i][j] = puyoMap[i][q];
                            puyoMap[i][q] = null;
                            puyoMap[i][j].pos(i,j);
                            break;
                        }
                    }
                }
            }
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

        initializeScoreVariable();
        scanNumberOfSamePuyo();
    }

    /**
     * 3판 2선승제 기준 승리 여부를 판단하는 함수
     */
    public void changeOneWin() {
        round.changeOneWin();
    }

    /**
     * 라운드 종료 상태를 전달하는 함수
     */
    public void setEnd(boolean state) {
        round.setEnd(state);
    }
}
