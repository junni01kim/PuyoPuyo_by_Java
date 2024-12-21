package puyopuyo.client.panel.map;

import puyopuyo.client.panel.map.subpanel.ground.GroundPanel;
import puyopuyo.client.panel.map.subpanel.score.ScorePanel;

/**
 * 게임 전체 화면을 관리하는 패널 서비스이다.<br>
 *
 * 패널 관리 책임을 가지며, MVC 패턴의 Model의 역할을 한다.
 *
 * 게임 창은 하나만 구현되므로, 싱글톤 패턴을 이용하여 개발했다.
 * 코드의 복잡성을 줄이기 위해 합성관계로 구현하였다. //TODO: 해당 내용에 맞게 수정하기
 */
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

    /**
     * 게임 진행을 위한 엔티티를 동시에 생성한다.
     *
     */
    public void openMap() {
        groundPanel1P = new GroundPanel(1);
        groundPanel2P = new GroundPanel(2);
        scorePanel = new ScorePanel();
    }

    public void closeMap() {
        var map = MapPanel.getInstance();
        map.remove(groundPanel1P);
        map.remove(groundPanel2P);
        map.remove(scorePanel);
    }

    /**
     * getter
     *
     */
    public GroundPanel getGroundPanel(int iAm) {
        if(iAm == 1) return groundPanel1P;
        else return groundPanel2P;
    }

    public ScorePanel getScorePanel() {
        return scorePanel;
    }
}
