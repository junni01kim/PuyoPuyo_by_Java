package puyopuyo.client.panel.map.subpanel.score;

import puyopuyo.resource.GameImageIcon;

import javax.swing.*;
import java.awt.*;

/**
 * 게임이 진행되는 동안 플레이어들의 점수를 관리하는 패널이다. <br>
 *
 * 시각적인 책임을 가지며, MVC 패턴의 View의 역할을 한다.
 */
public class ScorePanel extends JPanel {
    /** @property ScorePanel에서 진행되는 책임들을 수행하는 객체 */
    private final ScoreService scoreService = new ScoreService(this);

    public ScorePanel() {
        setUi();
    }

    /**
     * 패널의 백그라운드 이미지를 그리기 위한 함수 <br>
     *
     * 전체 화면 크기로 이미지가 배치 된다.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.drawImage(GameImageIcon.scorePanel.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    /**
     * 화면 설정을 하는 함수이다. <br>
     *
     * 1. 1P, 2P의 다음 뿌요를 지정한다. <br>
     * 2. 현재 라운드를 명시한다. <br>
     * 3. 현재 점수를 명시한다. <br>
     * 4. 진행 시간을 명시한다. <br>
     * 5. 상대에게 전달할 방해 뿌요를 명시한다. <br>
     * 6. 라운드 승리 수를 명시한다.
     */
    private void setUi() {
        setBackground(new Color(0, 0, 0, 255)); //TODO: 배경 완성되면 제거할 것
        setSize(300, 750);
        setLocation(490,60);
        setLayout(null);

        var nextLeftPuyo1P = scoreService.getNextLeftPuyo(1);
        var nextRightPuyo1P = scoreService.getNextRightPuyo(1);
        var nextLeftPuyo2P = scoreService.getNextLeftPuyo(2);
        var nextRightPuyo2P = scoreService.getNextRightPuyo(2);

        // 다음 뿌요 색상 1P
        scoreService.setPosition(nextLeftPuyo1P, 12+getWidth()/4-60);
        scoreService.setPosition(nextRightPuyo1P, 12+getWidth()/4);

        // 다음 뿌요 색상 2P
        scoreService.setPosition(nextLeftPuyo2P, getWidth()/4*3-60-12);
        scoreService.setPosition(nextRightPuyo2P, getWidth()/4*3-12);

        // 현재 라운드를 명시
        var roundCountLabel = scoreService.getRoundCountLabel();

        // 라운드 표시 (변동되는 숫자)
        scoreService.setLabel(roundCountLabel, getWidth()/2-75, 43, 300, 27);

        // 라운드 표시 (고정 텍스트)
        // TODO: 라운드 수정할 것
        scoreService.setLabel(new JLabel("R O U N D"), getWidth()/2-50, 43, 300, 27);

        // 1P 점수 표시 (고정 텍스트)
        scoreService.setLabel( new JLabel("1P SCORE"), getWidth() / 2 - 130, 175, 100, 15);

        // 2P 점수 표시 (고정 텍스트)
        scoreService.setLabel( new JLabel("2P SCORE"), getWidth()/2 + 55, 175, 100, 15);

        var scoreLabel1P = scoreService.getScoreLabel(1);
        var scoreLabel2P = scoreService.getScoreLabel(2);

        // 점수 표시 1P (변동되는 숫자)
        scoreService.setLabel(scoreLabel1P, 50, 205);

        // 점수 표시 2P (변동되는 숫자)
        scoreService.setLabel(scoreLabel2P, getWidth() - 60, 205);

        // 타이머 표시 (타이틀)
        scoreService.setLabel(new JLabel("T I M E"), getWidth()/2-20, 360);

        // 타이머 표시 (변동되는 숫자)
        var timer = scoreService.getTimer();
        timer.setForeground(Color.YELLOW);
        scoreService.setLabel(timer, getWidth()/2, 400);

        var numberOfGarbagePuyoLabel1P = scoreService.getGarbagePuyoCount(1);
        var numberOfGarbagePuyoLabel2P = scoreService.getGarbagePuyoCount(2);

        // 전달할 방해뿌요 표시 1P (변동되는 숫자)
        // TODO: 넘길 방해 뿌요 개발 완료 시 함께 수정
        scoreService.setLabel(numberOfGarbagePuyoLabel1P, getWidth()/2-75);

        // 전달할 방해뿌요 표시 2P (변동되는 숫자)
        // TODO: 넘길 방해 뿌요 개발 완료 시 함께 수정
        scoreService.setLabel(numberOfGarbagePuyoLabel2P, getWidth()/2+50);

        // TODO: 넘길 방해 뿌요 수 그림

        // TODO: 남은 라운드 현재 라운드 수
    }

    // getter
    public ScoreService getScoreService() {
        return scoreService;
    }
}