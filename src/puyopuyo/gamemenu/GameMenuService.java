package puyopuyo.gamemenu;

import puyopuyo.GameImageIcon;
import puyopuyo.game.gameframe.GameFrameService;

import javax.swing.*;
import java.awt.*;

public class GameMenuService {
    private final GameMenuRepository gameMenuRepository;
    private final GameFrameService gameFrameService;

    public GameMenuService(GameMenuRepository gameMenuRepository, GameFrameService gameFrameService) {
        this.gameFrameService = gameFrameService;
        this.gameMenuRepository = gameMenuRepository;
    }

    public void setUi() {
        var gameMenuPanel = gameMenuRepository.getGameMenuPanel();

        gameMenuPanel.setLayout(null);

        var explainGameButton = new JButton("ExplainGame");
        explainGameButton.addActionListener(null);
        explainGameButton.setLocation(550, 620);
        explainGameButton.setSize(150, 60);
        gameMenuPanel.add(explainGameButton);
    }
}
