package puyopuyo.game;

import puyopuyo.map.MapService;
import puyopuyo.round.RoundThread;

import static java.lang.Thread.sleep;

public class GameService {
    private final GameRepository gameRepository;

    private final MapService mapService;

    public GameService(GameThread gameThread, MapService mapService) {
        gameRepository = new GameRepository(gameThread);
        this.mapService = mapService;
    }

    /**
     * puyoLogic을 재설정하는 함수이다.
     */
    public void makePuyoLogic() {
        var puyoLogic = gameRepository.getPuyoLogic();

        int[][] puyoCase = new int[5][5];
        int firstPuyo;
        int secondPuyo;

        for (int i = 0; i < 25; i++) {
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
    }

    public void countThreeSecond() {
        try {
            for(int i=3; i>0; i--) {
                sleep(1000);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
        // 1라운드
        round();

        // 2라운드
        round();
        endCheck();

        // 3라운드
        round();
        endCheck();
    }

    private void round() {
        countThreeSecond(); // 3초 뒤 시작

        gameRepository.setRoundThread1P(new RoundThread(1, this, mapService)).start();
        gameRepository.setRoundThread2P(new RoundThread(2, this, mapService)).start();

        // 1 round TODO: true되면 다음 라운드로 진행
        changeRoundChangeToggle(); // 게임 진행중에는 true여야 함
        while(gameRepository.getRoundChangeToggle()) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace(); // sleep 실패
            }
        }
    }

    private void endCheck() {
        var winCount1P = gameRepository.getWinCount1P();
        var winCount2P = gameRepository.getWinCount2P();
        if(winCount1P == 2) {
            //mapService.closeGamePanel();
            // TODO: 메뉴 화면으로 복귀
        }
        else if(winCount2P == 2) {
            //mapService.closeGamePanel();
            // TODO: 메뉴 화면으로 복귀
        }
    }

    public boolean changeRoundChangeToggle() {return gameRepository.changeRoundChangeToggle();}

    public RoundThread getRoundThread(int player) {
        if(player==1) return gameRepository.getRoundThread1P();
        else return gameRepository.getRoundThread2P();
    }
    public int[] getPuyoLogic() {return gameRepository.getPuyoLogic();}
    public void plusWinCount(int player) {
        if(player==1) gameRepository.plusWinCount1P();
        else gameRepository.plusWinCount2P();
    }

    public void tossGarbagePuyo(int player, int plusScore) {
        if(player==1) {
            var roundThread = gameRepository.getRoundThread2P();
            roundThread.setGarbagePuyo(plusScore/70);
        }
        else {
            var roundThread = gameRepository.getRoundThread1P();
            roundThread.setGarbagePuyo(plusScore/70);
        }
    }
}
