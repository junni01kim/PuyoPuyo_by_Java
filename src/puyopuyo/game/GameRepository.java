package puyopuyo.game;

import puyopuyo.GameGround;
import puyopuyo.ScorePanel;

public class GameRepository {
    private final GamePanel gamePanel;
    private final GameGround gameGround1P;
    private final GameGround gameGround2P;
    private final ScorePanel scorePanel;

    public GameRepository(GamePanel gamePanel, GameGround gameGround1P, GameGround gameGround2P, ScorePanel scorePanel) {
        this.gamePanel = gamePanel;
        this.gameGround1P = gameGround1P;
        this.gameGround2P = gameGround2P;
        this.scorePanel = scorePanel;
    }

    public GamePanel getGamePanel() { return gamePanel; }
    public GameGround getGameGround1P() { return gameGround1P; }
    public GameGround getGameGround2P() { return gameGround2P; }
    public ScorePanel getScorePanel() { return scorePanel; }
}
