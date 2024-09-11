package puyopuyo.map;

import puyopuyo.ground.GroundService;
import puyopuyo.score.ScoreService;

import java.awt.*;
import java.util.Arrays;

public class MapService {
    private final MapRepository mapRepository;

    public MapService(MapPanel mapPanel) {
        mapRepository = new MapRepository(mapPanel);
    }

    public void addKeyListener() {
        var mapPanel = mapRepository.getMapPanel();
        /*
         * 해당 패널의 키보드 값을 받도록 설정.
         * 보통은 따로하나, 동시에 1p, 2p의 키보드 값을 받게하기 위함
         */
        mapPanel.addKeyListener(new ControlPuyoKeyListener(this));
        mapPanel.setFocusable(true);
        mapPanel.requestFocus();

        System.out.println("KetListener: "+Arrays.toString(mapPanel.getKeyListeners()));
        System.out.println("Focusable: "+mapPanel.isFocusable());
    }

    public void setUi() {
        var mapPanel = mapRepository.getMapPanel();
        mapPanel.setLayout(null);

        var scorePanel = mapRepository.getScorePanel();
        var gameGround1P = mapRepository.getGroundPanel1P();
        var gameGround2P = mapRepository.getGroundPanel2P();

        scorePanel.setLocation(490,60);
        gameGround1P.setLocation(50,60);
        gameGround2P.setLocation(830,60);

        mapPanel.add(scorePanel);
        mapPanel.add(gameGround1P);
        mapPanel.add(gameGround2P);
    }

    public ScoreService getScoreService() {
        return mapRepository.getScorePanel().getScoreService();
    }

    public GroundService getGroundService(int iAm) {
        if (iAm == 1) return mapRepository.getGroundPanel1P().getGroundServices();
        else return mapRepository.getGroundPanel2P().getGroundServices();
    }

    public void start() {
        mapRepository.addGameThread();
        
        mapRepository.getGameThread().start();
    }

    public void openGamePanel(Container contentPane) {
        var screenPanel = mapRepository.getMapPanel();
        contentPane.add(screenPanel);
        screenPanel.setVisible(true);
    }

    public void closeGamePanel() {
        var frame = mapRepository.getMapPanel().getParent();
        var screenPanel = mapRepository.getMapPanel();

        // 이전 화면 삭제
        frame.remove(screenPanel);
    }
}
