package puyopuyo.menu;

public class MenuRepository {
    private final MenuPanel menuPanel;

    MenuRepository(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }
}
