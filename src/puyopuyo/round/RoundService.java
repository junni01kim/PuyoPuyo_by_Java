package puyopuyo.round;

import puyopuyo.game.GameService;
import puyopuyo.map.MapService;
import puyopuyo.puyo.Puyo;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class RoundService {
    private final RoundRepository roundRepository;

    private final GameService gameService;
    private final MapService mapService;

    public RoundService(int iAm, RoundThread roundThread, GameService gameService, MapService mapService) {
        roundRepository = new RoundRepository(iAm, roundThread);
        this.gameService = gameService;
        this.mapService = mapService;
    }

    public void setRound() {
        var iAm = roundRepository.getIAm();
        var puyoMap = mapService.getGroundService(iAm).getPuyoMap();

        roundRepository.setColorChecker(new boolean[Puyo.getPuyoIcon().length]); // TODO: 여기서만 쓴다면 그냥 초기화 함수로 만들어도 될 듯

        for (Puyo[] puyos : puyoMap) Arrays.fill(puyos, null);

        nextPuyo();
    }

    public void clearRound() {
        var iAm = roundRepository.getIAm();
        var groundService = mapService.getGroundService(iAm);
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

    public void dropPuyo() {
        var iAm = roundRepository.getIAm();
        var groundService = mapService.getGroundService(iAm);

        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();

        checkPuyo();
        // JLabel을 +60 픽셀만큼 내린다.
        leftPuyo.setLocation(leftPuyo.getX(), leftPuyo.getY()+60);
        rightPuyo.setLocation(rightPuyo.getX(), rightPuyo.getY()+60);
    }

    public void checkPuyo() {
        var iAm = roundRepository.getIAm();
        var groundService = mapService.getGroundService(iAm);
        var puyoMap = groundService.getPuyoMap();

        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();
        var garbagePuyo = roundRepository.getGarbagePuyo();

        //게임 종료 조건
        if(puyoMap[3][1]!=null||puyoMap[4][1]!=null) {
            roundRepository.changeEnd(); // true로 변경
            // 상대 스레드 oneWin
            if(iAm == 1) {
                gameService.plusWinCount(2);
                gameService.getRoundThread(2).changeOneWin();
                gameService.getRoundThread(2).changeEndFlag();
            }
            else {
                gameService.plusWinCount(1);
                gameService.getRoundThread(2).changeOneWin();
                gameService.getRoundThread(2).changeEndFlag();
            }

            gameService.changeRoundChangeToggle(); // TODO: 타입 추적 안됨 <- 에러 발생 시 유의
            return;
        }

        // 뿌요1이 가장 아래로 내려운 경우
        if((leftPuyo.getY()-10)/60 >= 11 || puyoMap[(leftPuyo.getX()-20)/60][(leftPuyo.getY()-10)/60+1] != null) {
            leftPuyo.setVisible(false);
            puyoMap[(leftPuyo.getX()-20)/60][(leftPuyo.getY()-10)/60] = new Puyo(leftPuyo.getType(),(leftPuyo.getX()-20)/60,(leftPuyo.getY()-10)/60);
            puyoMap[(leftPuyo.getX()-20)/60][(leftPuyo.getY()-10)/60].setVisible(true);
            groundService.add(puyoMap[(leftPuyo.getX()-20)/60][(leftPuyo.getY()-10)/60]);

            dropAnotherPuyo(rightPuyo);

            scanNumberOfSamePuyo();
            //checkSamePuyo();

            // 방해뿌요 드롭
            if(garbagePuyo != 0)
                dropGarbagePuyo();

            repaint();

            nextPuyo();
        }
        // 뿌요2가 가장 아래로 내려운 경우
        else if((rightPuyo.getY()-10)/60 >= 11 || puyoMap[(rightPuyo.getX()-20)/60][(rightPuyo.getY()-10)/60+1] != null) {
            rightPuyo.setVisible(false);
            puyoMap[(rightPuyo.getX()-20)/60][(rightPuyo.getY()-10)/60] = new Puyo(rightPuyo.getType(),(rightPuyo.getX()-20)/60,(rightPuyo.getY()-10)/60);
            puyoMap[(rightPuyo.getX()-20)/60][(rightPuyo.getY()-10)/60].setVisible(true);
            groundService.add(puyoMap[(rightPuyo.getX()-20)/60][(rightPuyo.getY()-10)/60]);

            dropAnotherPuyo(leftPuyo);

            scanNumberOfSamePuyo();
            //checkSamePuyo();

            // 방해뿌요 드롭
            if(garbagePuyo != 0)
                dropGarbagePuyo();

            repaint();

            nextPuyo();
        }
    }

    public Boolean getEndFlag() {return roundRepository.isEnd();}

    public void repaint() {
        var iAm = roundRepository.getIAm();
        mapService.getGroundService(iAm).repaint();
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
        var groundService = mapService.getGroundService(iAm);

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

        //changeNextPuyo();
    }

    // TODO: ScorePanel 역할 아님?
//    void changeNextPuyo() {
//        var iAm = roundRepository.getIAm();
//        var scoreService = mapService.getScoreService();
//
//        var puyoLogic = gameService.getPuyoLogic();
//        var puyoIndex = roundRepository.getPuyoIndex();
//
//        int nextLeftControlPuyoType = (puyoLogic[(puyoIndex)%puyoLogic.length])/10;
//        int nextRightControlPuyoType = (puyoLogic[(puyoIndex)%puyoLogic.length])%10;
//
//        if(iAm == 1) {
//            scorePanel.getNextLeftControlPuyo1P().setType(nextLeftControlPuyoType);
//            scorePanel.getNextRightControlPuyo1P().setType(nextRightControlPuyoType);
//
//            // type에 맞는 아이콘을 사용한다.
//            scorePanel.getNextLeftControlPuyo1P().setIcon(Puyo.getPuyoIcon()[nextLeftControlPuyoType]);
//            scorePanel.getNextRightControlPuyo1P().setIcon(Puyo.getPuyoIcon()[nextRightControlPuyoType]);
//        }
//        else {
//            scorePanel.getNextLeftControlPuyo2P().setType(nextLeftControlPuyoType);
//            scorePanel.getNextRightControlPuyo2P().setType(nextRightControlPuyoType);
//
//            // type에 맞는 아이콘을 사용한다.
//            scorePanel.getNextLeftControlPuyo2P().setIcon(Puyo.getPuyoIcon()[nextLeftControlPuyoType]);
//            scorePanel.getNextRightControlPuyo2P().setIcon(Puyo.getPuyoIcon()[nextRightControlPuyoType]);
//        }
//    }

    // 한 뿌요의 위치가 정해지도 다음 뿌요의 위치를 보여주는 함수
    void dropAnotherPuyo(Puyo anotherPuyo) {
        var iAm = roundRepository.getIAm();
        var groundService = mapService.getGroundService(iAm);

        var puyoMap = groundService.getPuyoMap();

        int indexY = 11;
        while(true) {
            if(puyoMap[anotherPuyo.PixelXToindex()][indexY]==null) {
                anotherPuyo.setLocation(anotherPuyo.getX(),Puyo.indexXToPixel(indexY));
                puyoMap[anotherPuyo.PixelXToindex()][indexY] = new Puyo(anotherPuyo.getType(), anotherPuyo.PixelXToindex(), indexY);
                puyoMap[anotherPuyo.PixelXToindex()][indexY].setVisible(true);
                groundService.add(puyoMap[anotherPuyo.PixelXToindex()][indexY]);
                //System.out.println("tempPuyo:"+indexY);
                break;
            }
            indexY--;
            continue;
        }
        anotherPuyo.setVisible(false);
        //System.out.println("dropAnotherPuyo");
    }

    void checkSamePuyo() {
        var iAm = roundRepository.getIAm();
        var groundService = mapService.getGroundService(iAm);

        var puyoMap = groundService.getPuyoMap();
        var leftPuyo = groundService.getLeftPuyo();
        var rightPuyo = groundService.getRightPuyo();
        var numberOfSamePuyo = roundRepository.getNumberOfSamePuyo();

        initializeCheckNumberOfSamePuyoVariable();
        checkNumberOfSamePuyo(puyoMap[leftPuyo.PixelXToindex()][leftPuyo.PixelYToindex()], leftPuyo.PixelXToindex(), leftPuyo.PixelYToindex());
        if(numberOfSamePuyo>=4) {
            deletePuyos(puyoMap[leftPuyo.PixelXToindex()][leftPuyo.PixelYToindex()], leftPuyo.PixelXToindex(), leftPuyo.PixelYToindex());
            try {
                sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            dropPuyos();
            // 모든 뿌요에 대해서 검색
        }
        initializeCheckNumberOfSamePuyoVariable();
        checkNumberOfSamePuyo(puyoMap[rightPuyo.PixelXToindex()][rightPuyo.PixelYToindex()], rightPuyo.PixelXToindex(), rightPuyo.PixelYToindex());
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
        var groundService = mapService.getGroundService(iAm);
        var scoreService = mapService.getScoreService();

        var puyoMap = groundService.getPuyoMap();
        var garbagePuyo = roundRepository.getGarbagePuyo();

        int seperateGarbagePuyo = garbagePuyo / 6;
        //System.out.println("seperateGarbagePuyo:"+seperateGarbagePuyo);
        int moduloGarbagePuyo = garbagePuyo % 6;
        //System.out.println("moduloGarbagePuyo:"+moduloGarbagePuyo);
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
                        puyoMap[i][j - p].setVisible(true);
                        groundService.add(puyoMap[i][j - p]);
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
                    puyoMap[randomVariable][j].setVisible(true);
                    groundService.add(puyoMap[randomVariable][j]);
                    break;
                }
            }
        }
        roundRepository.setGarbagePuyo(0);

        if(roundRepository.getIAm() == 1) scoreService.getNumberOfGarbagePuyoLabel(1).setText("0");
        else scoreService.getNumberOfGarbagePuyoLabel(2).setText("0");
    }

    // CheckNumberOfSamePuyo와 deletePuyos에 필요한 변수들을 초기화 하는 함수
    void initializeCheckNumberOfSamePuyoVariable() {
        var iAm = roundRepository.getIAm();
        var groundService = mapService.getGroundService(iAm);
        var puyoMap = groundService.getPuyoMap();

        roundRepository.setNumberOfSamePuyo(0);

        for(int i=0; i<puyoMap.length; i++)
            for(int j=0; j<puyoMap[i].length; j++)
                roundRepository.setSamePuyoChecker(false, i, j);
    }

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
        var scoreService = mapService.getScoreService();

        var score = roundRepository.getScore();

        if(iAm == 1)
            scoreService.getScoreLabel(1).setText(Integer.toString(score));
        else
            scoreService.getScoreLabel(2).setText(Integer.toString(score));
    }

    void scanNumberOfSamePuyo() {
        var iAm = roundRepository.getIAm();
        var groundService = mapService.getGroundService(iAm);
        var scoreService = mapService.getScoreService();

        var puyoMap = groundService.getPuyoMap();
        var samePuyoChecker = roundRepository.getSamePuyoChecker();
        var numberOfSamePuyo = roundRepository.getNumberOfSamePuyo();
        var colorChecker = roundRepository.getColorChecker();
        var puyoColor = roundRepository.getPuyoColor();
        var puyoConnect = roundRepository.getPuyoConnect();
        var puyoRemovedSum = roundRepository.getPuyoRemovedSum();
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
                        initializeCheckNumberOfSamePuyoVariable();
                        checkNumberOfSamePuyo(puyo, i, j);
                        // 점수 계산할 때 사용한다.
                        if (numberOfSamePuyo >= 4) {
                            if(!colorChecker[T]) {
                                colorChecker[T]=true;
                                roundRepository.setPuyoColor(puyoColor++);
                            }
                            puyoConnect = numberOfSamePuyo;
                            roundRepository.setPuyoConnect(puyoConnect);
                            puyoRemovedSum += puyoConnect;
                            roundRepository.setPuyoRemovedSum(puyoRemovedSum);

                            //int plusScore = puyoRemovedSum * (puyoComboBonus[++puyoCombo] + puyoColorBonus[puyoColor] + puyoConnectBonus[puyoConnect]) * 10;
                            int plusScore = puyoRemovedSum * (++puyoCombo + puyoColor + puyoConnect) * 10;
                            roundRepository.setPuyoCombo(puyoCombo);

                            score += plusScore;
                            roundRepository.setScore(score);

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
                        puyoRemovedSum = 0;
                    }
                }
            }
        }


        if (check) {
            dropPuyos();
            scanNumberOfSamePuyo();
        }
    }

    // puyo1과 puyo2가 4개 이상 같은 색으로 연결되었는지 체크
    void checkNumberOfSamePuyo(Puyo puyo, int indexX, int indexY) {
        var iAm = roundRepository.getIAm();
        var groundService = mapService.getGroundService(iAm);
        var puyoMap = groundService.getPuyoMap();

        var samePuyoChecker = roundRepository.getSamePuyoChecker();
        var numberOfSamePuyo = roundRepository.getNumberOfSamePuyo();

        // 예외처리: 뿌요1과 2가 동시에 속해서 사라지는 경우 anotherPuyo는 존재하지 않음
        if(samePuyoChecker[indexX][indexY])
            return;

        numberOfSamePuyo++;
        roundRepository.setPuyoCombo(numberOfSamePuyo);

        samePuyoChecker[indexX][indexY] = true;

        if(numberOfSamePuyo>=4)
            // 예외처리: puyoMap[][] 범위밖에서 Puyo를 호출한다.
            if (indexX >= 0 && indexX <= 5 && indexY < 11 && puyoMap[indexX][indexY+1]!=null && puyoMap[indexX][indexY+1].getType() == puyo.getType() && !samePuyoChecker[indexX][indexY+1]) {
                checkNumberOfSamePuyo(puyo, indexX, indexY+1);
            }
        if (indexX >= 0 && indexX <= 5 && indexY > 0 && indexY < 12 && puyoMap[indexX][indexY-1]!=null && puyoMap[indexX][indexY-1].getType() == puyo.getType() && !samePuyoChecker[indexX][indexY-1]) {
            checkNumberOfSamePuyo(puyo, indexX, indexY-1);
        }
        if (indexX >= 0 && indexX <= 4 && indexY < 12 && puyoMap[indexX+1][indexY]!=null && puyoMap[indexX+1][indexY].getType() == puyo.getType() && !samePuyoChecker[indexX+1][indexY]) {
            checkNumberOfSamePuyo(puyo, indexX+1, indexY);
        }
        if (indexX >= 1 && indexX <= 5 && indexY < 12 && puyoMap[indexX-1][indexY]!=null && puyoMap[indexX-1][indexY].getType() == puyo.getType() && !samePuyoChecker[indexX-1][indexY]) {
            checkNumberOfSamePuyo(puyo, indexX-1, indexY);
        }
    }

    // 예외처리: puyoMap[][] 범위밖에서 Puyo를 호출한다.
    void deletePuyos(Puyo puyo, int indexX, int indexY) {
        var iAm = roundRepository.getIAm();
        var groundService = mapService.getGroundService(iAm);
        var puyoMap = groundService.getPuyoMap();

        roundRepository.setSamePuyoChecker(false, indexX, indexY);
        var samePuyoChecker = roundRepository.getSamePuyoChecker();

        puyoMap[indexX][indexY].setVisible(false);
        puyoMap[indexX][indexY]=null;

        splashObstructPuyo(indexX, indexY);

        // checkNumberOfSamePuyo()에서 포착된 뿌요들을 제거
        if (indexX >= 0 && indexX <= 5 && indexY < 11 && puyoMap[indexX][indexY+1]!=null && puyoMap[indexX][indexY+1].getType() == puyo.getType() && samePuyoChecker[indexX][indexY+1]) {
            deletePuyos(puyo, indexX, indexY+1);
        }
        if (indexX >= 0 && indexX <= 5 && indexY > 0 && indexY < 12 && puyoMap[indexX][indexY-1]!=null && puyoMap[indexX][indexY-1].getType() == puyo.getType() && samePuyoChecker[indexX][indexY-1]) {
            deletePuyos(puyo, indexX, indexY-1);
        }
        if (indexX >= 0 && indexX <= 4 && indexY < 12 && puyoMap[indexX+1][indexY]!=null && puyoMap[indexX+1][indexY].getType() == puyo.getType() && samePuyoChecker[indexX+1][indexY]) {
            deletePuyos(puyo, indexX+1, indexY);
        }
        if (indexX >= 1 && indexX <= 5 && indexY < 12 && puyoMap[indexX-1][indexY]!=null && puyoMap[indexX-1][indexY].getType() == puyo.getType() && samePuyoChecker[indexX-1][indexY]) {
            deletePuyos(puyo, indexX-1, indexY);
        }

    }

    void splashObstructPuyo(int indexX, int indexY) {
        var iAm = roundRepository.getIAm();
        var groundService = mapService.getGroundService(iAm);
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


    // deletePuyo() 이후 공중에 떠있는 블록들을 아래로 정렬한다.
    void dropPuyos() {
        var iAm = roundRepository.getIAm();
        var groundService = mapService.getGroundService(iAm);
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

    public void changeEndFlag() {
        roundRepository.changeEnd();
    }
}
