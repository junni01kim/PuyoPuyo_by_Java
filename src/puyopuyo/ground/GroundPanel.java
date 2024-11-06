package puyopuyo.ground;

import puyopuyo.puyo.GameImageIcon;

import javax.swing.*;
import java.awt.*;

public class GroundPanel extends JPanel {
    private final GroundService groundService;

    public GroundPanel(int iAm) {
        groundService = new GroundService(iAm, this);
        groundService.setUi();
    }

    public GroundService getGroundServices() {return groundService;}


    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        var iAm = groundService.getIAm();
        if(iAm == 1) graphics.drawImage(GameImageIcon.player1Ground.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        else graphics.drawImage(GameImageIcon.player2Ground.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
