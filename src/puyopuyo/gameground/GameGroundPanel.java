package puyopuyo.gameground;

import puyopuyo.game.GameService;

import javax.swing.JPanel;

public class GameGroundPanel extends JPanel {
	private final GameGroundRepository gameGroundRepository = new GameGroundRepository(this);
	private final GameGroundService gameGroundService;

	/**
	 * 게임 보드를 구성하는 화면이다.
	 * <p>
	 * 1. 기본세팅: 키보드 값을 받기위한 리스너가 존재한다. <br>
	 * 2. 플레이어가 조작할 뿌요들을 구성한다. <br>
	 *
	 * @param gameService TODO: 작성할 것
	 */
    public GameGroundPanel(GameService gameService) {
        gameGroundService = new GameGroundService(gameGroundRepository, gameService);

		gameGroundService.setUi();
	}

	public GameGroundService getService() { return gameGroundService; }
}