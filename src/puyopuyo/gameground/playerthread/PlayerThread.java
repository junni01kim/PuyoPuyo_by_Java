package puyopuyo.gameground.playerthread;

import puyopuyo.Puyo;
import puyopuyo.game.GameService;
import puyopuyo.game.roundthread.RoundThreadService;
import puyopuyo.gameground.GameGroundPanel;
import puyopuyo.gameground.GameGroundService;

public class PlayerThread extends Thread {
	private final PlayerThreadService playerThreadService;
	private final GameGroundService gameGroundService;

	private boolean endFlag = false;

	public void changeEndFlag() {
		endFlag = true;
	}

	// TODO: iAm Enum으로 바꿀 것
	public PlayerThread(GameService gameService, GameGroundService gameGroundService, RoundThreadService roundThreadService, int iAm) {
		playerThreadService = new PlayerThreadService(gameService, gameGroundService, roundThreadService, iAm);
		this.gameGroundService = gameGroundService;

		this.puyoLogic = puyoLogic;
		colorChecker = new boolean[Puyo.getPuyoIcon().length];
		this.iAm = iAm;

		for(int i=0;i<puyoMap.length;i++)
			for(int j=0;j<puyoMap[i].length;j++)
				puyoMap[i][j] = null;

		playerThreadService.nextPuyo();
	}

	// 게임이 끝나고 GameGround를 지우는데 사용한다.
	synchronized public void clearPlayerThread() {
		for(int i=0;i<puyoMap.length;i++)
			for(int j=0;j<puyoMap[i].length;j++) {
				if(puyoMap[i][j]!=null) {
					puyoMap[i][j].setVisible(false);
					puyoMap[i][j] = null;
				}
			}

		gameGround.getPuyo1().setVisible(false);
		gameGround.getPuyo2().setVisible(false);
	}

	// 뿌요 객체를 아래로 떨어트리는 함수
	public synchronized void dropPuyo() {
		checkPuyo();
		// JLabel을 +60 픽셀만큼 내린다.
		gameGroundPanel.getPuyo1().setLocation(gameGroundPanel.getPuyo1().getX(), gameGroundPanel.getPuyo1().getY()+60);
		gameGroundPanel.getPuyo2().setLocation(gameGroundPanel.getPuyo2().getX(), gameGroundPanel.getPuyo2().getY()+60);

		//System.out.println("dropPuyo");
	}

	// 뿌요가 바닥에 닿았는지 확인하는 함수
	synchronized void checkPuyo() {
		//게임 종료 조건
		if(puyoMap[3][1]!=null||puyoMap[4][1]!=null) {
			endFlag = true;
			// 상대 스레드 oneWin
			if(iAm == 1) {
				gameGroundPanel.getGamePanel().getRoundThread().plusWinCount2P();
				gameGroundPanel.getGamePanel().getRoundThread().getPlayerThread2P().changeOneWin();
				gameGroundPanel.getGamePanel().getRoundThread().getPlayerThread2P().changeEndFlag();
			}
			else {
				gameGroundPanel.getGamePanel().getRoundThread().plusWinCount1P();
				gameGroundPanel.getGamePanel().getRoundThread().getPlayerThread1P().changeOneWin();
				gameGroundPanel.getGamePanel().getRoundThread().getPlayerThread1P().changeEndFlag();
			}

			gameGroundPanel.getGamePanel().getRoundThread().changeRoundChangeToggle();
			return;
		}

		// 뿌요1이 가장 아래로 내려운 경우
		if((gameGroundPanel.getPuyo1().getY()-10)/60 >= 11 || puyoMap[(gameGroundPanel.getPuyo1().getX()-20)/60][(gameGroundPanel.getPuyo1().getY()-10)/60+1] != null) {
			gameGroundPanel.getPuyo1().setVisible(false);
			puyoMap[(gameGroundPanel.getPuyo1().getX()-20)/60][(gameGroundPanel.getPuyo1().getY()-10)/60] = new Puyo(gameGroundPanel.getPuyo1().getType(),(gameGroundPanel.getPuyo1().getX()-20)/60,(gameGroundPanel.getPuyo1().getY()-10)/60);
			puyoMap[(gameGroundPanel.getPuyo1().getX()-20)/60][(gameGroundPanel.getPuyo1().getY()-10)/60].setVisible(true);
			gameGroundPanel.add(puyoMap[(gameGroundPanel.getPuyo1().getX()-20)/60][(gameGroundPanel.getPuyo1().getY()-10)/60]);

			//System.out.println("Puyo1:"+(gameGround.getPuyo1().getY()-10)/60);
			dropAnotherPuyo(gameGroundPanel.getPuyo2());

			scanNumberOfSamePuyo();
			//checkSamePuyo();

			// 방해뿌요 드롭
			if(garbagePuyo != 0)
				dropGarbagePuyo();

			gameGroundPanel.repaint();

			nextPuyo();
		}
		// 뿌요2가 가장 아래로 내려운 경우
		else if((gameGroundPanel.getPuyo2().getY()-10)/60 >= 11 || puyoMap[(gameGroundPanel.getPuyo2().getX()-20)/60][(gameGroundPanel.getPuyo2().getY()-10)/60+1] != null) {
			gameGroundPanel.getPuyo2().setVisible(false);
			puyoMap[(gameGroundPanel.getPuyo2().getX()-20)/60][(gameGroundPanel.getPuyo2().getY()-10)/60] = new Puyo(gameGroundPanel.getPuyo2().getType(),(gameGroundPanel.getPuyo2().getX()-20)/60,(gameGroundPanel.getPuyo2().getY()-10)/60);
			puyoMap[(gameGroundPanel.getPuyo2().getX()-20)/60][(gameGroundPanel.getPuyo2().getY()-10)/60].setVisible(true);
			gameGroundPanel.add(puyoMap[(gameGroundPanel.getPuyo2().getX()-20)/60][(gameGroundPanel.getPuyo2().getY()-10)/60]);

			//System.out.println("Puyo2:"+(gameGround.getPuyo2().getY()-10)/60);
			dropAnotherPuyo(gameGroundPanel.getPuyo1());

			scanNumberOfSamePuyo();
			//checkSamePuyo();

			// 방해뿌요 드롭
			if(garbagePuyo != 0)
				dropGarbagePuyo();

			gameGroundPanel.repaint();

			nextPuyo();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				if(endFlag)
					break;
				playerThreadService.dropPuyo();
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("playerThread.run");
		}

		playerThreadService.clearPlayerThread();
		gameGroundService.repaint();
		//System.out.println("endGame!");
	}
}