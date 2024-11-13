package puyopuyo.Panel.map.subpanel.score;

import puyopuyo.Panel.map.subpanel.ground.Puyo;

import javax.swing.*;

public class ScoreRepository {
    /** 필요한 아이템 */
    private final Puyo nextLeftPuyo1P = new Puyo(5);
    private final Puyo nextRightPuyo1P = new Puyo(5);
    private final Puyo nextLeftPuyo2P = new Puyo(5);
    private final Puyo nextRightPuyo2P = new Puyo(5);
    // 남은 라운드랑 총 라운드 추가
    private int currentRound = 1;
    private final int totalRounds = 5;

    private JLabel roundCountLabel = new JLabel("1");
    private JLabel scoreLabel1P = new JLabel("0");
    private JLabel scoreLabel2P = new JLabel("0");
    private JLabel garbagePuyoCount1P = new JLabel("None");
    private JLabel garbagePuyoCount2P = new JLabel("None");
    private JLabel timer = new JLabel("0");

    // setter
    public void setRoundCount(int count) {roundCountLabel.setText(String.valueOf(count));}
    public void setScore1P(int score) {scoreLabel1P.setText(String.valueOf(score));}
    public void setScore2P(int score) {scoreLabel2P.setText(String.valueOf(score));}
    public void setGarbagePuyoCount1P(int count) {
        garbagePuyoCount1P.setText(String.valueOf(count));
    }
    public void setGarbagePuyoCount2P(int count) {
        garbagePuyoCount2P.setText(String.valueOf(count));
    }
    public void setTimer(int time) {timer.setText(String.valueOf(time));}


    // 남은 라운드 추가
    public int getCurrentRound() {return currentRound;}
    public void nextRound() {if (currentRound < totalRounds) {currentRound++;}}
    public int getRemainingRounds() {return totalRounds - currentRound;}

    // getter
    public Puyo getNextLeftPuyo1P() {return nextLeftPuyo1P;}
    public Puyo getNextRightPuyo1P() {return nextRightPuyo1P;}
    public Puyo getNextLeftPuyo2P() {return nextLeftPuyo2P;}
    public Puyo getNextRightPuyo2P() {return nextRightPuyo2P;}
    public JLabel getRoundCountLabel() {return roundCountLabel;}
    public JLabel getScoreLabel1P() {return scoreLabel1P;}
    public JLabel getScoreLabel2P() {return scoreLabel2P;}
    public JLabel getGarbagePuyoCount1P() {return garbagePuyoCount1P;}
    public JLabel getGarbagePuyoCount2P() {return garbagePuyoCount2P;}
    public JLabel getTimer() {return timer;}
}
