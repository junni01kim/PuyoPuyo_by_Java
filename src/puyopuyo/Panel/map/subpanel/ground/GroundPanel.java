package puyopuyo.Panel.map.subpanel.ground;

import puyopuyo.resource.GameImageIcon;

import javax.swing.*;
import java.awt.*;

/**
 * 한 라운드 간 진행되는 뿌요 보드을 관리하는 패널이다.<br>
 *
 * 시각적인 책임을 가지며, MVC 패턴의 View의 역할을 한다.
 */
public class GroundPanel extends JPanel {
    private final int player;

    private final GroundService groundService = new GroundService();

    public GroundPanel(int player) {
        this.player = player;
        setUi();
    }

    /**
     * 화면 설정을 하는 함수이다. <br>
     *
     * 1. 플레이어가 조작할 수 있는 뿌요를 생성한다. <br>
     * 2. 화면 사이즈: 400, 750 <br>
     * 3. 각 Puyo 위치 지정: <br>
     * __1) LeftPuyo: 140, 10
     * __2) RightPuyo: 200, 10
     */
    public void setUi() {
        setLayout(null);
        setSize(400,750);

        if(player == 1) setLocation(50,60);
        else setLocation(830,60);

        Puyo leftPuyo = groundService.getLeftPuyo();
        Puyo rightPuyo = groundService.getRightPuyo();

        leftPuyo.setLocation(140,10);
        rightPuyo.setLocation(200,10);

        add(leftPuyo);
        add(rightPuyo);

    }

    /**
     * 패널의 백그라운드 이미지를 그리기 위한 함수 <br>
     * 전체 화면 크기로 이미지가 배치 된다.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if(player == 1) graphics.drawImage(GameImageIcon.player1Ground.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        else graphics.drawImage(GameImageIcon.player2Ground.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public GroundService getGroundService() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGroundService'");
    }

    // getter

}
