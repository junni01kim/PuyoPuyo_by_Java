package puyopuyo.map;

import puyopuyo.ground.GroundPanel;
import puyopuyo.score.ScorePanel;

public class MapService {
    private static MapService instance;

    private GroundPanel groundPanel1P;
    private GroundPanel groundPanel2P;
    private ScorePanel scorePanel;

    public synchronized static MapService getInstance() {
        if (instance == null) {
            instance = new MapService();
        }
        return instance;
    }

    public void openMap() {
        groundPanel1P = new GroundPanel(1);
        groundPanel2P = new GroundPanel(2);
        scorePanel = new ScorePanel();
    }

    /**
     * getter
     *
     */
    public GroundPanel getGroundPanel1P() {
        return groundPanel1P;
    }

    public GroundPanel getGroundPanel2P() {
        return groundPanel2P;
    }

    public ScorePanel getScorePanel() {
        return scorePanel;
    }
}
