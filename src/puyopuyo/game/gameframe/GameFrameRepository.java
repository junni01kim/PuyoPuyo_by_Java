package puyopuyo.game.gameframe;

import puyopuyo.game.GamePanel;
import puyopuyo.gamemenu.GameMenuPanel;

public class GameFrameRepository {
    private final GameFrame gameFrame;
    private final GamePanel gamePanel;
    private final GameMenuPanel gameMenuPanel;

    public GameFrameRepository(GameFrame gameFrame, GamePanel gamePanel, GameMenuPanel gameMenuPanel) {
        this.gameFrame = gameFrame;
        this.gamePanel = gamePanel;
        this.gameMenuPanel = gameMenuPanel;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public GameMenuPanel getGameMenuPanel() {
        return gameMenuPanel;
    }
}
