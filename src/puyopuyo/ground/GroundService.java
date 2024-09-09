package puyopuyo.ground;

import java.awt.*;

public class GroundService {
    private final GroundRepository groundRepository;

    public GroundService(GroundPanel groundPanel) {
        groundRepository = new GroundRepository(groundPanel);
    }

    public void setUi() {
        var groundPanel = groundRepository.getGroundPanel();
        var leftPuyo = groundRepository.getLeftPuyo();
        var rightPuyo = groundRepository.getRightPuyo();

        groundPanel.setBackground(Color.CYAN);
        groundPanel.setLayout(null);
        groundPanel.setSize(400,750);

        leftPuyo.setLocation(140,10);
        rightPuyo.setLocation(200,10);

        groundPanel.add(leftPuyo);
        groundPanel.add(rightPuyo);
    }
}