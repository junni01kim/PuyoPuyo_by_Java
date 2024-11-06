package puyopuyo.menu;

import puyopuyo.frame.Frame;
import puyopuyo.frame.PanelState;
import puyopuyo.map.MapPanel;
import puyopuyo.puyo.GameImageIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements PanelState {
    private static MenuPanel instance;

    public synchronized static MenuPanel getInstance() {
        if (instance == null) {
            instance = new MenuPanel();
        }
        return instance;
    }

    public MenuPanel() {
        setUi();
    }

    /**
     * 패널의 백그라운드 이미지를 그리기 위한 함수 <br>
     * 전체 화면 크기로 이미지가 배치 된다.
     * @param graphics the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(GameImageIcon.gameMenuPanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    @Override
    public void setUi() {
        this.setLayout(null);

        var startGameButton = new JButton(GameImageIcon.startGameButtonImage);
        var explainGameButton = new JButton("ExplainGame");

        startGameButton.addActionListener(new StartGameButtonActionListener());

        startGameButton.setLocation(550, 540);
        startGameButton.setSize(150, 60);
        this.add(startGameButton);

        explainGameButton.addActionListener(null);
        explainGameButton.setLocation(550, 620);
        explainGameButton.setSize(150, 60);
        this.add(explainGameButton);
    }

    @Override
    public void process() {

    }

    @Override
    public void open() {
        var frame = Frame.getInstance();
        var me = MenuPanel.getInstance();

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
     * GamePanel로 이동하는 클릭 리스너
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
}