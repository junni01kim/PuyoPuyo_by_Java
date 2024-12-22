package puyopuyo.client.panel.map.subpanel.ground;

import puyopuyo.resource.GameImageIcon;
import puyopuyo.server.game.round.PuyoS;

import javax.swing.*;
import java.awt.*;

/**
 * 한 라운드 간 진행되는 뿌요 보드을 관리하는 패널이다.<br>
 *
 * 시각적인 책임을 가지며, MVC 패턴의 View의 역할을 한다.
 */
public class GroundPanel extends JPanel {
    private final int player;

    private Puyo leftPuyo = new Puyo(5);
    private Puyo rightPuyo = new Puyo(5);

    private Puyo[][] puyoMap = new Puyo[6][12];

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

    public void clearPuyoMap() {
        for (Puyo[] puyos : puyoMap) {
            for (Puyo puyo : puyos) {
                if(puyo != null) {
                    remove(puyo);
                }
            }
        }
        repaint();
    }

    public void drawPuyoMap(PuyoS[][] puyoMapS) {
        for(int x = 0; x < puyoMapS.length; x++) {
            for(int y = 0; y < puyoMapS[x].length; y++) {
                if(puyoMapS[x][y] != null) {
                    puyoMap[x][y] = new Puyo(puyoMapS[x][y].getColor(), x, y);
                    add(puyoMap[x][y]);
                }
            }
        }
        if(leftPuyo != null) add(leftPuyo);
        if(rightPuyo != null) add(rightPuyo);
        repaint();
    }

    public void clearLrPuyo() {
        if(leftPuyo != null) remove(leftPuyo);
        if(rightPuyo != null) remove(rightPuyo);
        leftPuyo = null;
        rightPuyo = null;
        repaint();
    }

    public void drawLrPuyo(PuyoS[] puyoS) {
        leftPuyo = new Puyo(puyoS[0].getColor(), puyoS[0].x(), puyoS[0].y());
        rightPuyo = new Puyo(puyoS[1].getColor(), puyoS[1].x(), puyoS[1].y());
        add(leftPuyo);
        add(rightPuyo);
        repaint();
    }
}
