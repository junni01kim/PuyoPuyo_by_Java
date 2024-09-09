package puyopuyo.score;

import javax.swing.*;
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

        var nextLeftControlPuyo1P = scoreRepository.getNextLeftControlPuyo1P();
        var nextRightControlPuyo1P = scoreRepository.getNextRightControlPuyo1P();
        var nextLeftControlPuyo2P = scoreRepository.getNextLeftControlPuyo2P();
        var nextRightControlPuyo2P = scoreRepository.getNextRightControlPuyo2P();

        // 다음 뿌요 색상 1P
        setPosition(nextLeftControlPuyo1P, 12+scorePanel.getWidth()/4-60);
        setPosition(nextRightControlPuyo1P, 12+scorePanel.getWidth()/4);

        // 다음 뿌요 색상 2P
        setPosition(nextLeftControlPuyo2P, scorePanel.getWidth()/4*3-60-12);
        setPosition(nextRightControlPuyo2P, scorePanel.getWidth()/4*3-12);

        var roundCountLabel = scoreRepository.getRoundCountLabel();

        // 라운드 표시 (변동되는 숫자)
        setLabel(roundCountLabel, scorePanel.getWidth()/2-75, 43, 300, 27);

        // 라운드 표시 (고정 텍스트)
        setLabel(new JLabel("R O U N D"), scorePanel.getWidth()/2-50, 43, 300, 27);

        // 점수 표시 (고정 텍스트)
        setLabel(new JLabel("S C O R E"),scorePanel.getWidth()/2-29, 175, 100, 15);

        var scoreLabel1P = scoreRepository.getScoreLabel1P();
        var scoreLabel2P = scoreRepository.getScoreLabel2P();

        // 점수 표시 1P (변동되는 숫자)
        setLabel(scoreLabel1P, scorePanel.getWidth()/2, 205);

        // 점수 표시 2P (변동되는 숫자)
        setLabel(scoreLabel2P, scorePanel.getWidth()/2, 235);

        // 타이머 표시 (타이틀)
        setLabel(new JLabel("T I M E"), scorePanel.getWidth()/2-20, 360);

        // 타이머 표시 (변동되는 숫자)
        setLabel(new JLabel("0"), scorePanel.getWidth()/2, 400);

        var numberOfGarbagePuyoLabel1P = scoreRepository.getNumberOfGarbagePuyoLabel1P();
        var numberOfGarbagePuyoLabel2P = scoreRepository.getNumberOfGarbagePuyoLabel2P();

        // 전달할 방해뿌요 표시 1P (변동되는 숫자)
        // TODO: 넘길 방해 뿌요 개발 완료 시 함께 수정
        setLabel(numberOfGarbagePuyoLabel1P, scorePanel.getWidth()/2-75);

        // 전달할 방해뿌요 표시 2P (변동되는 숫자)
        // TODO: 넘길 방해 뿌요 개발 완료 시 함께 수정
        setLabel(numberOfGarbagePuyoLabel2P, scorePanel.getWidth()/2+50);

        // TODO: 넘길 방해 뿌요 수 그림
        // TODO: 남은 라운드 현재 라운드 수
        // TODO: 자신의 점수
    }

    void setLabel(JLabel label, int x, int y) {
        var scorePanel = scoreRepository.getScorePanel();

        label.setFont(new Font("Serif", Font.BOLD, 15));
        label.setLocation(x, y);
        label.setSize(100, 30);
        scorePanel.add(label);
    }

    private void setLabel(JLabel label, int x, int y, int width, int fontSize) {
        var scorePanel = scoreRepository.getScorePanel();

        label.setFont(new Font("Serif", Font.BOLD, fontSize));
        label.setLocation(x, y);
        label.setSize(width, 30);
        scorePanel.add(label);
    }

    /**
     * 모듈화 하다보니 사용하는 곳이 하나 구간 밖에 없어서 축약함
     */
    private void setLabel(JLabel label, int x) {
        var scorePanel = scoreRepository.getScorePanel();

        label.setSize(100, 30);
        label.setLocation(x, 600);
        scorePanel.add(label);
    }

    /**
     * 모듈화 하다보니 사용하는 곳이 하나 구간 밖에 없어서 축약함
     */
    private void setPosition(JLabel label, int x) {
        var scorePanel = scoreRepository.getScorePanel();

        label.setLocation(x, 100);
        scorePanel.add(label);
    }
}
