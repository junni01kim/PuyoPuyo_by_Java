package puyopuyo.Panel.map.subpanel.score;

import puyopuyo.Panel.map.subpanel.ground.Puyo;

import javax.swing.*;

public class ScoreRepository {
    /** 필요한 아이템 */
    private final Puyo nextLeftPuyo1P = new Puyo(5);
    private final Puyo nextRightPuyo1P = new Puyo(5);
    private final Puyo nextLeftPuyo2P = new Puyo(5);
    private final Puyo nextRightPuyo2P = new Puyo(5);

    private JLabel roundCountLabel = new JLabel("1");
    private JLabel scoreLabel1P = new JLabel("0");
    private JLabel scoreLabel2P = new JLabel("0");
    private JLabel numberOfGarbagePuyoLabel1P = new JLabel("None");
    private JLabel numberOfGarbagePuyoLabel2P = new JLabel("None");

    // setter
    public void setRoundCount(int count) {roundCountLabel.setText(String.valueOf(count));}
    public void setScore1P(int score) {scoreLabel1P.setText(String.valueOf(score));}
    public void setScore2P(int score) {scoreLabel2P.setText(String.valueOf(score));}
    public void setNumberOfGarbagePuyoLabel1P(int count) {numberOfGarbagePuyoLabel1P.setText(String.valueOf(count));}
    public void setNumberOfGarbagePuyoLabel2P(int count) {numberOfGarbagePuyoLabel2P.setText(String.valueOf(count));}

    // getter
    public Puyo getNextLeftPuyo1P() {return nextLeftPuyo1P;}
    public Puyo getNextRightPuyo1P() {return nextRightPuyo1P;}
    public Puyo getNextLeftPuyo2P() {return nextLeftPuyo2P;}
    public Puyo getNextRightPuyo2P() {return nextRightPuyo2P;}
    public JLabel getRoundCountLabel() {return roundCountLabel;}
    public JLabel getScoreLabel1P() {return scoreLabel1P;}
    public JLabel getScoreLabel2P() {return scoreLabel2P;}
    public JLabel getNumberOfGarbagePuyoLabel1P() {return numberOfGarbagePuyoLabel1P;}
    public JLabel getNumberOfGarbagePuyoLabel2P() {return numberOfGarbagePuyoLabel2P;}
}
