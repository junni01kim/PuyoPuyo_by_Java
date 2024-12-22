package puyopuyo.client.panel.start;

import puyopuyo.client.panel.explain.ExplainPanel;
import puyopuyo.client.frame.Frame;
import puyopuyo.client.panel.PanelState;
import puyopuyo.client.panel.map.MapPanel;
import puyopuyo.resource.GameImageIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 시작 화면을 관리하는 패널이다.<br>
 *
 * 시각적인 책임을 가지며, MVC 패턴의 View의 역할을 한다.
 *
 * 시작 창은 하나만 구현되므로, 싱글톤 패턴을 이용하여 개발했다.
 *
 * ※ 따로 관리할 엔티티가 존재하지 않아 Model은 생략하였다.
 */
public class StartPanel extends JPanel implements PanelState {
    private static StartPanel instance;

    public synchronized static StartPanel getInstance() {
        if (instance == null) {
            instance = new StartPanel();
        }
        return instance;
    }

    public StartPanel() {
        setUi();
    }

    /**
     * 패널의 백그라운드 이미지를 그리기 위한 함수 <br>
     * 전체 화면 크기로 이미지가 배치 된다.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(GameImageIcon.gameMenuPanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    /**
     * 화면 설정을 하는 함수이다. <br>
     *
     * 1. MapPanel로 이동하는 버튼을 생성한다. <br>
     * 2. ExplainPanel로 이동하는 버튼을 생성한다. <br> //TODO: 제작 필요
     */
    @Override
    public void setUi() {
        this.setLayout(null);

        var startGameButton = new JButton(GameImageIcon.startGameButtonImage);
        var explainGameButton = new JButton("ExplainGame");

        startGameButton.addActionListener(new StartGameButtonActionListener());

        startGameButton.setLocation(550, 540);
        startGameButton.setSize(150, 60);
        this.add(startGameButton);

        explainGameButton.addActionListener(new ExplainButtonActionListener());
        explainGameButton.setLocation(550, 620);
        explainGameButton.setSize(150, 60);
        this.add(explainGameButton);
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
        var me = getInstance();

        // 이전 화면 삭제
        frame.remove(me);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * 패널 조작 책임을 가지며, MVC 패턴의 Controller의 역할을 한다.
     *
     * 코드의 복잡성을 줄이기 위해 합성관계로 구현하였다.
     */
    class StartGameButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton myButton = (JButton)e.getSource();
            myButton.getParent().setVisible(false);

            var frame = Frame.getInstance();
            var mapPanel = MapPanel.getInstance();

            frame.changePanel(mapPanel);
        }
    }

    class ExplainButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton myButton = (JButton)e.getSource();
            myButton.getParent().setVisible(false);

            var frame = Frame.getInstance();
            var explainPanel = ExplainPanel.getInstance();

            frame.changePanel(explainPanel);
        }
    }
}