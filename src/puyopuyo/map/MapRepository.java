package puyopuyo.map;

import puyopuyo.ground.GroundPanel;
import puyopuyo.game.GameThread;
import puyopuyo.score.ScorePanel;

public class MapRepository {
    /** 자기 자신 */
    private final MapPanel mapPanel;
    /** 자신의 화면 Ui */
    private final ScorePanel scorePanel = new ScorePanel();
    private final GroundPanel groundPanel1P = new GroundPanel();
    private final GroundPanel groundPanel2P = new GroundPanel();
    private final GameThread gameThread;

    public MapRepository(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
        gameThread = new GameThread(mapPanel.getScreenService());
    }

    public MapPanel getScreenPanel() {return mapPanel;}

    public ScorePanel getScorePanel() {return scorePanel;}

    public GroundPanel getGroundPanel1P() {return groundPanel1P;}

    public GroundPanel getGroundPanel2P() {return groundPanel2P;}

    public GameThread getRoundThread() {return gameThread;}
}
