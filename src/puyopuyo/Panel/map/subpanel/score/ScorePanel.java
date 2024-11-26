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

        // 반투명한 배경을 가지는 패널 생성
        JPanel transparentPanel = new JPanel();
        transparentPanel.setBackground(new Color(0, 0, 0, 100)); // 알파값 100으로 반투명 설정
        transparentPanel.setBounds(0, 0, 300, 750);
        transparentPanel.setLayout(null);
        add(transparentPanel);



        // 현재 라운드를 명시
        roundCountLabel = new JLabel(scoreService.getCurrentRound() + " R O U N D");
        roundCountLabel.setForeground(Color.WHITE);
        scoreService.setLabel(roundCountLabel, getWidth() / 2 - 75, 43, 300, 27);
        transparentPanel.add(roundCountLabel);


        // 남은 라운드 라벨 설정
        remainingRoundsLabel = new JLabel("남은 라운드: " + scoreService.getRemainingRounds());

        remainingRoundsLabel.setForeground(Color.WHITE);
        scoreService.setLabel(remainingRoundsLabel, getWidth() / 2 - 75, 70, 300, 27);
        transparentPanel.add(remainingRoundsLabel);

        // 점수 표시 (고정 텍스트)
        JLabel LeftscoreTextLabel = new JLabel("1P SCORE");
        LeftscoreTextLabel.setForeground(Color.WHITE);
        scoreService.setLabel(LeftscoreTextLabel, getWidth() / 2 - 130, 175, 100, 15);
        transparentPanel.add(LeftscoreTextLabel);

        JLabel RightscoreTextLabel = new JLabel("2P SCORE");
        RightscoreTextLabel.setForeground(Color.WHITE);
        scoreService.setLabel(RightscoreTextLabel, getWidth() / 2 + 55, 175, 100, 15);
        transparentPanel.add(RightscoreTextLabel);

        // 점수 표시 1P (변동되는 숫자)
        var scoreLabel1P = scoreService.getScoreLabel(1);
        scoreLabel1P.setForeground(Color.WHITE);
        scoreService.setLabel(scoreLabel1P, 50, 205);
        transparentPanel.add(scoreLabel1P);

        // 점수 표시 2P (변동되는 숫자)
        var scoreLabel2P = scoreService.getScoreLabel(2);
        scoreLabel2P.setForeground(Color.WHITE);
        scoreService.setLabel(scoreLabel2P, getWidth() - 60, 205);
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

        // 승리 횟수 Label들을 그룹화할 패널 생성
        JPanel winPanel = new JPanel();
        winPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        winPanel.setOpaque(false); // 패널 투명 설정
        winPanel.setBounds(getWidth() / 2 - 150, 650, 300, 50); // 위치 및 크기 설정
        add(winPanel);

        int scaledWidth = 280; // 원하는 크기로 설정
        int scaledHeight = 50;

        Image scaledWinImage = GameImageIcon.WINCountIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledWinIcon = new ImageIcon(scaledWinImage);

        /*승리 횟수 Label 추가*/
        winLabel = new JLabel(scaledWinIcon);
        winPanel.add(winLabel);

        // 1P Win Count Label 추가 (초기: 빈 별 이미지)
        winCount1PLabel1 = new JLabel(GameImageIcon.WInBaseIcon);
       winCount1PLabel1.setBounds(getWidth() / 2 - 150, 700, 50, 50); //왼쪽
        add(winCount1PLabel1);

        winCount1PLabel2 = new JLabel(GameImageIcon.WInBaseIcon);
        winCount1PLabel2.setBounds(getWidth() / 2 - 100, 700, 50, 50); //왼쪽
        add(winCount1PLabel2);

        // 2P Win Count Label 추가 (초기: 빈 별 이미지)
        winCount2PLabel1 = new JLabel(GameImageIcon.WInBaseIcon);
        winCount2PLabel1.setBounds(getWidth() / 2 + 50, 700,50, 50); //오른쪽
        add(winCount2PLabel1);

        winCount2PLabel2 = new JLabel(GameImageIcon.WInBaseIcon);
        winCount2PLabel2.setBounds(getWidth() / 2 + 100, 700, 50, 50); //오른쪽
        add(winCount2PLabel2);

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

