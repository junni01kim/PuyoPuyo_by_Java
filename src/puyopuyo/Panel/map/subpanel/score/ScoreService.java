package puyopuyo.Panel.map.subpanel.score;

import puyopuyo.Panel.map.subpanel.ground.Puyo;

import javax.swing.*;
import java.awt.*;

/**
 * 게임이 진행되는 동안 플레이어들의 점수를 관리하는 패널 서비스이다. <br>
 *
 * 패널 관리 책임을 가지며, MVC 패턴의 Model의 역할을 한다.
 *
 * 코드의 복잡성을 줄이기 위해 합성관계로 구현하였다.
 */
public class ScoreService {
    private final ScorePanel scorePanel;
    private final ScoreRepository scoreRepository = new ScoreRepository();

    public ScoreService(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    /**
     * PuyoLogic을 기반으로 다음 뿌요의 색상을 변경하는 함수
     */
    public void changeNextPuyo(int iAm, int[] puyoLogic, int puyoIndex) {
        int nextLeftControlPuyoType = (puyoLogic[(puyoIndex)%puyoLogic.length])/10;
        int nextRightControlPuyoType = (puyoLogic[(puyoIndex)%puyoLogic.length])%10;

        var nextLeftPuyo = getNextLeftPuyo(iAm);
        var nextRightPuyo = getNextRightPuyo(iAm);

        nextLeftPuyo.setType(nextLeftControlPuyoType);
        nextRightPuyo.setType(nextRightControlPuyoType);

        // type에 맞는 아이콘을 사용한다.
        nextLeftPuyo.setIcon(Puyo.getPuyoIcon()[nextLeftControlPuyoType]);
        nextRightPuyo.setIcon(Puyo.getPuyoIcon()[nextRightControlPuyoType]);
    }

    // setter
    /**
     * 모듈화 하다보니 사용하는 곳이 하나 구간 밖에 없어서 축약함
     */
    void setLabel(JLabel label, int x, int y, int width, int fontSize) {
        label.setFont(new Font("Serif", Font.BOLD, fontSize));
        label.setLocation(x, y);
        label.setSize(width, 30);
        scorePanel.add(label);
    }

    /**
     * 모듈화 하다보니 사용하는 곳이 하나 구간 밖에 없어서 축약함
     */
    void setLabel(JLabel label, int x) {
        label.setSize(100, 30);
        label.setLocation(x, 600);
        scorePanel.add(label);
    }

    /**
     * 모듈화 하다보니 사용하는 곳이 하나 구간 밖에 없어서 축약함
     */
    void setPosition(JLabel label, int x) {
        label.setLocation(x, 100);
        scorePanel.add(label);
    }

    void setLabel(JLabel label, int x, int y) {
        label.setFont(new Font("Serif", Font.BOLD, 15));
        label.setLocation(x, y);
        label.setSize(100, 30);
        scorePanel.add(label);
    }

    // getter
    public JLabel getRoundCountLabel() {
        return scoreRepository.getRoundCountLabel();
    }

    public JLabel getScoreLabel(int player) {
        if(player == 1) return scoreRepository.getScoreLabel1P();
        else return scoreRepository.getScoreLabel2P();
    }

    public JLabel getNumberOfGarbagePuyoLabel(int player) {
        if(player == 1) return scoreRepository.getNumberOfGarbagePuyoLabel1P();
        else return scoreRepository.getNumberOfGarbagePuyoLabel2P();
    }

    public Puyo getNextLeftPuyo(int iAm) {
        if(iAm == 1) return scoreRepository.getNextLeftPuyo1P();
        else return scoreRepository.getNextLeftPuyo2P();
    }

    public Puyo getNextRightPuyo(int iAm) {
        if(iAm == 1) return scoreRepository.getNextRightPuyo1P();
        else return scoreRepository.getNextRightPuyo2P();
    }
}
