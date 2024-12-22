
package puyopuyo.client.panel.explain;

import puyopuyo.Panel.PanelState;
import puyopuyo.client.panel.start.StartPanel;
import puyopuyo.client.frame.Frame;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

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
        setLayout(null); // 절대 레이아웃 사용

        // 이미지 레이블 추가
        JLabel explainImageLabel = new JLabel(GameImageIcon.explainGameButtonImage);
        explainImageLabel.setBounds(50, 50, GameImageIcon.explainGameButtonImage.getIconWidth(), GameImageIcon.explainGameButtonImage.getIconHeight());
        this.add(explainImageLabel);

        // "홈화면으로 이동" 버튼 추가
        var homeButton = new JButton("홈화면으로 이동");
        homeButton.setBounds(550, 750, 150, 30);
        homeButton.addActionListener(new HomeButtonActionListener());
<<<<<<<< HEAD:src/puyopuyo/Panel/explain/ExplainPanel.java
========

        homeButton.setLocation(550, 540);
        homeButton.setSize(150, 60);
>>>>>>>> main:src/puyopuyo/client/panel/explain/ExplainPanel.java
        this.add(homeButton);
    }



    @Override
    public void process() {
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
