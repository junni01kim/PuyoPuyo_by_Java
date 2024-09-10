package puyopuyo.map;

import puyopuyo.ground.GroundService;
import puyopuyo.score.ScoreService;

import java.awt.*;

public class MapService {
    private final MapRepository mapRepository;

    public MapService(MapPanel mapPanel) {
        mapRepository = new MapRepository(mapPanel);
    }

    public void setUi() {
        var screenPanel = mapRepository.getScreenPanel();
        screenPanel.setLayout(null);

        var scorePanel = mapRepository.getScorePanel();
        var gameGround1P = mapRepository.getGroundPanel1P();
        var gameGround2P = mapRepository.getGroundPanel2P();

        scorePanel.setLocation(490,60);
        gameGround1P.setLocation(50,60);
        gameGround2P.setLocation(830,60);

        screenPanel.add(scorePanel);
        screenPanel.add(gameGround1P);
        screenPanel.add(gameGround2P);

        /*
         * 해당 패널의 키보드 값을 받도록 설정.
         * 보통은 따로하나, 동시에 1p, 2p의 키보드 값을 받게하기 위함
         */
        screenPanel.setFocusable(true);
    }

    public void setKeyListener() {
        var gamePanel = mapRepository.getScreenPanel();
        gamePanel.addKeyListener(new ControlPuyoKeyListener(this));
    }

    public ScoreService getScoreService() {
        return mapRepository.getScorePanel().getScoreService();
    }

    public GroundService getGroundService(int iAm) {
        if (iAm == 1) return mapRepository.getGroundPanel1P().getGroundServices();
        else return mapRepository.getGroundPanel2P().getGroundServices();
    }

    public void start() {
        mapRepository.getRoundThread().start();}

    public void openGamePanel(Container contentPane) {
        var screenPanel = mapRepository.getScreenPanel();
        contentPane.add(screenPanel);
        screenPanel.setVisible(true);
    }

    public void closeGamePanel() {
        var frame = mapRepository.getScreenPanel().getParent();
        var screenPanel = mapRepository.getScreenPanel();

        // 이전 화면 삭제
        frame.remove(screenPanel);
    }
}
