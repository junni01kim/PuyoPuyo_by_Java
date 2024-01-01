//라운드 스레드
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
	
	int puyoLogic[] = new int[25];
	
	public PlayerThread getPlayerThread1P() {return playerThread1P;}
	public PlayerThread getPlayerThread2P() {return playerThread2P;}
	public boolean getRoundChangeToggle() {return roundChangeToggle;}
	
	public void plusWinCount1P() {++winCount1P;}
	public void plusWinCount2P() {++winCount2P;}
	public int getWinCount1P() {return winCount1P;}
	public int getWinCount2P() {return winCount2P;}
	
	public void changeRoundChangeToggle() {
		if(roundChangeToggle == true)
			roundChangeToggle = false;
		else
			roundChangeToggle = true;
	}
	
	public RoundThread(GamePanel gamePanel, GameGround gameGround1P, GameGround gameGround2P, ScorePanel scorePanel) {
		this.gameGround1P = gameGround1P;
		this.gameGround2P = gameGround2P;
		this.scorePanel = scorePanel;
		this.gamePanel = gamePanel;
		makePuyoLogic();
		playerThread1P = new PlayerThread(gameGround1P, puyoLogic, 1);
		playerThread2P = new PlayerThread(gameGround2P, puyoLogic, 2);
		System.out.println("gamePanel");
	}
	
	// puyoLogic을 재설정하는 함수이다.
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
		System.out.println("makePuyoLogic");
	}

	private void countThreeSecond() {
		try {
			for(int i=3; i>0; i--) {
				System.out.println(i);
				sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
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
		
		playerThread1P.clearPlayerThread();
		playerThread2P.clearPlayerThread();
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
			playerThread1P.clearPlayerThread();
			playerThread2P.clearPlayerThread();
			countThreeSecond();
			playerThread1P = new PlayerThread(gameGround1P, puyoLogic, 1);
			playerThread2P = new PlayerThread(gameGround2P, puyoLogic, 2);
			playerThread1P.start();
			playerThread2P.start();
		}
		else {
			if(getWinCount1P() == 2) {
				System.out.println("1P Win");		
			}
			else if(getWinCount2P() == 2) {
				System.out.println("2P Win");
			}
		}
	}
}
