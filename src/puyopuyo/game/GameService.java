package puyopuyo.game;

import puyopuyo.ScorePanel;
import puyopuyo.game.roundthread.RoundThread;
import puyopuyo.gameframe.GameFrameService;
import puyopuyo.gameground.GameGroundPanel;

public class GameService {
    private final GameRepository gameRepository;
    private final ScorePanel scorePanel;
    private final RoundThread roundThread;

    /**
     * MVC 패턴 주입 (???)
     *
     * @param gamePanel TODO: 작성하기
     */
    public GameService(GamePanel gamePanel, GameFrameService gameFrameService) {
        scorePanel = new ScorePanel();

        gameRepository = new GameRepository(gamePanel,  new GameGroundPanel(this, scorePanel, 1), new GameGroundPanel(this, scorePanel, 2), scorePanel);

        roundThread = new RoundThread(this, gameFrameService);
    }

    public RoundThread getRoundThread() { return roundThread; }

    public GameGroundPanel getGameGround1P() { return gameRepository.getGameGround1P(); }

    public GameGroundPanel getGameGround2P() { return gameRepository.getGameGround2P(); }

    public ScorePanel getScorePanel() { return scorePanel; }

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
