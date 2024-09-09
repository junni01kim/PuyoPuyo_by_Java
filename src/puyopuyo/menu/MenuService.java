package puyopuyo.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuService {
    private final MenuRepository menuRepository;

    /**
     * 생성자, 따로 변수를 주입받지는 않는다.
     *
     * @param menuPanel MenuRepository 생성 시 menuPanel을 전달하기 위해서 사용
     */
    public MenuService(MenuPanel menuPanel) {
        this.menuRepository = new MenuRepository(menuPanel);
    }

    public void setUi(JButton startGameButton, JButton explainGameButton) {
        var gameMenuPanel = menuRepository.getMenuPanel();
        gameMenuPanel.setLayout(null);

        startGameButton.addActionListener(new StartGameButtonActionListener());

        startGameButton.setLocation(550, 540);
        startGameButton.setSize(150, 60);
        gameMenuPanel.add(startGameButton);

        explainGameButton.addActionListener(null);
        explainGameButton.setLocation(550, 620);
        explainGameButton.setSize(150, 60);
        gameMenuPanel.add(explainGameButton);
    }

    public void openMenuPanel(Container contentPane) {
        var menuPanel = menuRepository.getMenuPanel();
        contentPane.add(menuPanel, BorderLayout.CENTER);
        menuPanel.setVisible(true);
    }

    public void closeMenuPanel() {
        var frame = menuRepository.getMenuPanel().getParent();
        var menuPanel = menuRepository.getMenuPanel();

        // 이전 화면 삭제
        frame.remove(menuPanel);
    }

    /**
     * GamePanel로 이동하는 클릭 리스너
     */
    class StartGameButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton myButton = (JButton)e.getSource();
            myButton.getParent().setVisible(false);
            
            // 새로운 화면 생성
            var frame = menuRepository.getMenuPanel().getParent();
            var screenService = menuRepository.getScreenPanel().getScreenService();
            screenService.openGamePanel(frame);

            // 이전 화면 삭제
            closeMenuPanel();
        }
    }
}
