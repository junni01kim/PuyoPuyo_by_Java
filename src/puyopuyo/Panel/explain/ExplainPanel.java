package puyopuyo.Panel.explain;

import puyopuyo.Panel.PanelState;
import puyopuyo.Panel.map.MapPanel;
import puyopuyo.Panel.start.StartPanel;
import puyopuyo.frame.Frame;
import puyopuyo.resource.GameImageIcon;

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
        setLayout(null); // 절대 위치 사용
        setBackground(Color.WHITE); // 배경 색상 설정

        // 이미지를 로드하여 JLabel에 추가
        ImageIcon explainImageIcon = new ImageIcon("path/to/Explain.png"); // 실제 파일 경로로 변경 필요
        JLabel explainImageLabel = new JLabel(explainImageIcon);
        explainImageLabel.setBounds(100, 50, explainImageIcon.getIconWidth(), explainImageIcon.getIconHeight()); // 위치와 크기 설정

        this.add(explainImageLabel); // 패널에 이미지 추가

        // 홈으로 이동 버튼 설정
        JButton homeButton = new JButton("홈화면으로 이동");
        homeButton.setLocation(550, 540);
        homeButton.setSize(150, 60);
        homeButton.addActionListener(new HomeButtonActionListener());
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
