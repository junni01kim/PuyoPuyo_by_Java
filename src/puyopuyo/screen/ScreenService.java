package puyopuyo.screen;

import java.awt.*;

public class ScreenService {
    private final ScreenRepository screenRepository;

    public ScreenService(ScreenPanel screenPanel) {
        screenRepository = new ScreenRepository(screenPanel);
    }

    public void setUi() {
        var screenPanel = screenRepository.getScreenPanel();
        screenPanel.setLayout(null);

        var scorePanel = screenRepository.getScorePanel();

//        var gameGround1P = gameRepository.getGameGround1P();
//        var gameGround2P = gameRepository.getGameGround2P();
//        var scorePanel = gameRepository.getScorePanel();
//
//        gameGround1P.setLocation(50,60);
//        gamePanel.add(gameGround1P);
//
//        gameGround2P.setLocation(830,60);
//        gamePanel.add(gameGround2P);
//
        scorePanel.setLocation(490,60);
        screenPanel.add(scorePanel);
//
        /*
         * 해당 패널의 키보드 값을 받도록 설정.
         * 보통은 따로하나, 동시에 1p, 2p의 키보드 값을 받게하기 위함
         */
        screenPanel.setFocusable(true);
    }

    public void openScreenPanel(Container contentPane) {
        var screenPanel = screenRepository.getScreenPanel();
        contentPane.add(screenPanel);
        screenPanel.setVisible(true);
    }
}
