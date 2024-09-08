package puyopuyo.game;

import puyopuyo.gameground.GameGroundPanel;
import puyopuyo.ScorePanel;

public class GameRepository {
    private final GamePanel gamePanel;
    private final GameGroundPanel gameGroundPanel1P;
    private final GameGroundPanel gameGroundPanel2P;
    private final ScorePanel scorePanel;

    public GameRepository(GamePanel gamePanel, GameGroundPanel gameGroundPanel1P, GameGroundPanel gameGroundPanel2P, ScorePanel scorePanel) {
        this.gamePanel = gamePanel;
        this.gameGroundPanel1P = gameGroundPanel1P;
        this.gameGroundPanel2P = gameGroundPanel2P;
        this.scorePanel = scorePanel;
    }

    public GamePanel getGamePanel() { return gamePanel; }
    public GameGroundPanel getGameGround1P() { return gameGroundPanel1P; }
    public GameGroundPanel getGameGround2P() { return gameGroundPanel2P; }
    public ScorePanel getScorePanel() { return scorePanel; }
}
