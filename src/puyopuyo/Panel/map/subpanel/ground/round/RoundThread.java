package puyopuyo.Panel.map.subpanel.ground.round;

import puyopuyo.Panel.map.game.GameService;
import puyopuyo.Panel.map.MapService;

public class RoundThread extends Thread{
    private final RoundService roundService;

    public RoundThread(int iAm, GameService gameService) {
        roundService = new RoundService(iAm, this, gameService);
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
    public void setEnd(boolean state) {
        roundService.setEnd(state);
    }

    @Override
    public void run() {
        roundService.run();
    }
}
