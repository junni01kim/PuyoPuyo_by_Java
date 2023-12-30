//라운드 스레드
public class RoundThread extends Thread {
	GamePanel gamePanel;
	PlayerThread playerThread1P;
	PlayerThread playerThread2P;
	ScorePanel scorePanel;
	
	int puyoLogic[] = new int[25];
	
	public PlayerThread getPlayerThread1P() {return playerThread1P;}
	public PlayerThread getPlayerThread2P() {return playerThread2P;}
	
	public RoundThread(GamePanel gamePanel, GameGround gameGround1P, GameGround gameGround2P, ScorePanel scorePanel) {
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
	
	@Override
	public void run() {
		playerThread1P.start();
		playerThread2P.start();
		System.out.println("gamePanel.run");
	}
}
