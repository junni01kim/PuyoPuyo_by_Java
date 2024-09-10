package puyopuyo.round;

import puyopuyo.game.GameService;
import puyopuyo.map.MapService;

public class RoundThread extends Thread{
    private final RoundService roundService;

    public RoundThread(int iAm, GameService gameService, MapService mapService) {
        roundService = new RoundService(iAm, this, gameService, mapService);
        roundService.setRound();
    }

    // TODO: 위치 조정 필요
    public void setGarbagePuyo(int plusScore) {
        roundService.setGarbagePuyo(plusScore);
    }

    // TODO: 위치 조정 필요
    public void changeOneWin() {
        roundService.changeOneWin();
    }

    // TODO: 위치 조정 필요
    public void changeEndFlag() {
        roundService.changeEndFlag();
    }

    @Override
    public void run() {
        roundService.run();
    }
}
