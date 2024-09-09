package puyopuyo.round;

import puyopuyo.game.GameService;

public class RoundThread extends Thread {
    private final RoundService roundService;

    public RoundThread(GameService gameService) {
        roundService = new RoundService(this, gameService); // TODO: 에러나면 여기가 원인
        roundService.makePuyoLogic();
    }

    @Override
    public void run() {
        super.run();

        roundService.run();
    }
}
