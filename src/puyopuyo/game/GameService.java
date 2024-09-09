package puyopuyo.game;

import java.awt.*;

public class GameService {
    private final GameRepository gameRepository;

    public GameService(GamePanel gamePanel) {
        gameRepository = new GameRepository(gamePanel);
    }

    public void setUi() {
        var screenPanel = gameRepository.getScreenPanel();
        screenPanel.setLayout(null);

        var scorePanel = gameRepository.getScorePanel();
        var gameGround1P = gameRepository.getGroundPanel1P();
        var gameGround2P = gameRepository.getGroundPanel2P();

        scorePanel.setLocation(490,60);
        gameGround1P.setLocation(50,60);
        gameGround2P.setLocation(830,60);

        screenPanel.add(scorePanel);
        screenPanel.add(gameGround1P);
        screenPanel.add(gameGround2P);

        /*
         * 해당 패널의 키보드 값을 받도록 설정.
         * 보통은 따로하나, 동시에 1p, 2p의 키보드 값을 받게하기 위함
         */
        screenPanel.setFocusable(true);
    }

    public void start() {gameRepository.getRoundThread().start();}

    public void openGamePanel(Container contentPane) {
        var screenPanel = gameRepository.getScreenPanel();
        contentPane.add(screenPanel);
        screenPanel.setVisible(true);
    }

    public void closeGamePanel() {
        var frame = gameRepository.getScreenPanel().getParent();
        var screenPanel = gameRepository.getScreenPanel();

        // 이전 화면 삭제
        frame.remove(screenPanel);
    }
}
