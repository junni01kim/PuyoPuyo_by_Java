package puyopuyo.gameframe;

import puyopuyo.game.GamePanel;
import puyopuyo.gamemenu.GameMenuPanel;

import java.awt.*;

public class GameFrameService {
    private final GameFrameRepository gameFrameRepository;

    public GameFrameService(GameFrame gameFrame) {
        this.gameFrameRepository = new GameFrameRepository(gameFrame, new GamePanel(this), new GameMenuPanel(this));
    }

    public GameFrame getGameFrame() {
        return gameFrameRepository.getGameFrame();
    }

    /**
     * GamePanel을 구성하기 위한 기본적인 아이템 세팅 <br>
     * 1. GamePanel을 생성한다. <br>
     * 2. GamePanel을 배치한다. <br>
     * 3. GamePanel을 보이게 한다.
     */
    public void openGamePanel() {
        var gameFrame = gameFrameRepository.getGameFrame();
        var gamePanel = gameFrameRepository.getGamePanel();
        gameFrame.getContentPane().add(gamePanel, BorderLayout.CENTER);
        gamePanel.setVisible(true);

        gamePanel.requestFocus();
    }

    /**
     * GameMenuPanel을 구성하기 위한 기본적인 아이템 세팅 <br>
     * 1. GameMenuPanel을 생성한다. <br>
     * 2. GameMunuPanel을 배치한다. <br>
     * 3. GameManuPanel을 보이게 한다.
     */
    public void openGameMenuPanel() {
        var gameFrame = getGameFrame();
        var gameMenuPanel = gameFrameRepository.getGameMenuPanel();

        gameFrame.getContentPane().add(gameMenuPanel, BorderLayout.CENTER);
        gameMenuPanel.setVisible(true);
    }

    /**
     * GamePanel을 제거한다.
     */
    public void closeGameMenuPanel() {
        var gameFrame = getGameFrame();
        var gameMenuPanel = gameFrameRepository.getGameMenuPanel();
        gameFrame.getContentPane().remove(gameMenuPanel);
    }
}
