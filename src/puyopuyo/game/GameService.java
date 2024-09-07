package puyopuyo.game;

import puyopuyo.GameGround;

public class GameService {
    private final GameRepository gameRepository;

    /**
     * MVC 패턴 주입 (???)
     *
     * @param gameRepository GamePanel에서 생성한 사용하는 엔티티
     */
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameGround getGameGround1P() {
        return gameRepository.getGameGround1P();
    }

    public GameGround getGameGround2P() {
        return gameRepository.getGameGround2P();
    }

    /**
     * gamePanel에 배치할 Ui 설정
     */
    public void setUi() {
        var gamePanel = gameRepository.getGamePanel();
        var gameGround1P = gameRepository.getGameGround1P();
        var gameGround2P = gameRepository.getGameGround2P();
        var scorePanel = gameRepository.getScorePanel();

        gamePanel.setLayout(null);

        gameGround1P.setLocation(50,60);
        gamePanel.add(gameGround1P);

        gameGround2P.setLocation(830,60);
        gamePanel.add(gameGround2P);

        scorePanel.setLocation(490,60);
        gamePanel.add(scorePanel);

        /*
         * 해당 패널의 키보드 값을 받도록 설정.
         * 보통은 따로하나, 동시에 1p, 2p의 키보드 값을 받게하기 위함
         */
        gamePanel.setFocusable(true);
    }

    public void close() {
        var gamePanel = gameRepository.getGamePanel();
        gamePanel.remove(gamePanel);
    }
}
