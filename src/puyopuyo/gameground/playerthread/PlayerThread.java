package puyopuyo.gameground.playerthread;

import puyopuyo.Puyo;
import puyopuyo.ScorePanel;
import puyopuyo.game.GameService;
import puyopuyo.game.roundthread.RoundThreadService;
import puyopuyo.gameground.GameGroundPanel;
import puyopuyo.gameground.GameGroundService;

public class PlayerThread extends Thread {
	private final PlayerThreadService playerThreadService;
	private final GameGroundService gameGroundService;
	private final RoundThreadService roundThreadService;

	private boolean endFlag = false;

	public void changeEndFlag() {
		endFlag = true;
	}

	public PlayerThreadService getPlayerThreadService() {
		return playerThreadService;
	}

	// TODO: iAm Enum으로 바꿀 것
	public PlayerThread(
			GameService gameService,
			GameGroundService gameGroundService,
			ScorePanel scorePanel,
			int iAm
	) {
		var roundThreadService = gameService.getRoundThread().getService();
		playerThreadService = new PlayerThreadService(this, gameGroundService, roundThreadService, gameService, scorePanel, iAm);
		this.gameGroundService = gameGroundService;
		this.roundThreadService = roundThreadService;

		playerThreadService.setColorChecker(new boolean[Puyo.getPuyoIcon().length]);

		var puyoMap = playerThreadService.getPuyoMap();

		for(int i=0;i<puyoMap.length;i++)
			for(int j=0;j<puyoMap[i].length;j++)
				puyoMap[i][j] = null;

		playerThreadService.nextPuyo();
	}

	// 게임이 끝나고 GameGround를 지우는데 사용한다.
	synchronized public void clearPlayerThread() {
		var puyoMap = playerThreadService.getPuyoMap();

		var leftPuyo = gameGroundService.getLeftPuyo();
		var rightPuyo = gameGroundService.getRightPuyo();

		for(int i=0;i<puyoMap.length;i++)
			for(int j=0;j<puyoMap[i].length;j++) {
				if(puyoMap[i][j]!=null) {
					puyoMap[i][j].setVisible(false);
					puyoMap[i][j] = null;
				}
			}

		leftPuyo.setVisible(false);
		rightPuyo.setVisible(false);
	}

	// 뿌요 객체를 아래로 떨어트리는 함수
	public synchronized void dropPuyo() {
		var leftPuyo = gameGroundService.getLeftPuyo();
		var rightPuyo = gameGroundService.getRightPuyo();

		checkPuyo();
		// JLabel을 +60 픽셀만큼 내린다.
		leftPuyo.setLocation(leftPuyo.getX(), leftPuyo.getY()+60);
		rightPuyo.setLocation(rightPuyo.getX(), rightPuyo.getY()+60);
	}

	// 뿌요가 바닥에 닿았는지 확인하는 함수
	synchronized void checkPuyo() {
		var puyoMap = playerThreadService.getPuyoMap();

		var leftPuyo = gameGroundService.getLeftPuyo();
		var rightPuyo = gameGroundService.getRightPuyo();
		var garbagePuyo = playerThreadService.getGarbagePuyo();

		var iAm = playerThreadService.getIam();

		//게임 종료 조건
		if(puyoMap[3][1]!=null||puyoMap[4][1]!=null) {
			endFlag = true;
			// 상대 스레드 oneWin
			if(iAm == 1) {
				roundThreadService.plusWinCount2P();
				roundThreadService.getPlayerThread2P().playerThreadService.changeOneWin();
				roundThreadService.getPlayerThread2P().changeEndFlag();
			}
			else {
				roundThreadService.plusWinCount1P();
				roundThreadService.getPlayerThread1P().playerThreadService.changeOneWin();
				roundThreadService.getPlayerThread1P().changeEndFlag();
			}

			roundThreadService.changeRoundChangeToggle();
			return;
		}

		// 뿌요1이 가장 아래로 내려운 경우
		if((leftPuyo.getY()-10)/60 >= 11 || puyoMap[(leftPuyo.getX()-20)/60][(leftPuyo.getY()-10)/60+1] != null) {
			leftPuyo.setVisible(false);
			puyoMap[(leftPuyo.getX()-20)/60][(leftPuyo.getY()-10)/60] = new Puyo(leftPuyo.getType(),(leftPuyo.getX()-20)/60,(leftPuyo.getY()-10)/60);
			puyoMap[(leftPuyo.getX()-20)/60][(leftPuyo.getY()-10)/60].setVisible(true);
			gameGroundService.add(puyoMap[(leftPuyo.getX()-20)/60][(leftPuyo.getY()-10)/60]);

			playerThreadService.dropAnotherPuyo(rightPuyo);

			playerThreadService.scanNumberOfSamePuyo();
			//checkSamePuyo();

			// 방해뿌요 드롭
			if(garbagePuyo != 0)
				playerThreadService.dropGarbagePuyo();

			gameGroundService.repaint();

			playerThreadService.nextPuyo();
		}
		// 뿌요2가 가장 아래로 내려운 경우
		else if((rightPuyo.getY()-10)/60 >= 11 || puyoMap[(rightPuyo.getX()-20)/60][(rightPuyo.getY()-10)/60+1] != null) {
			rightPuyo.setVisible(false);
			puyoMap[(rightPuyo.getX()-20)/60][(rightPuyo.getY()-10)/60] = new Puyo(rightPuyo.getType(),(rightPuyo.getX()-20)/60,(rightPuyo.getY()-10)/60);
			puyoMap[(rightPuyo.getX()-20)/60][(rightPuyo.getY()-10)/60].setVisible(true);
			gameGroundService.add(puyoMap[(rightPuyo.getX()-20)/60][(rightPuyo.getY()-10)/60]);

			playerThreadService.dropAnotherPuyo(leftPuyo);

			playerThreadService.scanNumberOfSamePuyo();
			//checkSamePuyo();

			// 방해뿌요 드롭
			if(garbagePuyo != 0)
				playerThreadService.dropGarbagePuyo();

			gameGroundService.repaint();

			playerThreadService.nextPuyo();
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				if(endFlag)
					break;
				dropPuyo();
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		clearPlayerThread();
		gameGroundService.repaint();
	}
}