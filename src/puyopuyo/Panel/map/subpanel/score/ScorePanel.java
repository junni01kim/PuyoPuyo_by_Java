package puyopuyo.Panel.map.subpanel.score;

import puyopuyo.resource.GameImageIcon;

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
    private JLabel winLabel;
    private JLabel winCount1PLabel1;
    private JLabel winCount1PLabel2;
    private JLabel winCount2PLabel1;
    private JLabel winCount2PLabel2;
    private JLabel timer;

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
        setBackground(new Color(0, 0, 0, 100)); // 완전 투명 배경 설정
        setSize(300, 750);
        setLocation(490, 60);
        setLayout(null);

        // 현재 라운드를 명시
        roundCountLabel = new JLabel(scoreService.getCurrentRound() + " R O U N D");
        roundCountLabel.setForeground(Color.WHITE);
        scoreService.setLabel(roundCountLabel, getWidth() / 2 - 75, 43, 300, 27);

        // 남은 라운드 라벨 설정
        remainingRoundsLabel = new JLabel("remaining Rounds: " + scoreService.getRemainingRounds());
        remainingRoundsLabel.setForeground(Color.WHITE);
        scoreService.setLabel(remainingRoundsLabel, getWidth() / 2 - 100, 70, 300, 20);


        // 점수 표시 (고정 텍스트)
        JLabel LeftscoreTextLabel = new JLabel("1P SCORE");
        LeftscoreTextLabel.setForeground(Color.WHITE);
        scoreService.setLabel(LeftscoreTextLabel, getWidth() / 2 - 130, 175, 100, 15);


        JLabel RightscoreTextLabel = new JLabel("2P SCORE");
        RightscoreTextLabel.setForeground(Color.WHITE);
        scoreService.setLabel(RightscoreTextLabel, getWidth() / 2 + 55, 175, 100, 15);


        // 점수 표시 1P (변동되는 숫자)
        var scoreLabel1P = scoreService.getScoreLabel(1);
        scoreLabel1P.setOpaque(true);
        scoreLabel1P.setBackground(new Color(255, 255, 255, 0));
        scoreService.setLabel(scoreLabel1P, 50, 205);


        // 점수 표시 2P (변동되는 숫자)
        var scoreLabel2P = scoreService.getScoreLabel(2);
        scoreLabel2P.setForeground(Color.WHITE);
        scoreService.setLabel(scoreLabel2P, getWidth() - 60, 205);


        // 타이머 표시 (타이틀)
        JLabel timeTextLabel = new JLabel("T I M E");
        timeTextLabel.setForeground(Color.WHITE);
        scoreService.setLabel(timeTextLabel, getWidth() / 2 - 20, 360);


        // 타이머 표시 (변동되는 숫자)
        timer = scoreService.getTimer();
        timer.setOpaque(false);
        timer.setForeground(Color.YELLOW);
        scoreService.setLabel(timer, getWidth() / 2, 400);



        int scaledWidth = 280; // 원하는 크기로 설정
        int scaledHeight = 50;

        Image scaledWinImage = GameImageIcon.WINCountIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledWinIcon = new ImageIcon(scaledWinImage);

        // 승리 횟수 Label 추가 (승리 아이콘)
        winLabel = new JLabel(scaledWinIcon);
        winLabel.setBounds(getWidth() / 2 - 140, 650, 280, 50); // 중앙에 위치하도록 설정
        add(winLabel); // 패널에 추가

        // 1P Win Count Label 추가 (초기: 빈 별 이미지)
        winCount1PLabel1 = new JLabel(GameImageIcon.WInBaseIcon);
        winCount1PLabel1.setBounds(getWidth() / 2 - 130, 700, 50, 50); // 왼쪽 첫 번째 별
        add(winCount1PLabel1); // 패널에 추가

        winCount1PLabel2 = new JLabel(GameImageIcon.WInBaseIcon);
        winCount1PLabel2.setBounds(getWidth() / 2 - 70, 700, 50, 50); // 왼쪽 두 번째 별
        add(winCount1PLabel2); // 패널에 추가

        // 2P Win Count Label 추가 (초기: 빈 별 이미지)
        winCount2PLabel1 = new JLabel(GameImageIcon.WInBaseIcon);
        winCount2PLabel1.setBounds(getWidth() / 2 + 10, 700, 50, 50); // 오른쪽 첫 번째 별
        add(winCount2PLabel1); // 패널에 추가

        winCount2PLabel2 = new JLabel(GameImageIcon.WInBaseIcon);
        winCount2PLabel2.setBounds(getWidth() / 2 + 70, 700, 50, 50); // 오른쪽 두 번째 별
        add(winCount2PLabel2); // 패널에 추가

    }



    public void WinCount(int player) {

        if (player == 1) {
            winCount1PLabel1.setIcon(GameImageIcon.WINCountedIcon);
        } else if (player == 2) {
            winCount2PLabel1.setIcon(GameImageIcon.WINCountedIcon);
        }
    }


    // getter
    public ScoreService getScoreService() {
        return scoreService;
    }
}

