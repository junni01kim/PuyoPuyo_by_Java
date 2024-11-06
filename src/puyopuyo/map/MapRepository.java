package puyopuyo.map;

import puyopuyo.ground.GroundPanel;
import puyopuyo.game.GameThread;
import puyopuyo.score.ScorePanel;

public class MapRepository {
    /** 자기 자신 */
    private MapPanel mapPanel;
    /** 자신의 화면 Ui */
    private final ScorePanel scorePanel = new ScorePanel();
    private final GroundPanel groundPanel1P = new GroundPanel(1);
    private final GroundPanel groundPanel2P = new GroundPanel(2);
    private GameThread gameThread;

    public MapRepository(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    public void addGameThread() {
        gameThread = new GameThread(mapPanel.getScreenService());
    }

    public MapPanel getMapPanel() {return mapPanel;}

    public ScorePanel getScorePanel() {return scorePanel;}

    public GroundPanel getGroundPanel1P() {return groundPanel1P;}

    public GroundPanel getGroundPanel2P() {return groundPanel2P;}

    public GameThread getGameThread() {return gameThread;}
}
