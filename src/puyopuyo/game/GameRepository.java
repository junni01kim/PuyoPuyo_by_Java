package puyopuyo.game;

import puyopuyo.ground.GroundPanel;
import puyopuyo.round.RoundThread;
import puyopuyo.score.ScorePanel;

public class GameRepository {
    /** 자기 자신 */
    private final GamePanel gamePanel;
    /** 자신의 화면 Ui */
    private final ScorePanel scorePanel = new ScorePanel();
    private final GroundPanel groundPanel1P = new GroundPanel();
    private final GroundPanel groundPanel2P = new GroundPanel();
    private final RoundThread roundThread;

    public GameRepository(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        roundThread = new RoundThread(gamePanel.getScreenService());
    }

    public GamePanel getScreenPanel() {return gamePanel;}

    public ScorePanel getScorePanel() {return scorePanel;}

    public GroundPanel getGroundPanel1P() {return groundPanel1P;}

    public GroundPanel getGroundPanel2P() {return groundPanel2P;}

    public RoundThread getRoundThread() {return roundThread;}
}
