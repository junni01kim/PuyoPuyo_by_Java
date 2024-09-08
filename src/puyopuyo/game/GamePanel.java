package puyopuyo.game;

import puyopuyo.GameImageIcon;
import puyopuyo.game.roundthread.RoundThreadService;
import puyopuyo.gameframe.GameFrameService;
import puyopuyo.game.roundthread.RoundThread;

import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	/** 게임 라운드 진행 스레드 TODO: 오류 발생 가능성 높음 */
	private RoundThreadService roundThreadService;

	private final GameService gameService = new GameService(this, roundThreadService);

	/**
	 * 게임을 진행하는 화면이다.
	 * <p>
	 * 1. 기본세팅: 키보드 값을 받기위한 리스너가 존재한다. <br>
	 * 2. 라운드를 진행하는 스레드가 존재한다. <br>
	 * 3. TODO: 지금 패널 배치를 수동으로 지정해두었는데, 화면을 자유롭게 조정할 수 있도록 배치관리자를 주성할 것 <br>
	 * 4. 실제 게임이 진행될 화면 1p, 2p를 구성한다. <br>
	 * 5. 게임의 점수가 표시될 scorePanel을 생성한다. <br>
	 * @param gameFrameService 다른 패널들을 참조하기 위해 gameFrame의 패널 객체를 이용한다.
	 */
	public GamePanel(GameFrameService gameFrameService) {
		var roundThread = new RoundThread(gameService, gameFrameService);
		roundThreadService = roundThread.getService();

		addKeyListener(new ControlPuyoKeyListener(gameService, roundThreadService));

		gameService.setUi();

		roundThreadService.start();
	}

	public RoundThreadService getRoundThread() {
		return roundThreadService;
	}

	/**
	 * 패널의 백그라운드 이미지를 그리기 위한 함수 <br>
	 * 전체 화면 크기로 이미지가 배치 된다.
	 * @param graphics the <code>Graphics</code> object to protect
	 */
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.drawImage(GameImageIcon.gamePanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
