import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PlayerThread extends Thread {
	GameGround gameGround;
	
	// 라운드별 순회할 뿌요 로직
	private int puyoLogic[] = new int[25];
	
	// nextPuyo()에서 사용한다.
	private int puyoIndex = 0;
	
	int iAm;
	boolean oneWin = false;
	public boolean getOneWin() {return oneWin;}
	
	// checkNumberOfSamePuyo()에서 사용한다.
	private int numberOfSamePuyo = 0;
	private boolean samePuyoChecker[][] = new boolean[6][12];
	
	private Puyo puyoMap[][] = new Puyo[6][12];
	public Puyo[][] getPuyoMap() {return puyoMap;}
	
	private boolean colorChecker[] = null;
	private int puyoRemovedSum;
	private int puyoConnect;
	private int puyoCombo;
	private int puyoColor;
	
	private int puyoComboBonus[] = {0,0,8,16,32,64,96,128,160,192,224,256,288,320,352,384,416,448,480,512};
	private int puyoConnectBonus[] = {0,0,0,0,0,2,3,4,5,6,7,10};
	private int puyoColorBonus[]= {0,3,6,12,24};
	
	private int garbagePuyo = 0;
	public void setGarbagePuyo(int numberOfGarbagePuyo) {garbagePuyo = numberOfGarbagePuyo;}
	
	private int score = 0;
	
	private boolean endFlag = false;
	
	public void changeOneWin() {oneWin = true;}
	
	public void changeEndFlag() {
		endFlag = true;
	}
	
	// 게임이 끝나고 GameGround를 지우는데 사용한다.
	public void clearPlayerThread() {
		for(int i=0;i<puyoMap.length;i++)
			for(int j=0;j<puyoMap[i].length;j++) {
				if(puyoMap[i][j]!=null)
					puyoMap[i][j].setVisible(false);
			}
		
		gameGround.getPuyo1().setVisible(false);
		gameGround.getPuyo2().setVisible(false);
	}
	
	public PlayerThread(GameGround gameGround, int puyoLogic[], int iAm) {
		this.gameGround = gameGround;
		this.puyoLogic = puyoLogic;
		colorChecker = new boolean[gameGround.getPuyoIcon().length];
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
	
	// 다음 뿌요로 전환시켜주는 함수이다.
	void nextPuyo() {
		int puyo1Type = (puyoLogic[puyoIndex%puyoLogic.length]) / 10;
		int puyo2Type = (puyoLogic[(puyoIndex++)%puyoLogic.length]) % 10;
		
		gameGround.getPuyo1().setType(puyo1Type);
		gameGround.getPuyo2().setType(puyo2Type);
		
		// type에 맞는 아이콘을 사용한다.
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
			
			// type에 맞는 아이콘을 사용한다.
			gameGround.getGamePanel().getScorePanel().getNextLeftControlPuyo1P().setIcon(gameGround.getPuyoIcon()[nextLeftControlPuyoType]);
			gameGround.getGamePanel().getScorePanel().getNextRightControlPuyo1P().setIcon(gameGround.getPuyoIcon()[nextRightControlPuyoType]);
		}
		else {
			gameGround.getGamePanel().getScorePanel().getNextLeftControlPuyo2P().setType(nextLeftControlPuyoType);
			gameGround.getGamePanel().getScorePanel().getNextRightControlPuyo2P().setType(nextRightControlPuyoType);
			
			// type에 맞는 아이콘을 사용한다.
			gameGround.getGamePanel().getScorePanel().getNextLeftControlPuyo2P().setIcon(gameGround.getPuyoIcon()[nextLeftControlPuyoType]);
			gameGround.getGamePanel().getScorePanel().getNextRightControlPuyo2P().setIcon(gameGround.getPuyoIcon()[nextRightControlPuyoType]);
		}
		
		System.out.println("nextPuyo");
	}
	
	// 한 뿌요의 위치가 정해지도 다음 뿌요의 위치를 보여주는 함수
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
			// 모든 뿌요에 대해서 검색
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
	
	void dropGarbagePuyo() {
		System.out.println("dropGarbagePuyo:"+garbagePuyo);
		int seperateGarbagePuyo = garbagePuyo / 6;
		int moduloGarbagePuyo = garbagePuyo % 6;
		int randomVariable;
		//Puyo.type = 5;
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 12; j++)
			{
				if (puyoMap[i][j] == null)
				{
					for (int p = 0; p < seperateGarbagePuyo; p++)
					{
						if (j + p > 10) continue;
						puyoMap[i][j + p] = new Puyo(gameGround, 5, i , i+p);
					}
					break;
				}
			}
		}
		
		while (moduloGarbagePuyo-- > 0)
		{
			randomVariable = (int)(Math.random()*6);
			for (int j = 0; j < 12; j++)
			{
				if (puyoMap[randomVariable][j] == null)
				{
					puyoMap[randomVariable][j] = new Puyo(gameGround, 5);
					puyoMap[randomVariable][j].setLocation(puyoMap[randomVariable][j].indexXToPixel(randomVariable), puyoMap[randomVariable][j].indexYToPixel(j));
					break;
				}
			}
		}
		setGarbagePuyo(0);
	}
	
	// 뿌요가 바닥에 닿았는지 확인하는 함수
	void checkPuyo() {	
		// 뿌요1이 가장 아래로 내려운 경우
		if((gameGround.getPuyo1().getY()-10)/60 >= 11 || puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60+1] != null) {
			gameGround.getPuyo1().setVisible(false);
			puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60] = new Puyo(gameGround, gameGround.getPuyo1().getType(),(gameGround.getPuyo1().getX()-20)/60,(gameGround.getPuyo1().getY()-10)/60);
			
			gameGround.add(puyoMap[(gameGround.getPuyo1().getX()-20)/60][(gameGround.getPuyo1().getY()-10)/60]);
			
			System.out.println("Puyo1:"+(gameGround.getPuyo1().getY()-10)/60);
			dropAnotherPuyo(gameGround.getPuyo2());
			
			checkSamePuyo();
			
			// 방해뿌요 드롭
			dropGarbagePuyo();

			nextPuyo();
		}
		// 뿌요2가 가장 아래로 내려운 경우
		else if((gameGround.getPuyo2().getY()-10)/60 >= 11 || puyoMap[(gameGround.getPuyo2().getX()-20)/60][(gameGround.getPuyo2().getY()-10)/60+1] != null) {
			gameGround.getPuyo2().setVisible(false);
			puyoMap[(gameGround.getPuyo2().getX()-20)/60][(gameGround.getPuyo2().getY()-10)/60] = new Puyo(gameGround, gameGround.getPuyo2().getType(),(gameGround.getPuyo2().getX()-20)/60,(gameGround.getPuyo2().getY()-10)/60);
			
			gameGround.add(puyoMap[(gameGround.getPuyo2().getX()-20)/60][(gameGround.getPuyo2().getY()-10)/60]);
			
			System.out.println("Puyo2:"+(gameGround.getPuyo2().getY()-10)/60);
			dropAnotherPuyo(gameGround.getPuyo1());
			
			checkSamePuyo();
			
			// 방해뿌요 드롭
			dropGarbagePuyo();
			
			nextPuyo();
		}
		
		//게임 종료 조건
		if(puyoMap[3][1]!=null||puyoMap[4][1]!=null) {
			endFlag = true;
			// 상대 스레드 oneWin
			if(iAm == 1) {
				gameGround.getGamePanel().getRoundThread().plusWinCount2P();
				gameGround.getGamePanel().getRoundThread().getPlayerThread2P().changeOneWin();
				gameGround.getGamePanel().getRoundThread().getPlayerThread2P().changeEndFlag();
			}
			else {
				gameGround.getGamePanel().getRoundThread().plusWinCount1P();
				gameGround.getGamePanel().getRoundThread().getPlayerThread1P().changeOneWin();
				gameGround.getGamePanel().getRoundThread().getPlayerThread1P().changeEndFlag();
			}
					
			gameGround.getGamePanel().getRoundThread().changeRoundChangeToggle();
			return;
		}
	}
		
	// 뿌요 객체를 아래로 떨어트리는 함수 
	void dropPuyo() {
		checkPuyo();
		// JLabel을 +60 픽셀만큼 내린다.
		gameGround.getPuyo1().setLocation(gameGround.getPuyo1().getX(),gameGround.getPuyo1().getY()+60);
		gameGround.getPuyo2().setLocation(gameGround.getPuyo2().getX(),gameGround.getPuyo2().getY()+60);
		
		//System.out.println("dropPuyo");
	}
	
	// CheckNumberOfSamePuyo와 deletePuyos에 필요한 변수들을 초기화 하는 함수
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
		int T = 4;
		Puyo puyo = new Puyo(gameGround, 0, 0, 0);
		boolean check = false;
		
		gameGround.getPuyo1().setVisible(false);
		gameGround.getPuyo2().setVisible(false);
		
		while (T >= 0) {
			initializeCheckNumberOfSamePuyoVariable();
			
			// 0색 체크
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 12; j++) {
					if (samePuyoChecker[i][j] == false && puyoMap[i][j] != null && puyoMap[i][j].getType() == T) {
						puyo.setLocation(puyo.indexXToPixel(i),puyo.indexYToPixel(j));
						puyo.setType(T);
						checkNumberOfSamePuyo(puyo, i, j);
						// 점수 계산할 때 사용한다.
						if (numberOfSamePuyo >= 4) {
							if(colorChecker[T]==false) {
								colorChecker[T]=true;
								puyoColor++;
							}
							puyoConnect = numberOfSamePuyo;
							puyoRemovedSum += puyoConnect;
							int plusScore = puyoRemovedSum * (puyoComboBonus[++puyoCombo] + puyoColorBonus[puyoColor] +puyoConnectBonus[puyoConnect]) * 10;
							if(iAm == 1)
								gameGround.getGamePanel().getRoundThread().getPlayerThread2P().setGarbagePuyo(plusScore/70);
							else
								gameGround.getGamePanel().getRoundThread().getPlayerThread1P().setGarbagePuyo(plusScore/70);
							System.out.println("----------------------------------");
							score += plusScore;
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
	
	// puyo1과 puyo2가 4개 이상 같은 색으로 연결되었는지 체크
	void checkNumberOfSamePuyo(Puyo puyo, int indexX, int indexY) {
		// 예외처리: 뿌요1과 2가 동시에 속해서 사라지는 경우 anotherPuyo는 존재하지 않음
		if(puyoMap[indexX][indexY]==null)
			return;
		numberOfSamePuyo++;
		samePuyoChecker[indexX][indexY] = true;
		
		if(numberOfSamePuyo>=4)
			System.out.println("뿌요개수:"+numberOfSamePuyo);
		
		// 예외처리: puyoMap[][] 범위밖에서 Puyo를 호출한다.
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
	
	// checkNumberOfSamePuyo()에서 포착된 뿌요들을 제거 
	void deletePuyos(Puyo puyo, int indexX, int indexY) {
		System.out.println("deletePuyos"+"("+indexX+","+indexY+")");
		samePuyoChecker[indexX][indexY] = false;
		puyoMap[indexX][indexY].setVisible(false);
		puyoMap[indexX][indexY]=null;
		
		// 예외처리: puyoMap[][] 범위밖에서 Puyo를 호출한다.
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
	
	
	// deletePuyo() 이후 공중에 떠있는 블록들을 아래로 정렬한다.
	void dropPuyos() {
		int i, j, q;
		
		for (i = 0; i < 6; i++)
		{
			for (j = 11; j >= 0; j--)
			{
				if (puyoMap[i][j] == null) {
					for (q = j-1; q >= 0; q--)
					{
						if (puyoMap[i][q] != null) {// 만약에 블록이 위에 있다면,
							puyoMap[i][j] = puyoMap[i][q];
							puyoMap[i][q] = null;
							puyoMap[i][j].setLocation(puyoMap[i][j].indexXToPixel(i),puyoMap[i][j].indexYToPixel(j));
							break;
						}
					}
					// 만약 블록이 위에 없다면, -> 중첩 되어있지 않으니 j 반복 필요X
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
				if(endFlag == true)
					break;
				dropPuyo();
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("playerThread.run");
		}
		System.out.println("endGame!");
	}
}
