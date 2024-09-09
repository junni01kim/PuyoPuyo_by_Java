package puyopuyo.menu;

import javax.swing.*;

public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuPanel menuPanel) {
        this.menuRepository = new MenuRepository(menuPanel);
    }

    public void setUi( JButton startGameButton, JButton explainGameButton) {
        var gameMenuPanel = menuRepository.getMenuPanel();

        startGameButton.setLocation(550, 540);
        startGameButton.setSize(150, 60);
        gameMenuPanel.add(startGameButton);

        explainGameButton.addActionListener(null);
        explainGameButton.setLocation(550, 620);
        explainGameButton.setSize(150, 60);
        gameMenuPanel.add(explainGameButton);

        gameMenuPanel.setLayout(null);
    }
}
