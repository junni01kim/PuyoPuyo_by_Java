package puyopuyo.map;

import puyopuyo.puyo.GameImageIcon;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private final MapService mapService = new MapService(this);

    public MapPanel() {
        mapService.setUi();
        mapService.setKeyListener();

        setFocusable(true);

        mapService.start();
    }

    public MapService getScreenService() {return mapService;}

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(GameImageIcon.gamePanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
