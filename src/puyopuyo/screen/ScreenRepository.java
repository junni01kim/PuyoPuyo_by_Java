package puyopuyo.screen;

import puyopuyo.score.ScorePanel;

import java.awt.*;

public class ScreenRepository {
    /** 자기 자신 */
    private final ScreenPanel screenPanel;
    /** 자신의 화면 Ui */
    private final ScorePanel scorePanel = new ScorePanel();

    public ScreenRepository(ScreenPanel screenPanel) {
        this.screenPanel = screenPanel;
    }

    public ScreenPanel getScreenPanel() {return screenPanel;}

    public ScorePanel getScorePanel() {return scorePanel;}
}
