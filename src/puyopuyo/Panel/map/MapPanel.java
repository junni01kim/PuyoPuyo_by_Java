package puyopuyo.Panel.map;

import puyopuyo.frame.Frame;
import puyopuyo.Panel.PanelState;
import puyopuyo.Panel.map.game.GameThread;
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
        process();
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
     * 2. SubPanel 위치: <br>
     *   __1) GameGround1P: 50, 60 <br>
     *   __2) GameGround2P: 830, 60 <br>
     *   __3) ScorePanel: 490, 60 <br>
     * 3. 해당 패널에 ControlPuyoKeyListener 연결한다. <br>
     */
    @Override
    public void setUi() {
        this.setLayout(null);

        var mapService = MapService.getInstance();

        mapService.openMap();

        var scorePanel = mapService.getScorePanel();
        var gameGround1P = mapService.getGroundPanel1P();
        var gameGround2P = mapService.getGroundPanel2P();

        scorePanel.setLocation(490,60);
        gameGround1P.setLocation(50,60);
        gameGround2P.setLocation(830,60);

        add(scorePanel);
        add(mapService.getGroundPanel1P());
        add(mapService.getGroundPanel2P());

        addKeyListener();
    }

    @Override
    public void process() {
        new GameThread(MapService.getInstance()).start();
    }

    @Override
    public void open() {
        var frame = Frame.getInstance();
        var me = MapPanel.getInstance();

        frame.add(me);
        frame.setVisible(true);
    }

    @Override
    public void close() {
        var frame = Frame.getInstance();
        var me = getInstance();

        // 이전 화면 삭제
        frame.remove(me);
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
        //TODO: 에러발생 위치:: 수정할 것
        addKeyListener(new ControlPuyoKeyListener());
        setFocusable(true);
        requestFocus();
    }
}
