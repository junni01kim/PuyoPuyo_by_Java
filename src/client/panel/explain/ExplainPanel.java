package client.panel.explain;

import client.ClientKeyListener;
import client.ClientProcess;
import puyopuyo.Panel.PanelState;
import client.panel.start.StartPanel;
import client.frame.Frame;

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

        ClientProcess.getInstance(); //TODO: 임시 코드
    }

    @Override
    public void setUi() {
        var homeButton = new JButton("Nav Home");
        homeButton.addActionListener(new HomeButtonActionListener());

        addKeyListener(); // TODO: 임시!!

        homeButton.setLocation(550, 540);
        homeButton.setSize(150, 60);
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
        
        ClientProcess.getInstance().closeSocket(); // TODO: 임시코드
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

    /**
     * TODO: 임시 패널!!
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
        requestFocus();
    }
}
