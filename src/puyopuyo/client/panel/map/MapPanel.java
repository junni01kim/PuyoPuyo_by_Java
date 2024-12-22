package puyopuyo.client.panel.map;

import puyopuyo.client.ClientKeyListener;
import puyopuyo.client.ClientProcess;
import puyopuyo.client.frame.Frame;
import puyopuyo.client.panel.PanelState;
import puyopuyo.resource.GameImageIcon;

import javax.swing.*;
import java.awt.*;

/**
 * 게임 전체 화면을 관리하는 패널이다.<br>
 *
 * 시각적인 책임을 가지며, MVC 패턴의 View의 역할을 한다.
 *
 * 게임 창은 하나만 구현되므로, 싱글톤 패턴을 이용하여 개발했다.
 */
public class MapPanel extends JPanel implements PanelState {
    private static MapPanel instance;

    public synchronized static MapPanel getInstance() {
        if (instance == null) {
            instance = new MapPanel();
        }
        return instance;
    }

    public MapPanel() {
        setUi();
    }

    /**
     * 패널의 백그라운드 이미지를 그리기 위한 함수 <br>
     * 전체 화면 크기로 이미지가 배치 된다.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(GameImageIcon.gamePanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    /**
     * 화면 설정을 하는 함수이다. <br>
     *
     * 1. 플레이어가 진행할 GameGround, ScorePanel을 생성한다. <br>
     * 2. 해당 패널에 ControlPuyoKeyListener 연결한다. <br>
     */
    @Override
    public void setUi() {
        this.setLayout(null);
        var mapService = MapService.getInstance();

        mapService.openMap();

        add(mapService.getScorePanel());
        add(mapService.getGroundPanel(1));
        add(mapService.getGroundPanel(2));

        addKeyListener();
    }

    @Override
    public void open(Frame frame) {
        var me = getInstance();
        me.setVisible(true);
        frame.add(me);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void close(Frame frame) {
        ClientProcess.getInstance().closeSocket();

        MapService.getInstance().closeMap();
        var me = getInstance();

        // 이전 화면 삭제
        frame.remove(me);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * 해당 패널에서 키보드를 주관할 수 있게 만드는 함수이다.
     *
     */
    public void addKeyListener() {

        /*
         * 해당 패널의 키보드 값을 받도록 설정.
         * 보통은 따로하나, 동시에 1p, 2p의 키보드 값을 받게하기 위함
         */
        addKeyListener(new ClientKeyListener());
        setFocusable(true);
        SwingUtilities.invokeLater(this::requestFocus);
    }
}
