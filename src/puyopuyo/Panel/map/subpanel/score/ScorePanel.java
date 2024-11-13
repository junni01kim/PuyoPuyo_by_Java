package puyopuyo.Panel.map.subpanel.score;

import javax.swing.*;
import java.awt.*;

/**
 * 게임이 진행되는 동안 플레이어들의 점수를 관리하는 패널이다. <br>
 *
 * 시각적인 책임을 가지며, MVC 패턴의 View의 역할을 한다.
 */
public class ScorePanel extends JPanel {
    private final ScoreService scoreService = new ScoreService(this);
    private JLabel roundCountLabel;
    private JLabel remainingRoundsLabel;

    public ScorePanel() {
        setUi();
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
        setBackground(new Color(0, 0, 0, 0)); // 완전 투명 배경 설정
        setSize(300, 750);
        setLocation(490, 60);
        setLayout(null);
        
        var nextLeftPuyo1P = scoreService.getNextLeftPuyo(1);
        var nextRightPuyo1P = scoreService.getNextRightPuyo(1);
        var nextLeftPuyo2P = scoreService.getNextLeftPuyo(2);
        var nextRightPuyo2P = scoreService.getNextRightPuyo(2);

        // 다음 뿌요 색상 1P
        scoreService.setPosition(nextLeftPuyo1P, 12 + getWidth() / 4 - 60);
        scoreService.setPosition(nextRightPuyo1P, 12 + getWidth() / 4);
        add(nextLeftPuyo1P);
        add(nextRightPuyo1P);

        // 다음 뿌요 색상 2P
        scoreService.setPosition(nextLeftPuyo2P, getWidth() / 4 * 3 - 60 - 12);
        scoreService.setPosition(nextRightPuyo2P, getWidth() / 4 * 3 - 12);
        add(nextLeftPuyo2P);
        add(nextRightPuyo2P);

        // 반투명한 배경을 가지는 패널 생성
        JPanel transparentPanel = new JPanel();
        transparentPanel.setBackground(new Color(0, 0, 0, 100)); // 알파값 100으로 반투명 설정
        transparentPanel.setBounds(0, 0, 300, 750);
        transparentPanel.setLayout(null);
        add(transparentPanel);

        // 현재 라운드를 명시
        var roundCountLabel = scoreService.getRoundCountLabel();
        roundCountLabel.setForeground(Color.white);

        // 라운드 표시 (변동되는 숫자)
        scoreService.setLabel(roundCountLabel, getWidth() / 2 - 75, 43, 300, 27);
        transparentPanel.add(roundCountLabel);

        // 라운드 표시 (고정 텍스트)
        JLabel roundLabel = new JLabel("R O U N D");
        roundLabel.setForeground(Color.white);
        scoreService.setLabel(roundLabel, getWidth() / 2 - 50, 43, 300, 27);
        transparentPanel.add(roundLabel);

        // 현재 라운드 라벨 설정
        roundCountLabel = new JLabel(scoreService.getCurrentRound() + " R O U N D");
        roundCountLabel.setForeground(Color.WHITE);
        scoreService.setLabel(roundCountLabel, getWidth() / 2 - 75, 43, 300, 27);
        add(roundCountLabel);

        // 남은 라운드 라벨 설정
        remainingRoundsLabel = new JLabel("남은 라운드: " + scoreService.getRemainingRounds());
        remainingRoundsLabel.setForeground(Color.WHITE);
        scoreService.setLabel(remainingRoundsLabel, getWidth() / 2 - 75, 70, 300, 27);
        add(remainingRoundsLabel);

        // 점수 표시 (고정 텍스트)
        JLabel LeftscoreTextLabel = new JLabel("1P SCORE");
        LeftscoreTextLabel.setForeground(Color.WHITE);
        scoreService.setLabel(LeftscoreTextLabel, getWidth() / 2 - 130, 175, 100, 15);
        transparentPanel.add(LeftscoreTextLabel);

        JLabel RightscoreTextLabel = new JLabel("2P SCORE");
        RightscoreTextLabel.setForeground(Color.WHITE);
        scoreService.setLabel(RightscoreTextLabel, getWidth()/2 + 55, 175, 100, 15);
        transparentPanel.add(RightscoreTextLabel);

        var scoreLabel1P = scoreService.getScoreLabel(1);
        var scoreLabel2P = scoreService.getScoreLabel(2);

        // 점수 표시 1P (변동되는 숫자)
        scoreLabel1P.setForeground(Color.white);
        scoreService.setLabel(scoreLabel1P, 50, 205); // 좌측에 배치
        transparentPanel.add(scoreLabel1P);

        // 점수 표시 2P (변동되는 숫자)
        scoreLabel2P.setForeground(Color.white);
        scoreService.setLabel(scoreLabel2P, getWidth() - 60, 205); // 우측에 배치
        transparentPanel.add(scoreLabel2P);

        // 타이머 표시 (타이틀)
        JLabel timeTextLabel = new JLabel("T I M E");
        timeTextLabel.setForeground(Color.WHITE);
        scoreService.setLabel(timeTextLabel, getWidth() / 2 - 20, 360);
        transparentPanel.add(timeTextLabel);

        // 타이머 표시 (변동되는 숫자)
        var timer = scoreService.getTimer();
        timer.setForeground(Color.YELLOW);
        scoreService.setLabel(timer, getWidth() / 2, 400);
        transparentPanel.add(timer);

        var numberOfGarbagePuyoLabel1P = scoreService.getGarbagePuyoCount(1);
        var numberOfGarbagePuyoLabel2P = scoreService.getGarbagePuyoCount(2);

        // 전달할 방해뿌요 표시 1P (변동되는 숫자)
        // TODO: 넘길 방해 뿌요 개발 완료 시 함께 수정
        numberOfGarbagePuyoLabel1P.setForeground(Color.white);
        scoreService.setLabel(numberOfGarbagePuyoLabel1P, getWidth() / 2 - 75);
        transparentPanel.add(numberOfGarbagePuyoLabel1P);

        // 전달할 방해뿌요 표시 2P (변동되는 숫자)
        // TODO: 넘길 방해 뿌요 수 그림

        // TODO: 남은 라운드 현재 라운드 수
        numberOfGarbagePuyoLabel2P.setForeground(Color.white);
        scoreService.setLabel(numberOfGarbagePuyoLabel2P, getWidth() / 2 + 50);
        transparentPanel.add(numberOfGarbagePuyoLabel2P);
    }

    // getter
    public ScoreService getScoreService() {
        return scoreService;
    }

      // 다음 라운드로 넘어갈 때 UI 업데이트
      public void advanceToNextRound() {
        scoreService.advanceToNextRound();
        remainingRoundsLabel.setText("remaining rounds:" + scoreService.getRemainingRounds());
        remainingRoundsLabel.setForeground(Color.white);
    }
}
