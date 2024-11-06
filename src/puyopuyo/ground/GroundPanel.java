package puyopuyo.ground;

import puyopuyo.puyo.GameImageIcon;
import puyopuyo.puyo.Puyo;

import javax.swing.*;
import java.awt.*;

public class GroundPanel extends JPanel {
    private final int iAm;

    private final GroundService groundService = new GroundService();

    public GroundPanel(int iAm) {
        this.iAm = iAm;

        setUi();
    }

    public void setUi() {
        Puyo leftPuyo = groundService.getLeftPuyo();
        Puyo rightPuyo = groundService.getRightPuyo();

        setBackground(Color.CYAN);
        setLayout(null);
        setSize(400,750);

        leftPuyo.setLocation(140,10);
        rightPuyo.setLocation(200,10);

        add(leftPuyo);
        add(rightPuyo);
    }

    public GroundService getGroundService() {
        return groundService;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if(iAm == 1) graphics.drawImage(GameImageIcon.player1Ground.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        else graphics.drawImage(GameImageIcon.player2Ground.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
