package puyopuyo.game;

import puyopuyo.puyo.GameImageIcon;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final GameService gameService = new GameService(this);

    public GamePanel() {
        addKeyListener(null);

        gameService.setUi();
        setFocusable(true);
    }

    public GameService getScreenService() {return gameService;}

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(GameImageIcon.gamePanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
