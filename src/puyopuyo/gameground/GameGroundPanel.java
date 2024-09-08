package puyopuyo.gameground;

import puyopuyo.ScorePanel;
import puyopuyo.game.GameService;
import puyopuyo.game.roundthread.RoundThreadService;
import puyopuyo.gameground.playerthread.PlayerThread;
import puyopuyo.gameground.playerthread.PlayerThreadService;

import javax.swing.JPanel;

public class GameGroundPanel extends JPanel {
	private final GameGroundRepository gameGroundRepository = new GameGroundRepository(this);
	private final GameGroundService gameGroundService;

	private PlayerThreadService playerThreadService;

	/**
	 * 게임 보드를 구성하는 화면이다.
	 * <p>
	 * 1. 기본세팅: 키보드 값을 받기위한 리스너가 존재한다. <br>
	 * 2. 플레이어가 조작할 뿌요들을 구성한다. <br>
	 *
	 * @param gameService TODO: 작성할 것
	 */
    public GameGroundPanel(GameService gameService, RoundThreadService roundThreadService, ScorePanel scorePanel, int iAm) {
        gameGroundService = new GameGroundService(gameGroundRepository, gameService);

		var playerThread = new PlayerThread(gameService, gameGroundService, roundThreadService, scorePanel, iAm);

		gameGroundService.setUi();
	}

	public GameGroundService getService() { return gameGroundService; }
	public PlayerThreadService getThreadService() { return playerThreadService; }
}