package puyopuyo.menu;

import puyopuyo.game.GamePanel;

public class MenuRepository {
    /** 자기 자신 */
    private final MenuPanel menuPanel;
    /** 화면 이동을 위한 Ui */
    private final GamePanel gamePanel = new GamePanel();

    MenuRepository(MenuPanel menuPanel) {this.menuPanel = menuPanel;}

    public MenuPanel getMenuPanel() {return menuPanel;}
    public GamePanel getScreenPanel() {return gamePanel;}
}
