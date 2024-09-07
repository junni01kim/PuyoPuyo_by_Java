package puyopuyo.gamemenu;

public class GameMenuRepository {
    private final GameMenuPanel gameMenuPanel;

    public GameMenuRepository(GameMenuPanel gameMenuPanel) {
        this.gameMenuPanel = gameMenuPanel;
    }

    public GameMenuPanel getGameMenuPanel() {
        return gameMenuPanel;
    }
}
