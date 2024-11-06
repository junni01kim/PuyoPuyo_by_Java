package puyopuyo.map;

import puyopuyo.frame.Frame;
import puyopuyo.frame.PanelState;
import puyopuyo.game.GameThread;
import puyopuyo.puyo.GameImageIcon;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel implements PanelState {
    private static MapPanel instance;

    public synchronized static MapPanel getInstance() {
        if (instance == null) {
            instance = new MapPanel();
        }
        return instance;
    }

    public MapPanel() {
        setUi();
        process();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(GameImageIcon.gamePanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    @Override
    public void setUi() {
        this.setLayout(null);

        var mapService = MapService.getInstance();

        mapService.openMap();

        var scorePanel = mapService.getScorePanel();
        var gameGround1P = mapService.getGroundPanel1P();
        var gameGround2P = mapService.getGroundPanel2P();

        scorePanel.setLocation(490,60);
        gameGround1P.setLocation(50,60);
        gameGround2P.setLocation(830,60);

        add(scorePanel);
        add(mapService.getGroundPanel1P());
        add(mapService.getGroundPanel2P());

        addKeyListener();
    }

    @Override
    public void process() {
        new GameThread(MapService.getInstance()).start();
    }

    @Override
    public void open() {
        var frame = Frame.getInstance();
        var me = MapPanel.getInstance();

        frame.add(me);
        frame.setVisible(true);
    }

    @Override
    public void close() {
        var frame = Frame.getInstance();
        var me = getInstance();

        // 이전 화면 삭제
        frame.remove(me);
    }

    public void addKeyListener() {
        /*
         * 해당 패널의 키보드 값을 받도록 설정.
         * 보통은 따로하나, 동시에 1p, 2p의 키보드 값을 받게하기 위함
         */
        addKeyListener(new ControlPuyoKeyListener()); //TODO: 수정할 것
        setFocusable(true);
        //mapPanel.requestFocus();
    }
}
