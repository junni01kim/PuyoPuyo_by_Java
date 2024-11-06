package puyopuyo.score;

import puyopuyo.puyo.Puyo;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private final ScoreService scoreService = new ScoreService();

    public ScorePanel() {
        setUi();
    }

    private void setUi() {
        setBackground(Color.YELLOW);
        setSize(300, 750);
        setLayout(null);

        var nextLeftPuyo1P = scoreService.getNextLeftPuyo(1);
        var nextRightPuyo1P = scoreService.getNextRightPuyo(1);
        var nextLeftPuyo2P = scoreService.getNextLeftPuyo(2);
        var nextRightPuyo2P = scoreService.getNextRightPuyo(2);

        // 다음 뿌요 색상 1P
        setPosition(nextLeftPuyo1P, 12+getWidth()/4-60);
        setPosition(nextRightPuyo1P, 12+getWidth()/4);

        // 다음 뿌요 색상 2P
        setPosition(nextLeftPuyo2P, getWidth()/4*3-60-12);
        setPosition(nextRightPuyo2P, getWidth()/4*3-12);

        var roundCountLabel = scoreService.getRoundCountLabel();

        // 라운드 표시 (변동되는 숫자)
        setLabel(roundCountLabel, getWidth()/2-75, 43, 300, 27);

        // 라운드 표시 (고정 텍스트)
        setLabel(new JLabel("R O U N D"), getWidth()/2-50, 43, 300, 27);

        // 점수 표시 (고정 텍스트)
        setLabel(new JLabel("S C O R E"), getWidth()/2-29, 175, 100, 15);

        var scoreLabel1P = scoreService.getScoreLabel(1);
        var scoreLabel2P = scoreService.getScoreLabel(2);

        // 점수 표시 1P (변동되는 숫자)
        setLabel(scoreLabel1P, getWidth()/2, 205);

        // 점수 표시 2P (변동되는 숫자)
        setLabel(scoreLabel2P, getWidth()/2, 235);

        // 타이머 표시 (타이틀)
        setLabel(new JLabel("T I M E"), getWidth()/2-20, 360);

        // 타이머 표시 (변동되는 숫자)
        setLabel(new JLabel("0"), getWidth()/2, 400);

        var numberOfGarbagePuyoLabel1P = scoreService.getNumberOfGarbagePuyoLabel(1);
        var numberOfGarbagePuyoLabel2P = scoreService.getNumberOfGarbagePuyoLabel(2);

        // 전달할 방해뿌요 표시 1P (변동되는 숫자)
        // TODO: 넘길 방해 뿌요 개발 완료 시 함께 수정
        setLabel(numberOfGarbagePuyoLabel1P, getWidth()/2-75);

        // 전달할 방해뿌요 표시 2P (변동되는 숫자)
        // TODO: 넘길 방해 뿌요 개발 완료 시 함께 수정
        setLabel(numberOfGarbagePuyoLabel2P, getWidth()/2+50);

        // TODO: 넘길 방해 뿌요 수 그림
        // TODO: 남은 라운드 현재 라운드 수
        // TODO: 자신의 점수
    }

    public ScoreService getScoreService() {
        return scoreService;
    }

    public void changeNextPuyo(int iAm, int[] puyoLogic, int puyoIndex) {
        int nextLeftControlPuyoType = (puyoLogic[(puyoIndex)%puyoLogic.length])/10;
        int nextRightControlPuyoType = (puyoLogic[(puyoIndex)%puyoLogic.length])%10;

        var nextLeftPuyo = scoreService.getNextLeftPuyo(iAm);
        var nextRightPuyo = scoreService.getNextRightPuyo(iAm);

        nextLeftPuyo.setType(nextLeftControlPuyoType);
        nextRightPuyo.setType(nextRightControlPuyoType);

        // type에 맞는 아이콘을 사용한다.
        nextLeftPuyo.setIcon(Puyo.getPuyoIcon()[nextLeftControlPuyoType]);
        nextRightPuyo.setIcon(Puyo.getPuyoIcon()[nextRightControlPuyoType]);
    }

    /**
     * 모듈화 하다보니 사용하는 곳이 하나 구간 밖에 없어서 축약함
     */
    void setLabel(JLabel label, int x, int y, int width, int fontSize) {
        label.setFont(new Font("Serif", Font.BOLD, fontSize));
        label.setLocation(x, y);
        label.setSize(width, 30);
        add(label);
    }

    /**
     * 모듈화 하다보니 사용하는 곳이 하나 구간 밖에 없어서 축약함
     */
    void setLabel(JLabel label, int x) {
        label.setSize(100, 30);
        label.setLocation(x, 600);
        add(label);
    }

    /**
     * 모듈화 하다보니 사용하는 곳이 하나 구간 밖에 없어서 축약함
     */
    void setPosition(JLabel label, int x) {
        label.setLocation(x, 100);
        add(label);
    }

    void setLabel(JLabel label, int x, int y) {
        label.setFont(new Font("Serif", Font.BOLD, 15));
        label.setLocation(x, y);
        label.setSize(100, 30);
        add(label);
    }
}