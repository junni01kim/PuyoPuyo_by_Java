package puyopuyo.menu;

public class MenuRepository {
    /** 자기 자신 */
    private final MenuPanel menuPanel;
    /** 화면 이동을 위한 Ui */

    MenuRepository(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }
}
