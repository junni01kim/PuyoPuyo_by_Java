
public class PlayerThread extends Thread {
	GameGround gameGround;
	// ���庰 ��ȸ�� �ѿ� ����
	int puyoLogic[] = new int[25];
	int puyoIndex = 0;
	
	public PlayerThread(GameGround gameGround, int puyoLogic[]) {
		this.gameGround = gameGround;
		this.puyoLogic = puyoLogic;
		nextPuyo();
		System.out.println("PlayerThread");
	}
	
	void nextPuyo() {
		Puyo puyo1 = new Puyo(puyoLogic[puyoIndex] / 10);
		Puyo puyo2 = new Puyo(puyoLogic[puyoIndex++] % 10);
		gameGround.getTestLabel1().setText(Integer.toString(puyo1.getType()));
		gameGround.getTestLabel2().setText(Integer.toString(puyo2.getType()));
		System.out.println("nextPuyo");
	}
	
	// �ѿ� ��ü�� �Ʒ��� ����Ʈ���� �Լ��̴�. 
	void dropPuyo() {
		gameGround.getTestLabel1().setLocation(gameGround.getTestLabel1().getX(),gameGround.getTestLabel1().getY()+10);
		gameGround.getTestLabel2().setLocation(gameGround.getTestLabel2().getX(),gameGround.getTestLabel2().getY()+10);
		
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