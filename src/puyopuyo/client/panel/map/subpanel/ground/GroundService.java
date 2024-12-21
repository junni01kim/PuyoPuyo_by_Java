package puyopuyo.client.panel.map.subpanel.ground;

import puyopuyo.server.game.round.PuyoS;

/**
 * 한 라운드 간 진행되는 뿌요 보드을 관리하는 패널 서비스이다.<br>
 *
 * 패널 관리 책임을 가지며, MVC 패턴의 Model의 역할을 한다.
 *
 * 코드의 복잡성을 줄이기 위해 합성관계로 구현하였다.
 */
public class GroundService {
    private final Puyo leftPuyo = new Puyo(5);
    private final Puyo rightPuyo = new Puyo(5);

    private Puyo[][] puyoMap = new Puyo[6][12];

    /**
     * 사용자가 조작 가능한 왼쪽 뿌요를 반환한다.
     *
     */
    public Puyo getLeftPuyo() {
        return leftPuyo;
    }

    /**
     * 사용자가 조작 가능한 오른쪽 뿌요를 반환한다.
     *
     */
    public Puyo getRightPuyo() {
        return rightPuyo;
    }

    /**
     * 사용자가 조작하고 있는 화면의 값을 반환한다.
     *
     */
    public Puyo[][] getPuyoMap() {
        return puyoMap;
    }
}