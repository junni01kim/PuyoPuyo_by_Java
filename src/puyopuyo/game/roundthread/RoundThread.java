package puyopuyo.game.roundthread;

import puyopuyo.PlayerThread;
import puyopuyo.game.GameRepository;
import puyopuyo.game.GameService;
import puyopuyo.game.gameframe.GameFrameService;
import puyopuyo.gamemenu.GameMenuService;

/**
 * 라운드 스레드
 * <p>
 * 한 라운드 전체를 관리하는 스레드이다.
 */
public class RoundThread extends Thread {

	RoundThreadService roundThreadService = new RoundThreadService();
	GameFrameService gameFrameService;
	GameService gameService;

	PlayerThread playerThread1P;
	PlayerThread playerThread2P;

	/** playerThread getter */
	public PlayerThread getPlayerThread1P() {return playerThread1P;}
	public PlayerThread getPlayerThread2P() {return playerThread2P;}

	/**
	 * 게임의 한 라운드를 관리하는 스레드이다.
	 * <p>
	 * 1. 객체 레퍼런스 연결
	 * 2. 뿌요뿌요 로직 생성
	 * 3. 로직 통일
	 */
	public RoundThread(
			GameService gameService,
			GameFrameService gameFrameService
	) {
		this.gameService = gameService;
		this.gameFrameService = gameFrameService;

		roundThreadService.makePuyoLogic();

		playerThread1P = new PlayerThread(gameGround1P, puyoLogic, 1);
		playerThread2P = new PlayerThread(gameGround2P, puyoLogic, 2);
		//System.out.println("gamePanel");
	}
	
	@Override
	public void run() {
		var roundChangeToggle = roundThreadService.getRoundChangeToggle();
		var winCount1P = roundThreadService.getWinCount1P();
		var winCount2P = roundThreadService.getWinCount2P();

		roundChangeToggle = false;
		roundThreadService.changeRoundChangeToggle();
		playerThread1P.start();
		playerThread2P.start();
		while(true) {
			if(roundChangeToggle)
				break;
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		roundThreadService.countThreeSecond();
		playerThread1P = new PlayerThread(gameGround1P, puyoLogic, 1);
		playerThread2P = new PlayerThread(gameGround2P, puyoLogic, 2);
		playerThread1P.start();
		playerThread2P.start();
		while(true) {
			if(!roundChangeToggle)
				break;
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(winCount1P == 1 && winCount2P == 1) {
			roundThreadService.countThreeSecond();
			playerThread1P = new PlayerThread(gameGround1P, puyoLogic, 1);
			playerThread2P = new PlayerThread(gameGround2P, puyoLogic, 2);
			playerThread1P.start();
			playerThread2P.start();
		}
		else {
			if(winCount1P == 2) {
				//System.out.println("1P Win");		
			}
			else if(winCount2P == 2) {
				//System.out.println("2P Win");
			}
		}
		while(true) {
			if(roundChangeToggle)
				break;
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		gameService.close();
		gameFrameService.openGameMenuPanel();
	}
}
