package puyopuyo.game.roundthread;

import puyopuyo.gameground.playerthread.PlayerThread;
import puyopuyo.game.GameService;
import puyopuyo.gameframe.GameFrameService;

/**
 * 라운드 스레드
 * <p>
 * 한 라운드 전체를 관리하는 스레드이다.
 */
public class RoundThread extends Thread {
	private final RoundThreadService roundThreadService = new RoundThreadService();
	private final GameFrameService gameFrameService;
	private final GameService gameService;

	private PlayerThread playerThread1P;
	private PlayerThread playerThread2P;

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

		playerThread1P = new PlayerThread(gameService, gameService.getGameGround1P().getService(), roundThreadService, 1);
		playerThread2P = new PlayerThread(gameService, gameService.getGameGround2P().getService(), roundThreadService, 2);
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
		playerThread1P = new PlayerThread(gameService, gameService.getGameGround1P().getService(), roundThreadService, 1);
		playerThread2P = new PlayerThread(gameService, gameService.getGameGround2P().getService(), roundThreadService, 2);
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
			playerThread1P = new PlayerThread(gameService, gameService.getGameGround1P().getService(), roundThreadService, 1);
			playerThread2P = new PlayerThread(gameService, gameService.getGameGround2P().getService(), roundThreadService, 2);
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
        while (!roundChangeToggle) {
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
