import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PlayerThread extends Thread {
	GameGround gameGround;
	
	// ���庰 ��ȸ�� �ѿ� ����
	private int puyoLogic[] = new int[25];
	
	// nextPuyo()���� ����Ѵ�.
	private int puyoIndex = 0;
	
	int iAm;
	
	// checkNumberOfSamePuyo()���� ����Ѵ�.
	private int numberOfSamePuyo = 0;
	private boolean samePuyoChecker[][] = new boolean[6][12];
	
	private Puyo puyoMap[][] = new Puyo[6][12];
	public Puyo[][] getPuyoMap() {return puyoMap;}
	
	private boolean colorChecker[] = new boolean[5];
	private int puyoRemovedSum;
	private int puyoConnect;
	private int puyoCombo;
	private int puyoColor;
	
	private int puyoComboBonus[] = {0,0,8,16,32,64,96,128,160,192,224,256,288,320,352,384,416,448,480,512};
	private int puyoConnectBonus[] = {0,0,0,0,0,2,3,4,5,6,7,10};
	private int puyoColorBonus[]= {0,3,6,12,24};
	
	private int score = 0;
	
	public PlayerThread(GameGround gameGround, int puyoLogic[], int iAm) {
		this.gameGround = gameGround;
		this.puyoLogic = puyoLogic;
		this.iAm = iAm;
		
		for(int i=0;i<puyoMap.length;i++)
			for(int j=0;j<puyoMap[i].length;j++)
				puyoMap[i][j] = null; 
		
		nextPuyo();
		System.out.println("PlayerThread");
	}

	GameGround getGameGround() {return gameGround;}
	int[] getPuyoLogic() {return puyoLogic;}
	int getPuyoIndex() {return puyoIndex;}
	
	// ���� �ѿ�� ��ȯ�����ִ� �Լ��̴�.
	void nextPuyo() {
		int puyo1Type = (puyoLogic[puyoIndex%puyoLogic.length]) / 10;
		int puyo2Type = (puyoLogic[(puyoIndex++)%puyoLogic.length]) % 10;
		
		gameGround.getPuyo1().setType(puyo1Type);
		gameGround.getPuyo2().setType(puyo2Type);
		
		// type�� �´� �������� ����Ѵ�.
		gameGround.getPuyo1().setIcon(gameGround.getPuyoIcon()[puyo1Type]);
		gameGround.getPuyo2().setIcon(gameGround.getPuyoIcon()[puyo2Type]);
		
		gameGround.getPuyo1().setLocation(140,10);
		gameGround.getPuyo2().setLocation(200,10);
		
		gameGround.getPuyo1().setVisible(true);
		gameGround.getPuyo2().setVisible(true);
		
		changeNextPuyo();
		
		System.out.println("nextPuyo");
	}
	
	void changeNextPuyo() {
		
		int nextLeftControlPuyoType = (puyoLogic[(puyoIndex)%puyoLogic.length])/10;
		int nextRightControlPuyoType = (puyoLogic[(puyoIndex)%puyoLogic.length])%10;

		if(iAm == 1) {
			gameGround.getGamePanel().getScorePanel().getNextLeftControlPuyo1P().setType(nextLeftControlPuyoType);
			gameGround.getGamePanel().getScorePanel().getNextRightControlPuyo1P().setType(nextRightControlPuyoType);
			
			// type�� �´� �������� ����Ѵ�.
			gameGround.getGamePanel().getScorePanel().getNextLeftControlPuyo1P().setIcon(gameGround.getPuyoIcon()[nextLeftControlPuyoType]);
			gameGround.getGamePanel().getScorePanel().getNextRightControlPuyo1P().setIcon(gameGround.getPuyoIcon()[nextRightControlPuyoType]);
		}
		else {
			gameGround.getGamePanel().getScorePanel().getNextLeftControlPuyo2P().setType(nextLeftControlPuyoType);
			gameGround.getGamePanel().getScorePanel().getNextRightControlPuyo2P().setType(nextRightControlPuyoType);
			
			// type�� �´� �������� ����Ѵ�.
			gameGround.getGamePanel().getScorePanel().getNextLeftControlPuyo2P().setIcon(gameGround.getPuyoIcon()[nextLeftControlPuyoType]);
			gameGround.getGamePanel().getScorePanel().getNextRightControlPuyo2P().setIcon(gameGround.getPuyoIcon()[nextRightControlPuyoType]);
		}
		
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
		anotherPuyo.setVisible(false);
		//System.out.println("dropAnotherPuyo");
	}
	
	void checkSamePuyo() {
		initializeCheckNumberOfSamePuyoVariable();
		checkNumberOfSamePuyo(puyoMap[gameGround.getPuyo1().PixelXToindex()][gameGround.getPuyo1().PixelYToindex()],gameGround.getPuyo1().PixelXToindex(),gameGround.getPuyo1().PixelYToindex());
		if(numberOfSamePuyo>=4) {
			deletePuyos(puyoMap[gameGround.getPuyo1().PixelXToindex()][gameGround.getPuyo1().PixelYToindex()],gameGround.getPuyo1().PixelXToindex(),gameGround.getPuyo1().PixelYToindex());
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dropPuyos();
			// ��� �ѿ信 ���ؼ� �˻�
		}
		initializeCheckNumberOfSamePuyoVariable();
		checkNumberOfSamePuyo(puyoMap[gameGround.getPuyo2().PixelXToindex()][gameGround.getPuyo2().PixelYToindex()],gameGround.getPuyo2().PixelXToindex(),gameGround.getPuyo2().PixelYToindex());
		if(numberOfSamePuyo>=4) {
			deletePuyos(puyoMap[gameGround.getPuyo2().PixelXToindex()][gameGround.getPuyo2().PixelYToindex()],gameGround.getPuyo2().PixelXToindex(),gameGround.getPuyo2().PixelYToindex());
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dropPuyos();
		}
	}
	
	// �ѿ䰡 �ٴڿ� ��Ҵ��� Ȯ���ϴ� �Լ�
	void checkPuyo() {
		// �ѿ�1�� ���� �Ʒ��� ������ ���
		if((gameGround.getPuyo1().getY()-10)/60 >= 11 || puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60+1] != null) {
			gameGround.getPuyo1().setVisible(false);
			puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60] = new Puyo(gameGround, gameGround.getPuyo1().getType(),(gameGround.getPuyo1().getX()-20)/60,(gameGround.getPuyo1().getY()-10)/60);

			gameGround.add(puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60]);
			
			System.out.println("Puyo1:"+(gameGround.getPuyo1().getY()-10)/60);
			dropAnotherPuyo(gameGround.getPuyo2());
			
			checkSamePuyo();

			nextPuyo();
		}
		// �ѿ�2�� ���� �Ʒ��� ������ ���
		else if((gameGround.getPuyo2().getY()-10)/60 >= 11 || puyoMap[(gameGround.getPuyo2().getX()-20)/60][(gameGround.getPuyo2().getY()-10)/60+1] != null) {
			gameGround.getPuyo2().setVisible(false);
			puyoMap[(gameGround.getPuyo2().getX()-20)/60][(gameGround.getPuyo2().getY()-10)/60] = new Puyo(gameGround, gameGround.getPuyo2().getType(),(gameGround.getPuyo2().getX()-20)/60,(gameGround.getPuyo2().getY()-10)/60);
			
			gameGround.add(puyoMap[(gameGround.getPuyo2().getX()-20)/60][(gameGround.getPuyo2().getY()-10)/60]);
			
			System.out.println("Puyo2:"+(gameGround.getPuyo2().getY()-10)/60);
			dropAnotherPuyo(gameGround.getPuyo1());
			
			checkSamePuyo();
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
		System.out.println("initializeCheckNumberOfSamePuyoVariable");
		numberOfSamePuyo=0;
		for(int i=0; i<puyoMap.length; i++)
			for(int j=0; j<puyoMap[i].length; j++)
					samePuyoChecker[i][j] = false;
	}
	
	void initializeScoreVariable() {
		for(int i=0;i<gameGround.getPuyoIcon().length;i++)
			colorChecker[i]=false;
		
		puyoRemovedSum=0;
		puyoConnect=0;
		puyoCombo=0;
		puyoColor=0;
	}
	
	void printScore() {
		if(iAm == 1)
			gameGround.getGamePanel().getScorePanel().getScoreLabel1P().setText(Integer.toString(score));
		else
			gameGround.getGamePanel().getScorePanel().getScoreLabel2P().setText(Integer.toString(score));
	}
	
	void scanNumberOfSamePuyo() {
		System.out.println("scanNumberOfSamePuyo");
		int T = 5;
		Puyo puyo = new Puyo(gameGround, 0, 0, 0);
		boolean check = false;
		
		gameGround.getPuyo1().setVisible(false);
		gameGround.getPuyo2().setVisible(false);
		
		while (T >= 0) {
			initializeCheckNumberOfSamePuyoVariable();
			
			// 0�� üũ
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 12; j++) {
					if (samePuyoChecker[i][j] == false && puyoMap[i][j] != null && puyoMap[i][j].getType() == T) {
						puyo.setLocation(puyo.indexXToPixel(i),puyo.indexYToPixel(j));
						puyo.setType(T);
						checkNumberOfSamePuyo(puyo, i, j);
						// ���� ����� �� ����Ѵ�.
						if (numberOfSamePuyo >= 4) {
							if(colorChecker[T]==false) {
								colorChecker[T]=true;
								puyoColor++;
							}
							puyoConnect = numberOfSamePuyo;
							puyoRemovedSum += puyoConnect;
							score += puyoRemovedSum * (puyoComboBonus[++puyoCombo] + puyoColorBonus[puyoColor] +puyoConnectBonus[puyoConnect]) * 10;
							printScore();
							deletePuyos(puyo, i, j);
							check = true;
						}
						numberOfSamePuyo=0;
					}
				}
			}
			T--;
		}
		
		if (check) {
			dropPuyos();
			scanNumberOfSamePuyo();
		}
	}
	
	// puyo1�� puyo2�� 4�� �̻� ���� ������ ����Ǿ����� üũ
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
	
	// checkNumberOfSamePuyo()���� ������ �ѿ���� ���� 
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
	
	
	// deletePuyo() ���� ���߿� ���ִ� ��ϵ��� �Ʒ��� �����Ѵ�.
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
							break;
						}
					}
					// ���� ����� ���� ���ٸ�, -> ��ø �Ǿ����� ������ j �ݺ� �ʿ�X
					if (q == -1) 
						break;
				}
			}
		}
		
		try {
			sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initializeScoreVariable();
		scanNumberOfSamePuyo();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				dropPuyo();
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("playerThread.run");
		}
	}
}