package puyopuyo.menu;

import puyopuyo.screen.ScreenPanel;

public class MenuRepository {
    /** 자기 자신 */
    private final MenuPanel menuPanel;
    /** 화면 이동을 위한 Ui */
    private final ScreenPanel screenPanel = new ScreenPanel();

    MenuRepository(MenuPanel menuPanel) {this.menuPanel = menuPanel;}

    public MenuPanel getMenuPanel() {return menuPanel;}
    public ScreenPanel getScreenPanel() {return screenPanel;}
}
