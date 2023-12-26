import javax.swing.JLabel;

public class PlayerThread extends Thread {
	GameGround gameGround;
	
	// ���庰 ��ȸ�� �ѿ� ����
	private int puyoLogic[] = new int[25];
	
	// nextPuyo()���� ����Ѵ�.
	private int puyoIndex = 0;
	
	// checkNumberOfSamePuyo()���� ����Ѵ�.
	private int numberOfSamePuyo = 0;
	private boolean samePuyoChecker[][] = new boolean[6][12];
	
	private Puyo puyoMap[][] = new Puyo[6][12];
	public Puyo[][] getPuyoMap() {return puyoMap;}
	
	public PlayerThread(GameGround gameGround, int puyoLogic[]) {
		this.gameGround = gameGround;
		this.puyoLogic = puyoLogic;
		
		for(int i=0;i<puyoMap.length;i++)
			for(int j=0;j<puyoMap[i].length;j++)
				puyoMap[i][j] = null; 
		
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
	
	// �� �ѿ��� ��ġ�� �������� ���� �ѿ��� ��ġ�� �����ִ� �Լ�
	void dropAnotherPuyo(Puyo anotherPuyo) {
		int indexY = 11;
		while(true) {
			if(puyoMap[anotherPuyo.PixelXToindex()][indexY]==null) {
				puyoMap[anotherPuyo.PixelXToindex()][indexY] = new Puyo(gameGround, anotherPuyo.getType(), anotherPuyo.PixelXToindex(), indexY);
				anotherPuyo.setLocation(anotherPuyo.getX(),anotherPuyo.indexXToPixel(indexY));
				gameGround.add(puyoMap[(anotherPuyo.getX()-20)/60][indexY]);
				System.out.println("tempPuyo:"+indexY);
				break;
			}
			indexY--;
			continue;
		}
		//System.out.println("dropAnotherPuyo");
	}
	
	// �ѿ䰡 �ٴڿ� ��Ҵ��� Ȯ���ϴ� �Լ�
	void checkPuyo() {
		// �ѿ�1�� ���� �Ʒ��� ������ ���
		if((gameGround.getPuyo1().getY()-10)/60 >= 11 || puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60+1] != null) {
			
			puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60] = new Puyo(gameGround, gameGround.getPuyo1().getType(),(gameGround.getPuyo1().getX()-20)/60,(gameGround.getPuyo1().getY()-10)/60);

			gameGround.add(puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60]);
			
			System.out.println("Puyo1:"+(gameGround.getPuyo1().getY()-10)/60);
			dropAnotherPuyo(gameGround.getPuyo2());
			
			initializeCheckNumberOfSamePuyoVariable();
			checkNumberOfSamePuyo(puyoMap[gameGround.getPuyo1().PixelXToindex()][gameGround.getPuyo1().PixelYToindex()],gameGround.getPuyo1().PixelXToindex(),gameGround.getPuyo1().PixelYToindex());
			if(numberOfSamePuyo>=4) {
				deletePuyos(puyoMap[gameGround.getPuyo1().PixelXToindex()][gameGround.getPuyo1().PixelYToindex()],gameGround.getPuyo1().PixelXToindex(),gameGround.getPuyo1().PixelYToindex());
				dropPuyos();
			}
			initializeCheckNumberOfSamePuyoVariable();
			checkNumberOfSamePuyo(puyoMap[gameGround.getPuyo2().PixelXToindex()][gameGround.getPuyo2().PixelYToindex()],gameGround.getPuyo2().PixelXToindex(),gameGround.getPuyo2().PixelYToindex());
			if(numberOfSamePuyo>=4) {
				deletePuyos(puyoMap[gameGround.getPuyo2().PixelXToindex()][gameGround.getPuyo2().PixelYToindex()],gameGround.getPuyo2().PixelXToindex(),gameGround.getPuyo2().PixelYToindex());
				dropPuyos();
			}
			nextPuyo();
		}
		// �ѿ�2�� ���� �Ʒ��� ������ ���
		else if((gameGround.getPuyo2().getY()-10)/60 >= 11 || puyoMap[(gameGround.getPuyo2().getX()-20)/60][(gameGround.getPuyo2().getY()-10)/60+1] != null) {
			puyoMap[(gameGround.getPuyo2().getX()-20)/60][(gameGround.getPuyo2().getY()-10)/60] = new Puyo(gameGround, gameGround.getPuyo2().getType(),(gameGround.getPuyo2().getX()-20)/60,(gameGround.getPuyo2().getY()-10)/60);
			
			gameGround.add(puyoMap[(gameGround.getPuyo2().getX()-20)/60][(gameGround.getPuyo2().getY()-10)/60]);
			
			System.out.println("Puyo2:"+(gameGround.getPuyo2().getY()-10)/60);
			dropAnotherPuyo(gameGround.getPuyo1());
			
			initializeCheckNumberOfSamePuyoVariable();
			checkNumberOfSamePuyo(puyoMap[gameGround.getPuyo1().PixelXToindex()][gameGround.getPuyo1().PixelYToindex()],gameGround.getPuyo1().PixelXToindex(),gameGround.getPuyo1().PixelYToindex());
			if(numberOfSamePuyo>=4) {
				deletePuyos(puyoMap[gameGround.getPuyo1().PixelXToindex()][gameGround.getPuyo1().PixelYToindex()],gameGround.getPuyo1().PixelXToindex(),gameGround.getPuyo1().PixelYToindex());
				dropPuyos();
			}
			initializeCheckNumberOfSamePuyoVariable();
			checkNumberOfSamePuyo(puyoMap[gameGround.getPuyo2().PixelXToindex()][gameGround.getPuyo2().PixelYToindex()],gameGround.getPuyo2().PixelXToindex(),gameGround.getPuyo2().PixelYToindex());
			if(numberOfSamePuyo>=4) {
				deletePuyos(puyoMap[gameGround.getPuyo2().PixelXToindex()][gameGround.getPuyo2().PixelYToindex()],gameGround.getPuyo2().PixelXToindex(),gameGround.getPuyo2().PixelYToindex());
				dropPuyos();
			}
			nextPuyo();
		}
	}
		
	// �ѿ� ��ü�� �Ʒ��� ����Ʈ���� �Լ� 
	void dropPuyo() {
		checkPuyo();
		// JLabel�� +60 �ȼ���ŭ ������.
		gameGround.getPuyo1().setLocation(gameGround.getPuyo1().getX(),gameGround.getPuyo1().getY()+60);
		gameGround.getPuyo2().setLocation(gameGround.getPuyo2().getX(),gameGround.getPuyo2().getY()+60);
		
		//System.out.println("dropPuyo");
	}
	
	// CheckNumberOfSamePuyo�� deletePuyos�� �ʿ��� �������� �ʱ�ȭ �ϴ� �Լ�
	void initializeCheckNumberOfSamePuyoVariable() {
		numberOfSamePuyo=0;
		for(int i=0; i<puyoMap.length; i++)
			for(int j=0; j<puyoMap[i].length; j++)
					samePuyoChecker[i][j] = false;
	}
	
	// ���ǻ���: ���� samePuyoCheck[][] �ʱ�ȭ ����
	void checkNumberOfSamePuyo(Puyo puyo, int indexX, int indexY) {
		// ����ó��: �ѿ�1�� 2�� ���ÿ� ���ؼ� ������� ��� anotherPuyo�� �������� ����
		if(puyoMap[indexX][indexY]==null)
			return;
		numberOfSamePuyo++;
		samePuyoChecker[indexX][indexY] = true;
		
		if(numberOfSamePuyo>=4)
			System.out.println("�ѿ䰳��:"+numberOfSamePuyo);
		
		// ����ó��: puyoMap[][] �����ۿ��� Puyo�� ȣ���Ѵ�.
		if (indexX >= 0 && indexX <= 5 && indexY < 11 && puyoMap[indexX][indexY+1]!=null && puyoMap[indexX][indexY+1].getType() == puyo.getType() && !samePuyoChecker[indexX][indexY+1]) {
			checkNumberOfSamePuyo(puyo, indexX, indexY+1);
		}
		if (indexX >= 0 && indexX <= 5 && indexY > 0 && indexY < 12 && puyoMap[indexX][indexY-1]!=null && puyoMap[indexX][indexY-1].getType() == puyo.getType() && !samePuyoChecker[indexX][indexY-1]) {
			checkNumberOfSamePuyo(puyo, indexX, indexY-1);
		}
		if (indexX >= 0 && indexX <= 4 && indexY < 12 && puyoMap[indexX+1][indexY]!=null && puyoMap[indexX+1][indexY].getType() == puyo.getType() && !samePuyoChecker[indexX+1][indexY]) {
			checkNumberOfSamePuyo(puyo, indexX+1, indexY);
		}
		if (indexX >= 1 && indexX <= 5 && indexY < 12 && puyoMap[indexX-1][indexY]!=null && puyoMap[indexX-1][indexY].getType() == puyo.getType() && !samePuyoChecker[indexX-1][indexY]) {
			checkNumberOfSamePuyo(puyo, indexX-1, indexY);
		}
	}
	
	void deletePuyos(Puyo puyo, int indexX, int indexY) {
		System.out.println("deletePuyos"+"("+indexX+","+indexY+")");
		samePuyoChecker[indexX][indexY] = false;
		puyoMap[indexX][indexY].setVisible(false);
		puyoMap[indexX][indexY]=null;
		
		// ����ó��: puyoMap[][] �����ۿ��� Puyo�� ȣ���Ѵ�.
		if (indexX >= 0 && indexX <= 5 && indexY < 11 && puyoMap[indexX][indexY+1]!=null && puyoMap[indexX][indexY+1].getType() == puyo.getType() && samePuyoChecker[indexX][indexY+1]) {
			deletePuyos(puyo, indexX, indexY+1);
		}
		if (indexX >= 0 && indexX <= 5 && indexY > 0 && indexY < 12 && puyoMap[indexX][indexY-1]!=null && puyoMap[indexX][indexY-1].getType() == puyo.getType() && samePuyoChecker[indexX][indexY-1]) {
			deletePuyos(puyo, indexX, indexY-1);
		}
		if (indexX >= 0 && indexX <= 4 && indexY < 12 && puyoMap[indexX+1][indexY]!=null && puyoMap[indexX+1][indexY].getType() == puyo.getType() && samePuyoChecker[indexX+1][indexY]) {
			deletePuyos(puyo, indexX+1, indexY);
		}
		if (indexX >= 1 && indexX <= 5 && indexY < 12 && puyoMap[indexX-1][indexY]!=null && puyoMap[indexX-1][indexY].getType() == puyo.getType() && samePuyoChecker[indexX-1][indexY]) {
			deletePuyos(puyo, indexX-1, indexY);
		}
	}
	
	void dropPuyos() {
		int i, j, q;
		
		for (i = 0; i < 6; i++)
		{
			for (j = 11; j >= 0; j--)
			{
				if (puyoMap[i][j] == null) {
					for (q = j-1; q >= 0; q--)
					{
						if (puyoMap[i][q] != null) {// ���࿡ ����� ���� �ִٸ�,
							puyoMap[i][j] = puyoMap[i][q];
							puyoMap[i][q] = null;
							puyoMap[i][j].setLocation(puyoMap[i][j].indexXToPixel(i),puyoMap[i][j].indexYToPixel(j));
							System.out.println("drop!"+j);
							break;
						}
					}
					// ���� ����� ���� ���ٸ�, -> ��ø �Ǿ����� ������ j �ݺ� �ʿ�X
					if (q == -1) 
						break;
				}
			}
		}
		checkPuyo();
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
			//System.out.println("playerThread.run");
		}
	}
}