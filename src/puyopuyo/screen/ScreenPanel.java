package puyopuyo.screen;

import puyopuyo.puyo.GameImageIcon;

import javax.swing.*;
import java.awt.*;

public class ScreenPanel extends JPanel {
    private final ScreenService screenService = new ScreenService(this);

    public ScreenPanel() {
        addKeyListener(null);

        screenService.setUi();
        setFocusable(true);
    }

    public ScreenService getScreenService() {return screenService;}

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(GameImageIcon.gamePanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
