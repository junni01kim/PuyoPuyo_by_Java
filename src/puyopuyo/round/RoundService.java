package puyopuyo.round;

import puyopuyo.game.ControlPuyoKeyListener;
import puyopuyo.game.GameService;

import static java.lang.Thread.sleep;

public class RoundService {
    private final RoundRepository roundRepository;

    private final GameService gameService;

    public RoundService(RoundThread roundThread, GameService gameService) {
        roundRepository = new RoundRepository(roundThread);
        this.gameService = gameService;
    }

    /**
     * puyoLogic을 재설정하는 함수이다.
     */
    public void makePuyoLogic() {
        var puyoLogic = roundRepository.getPuyoLogic();

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
        System.out.println("Running round service");
//        var playerThread1P = roundThreadService.getPlayerThread1P();
//        var playerThread2P = roundThreadService.getPlayerThread2P();
//        playerThread1P.start();
//        playerThread2P.start();

        countThreeSecond(); // 3초 뒤 시작
        changeRoundChangeToggle(); // 게임 진행중에는 true여야 함

        // 1 round TODO: true되면 다음 라운드로 진행
        while(roundRepository.getRoundChangeToggle()) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace(); // sleep 실패
            }
        }

        countThreeSecond(); // 3초 뒤 시작
        changeRoundChangeToggle(); // 게임 진행중에는 true여야 함

//        playerThread1P = new PlayerThread(gameService, gameService.getGameGround1P().getService(), scorePanel,1);
//        playerThread2P = new PlayerThread(gameService, gameService.getGameGround2P().getService(), scorePanel,2);
//        playerThread1P.start();
//        playerThread2P.start();

        // 2 round TODO: true되면 다음 라운드로 진행ㅉ
        while(roundRepository.getRoundChangeToggle()) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace(); // sleep 실패
            }
        }

        var winCount1P = roundRepository.getWinCount1P();
        var winCount2P = roundRepository.getWinCount2P();

        if(winCount1P == 2) {
            //System.out.println("1P Win");
            gameService.closeGamePanel();
            // TODO: 메뉴 화면으로 복귀
        }
        else if(winCount2P == 2) {
            //System.out.println("2P Win");
            gameService.closeGamePanel();
            // TODO: 메뉴 화면으로 복귀
        }

        countThreeSecond(); // 3초 뒤 시작
        changeRoundChangeToggle(); // 게임 진행중에는 true여야 함

//        playerThread1P = new PlayerThread(gameService, gameService.getGameGround1P().getService(), scorePanel, 1);
//        playerThread2P = new PlayerThread(gameService, gameService.getGameGround2P().getService(), scorePanel, 2);
//        playerThread1P.start();
//        playerThread2P.start();

        winCount1P = roundRepository.getWinCount1P();
        winCount2P = roundRepository.getWinCount2P();
        if(winCount1P == 2) {
            //System.out.println("1P Win");
            gameService.closeGamePanel();
            // TODO: 메뉴 화면으로 복귀
        }
        else if(winCount2P == 2) {
            //System.out.println("2P Win");
            gameService.closeGamePanel();
            // TODO: 메뉴 화면으로 복귀
        }
    }

    private boolean changeRoundChangeToggle() {return roundRepository.changeRoundChangeToggle();}
}
