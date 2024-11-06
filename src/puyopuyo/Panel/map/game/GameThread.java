package puyopuyo.Panel.map.game;

import puyopuyo.Panel.map.MapService;

public class GameThread extends Thread {
    private final GameService gameService;

    public GameThread(MapService mapService) {
        gameService = new GameService(this, mapService); // TODO: 에러나면 여기가 원인
        gameService.makePuyoLogic();
    }

    @Override
    public void run() {
        super.run();

        gameService.run();
    }
}
