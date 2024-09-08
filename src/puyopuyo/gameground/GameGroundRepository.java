package puyopuyo.gameground;

import puyopuyo.Puyo;

public class GameGroundRepository {
    private final GameGroundPanel gameGroundPanel;

    /**
     * 조작할 puyopuyo.Puyo
     */
    private final Puyo leftPuyo = new Puyo(0);
    private final Puyo rightPuyo = new Puyo(0);


    public GameGroundRepository(GameGroundPanel gameGroundPanel) {
        this.gameGroundPanel = gameGroundPanel;
    }

    public GameGroundPanel getGameGroundPanel() { return gameGroundPanel; }

    /** getter */
    public Puyo getLeftPuyo() { return leftPuyo; }
    public Puyo getRightPuyo() { return rightPuyo; }
}
