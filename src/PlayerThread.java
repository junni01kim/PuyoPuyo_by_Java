import javax.swing.JLabel;

public class PlayerThread extends Thread {
	GameGround gameGround;
	
	// ���庰 ��ȸ�� �ѿ� ����
	private int puyoLogic[] = new int[25];
	private int puyoIndex = 0;
	
	private Puyo puyoMap[][] = new Puyo[6][12];
	
	public PlayerThread(GameGround gameGround, int puyoLogic[]) {
		this.gameGround = gameGround;
		this.puyoLogic = puyoLogic;
		
		for(int i=0;i<12;i++)
			for(int j=0;j<6;j++)
				puyoMap[j][i] = null; 
		
		nextPuyo();
		System.out.println("PlayerThread");
	}

	
	// ���� �ѿ�� ��ȯ�����ִ� �Լ��̴�.
	void nextPuyo() {
		int puyo1Type = puyoLogic[puyoIndex] / 10;
		int puyo2Type = puyoLogic[puyoIndex++] % 10;
		
		gameGround.getPuyo1().setType(puyo1Type);
		gameGround.getPuyo2().setType(puyo2Type);
		
		// type�� �´� �������� ����Ѵ�.
		gameGround.getPuyo1().setIcon(gameGround.getPuyoIcon()[puyo1Type]);
		gameGround.getPuyo2().setIcon(gameGround.getPuyoIcon()[puyo2Type]);
		
		gameGround.getPuyo1().setLocation(140,10);
		gameGround.getPuyo2().setLocation(200,10);
		
		System.out.println("nextPuyo");
	}
	
	// �ѿ� ��ü�� �Ʒ��� ����Ʈ���� �Լ��̴�. 
	void dropPuyo() {
		if((gameGround.getPuyo1().getY()-10)/60+2 > 12 || puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60+1] != null) {
			puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60] = new Puyo(gameGround, gameGround.getPuyo1().getType(),(gameGround.getPuyo1().getX()-20)/60,(gameGround.getPuyo1().getY()-10)/60);
			puyoMap[(gameGround.getPuyo2().getX()-20)/60][(gameGround.getPuyo2().getY()-10)/60] = new Puyo(gameGround, gameGround.getPuyo2().getType(),(gameGround.getPuyo2().getX()-20)/60,(gameGround.getPuyo2().getY()-10)/60);

			gameGround.add(puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60]);
			gameGround.add(puyoMap[(gameGround.getPuyo2().getX()-20)/60][(gameGround.getPuyo2().getY()-10)/60]);
			
			
			System.out.println("Puyo1:"+(gameGround.getPuyo1().getY()-10)/60);
			System.out.println("Puyo2:"+(gameGround.getPuyo2().getY()-10)/60);
			nextPuyo();
		}
		
		// JLabel�� +60 �ȼ���ŭ ������.
		gameGround.getPuyo1().setLocation(gameGround.getPuyo1().getX(),gameGround.getPuyo1().getY()+60);
		gameGround.getPuyo2().setLocation(gameGround.getPuyo2().getX(),gameGround.getPuyo2().getY()+60);
		
		System.out.println("dropPuyo");
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				dropPuyo();
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("playerThread.run");
		}
	}
}