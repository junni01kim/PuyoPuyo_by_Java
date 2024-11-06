package puyopuyo.ground;

import puyopuyo.puyo.Puyo;

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

        groundPanel.setLayout(null);
        groundPanel.setSize(400,750);

        leftPuyo.setLocation(140,10);
        rightPuyo.setLocation(200,10);

        groundPanel.add(leftPuyo);
        groundPanel.add(rightPuyo);
    }

    public Puyo getLeftPuyo() {return groundRepository.getLeftPuyo();}
    public Puyo getRightPuyo() {return groundRepository.getRightPuyo();}

    public Puyo[][] getPuyoMap() {return groundRepository.getPuyoMap();}

    public Puyo setLeftPuyoLocation(int x, int y) {return groundRepository.setLeftPuyo(x,y);}
    public Puyo setRightPuyoLocation(int x, int y) {return groundRepository.setRightPuyo(x,y);}

    public Puyo[][] setPuyo(int indexX, int indexY, Puyo puyo) {return groundRepository.setPuyo(indexX, indexY, puyo);}
    public Puyo[][] setPuyoMap(Puyo[][] puyoMap) {return groundRepository.setPuyoMap(puyoMap);}

    public void repaint() {
        var groundPanel = groundRepository.getGroundPanel();
        groundPanel.repaint();
    }

    public void add(Puyo puyo) {groundRepository.getGroundPanel().add(puyo);}
}