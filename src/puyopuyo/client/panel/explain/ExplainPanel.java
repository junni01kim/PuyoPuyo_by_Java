package puyopuyo.client.panel.explain;

import puyopuyo.client.panel.PanelState;
import puyopuyo.client.panel.start.StartPanel;
import puyopuyo.client.frame.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 게임의 규칙을 설명하는 패널이다. <br>
 *
 * 시각적인 책임을 가지며, MVC 패턴의 View의 역할을 한다.
 *
 * 설명 창은 하나만 구현되므로, 싱글 통 패턴을 이용하여 개발했다.
 *
 * ※ 따로 관리할 엔티티가 존재하지 않아 Model은 생략하였다.
 */
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

    /**
     * 화면 설정을 하는 함수이다. <br>
     *
     * 1. 플레이어가 뒤로가기를 진행할 Button을 배치한다.
     */
    @Override
    public void setUi() {
        var homeButton = new JButton("Nav Home");
        homeButton.addActionListener(new HomeButtonActionListener());

        homeButton.setLocation(550, 540);
        homeButton.setSize(150, 60);
        this.add(homeButton);
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
        me.setVisible(true);
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
