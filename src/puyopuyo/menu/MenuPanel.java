package puyopuyo.menu;

import puyopuyo.GameImageIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private final MenuService menuService = new MenuService(this);

    public MenuPanel() {
        // TODO: Action Listener 때문에 따로 빼두지 못함 대안 생각해볼 것
        var startGameButton = new JButton(GameImageIcon.startGameButtonImage);
        startGameButton.addActionListener(new MenuPanel.StartGameButtonActionListener());
        var explainGameButton = new JButton("ExplainGame");

        menuService.setUi(startGameButton, explainGameButton);
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

    /**
     * GamePanel로 이동하는 클릭 리스너
     */
    class StartGameButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton myButton = (JButton)e.getSource();
            myButton.getParent().setVisible(false);
            //gameFrameService.openGameMenuPanel();
        }
    }
}