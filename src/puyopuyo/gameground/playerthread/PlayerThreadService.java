package puyopuyo.gameground.playerthread;

import puyopuyo.Puyo;
import puyopuyo.ScorePanel;
import puyopuyo.game.GameService;
import puyopuyo.game.roundthread.RoundThreadService;
import puyopuyo.gameground.GameGroundService;

import static java.lang.Thread.sleep;

public class PlayerThreadService {
    private final PlayerThread playerThread;
    private final GameGroundService gameGroundService;
    private final RoundThreadService roundThreadService;
    private final GameService gameService;
    private final ScorePanel scorePanel; // TODO: 서비스 추가하기

    private final PlayerThreadRepository playerThreadRepository;

    public int getIam() {
        return playerThreadRepository.getIAm();
    }

    public int getGarbagePuyo() {
        return playerThreadRepository.getGarbagePuyo();
    }

    public void start() {
        playerThread.start();
    }

    public void setColorChecker(boolean[] colorChecker) {
        playerThreadRepository.setColorChecker(colorChecker);
    }

    public void setGarbagePuyo(int numberOfGarbagePuyo) {
        playerThreadRepository.setGarbagePuyo(numberOfGarbagePuyo);
    }

    public PlayerThreadService(PlayerThread playerThread, GameGroundService gameGroundService, RoundThreadService roundThreadService, GameService gameService, ScorePanel scorePanel, int iAm) {
        this.playerThread = playerThread;
        this.gameGroundService = gameGroundService;
        this.roundThreadService = roundThreadService;
        this.gameService = gameService;
        this.scorePanel = scorePanel;
        playerThreadRepository = new PlayerThreadRepository(iAm);
    }

    public void changeOneWin() {
        var oneWin = playerThreadRepository.getOneWin();
        playerThreadRepository.setOneWin(!oneWin);
    }

    // 다음 뿌요로 전환시켜주는 함수이다.
    void nextPuyo() {
        var puyoLogic = roundThreadService.getPuyoLogic();
        var puyoIndex = playerThreadRepository.getPuyoIndex();

        var leftPuyo = gameGroundService.getLeftPuyo();
        var rightPuyo = gameGroundService.getRightPuyo();

        int puyo1Type = (puyoLogic[puyoIndex%puyoLogic.length]) / 10;
        int puyo2Type = (puyoLogic[(puyoIndex++)%puyoLogic.length]) % 10;
        playerThreadRepository.setPuyoIndex(puyoIndex);

        leftPuyo.setType(puyo1Type);
        rightPuyo.setType(puyo2Type);

        // type에 맞는 아이콘을 사용한다.
        leftPuyo.setIcon(Puyo.getPuyoIcon()[puyo1Type]);
        rightPuyo.setIcon(Puyo.getPuyoIcon()[puyo2Type]);

        leftPuyo.setLocation(140,10);
        rightPuyo.setLocation(200,10);

        leftPuyo.setVisible(true);
        rightPuyo.setVisible(true);

        changeNextPuyo();
    }

    void changeNextPuyo() {
        var puyoLogic = roundThreadService.getPuyoLogic();
        var puyoIndex = playerThreadRepository.getPuyoIndex();
        var iAm = playerThreadRepository.getIAm();

        int nextLeftControlPuyoType = (puyoLogic[(puyoIndex)%puyoLogic.length])/10;
        int nextRightControlPuyoType = (puyoLogic[(puyoIndex)%puyoLogic.length])%10;

        if(iAm == 1) {
            scorePanel.getNextLeftControlPuyo1P().setType(nextLeftControlPuyoType);
            scorePanel.getNextRightControlPuyo1P().setType(nextRightControlPuyoType);

            // type에 맞는 아이콘을 사용한다.
            scorePanel.getNextLeftControlPuyo1P().setIcon(Puyo.getPuyoIcon()[nextLeftControlPuyoType]);
            scorePanel.getNextRightControlPuyo1P().setIcon(Puyo.getPuyoIcon()[nextRightControlPuyoType]);
        }
        else {
            scorePanel.getNextLeftControlPuyo2P().setType(nextLeftControlPuyoType);
            scorePanel.getNextRightControlPuyo2P().setType(nextRightControlPuyoType);

            // type에 맞는 아이콘을 사용한다.
            scorePanel.getNextLeftControlPuyo2P().setIcon(Puyo.getPuyoIcon()[nextLeftControlPuyoType]);
            scorePanel.getNextRightControlPuyo2P().setIcon(Puyo.getPuyoIcon()[nextRightControlPuyoType]);
        }
    }

    // 한 뿌요의 위치가 정해지도 다음 뿌요의 위치를 보여주는 함수
    void dropAnotherPuyo(Puyo anotherPuyo) {
        var puyoMap = playerThreadRepository.getPuyoMap();

        int indexY = 11;
        while(true) {
            if(puyoMap[anotherPuyo.PixelXToindex()][indexY]==null) {
                anotherPuyo.setLocation(anotherPuyo.getX(),Puyo.indexXToPixel(indexY));
                puyoMap[anotherPuyo.PixelXToindex()][indexY] = new Puyo(anotherPuyo.getType(), anotherPuyo.PixelXToindex(), indexY);
                puyoMap[anotherPuyo.PixelXToindex()][indexY].setVisible(true);
                gameGroundService.add(puyoMap[anotherPuyo.PixelXToindex()][indexY]);
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
        var puyoMap = playerThreadRepository.getPuyoMap();
        var leftPuyo = gameGroundService.getLeftPuyo();
        var rightPuyo = gameGroundService.getRightPuyo();
        var numberOfSamePuyo = playerThreadRepository.getNumberOfSamePuyo();

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
        var puyoMap = playerThreadRepository.getPuyoMap();
        var garbagePuyo = playerThreadRepository.getGarbagePuyo();

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
                        gameGroundService.add(puyoMap[i][j - p]);
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
                    gameGroundService.add(puyoMap[randomVariable][j]);
                    break;
                }
            }
        }
        playerThreadRepository.setGarbagePuyo(0);
        if(playerThreadRepository.getIAm() == 1)
            scorePanel.getNumberOfGarbagePuyoLabel1P().setText("0");
        else
            scorePanel.getNumberOfGarbagePuyoLabel2P().setText("0");
    }

    // CheckNumberOfSamePuyo와 deletePuyos에 필요한 변수들을 초기화 하는 함수
    void initializeCheckNumberOfSamePuyoVariable() {
        var puyoMap = playerThreadRepository.getPuyoMap();

        playerThreadRepository.setNumberOfSamePuyo(0);

        for(int i=0; i<puyoMap.length; i++)
            for(int j=0; j<puyoMap[i].length; j++)
                playerThreadRepository.setSamePuyoChecker(false, i, j);
    }

    void initializeScoreVariable() {
        var colorChecker = playerThreadRepository.getColorChecker();

        for(int i=0;i<Puyo.getPuyoIcon().length;i++)
            colorChecker[i]=false;

        playerThreadRepository.setPuyoRemovedSum(0);
        playerThreadRepository.setPuyoConnect(0);
        playerThreadRepository.setPuyoCombo(0);
        playerThreadRepository.setPuyoColor(0);
    }

    void printScore() {
        var iAm = playerThreadRepository.getIAm();
        var score = playerThreadRepository.getScore();

        if(iAm == 1)
            scorePanel.getScoreLabel1P().setText(Integer.toString(score));
        else
            scorePanel.getScoreLabel2P().setText(Integer.toString(score));
    }

    void scanNumberOfSamePuyo() {
        var puyoMap = playerThreadRepository.getPuyoMap();
        var samePuyoChecker = playerThreadRepository.getSamePuyoChecker();
        var numberOfSamePuyo = playerThreadRepository.getNumberOfSamePuyo();
        var colorChecker = playerThreadRepository.getColorChecker();
        var puyoColor = playerThreadRepository.getPuyoColor();
        var puyoConnect = playerThreadRepository.getPuyoConnect();
        var puyoRemovedSum = playerThreadRepository.getPuyoRemovedSum();
        var puyoCombo = playerThreadRepository.getPuyoCombo();
        var score = playerThreadRepository.getScore();
        var iAm = playerThreadRepository.getIAm();

        var leftPuyo = gameGroundService.getLeftPuyo();
        var rightPuyo = gameGroundService.getRightPuyo();

        var playerThread1PService = gameService.getGameGround1P().getService().getPlayerThread().getPlayerThreadService();
        var playerThread2PService = gameService.getGameGround2P().getService().getPlayerThread().getPlayerThreadService();

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
                                playerThreadRepository.setPuyoColor(puyoColor++);
                            }
                            puyoConnect = numberOfSamePuyo;
                            playerThreadRepository.setPuyoConnect(puyoConnect);
                            puyoRemovedSum += puyoConnect;
                            playerThreadRepository.setPuyoRemovedSum(puyoRemovedSum);

                            //int plusScore = puyoRemovedSum * (puyoComboBonus[++puyoCombo] + puyoColorBonus[puyoColor] + puyoConnectBonus[puyoConnect]) * 10;
                            int plusScore = puyoRemovedSum * (++puyoCombo + puyoColor + puyoConnect) * 10;
                            playerThreadRepository.setPuyoCombo(puyoCombo);

                            score += plusScore;
                            playerThreadRepository.setScore(score);

                            printScore();
                            deletePuyos(puyo, i, j);
                            if(iAm == 1) {
                                playerThread2PService.setGarbagePuyo(plusScore/70);
                                scorePanel.getNumberOfGarbagePuyoLabel2P().setText(Integer.toString(plusScore/70));
                            }
                            else {
                                playerThread1PService.setGarbagePuyo(plusScore/70);
                                scorePanel.getNumberOfGarbagePuyoLabel1P().setText(Integer.toString(plusScore/70));
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
        var samePuyoChecker = playerThreadRepository.getSamePuyoChecker();
        var numberOfSamePuyo = playerThreadRepository.getNumberOfSamePuyo();
        var puyoMap = playerThreadRepository.getPuyoMap();

        // 예외처리: 뿌요1과 2가 동시에 속해서 사라지는 경우 anotherPuyo는 존재하지 않음
        if(samePuyoChecker[indexX][indexY])
            return;

        numberOfSamePuyo++;
        playerThreadRepository.setPuyoCombo(numberOfSamePuyo);

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
        var puyoMap = playerThreadRepository.getPuyoMap();
        playerThreadRepository.setSamePuyoChecker(false, indexX, indexY);
        var samePuyoChecker = playerThreadRepository.getSamePuyoChecker();

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
        var puyoMap = playerThreadRepository.getPuyoMap();

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
        var puyoMap = playerThreadRepository.getPuyoMap();


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

    public Puyo[][] getPuyoMap() {
        return playerThreadRepository.getPuyoMap();
    }
}
