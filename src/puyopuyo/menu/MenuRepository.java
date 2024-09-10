package puyopuyo.menu;

import puyopuyo.map.MapPanel;

public class MenuRepository {
    /** 자기 자신 */
    private final MenuPanel menuPanel;
    /** 화면 이동을 위한 Ui */
    private final MapPanel mapPanel = new MapPanel();

    MenuRepository(MenuPanel menuPanel) {this.menuPanel = menuPanel;}

    public MenuPanel getMenuPanel() {return menuPanel;}
    public MapPanel getScreenPanel() {return mapPanel;}
}
