package puyopuyo.ground;

import puyopuyo.puyo.Puyo;

public class GroundService {
    private final GroundRepository groundRepository = new GroundRepository();

    public Puyo getLeftPuyo() {
        return groundRepository.getLeftPuyo();
    }

    public Puyo getRightPuyo() {
        return groundRepository.getRightPuyo();
    }

    public Puyo[][] getPuyoMap() {
        return groundRepository.getPuyoMap();
    }

    public Puyo setLeftPuyoLocation(int x, int y) {
        return groundRepository.setLeftPuyo(x,y);
    }

    public Puyo setRightPuyoLocation(int x, int y) {
        return groundRepository.setRightPuyo(x, y);
    }

    public Puyo[][] setPuyo(int indexX, int indexY, Puyo puyo) {
        return groundRepository.setPuyo(indexX, indexY, puyo);
    }

    public Puyo[][] setPuyoMap(Puyo[][] puyoMap) {
        return groundRepository.setPuyoMap(puyoMap);
    }
}