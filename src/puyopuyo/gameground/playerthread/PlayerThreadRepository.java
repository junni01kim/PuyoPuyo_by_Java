package puyopuyo.gameground.playerthread;

import puyopuyo.Puyo;

public class PlayerThreadRepository {
    // nextPuyo()에서 사용한다.
    private int puyoIndex = 0;

    private final int iAm;
    boolean oneWin = false;

    private Puyo[][] puyoMap = new Puyo[6][12];

    // 폭발 계산 용 변수
    private boolean[][] samePuyoChecker = new boolean[6][12];
    private int numberOfSamePuyo = 0;
    private boolean[] colorChecker = null;

    private int score = 0;

    // 점수 계산 용 변수들
    // checkNumberOfSamePuyo()에서 사용한다.
    private int puyoRemovedSum;
    private int puyoConnect;
    private int puyoCombo;
    private int puyoColor;

    private int garbagePuyo = 0;

    public PlayerThreadRepository(int iAm) {
        this.iAm = iAm;
    }

    // setter
    public void setPuyoIndex(int i) { puyoIndex = i; }

    public void setOneWin(boolean oneWin) { this.oneWin = oneWin; }

    public void setPuyoMap(Puyo[][] puyoMap) { this.puyoMap = puyoMap; }

    public void setSamePuyoChecker(boolean[][] samePuyoChecker) { this.samePuyoChecker = samePuyoChecker; }
    public void setNumberOfSamePuyo(int i) { numberOfSamePuyo = i; }
    public void setColorChecker(boolean[] colorChecker) { this.colorChecker = colorChecker; }

    public void setScore(int i) { score = i; }

    public void setPuyoRemovedSum(int i) { puyoRemovedSum = i; }
    public void setPuyoConnect(int i) { puyoConnect = i; }
    public void setPuyoCombo(int i) { puyoCombo = i; }
    public void setPuyoColor(int i) { puyoColor = i; }

    public void setGarbagePuyo(int i) { garbagePuyo = i; }


    // getter
    public int getPuyoIndex() { return puyoIndex; }

    public int getIAm() { return iAm; }
    public boolean getOneWin() { return oneWin; }

    public Puyo[][] getPuyoMap() { return puyoMap; }

    public boolean[][] getSamePuyoChecker() { return samePuyoChecker; }
    public int getNumberOfSamePuyo() { return numberOfSamePuyo; }
    public boolean[] getColorChecker() { return colorChecker; }

    public int getScore() { return score; }

    public int getPuyoRemovedSum() { return puyoRemovedSum; }
    public int getPuyoConnect() { return puyoConnect; }
    public int getPuyoCombo() { return puyoCombo; }
    public int getPuyoColor() { return puyoColor; }

    public int getGarbagePuyo() { return garbagePuyo; }

    public boolean isOneWin() { return oneWin; }
}
