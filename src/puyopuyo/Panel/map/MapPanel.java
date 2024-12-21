package puyopuyo.Panel.map;

import puyopuyo.frame.Frame;
import puyopuyo.Panel.PanelState;
import puyopuyo.Panel.map.game.GameThread;
import puyopuyo.resource.GameImageIcon;

import javax.swing.*;
import java.awt.*;

/**
 * 게임 전체 화면을 관리하는 패널이다.<br>
 * 시각적인 책임을 가지며, MVC 패턴의 View의 역할을 한다.
 * 게임 창은 하나만 구현되므로, 싱글톤 패턴을 이용하여 개발했다.
 */
public class MapPanel extends JPanel implements PanelState {
    private static MapPanel instance;

    private JLabel winnerLabel;
    private JLabel loserLabel;

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
     * 전체 화면 크기로 이미지가 배치된다.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(GameImageIcon.gamePanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    /**
     * 화면 설정을 하는 함수 <br>
     * 1. 플레이어가 진행할 GameGround, ScorePanel을 생성한다. <br>
     * 2. 해당 패널에 ControlPuyoKeyListener 연결.
     */
    @Override
    public void setUi() {
        this.setLayout(null);

        var mapService = MapService.getInstance();
        mapService.openMap();

        add(mapService.getScorePanel());
        add(mapService.getGroundPanel(1));
        add(mapService.getGroundPanel(2));

        // 승패 이미지를 위한 JLabel 초기화
        winnerLabel = new JLabel();
        winnerLabel.setBounds(830, 300, 400, 200); // 기본 위치 설정
        winnerLabel.setVisible(false);
        add(winnerLabel);

        loserLabel = new JLabel();
        loserLabel.setBounds(50, 300, 400, 200); // 기본 위치 설정
        loserLabel.setVisible(false);
        add(loserLabel);

        addKeyListener();
    }

    @Override
    public void process() {
        new GameThread().start();
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
        MapService.getInstance().closeMap();
        var me = getInstance();

        // 이전 화면 삭제
        frame.remove(me);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * 해당 패널에서 키보드를 주관할 수 있게 만드는 함수.
     */
    public void addKeyListener() {
        addKeyListener(new ControlPuyoKeyListener());
        setFocusable(true);
        requestFocus();
    }

    /**
     * 승리 및 패배 이미지를 표시하는 함수.
     *
     * @param is1PWinner 1P가 승리했는지 여부.
     */
    public void showWinnerAndLoser(boolean is1PWinner) {
        if (is1PWinner) {
            winnerLabel.setIcon(GameImageIcon.WINImage);
            loserLabel.setIcon(GameImageIcon.LOSERImage);

            winnerLabel.setLocation(400, 300); // 1P 영역
            loserLabel.setLocation(900, 300); // 2P 영역
        } else {
            winnerLabel.setIcon(GameImageIcon.WINImage);
            loserLabel.setIcon(GameImageIcon.LOSERImage);

            winnerLabel.setLocation(900, 300); // 2P 영역
            loserLabel.setLocation(400, 300); // 1P 영역
        }

        winnerLabel.setVisible(true);
        loserLabel.setVisible(true);

        // 3초 뒤 이미지를 숨기는 타이머
        Timer timer = new Timer(3000, e -> {
            winnerLabel.setVisible(false);
            loserLabel.setVisible(false);
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }
}
