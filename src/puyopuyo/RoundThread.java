package puyopuyo;

import puyopuyo.gamepanel.GamePanel;

/**
 * 라운드 스레드
 * <p>
 * 한 라운드 전체를 관리하는 스레드이다.
 */
public class RoundThread extends Thread {
	GamePanel gamePanel;
	GameGround gameGround1P;
	GameGround gameGround2P;
	PlayerThread playerThread1P;
	PlayerThread playerThread2P;
	ScorePanel scorePanel;
	boolean roundChangeToggle = false;
	
	int winCount1P = 0;
	int winCount2P = 0;
	
	int[] puyoLogic = new int[25];

	/** playerThread getter */
	public PlayerThread getPlayerThread1P() {return playerThread1P;}
	public PlayerThread getPlayerThread2P() {return playerThread2P;}

	public boolean getRoundChangeToggle() {return roundChangeToggle;}

	/** 1P 승리 횟수 카운트 */
	public void plusWinCount1P() {++winCount1P;}

	/** 2P 승리 횟수 카운트 */
	public void plusWinCount2P() {++winCount2P;}

	/** winCount getter */
	public int getWinCount1P() {return winCount1P;}
	public int getWinCount2P() {return winCount2P;}
	
	public void changeRoundChangeToggle() {
		if(roundChangeToggle == true)
			roundChangeToggle = false;
		else
			roundChangeToggle = true;
	}

	/**
	 * 게임의 한 라운드를 관리하는 스레드이다.
	 * <p>
	 * 1. 객체 레퍼런스 연결
	 * 2. 뿌요뿌요 로직 생성
	 * 3. 로직 통일
	 *
	 * @param gamePanel 게임을 진행하는 화면이다.
	 * @param gameGround1P 1P의 게임 보드를 구성하는 화면이다.
	 * @param gameGround2P 2P의 게임 보드를 구성하는 화면이다.
	 * @param scorePanel 게임 라운드 내 인게임 정보를 나타내는 화면이다.
	 */
	public RoundThread(GamePanel gamePanel, GameGround gameGround1P, GameGround gameGround2P, ScorePanel scorePanel) {
		this.gameGround1P = gameGround1P;
		this.gameGround2P = gameGround2P;
		this.scorePanel = scorePanel;
		this.gamePanel = gamePanel;
		
		makePuyoLogic();
		playerThread1P = new PlayerThread(gameGround1P, puyoLogic, 1);
		playerThread2P = new PlayerThread(gameGround2P, puyoLogic, 2);
		//System.out.println("gamePanel");
	}

	/**
	 * puyoLogic을 재설정하는 함수이다.
	 */
	private void makePuyoLogic() {
		int puyoCase[][] = new int[5][5];
		int firstPuyo;
		int secondPuyo;
		for (int i = 0; i < 25; i++)
		{
			while (true) {
				firstPuyo = (int)(Math.random()*5);
				secondPuyo = (int)(Math.random()*5);
				if (puyoCase[firstPuyo][secondPuyo] == 0) {
					puyoCase[firstPuyo][secondPuyo] = 1;
					puyoLogic[i] = firstPuyo * 10 + secondPuyo;
					break;
				}
			}
		}
		//System.out.println("makePuyoLogic");
	}

	private void countThreeSecond() {
		try {
			for(int i=3; i>0; i--) {
				//System.out.println(i);
				sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		roundChangeToggle = false;
		countThreeSecond();
		playerThread1P.start();
		playerThread2P.start();
		while(true) {
			if(roundChangeToggle == true)
				break;
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		countThreeSecond();
		playerThread1P = new PlayerThread(gameGround1P, puyoLogic, 1);
		playerThread2P = new PlayerThread(gameGround2P, puyoLogic, 2);
		playerThread1P.start();
		playerThread2P.start();
		while(true) {
			if(roundChangeToggle == false)
				break;
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(getWinCount1P() == 1 && getWinCount2P() == 1) {
			countThreeSecond();
			playerThread1P = new PlayerThread(gameGround1P, puyoLogic, 1);
			playerThread2P = new PlayerThread(gameGround2P, puyoLogic, 2);
			playerThread1P.start();
			playerThread2P.start();
		}
		else {
			if(getWinCount1P() == 2) {
				//System.out.println("1P Win");		
			}
			else if(getWinCount2P() == 2) {
				//System.out.println("2P Win");
			}
		}
		while(true) {
			if(roundChangeToggle == true)
				break;
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		gamePanel.getGameFrame().closeGamePanel();
		gamePanel.getGameFrame().openGameMenuPanel();
		
	}
}
