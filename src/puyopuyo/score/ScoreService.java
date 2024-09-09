package puyopuyo.score;

import java.awt.*;

public class ScoreService {
    private final ScoreRepository scoreRepository;

    public ScoreService(ScorePanel scorePanel) {
        scoreRepository = new ScoreRepository(scorePanel);
    }

    public void setUi() {
        var scorePanel = scoreRepository.getScorePanel();

        scorePanel.setBackground(Color.YELLOW);
        scorePanel.setSize(300, 750);
        scorePanel.setLayout(null);

//        // 다음 뿌요 색상 1P
//        nextLeftControlPuyo1P.setLocation(12+this.getWidth()/4-60,100);
//        nextRightControlPuyo1P.setLocation(12+this.getWidth()/4,100);
//        add(nextLeftControlPuyo1P);
//        add(nextRightControlPuyo1P);
//
//        // 다음 뿌요 색상 2P
//        nextLeftControlPuyo2P.setLocation(this.getWidth()/4*3-60-12,100);
//        nextRightControlPuyo2P.setLocation(this.getWidth()/4*3-12,100);
//        add(nextLeftControlPuyo2P);
//        add(nextRightControlPuyo2P);
//
//        // 라운드 표시 (변동되는 숫자)
//        roundCountLabel.setFont(new Font("Serif", Font.BOLD, 27));
//        roundCountLabel.setLocation(this.getWidth()/2-75, 43);
//        roundCountLabel.setSize(300, 30);
//        add(roundCountLabel);
//
//        // 라운드 표시 (고정 텍스트)
//        JLabel roundLabel = new JLabel("R O U N D");
//        roundLabel.setFont(new Font("Serif", Font.BOLD, 27));
//        roundLabel.setLocation(this.getWidth()/2-50, 43);
//        roundLabel.setSize(300, 30);
//        add(roundLabel);
//
//        // 점수 표시 (타이틀)
//        JLabel scoreTextLabel = new JLabel("S C O R E");
//        scoreTextLabel.setFont(new Font("Serif", Font.BOLD, 15));
//        scoreTextLabel.setLocation(this.getWidth()/2-29, 175);
//        scoreTextLabel.setSize(100, 30);
//        add(scoreTextLabel);
//
//        // 점수 표시 1P (변동되는 숫자)
//        setLabel(scoreLabel1P, this.getWidth()/2, 205);
//        add(scoreLabel1P);
//
//        // 점수 표시 2P (변동되는 숫자)
//        setLabel(scoreLabel1P, this.getWidth()/2, 235);
//        add(scoreLabel2P);
//
//        // 타이머 표시 (타이틀)
//        JLabel timerTextLabel = new JLabel("T I M E");
//        setLabel(timerTextLabel, this.getWidth()/2-20, 360);
//        add(timerTextLabel);
//
//        // 타이머 표시 (변동되는 숫자)
//        JLabel timerLabel = new JLabel("0");
//        setLabel(timerLabel, this.getWidth()/2, 400);
//        add(timerLabel);
//
//        // 전달할 방해뿌요 표시 1P (변동되는 숫자)
//        // TODO: 넘길 방해 뿌요 개발 완료 시 함께 수정
//        numberOfGarbagePuyoLabel1P.setLocation(this.getWidth()/2-75, 600);
//        numberOfGarbagePuyoLabel1P.setSize(100,30);
//        add(numberOfGarbagePuyoLabel1P);
//
//        // 전달할 방해뿌요 표시 2P (변동되는 숫자)
//        // TODO: 넘길 방해 뿌요 개발 완료 시 함께 수정
//        numberOfGarbagePuyoLabel2P.setLocation(this.getWidth()/2+50, 600);
//        numberOfGarbagePuyoLabel2P.setSize(100,30);
//        add(numberOfGarbagePuyoLabel2P);
//
//        // TODO: 넘길 방해 뿌요 수 그림
//        // TODO: 남은 라운드 현재 라운드 수
//        // TODO: 자신의 점수
    }
}
