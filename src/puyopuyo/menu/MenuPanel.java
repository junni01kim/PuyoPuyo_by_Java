package puyopuyo.menu;

import puyopuyo.puyo.GameImageIcon;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private final MenuService menuService = new MenuService(this);

    public MenuPanel() {
        var startGameButton = new JButton(GameImageIcon.startGameButtonImage);
        var explainGameButton = new JButton("ExplainGame");

        menuService.setUi(startGameButton, explainGameButton);
    }

    public MenuService getMenuService() {return menuService;}

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
}