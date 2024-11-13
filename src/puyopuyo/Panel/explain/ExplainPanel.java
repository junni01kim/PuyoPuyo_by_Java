package puyopuyo.Panel.explain;

import puyopuyo.Panel.PanelState;
import puyopuyo.Panel.map.MapPanel;
import puyopuyo.Panel.start.StartPanel;
import puyopuyo.frame.Frame;
import puyopuyo.resource.GameImageIcon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExplainPanel extends JPanel implements PanelState {
    private static ExplainPanel instance;

    public synchronized static ExplainPanel getInstance() {
        if (instance == null) {
            instance = new ExplainPanel();
        }
        return instance;
    }

    public ExplainPanel() {
        setUi();
    }

    @Override
    public void setUi() {
        var homeButton = new JButton("홈화면으로 이동");
        homeButton.addActionListener(new HomeButtonActionListener());

        homeButton.setLocation(550, 540);
        homeButton.setSize(150, 60);
        this.add(homeButton);
    }

    @Override
    public void process() {

    }

    @Override
    public void open(Frame frame) {
        var me = ExplainPanel.getInstance();

        frame.add(me);
        frame.setVisible(true);
        frame.repaint();
    }

    @Override
    public void close(Frame frame) {
        var me = getInstance();

        // 이전 화면 삭제
        frame.remove(me);
        frame.repaint();
    }

    /**
     * 패널 조작 책임을 가지며, MVC 패턴의 Controller의 역할을 한다.
     *
     * 코드의 복잡성을 줄이기 위해 합성관계로 구현하였다.
     */
    class HomeButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton myButton = (JButton)e.getSource();
            myButton.getParent().setVisible(false);

            var frame = Frame.getInstance();
            var startPanel = StartPanel.getInstance();

            frame.changePanel(startPanel);
        }
    }
}
