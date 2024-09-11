package puyopuyo.ground;

import javax.swing.*;

public class GroundPanel extends JPanel {
    private final GroundService groundService = new GroundService(this);

    public GroundPanel() {
        groundService.setUi();
    }

    public GroundService getGroundServices() {return groundService;}
}
