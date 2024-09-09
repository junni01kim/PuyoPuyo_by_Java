package puyopuyo.screen;

import puyopuyo.ground.GroundPanel;
import puyopuyo.score.ScorePanel;

public class ScreenRepository {
    /** 자기 자신 */
    private final ScreenPanel screenPanel;
    /** 자신의 화면 Ui */
    private final ScorePanel scorePanel = new ScorePanel();
    private final GroundPanel groundPanel1P = new GroundPanel();
    private final GroundPanel groundPanel2P = new GroundPanel();

    public ScreenRepository(ScreenPanel screenPanel) {
        this.screenPanel = screenPanel;
    }

    public ScreenPanel getScreenPanel() {return screenPanel;}

    public ScorePanel getScorePanel() {return scorePanel;}

    public GroundPanel getGroundPanel1P() {return groundPanel1P;}

    public GroundPanel getGroundPanel2P() {return groundPanel2P;}
}
