package puyopuyo.ground;

import puyopuyo.puyo.Puyo;

public class GroundRepository {
    /** 자기 자신 */
    private final GroundPanel groundPanel;

    public GroundRepository(GroundPanel groundPanel) {
        this.groundPanel = groundPanel;
    }

    /** 필요한 아이템 */
    public GroundPanel getGroundPanel() {return groundPanel;}

    private final Puyo leftPuyo = new Puyo(0);
    private final Puyo rightPuyo = new Puyo(0);

    public Puyo getLeftPuyo() {return leftPuyo;}
    public Puyo getRightPuyo() {return rightPuyo;}
}
