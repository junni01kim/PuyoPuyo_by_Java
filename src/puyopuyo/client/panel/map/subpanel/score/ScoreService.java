package puyopuyo.client.panel.map.subpanel.score;

import puyopuyo.client.panel.map.subpanel.ground.Puyo;

import javax.swing.*;
import java.awt.*;

/**
 * 게임이 진행되는 동안 플레이어들의 점수를 관리하는 패널 서비스이다. <br>
 *
 * 패널 관리 책임을 가지며, MVC 패턴의 Model의 역할을 한다.
 *
 * 코드의 복잡성을 줄이기 위해 합성관계로 구현하였다.
 * 
 * TODO: 수정해야 할 사항 많음
 */
public class ScoreService {
    private final ScorePanel scorePanel;
    private final ScoreRepository scoreRepository = new ScoreRepository();

    public ScoreService(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    /**
     * 모듈화 하다보니 사용하는 곳이 하나 구간 밖에 없어서 축약함
     */
    void setLabel(JLabel label, int x, int y, int width, int fontSize) {
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setLocation(x, y);
        label.setSize(width, 30);
        label.setForeground(Color.white);
        scorePanel.add(label);
    }

    /**
     * 모듈화 하다보니 사용하는 곳이 하나 구간 밖에 없어서 축약함
     */
    void setLabel(JLabel label, int x) {
        label.setSize(100, 30);
        label.setLocation(x, 600);
        label.setForeground(Color.white);
        scorePanel.add(label);
    }

    void setLabel(JLabel label, int x, int y) {
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setLocation(x, y);
        label.setSize(100, 30);
        label.setForeground(Color.white);
        scorePanel.add(label);
    }

    /**
     * 모듈화 하다보니 사용하는 곳이 하나 구간 밖에 없어서 축약함
     */
    void setPosition(JLabel label, int x) {
        label.setLocation(x, 100);
        scorePanel.add(label);
    }

    /**
     * 방해 뿌요 개수를 수정하는 함수
     * @param player
     * @param count
     */
    public void setGarbagePuyoCount(int player, int count) {
        if(player == 1) scoreRepository.setGarbagePuyoCount2P(count);
        else scoreRepository.setGarbagePuyoCount1P(count);
    }

    /**
     * 플레이어 점수를 수정하는 함수
     * @param player
     * @param score
     */
    public void setScore(int player, int score) {
        if(player == 1) scoreRepository.setScore2P(score);
        else scoreRepository.setScore1P(score);
    }

    /**
     * 타이머를 수정하는 함수
     * @param timer
     */
    public void setTimer(int timer) {
        scoreRepository.setTimer(timer);
    }

    // getter
    public JLabel getRoundCountLabel() {
        return scoreRepository.getRoundCountLabel();
    }

    public JLabel getScoreLabel(int player) {
        if(player == 1) return scoreRepository.getScoreLabel1P();
        else return scoreRepository.getScoreLabel2P();
    }

    public JLabel getGarbagePuyoCount(int player) {
        if(player == 1) return scoreRepository.getGarbagePuyoCount1P();
        else return scoreRepository.getGarbagePuyoCount2P();
    }

    public Puyo getNextLeftPuyo(int iAm) {
        if(iAm == 1) return scoreRepository.getNextLeftPuyo1P();
        else return scoreRepository.getNextLeftPuyo2P();
    }

    public Puyo getNextRightPuyo(int iAm) {
        if(iAm == 1) return scoreRepository.getNextRightPuyo1P();
        else return scoreRepository.getNextRightPuyo2P();
    }

    public JLabel getTimer() {
        return scoreRepository.getTimer();
    }

    /**
     * 수신받은 nextPuyo 정보를 그리는 함수
     * @param player
     * @param nextPuyo
     */
    public void drawNextPuyo(int player, int[] nextPuyo) {
        var nextLeftPuyo = getNextLeftPuyo(player);
        var nextRightPuyo = getNextRightPuyo(player);

        // type에 맞는 아이콘을 사용한다.
        nextLeftPuyo.setIcon(Puyo.getPuyoIcon()[nextPuyo[0]]);
        nextRightPuyo.setIcon(Puyo.getPuyoIcon()[nextPuyo[1]]);
    }
}
