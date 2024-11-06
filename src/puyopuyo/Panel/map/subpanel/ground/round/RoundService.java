package puyopuyo.Panel.map.subpanel.ground.round;

import puyopuyo.Panel.map.game.GameService;
import puyopuyo.Panel.map.MapService;
import puyopuyo.Panel.map.subpanel.ground.GroundPanel;
import puyopuyo.Panel.map.subpanel.ground.GroundService;
import puyopuyo.Panel.map.subpanel.ground.Puyo;

import java.util.Arrays;

import static java.lang.Thread.sleep;
import static puyopuyo.resource.Constants.*;

public class RoundService {
    private final RoundRepository roundRepository;

    private final GameService gameService;
    private final MapService mapService;

    public RoundService(int iAm, RoundThread roundThread, GameService gameService, MapService mapService) {
        roundRepository = new RoundRepository(iAm, roundThread);
        this.gameService = gameService;
        this.mapService = mapService;
    }

    /**
     * 새 라운드를 시작하기 위해 초기화 하는 함수
     * <p>
     * 자신이 관할하는 roundRepository를 초기화 한다.
     */
    public void setRound() {
        var player = roundRepository.getIAm();
        //var puyoMap = mapService.getGroundService(player).getPuyoMap();

        Puyo[][] puyoMap = null;
        if(player == 1) puyoMap = mapService.getGroundPanel1P().getGroundService().getPuyoMap();
        else if(player == 2) puyoMap = mapService.getGroundPanel2P().getGroundService().getPuyoMap();

        // TODO: 여기서만 쓴다면 그냥 초기화 함수로 만들어도 될 듯
        roundRepository.setColorChecker(new boolean[Puyo.getPuyoIcon().length]);

        for (Puyo[] puyos : puyoMap) Arrays.fill(puyos, null);

        nextPuyo();
    }

    /**
     * 라운드 종료 후 모든 roundRepository를 정리한다.
     */
    public void clearRound() {
        var iAm = roundRepository.getIAm();
        GroundService groundService = null;
        if(iAm == 1) groundService = mapService.getGroundPanel1P().getGroundService();
        else if(iAm == 2) groundService = mapService.getGroundPanel2P().getGroundService();

        var puyoMap = groundService.getPuyoMap();

        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        for(int i=0;i<puyoMap.length;i++)
            for(int j=0;j<puyoMap[i].length;j++) {
                if(puyoMap[i][j]!=null) {
                    puyoMap[i][j].setVisible(false);
                    puyoMap[i][j] = null;
                }
            }

        leftPuyo.setVisible(false);
        rightPuyo.setVisible(false);
    }

    /**
     * 뿌요를 한칸 밑으로 내린다.
     */
    public void dropPuyo() {
        var iAm = roundRepository.getIAm();
        GroundService groundService = null;
        if(iAm == 1) groundService = mapService.getGroundPanel1P().getGroundService();
        else if(iAm == 2) groundService = mapService.getGroundPanel2P().getGroundService();

        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        checkPuyo();
        // JLabel을 +60 픽셀만큼 내린다.
        leftPuyo.setLocation(leftPuyo.getX(), leftPuyo.getY()+60);
        rightPuyo.setLocation(rightPuyo.getX(), rightPuyo.getY()+60);
    }

    /**
     * 내 주변에 뿌요가 존재하는지 판단한다.
     */
    public void checkPuyo() {
        // TODO: 뿌요 맵을 RoundService로 이동시키고 화면 디자인만 GroundPanel로 옮기는 것도 좋을듯
        var iAm = roundRepository.getIAm();
        GroundService groundService = null;
        if(iAm == 1) groundService = mapService.getGroundPanel1P().getGroundService();
        else if(iAm == 2) groundService = mapService.getGroundPanel2P().getGroundService();
        
        var puyoMap = groundService.getPuyoMap();

        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        var garbagePuyo = roundRepository.getGarbagePuyo();

        //게임 종료 조건
        if(puyoMap[2][1]!=null||puyoMap[3][1]!=null) {
            roundRepository.setEnd(true); // 종료됨을 알린다.
            
            // 상대 스레드 oneWin
            if(iAm == 1) {
                gameService.plusWinCount(2);
                gameService.getRoundThread(2).changeOneWin();
                gameService.getRoundThread(2).setEnd(true);
            }
            else {
                gameService.plusWinCount(1);
                gameService.getRoundThread(1).changeOneWin(); // TODO: 삭제해도 무방
                gameService.getRoundThread(1).setEnd(true);
            }

            gameService.changeRoundChangeToggle();
            return;
        }

        // 뿌요1이 가장 아래로 내려운 경우
        if((leftPuyo.getY()-10)/60 >= 11 || puyoMap[(leftPuyo.getX()-20)/60][(leftPuyo.getY()-10)/60+1] != null) {
            putPuyo(leftPuyo, rightPuyo);
        }
        // 뿌요2가 가장 아래로 내려운 경우
        else if((rightPuyo.getY()-10)/60 >= 11 || puyoMap[(rightPuyo.getX()-20)/60][(rightPuyo.getY()-10)/60+1] != null) {
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
        var iAm = roundRepository.getIAm();

        GroundPanel groundPanel = null;
        GroundService groundService = null;
        if(iAm == 1) {
            groundPanel = mapService.getGroundPanel1P();
            groundService = groundPanel.getGroundService();
        }
        else if(iAm == 2) {
            groundPanel = mapService.getGroundPanel2P();
            groundService = groundPanel.getGroundService();
        }

        var puyoMap = groundService.getPuyoMap();

        var garbagePuyo = roundRepository.getGarbagePuyo();

        puyo.setVisible(false);

        // puyoMap은 reference이기 때문에 따로 setter를 사용할 필요가 없다.
        puyoMap[(puyo.getX()-20)/60][(puyo.getY()-10)/60] = new Puyo(puyo.getType(),(puyo.getX()-20)/60,(puyo.getY()-10)/60);
        groundPanel.add(puyoMap[(puyo.getX()-20)/60][(puyo.getY()-10)/60]);

        dropAnotherPuyo(anotherPuyo);

        scanNumberOfSamePuyo();
        checkSamePuyo();

        // 방해뿌요 드롭
        if(garbagePuyo != 0)
            dropGarbagePuyo();

        repaint();

        nextPuyo();
    }

    public Boolean getEndFlag() {return roundRepository.isEnd();}

    public void repaint() {
        var iAm = roundRepository.getIAm();
        GroundPanel groundPanel = null;
        if(iAm == 1) groundPanel = mapService.getGroundPanel1P();
        else if(iAm == 2) groundPanel = mapService.getGroundPanel2P();

        groundPanel.repaint();
    }

    public void run() {
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
        repaint();
    }

    public void setGarbagePuyo(int numberOfGarbagePuyo) {
        roundRepository.setGarbagePuyo(numberOfGarbagePuyo);
    }

    // 다음 뿌요로 전환시켜주는 함수이다.
    void nextPuyo() {
        var iAm = roundRepository.getIAm();
        GroundService groundService = null;
        if(iAm == 1) groundService = mapService.getGroundPanel1P().getGroundService();
        else if(iAm == 2) groundService = mapService.getGroundPanel2P().getGroundService();

        var scoreService = mapService.getScorePanel().getScoreService();

        var puyoLogic = gameService.getPuyoLogic();
        var puyoIndex = roundRepository.getPuyoIndex();

        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        int puyo1Type = (puyoLogic[puyoIndex%puyoLogic.length]) / 10;
        int puyo2Type = (puyoLogic[(puyoIndex++)%puyoLogic.length]) % 10;
        roundRepository.setPuyoIndex(puyoIndex);

        leftPuyo.setType(puyo1Type);
        rightPuyo.setType(puyo2Type);

        // type에 맞는 아이콘을 사용한다.
        leftPuyo.setIcon(Puyo.getPuyoIcon()[puyo1Type]);
        rightPuyo.setIcon(Puyo.getPuyoIcon()[puyo2Type]);

        leftPuyo.setLocation(140,10);
        rightPuyo.setLocation(200,10);

        leftPuyo.setVisible(true);
        rightPuyo.setVisible(true);

        scoreService.changeNextPuyo(iAm, puyoLogic, puyoIndex);
    }

    /**
     * 한 뿌요의 위치가 정해지고 다음 뿌요의 위치를 보여주는 함수
     * 
     * @param anotherPuyo
     */
    void dropAnotherPuyo(Puyo anotherPuyo) {
        var iAm = roundRepository.getIAm();

        GroundPanel groundPanel = null;
        GroundService groundService = null;
        if(iAm == 1) {
            groundPanel = mapService.getGroundPanel1P();
            groundService = groundPanel.getGroundService();
        }
        else if(iAm == 2) {
            groundPanel = mapService.getGroundPanel2P();
            groundService = groundPanel.getGroundService();
        }

        var puyoMap = groundService.getPuyoMap();

        int indexY = 11;
        while(true) {
            if(puyoMap[anotherPuyo.PixelXToindex()][indexY]==null) {
                anotherPuyo.setLocation(anotherPuyo.getX(),Puyo.indexXToPixel(indexY));
                puyoMap[anotherPuyo.PixelXToindex()][indexY] = new Puyo(anotherPuyo.getType(), anotherPuyo.PixelXToindex(), indexY);
                groundPanel.add(puyoMap[anotherPuyo.PixelXToindex()][indexY]);
                break;
            }
            indexY--;
        }
        anotherPuyo.setVisible(false);
    }

    void checkSamePuyo() {
        var iAm = roundRepository.getIAm();
        GroundService groundService = null;
        if(iAm == 1) groundService = mapService.getGroundPanel1P().getGroundService();
        else if(iAm == 2) groundService = mapService.getGroundPanel2P().getGroundService();

        var puyoMap = groundService.getPuyoMap();
        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();
        int numberOfSamePuyo;

        initializeCheckNumberOfSamePuyoVariable();
        checkNumberOfSamePuyo(puyoMap[leftPuyo.PixelXToindex()][leftPuyo.PixelYToindex()], leftPuyo.PixelXToindex(), leftPuyo.PixelYToindex());

        numberOfSamePuyo  = roundRepository.getNumberOfSamePuyo();
        if(numberOfSamePuyo>=4) {
            deletePuyos(puyoMap[leftPuyo.PixelXToindex()][leftPuyo.PixelYToindex()], leftPuyo.PixelXToindex(), leftPuyo.PixelYToindex());
            try {
                sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            dropPuyos();
        }

        initializeCheckNumberOfSamePuyoVariable();
        checkNumberOfSamePuyo(puyoMap[rightPuyo.PixelXToindex()][rightPuyo.PixelYToindex()], rightPuyo.PixelXToindex(), rightPuyo.PixelYToindex());

        numberOfSamePuyo  = roundRepository.getNumberOfSamePuyo();
        if(numberOfSamePuyo>=4) {
            deletePuyos(puyoMap[rightPuyo.PixelXToindex()][rightPuyo.PixelYToindex()], rightPuyo.PixelXToindex(), rightPuyo.PixelYToindex());
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
        var iAm = roundRepository.getIAm();

        GroundPanel groundPanel = null;
        GroundService groundService = null;
        if(iAm == 1) {
            groundPanel = mapService.getGroundPanel1P();
            groundService = groundPanel.getGroundService();
        }
        else if(iAm == 2) {
            groundPanel = mapService.getGroundPanel2P();
            groundService = groundPanel.getGroundService();
        }

        var scorePanel = mapService.getScorePanel();

        var puyoMap = groundService.getPuyoMap();
        var garbagePuyo = roundRepository.getGarbagePuyo();

        int seperateGarbagePuyo = garbagePuyo / 6;
        int moduloGarbagePuyo = garbagePuyo % 6;
        int randomVariable;
        //puyopuyo.Puyo.type = 5;
        for (int i = 0; i < 6; i++)
        {
            for (int j = 11; j >= 0; j--)
            {
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
            }
        }

        while (moduloGarbagePuyo-- > 0)
        {
            randomVariable = (int)(Math.random()*6);
            for (int j = 11; j >= 0; j--)
            {
                if (puyoMap[randomVariable][j] == null)
                {
                    if(j<=2) continue;
                    puyoMap[randomVariable][j] = new Puyo(5, randomVariable, j);
                    groundPanel.add(puyoMap[randomVariable][j]);
                    break;
                }
            }
        }
        roundRepository.setGarbagePuyo(0);

        if(roundRepository.getIAm() == 1) scorePanel.getScoreService().getNumberOfGarbagePuyoLabel(1).setText("0");
        else scorePanel.getScoreService().getNumberOfGarbagePuyoLabel(2).setText("0");
    }

    // CheckNumberOfSamePuyo와 deletePuyos에 필요한 변수들을 초기화 하는 함수
    void initializeCheckNumberOfSamePuyoVariable() {
        var iAm = roundRepository.getIAm();
        GroundService groundService = null;
        if(iAm == 1) groundService = mapService.getGroundPanel1P().getGroundService();
        else if(iAm == 2) groundService = mapService.getGroundPanel2P().getGroundService();

        var puyoMap = groundService.getPuyoMap();

        roundRepository.setNumberOfSamePuyo(0);

        for(int i=0; i<puyoMap.length; i++)
            for(int j=0; j<puyoMap[i].length; j++)
                roundRepository.setSamePuyoChecker(false, i, j);
    }

    /**
     * 뿌요 폭발 후 가산에 이용했던 점수 변수 초기화
     */
    void initializeScoreVariable() {
        var colorChecker = roundRepository.getColorChecker();

        for(int i=0;i<Puyo.getPuyoIcon().length;i++)
            colorChecker[i]=false;

        roundRepository.setPuyoRemovedSum(0);
        roundRepository.setPuyoConnect(0);
        roundRepository.setPuyoCombo(0);
        roundRepository.setPuyoColor(0);
    }

    void printScore() {
        var iAm = roundRepository.getIAm();
        var scoreService = mapService.getScorePanel().getScoreService();

        var score = roundRepository.getScore();

        if(iAm == 1)
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
        var iAm = roundRepository.getIAm();
        GroundService groundService = null;
        if(iAm == 1) groundService = mapService.getGroundPanel1P().getGroundService();
        else if(iAm == 2) groundService = mapService.getGroundPanel2P().getGroundService();

        var scoreService = mapService.getScorePanel().getScoreService();

        var puyoMap = groundService.getPuyoMap();

        var samePuyoChecker = roundRepository.getSamePuyoChecker();
        var colorChecker = roundRepository.getColorChecker();
        var puyoColor = roundRepository.getPuyoColor();
        var puyoConnect = roundRepository.getPuyoConnect();
        var puyoCombo = roundRepository.getPuyoCombo();
        var score = roundRepository.getScore();

        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        Puyo puyo = new Puyo(0, 0, 0);
        boolean check = false;

        leftPuyo.setVisible(false);
        rightPuyo.setVisible(false);

        for(int T = 0; T < Puyo.getPuyoIcon().length-1; T++) {
            // 0색 체크
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 12; j++) {
                    if (!samePuyoChecker[i][j] && puyoMap[i][j] != null && puyoMap[i][j].getType() == T) {
                        puyo.setLocation(Puyo.indexXToPixel(i),Puyo.indexYToPixel(j));
                        puyo.setType(T);
                        initializeCheckNumberOfSamePuyoVariable(); // 탐색 전 초기화

                        checkNumberOfSamePuyo(puyo, i, j); // 여기서 numberOfSamePuyo의 값이 확정된다.
                        var numberOfSamePuyo = roundRepository.getNumberOfSamePuyo();

                        // 점수 계산할 때 사용한다.
                        if (numberOfSamePuyo >= 4) {
                            if(!colorChecker[T]) {
                                colorChecker[T]=true;
                                roundRepository.setPuyoColor(puyoColor++);
                            }
                            puyoConnect = roundRepository.setPuyoConnect(numberOfSamePuyo);

                            var puyoRemovedSum = roundRepository.plusPuyoRemovedSum(puyoConnect);

                            roundRepository.setPuyoCombo(++puyoCombo);

                            int plusScore = puyoRemovedSum * (COMBO_BONUS[++puyoCombo] + COLOR_BONUS[puyoColor] + CONNECT_BONUS[puyoConnect]) * 10;
                            //int plusScore = puyoRemovedSum * (puyoCombo + puyoColor + puyoConnect) * 10;

                            roundRepository.plusScore(score);

                            printScore();

                            deletePuyos(puyo, i, j);

                            if(iAm == 1) {
                                gameService.tossGarbagePuyo(1, plusScore);
                                scoreService.getNumberOfGarbagePuyoLabel(2).setText(Integer.toString(plusScore/70));
                            }
                            else {
                                gameService.tossGarbagePuyo(2, plusScore);
                                scoreService.getNumberOfGarbagePuyoLabel(1).setText(Integer.toString(plusScore/70));
                            }
                            check = true;
                        }
                        roundRepository.setPuyoRemovedSum(0);
                    }
                }
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

        var iAm = roundRepository.getIAm();
        GroundService groundService = null;
        if(iAm == 1) groundService = mapService.getGroundPanel1P().getGroundService();
        else if(iAm == 2) groundService = mapService.getGroundPanel2P().getGroundService();

        var puyoMap = groundService.getPuyoMap();

        var samePuyoChecker = roundRepository.getSamePuyoChecker();
        var numberOfSamePuyo = roundRepository.getNumberOfSamePuyo();

        // 예외처리: 뿌요1과 2가 동시에 속해서 사라지는 경우 anotherPuyo는 존재하지 않음(null)
        if(samePuyoChecker[indexX][indexY]) return;

        roundRepository.setNumberOfSamePuyo(++numberOfSamePuyo);

        samePuyoChecker[indexX][indexY] = true;

        if(numberOfSamePuyo>=4)
            // 예외처리: puyoMap[][] 범위밖에서 Puyo를 호출한다.
            if (indexX <= 5 && indexY < 11 && puyoMap[indexX][indexY + 1] != null && puyoMap[indexX][indexY + 1].getType() == puyo.getType() && !samePuyoChecker[indexX][indexY + 1]) {
                checkNumberOfSamePuyo(puyo, indexX, indexY+1);
            }
        if (indexX <= 5 && indexY > 0 && indexY < 12 && puyoMap[indexX][indexY - 1] != null && puyoMap[indexX][indexY - 1].getType() == puyo.getType() && !samePuyoChecker[indexX][indexY - 1]) {
            checkNumberOfSamePuyo(puyo, indexX, indexY-1);
        }
        if (indexX <= 4 && indexY < 12 && puyoMap[indexX + 1][indexY] != null && puyoMap[indexX + 1][indexY].getType() == puyo.getType() && !samePuyoChecker[indexX + 1][indexY]) {
            checkNumberOfSamePuyo(puyo, indexX+1, indexY);
        }
        if (indexX >= 1 && indexX <= 5 && indexY < 12 && puyoMap[indexX-1][indexY]!=null && puyoMap[indexX-1][indexY].getType() == puyo.getType() && !samePuyoChecker[indexX-1][indexY]) {
            checkNumberOfSamePuyo(puyo, indexX-1, indexY);
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
        var iAm = roundRepository.getIAm();
        GroundService groundService = null;
        if(iAm == 1) groundService = mapService.getGroundPanel1P().getGroundService();
        else if(iAm == 2) groundService = mapService.getGroundPanel2P().getGroundService();

        var puyoMap = groundService.getPuyoMap();

        roundRepository.setSamePuyoChecker(false, indexX, indexY);
        var samePuyoChecker = roundRepository.getSamePuyoChecker();

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
        var iAm = roundRepository.getIAm();
        GroundService groundService = null;
        if(iAm == 1) groundService = mapService.getGroundPanel1P().getGroundService();
        else if(iAm == 2) groundService = mapService.getGroundPanel2P().getGroundService();

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
        var iAm = roundRepository.getIAm();
        GroundService groundService = null;
        if(iAm == 1) groundService = mapService.getGroundPanel1P().getGroundService();
        else if(iAm == 2) groundService = mapService.getGroundPanel2P().getGroundService();

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
                            puyoMap[i][j].setLocation(Puyo.indexXToPixel(i),Puyo.indexYToPixel(j));
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

    public void changeOneWin() {
        roundRepository.changeOneWin();
    }

    public void setEnd(boolean state) {
        roundRepository.setEnd(state);
    }
}
