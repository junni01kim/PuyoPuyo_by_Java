package puyopuyo.map;

import puyopuyo.puyo.GameImageIcon;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private final MapService mapService = new MapService(this);

    public MapPanel() {
        mapService.setUi();

        /*
         * 해당 패널의 키보드 값을 받도록 설정.
         * 보통은 따로하나, 동시에 1p, 2p의 키보드 값을 받게하기 위함
         */
        mapService.addKeyListener();

        mapService.start();
    }

    public MapService getScreenService() {return mapService;}

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(GameImageIcon.gamePanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
