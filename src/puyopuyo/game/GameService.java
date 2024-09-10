package puyopuyo.game;

import puyopuyo.map.MapService;

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
        System.out.println("Running round service");
//        var playerThread1P = roundThreadService.getPlayerThread1P();
//        var playerThread2P = roundThreadService.getPlayerThread2P();
//        playerThread1P.start();
//        playerThread2P.start();

        countThreeSecond(); // 3초 뒤 시작
        changeRoundChangeToggle(); // 게임 진행중에는 true여야 함

        // 1 round TODO: true되면 다음 라운드로 진행
        while(gameRepository.getRoundChangeToggle()) {
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
        while(gameRepository.getRoundChangeToggle()) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace(); // sleep 실패
            }
        }

        var winCount1P = gameRepository.getWinCount1P();
        var winCount2P = gameRepository.getWinCount2P();

        if(winCount1P == 2) {
            //System.out.println("1P Win");
            mapService.closeGamePanel();
            // TODO: 메뉴 화면으로 복귀
        }
        else if(winCount2P == 2) {
            //System.out.println("2P Win");
            mapService.closeGamePanel();
            // TODO: 메뉴 화면으로 복귀
        }

        countThreeSecond(); // 3초 뒤 시작
        changeRoundChangeToggle(); // 게임 진행중에는 true여야 함

//        playerThread1P = new PlayerThread(gameService, gameService.getGameGround1P().getService(), scorePanel, 1);
//        playerThread2P = new PlayerThread(gameService, gameService.getGameGround2P().getService(), scorePanel, 2);
//        playerThread1P.start();
//        playerThread2P.start();

        winCount1P = gameRepository.getWinCount1P();
        winCount2P = gameRepository.getWinCount2P();
        if(winCount1P == 2) {
            //System.out.println("1P Win");
            mapService.closeGamePanel();
            // TODO: 메뉴 화면으로 복귀
        }
        else if(winCount2P == 2) {
            //System.out.println("2P Win");
            mapService.closeGamePanel();
            // TODO: 메뉴 화면으로 복귀
        }
    }

    private boolean changeRoundChangeToggle() {return gameRepository.changeRoundChangeToggle();}
}
